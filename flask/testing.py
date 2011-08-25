# -*- coding: utf-8 -*-
"""
    flask.testing
    ~~~~~~~~~~~~~

    Implements test support helpers.  This module is lazily imported
    and usually not used in production environments.

    :copyright: (c) 2010 by Armin Ronacher.
    :license: BSD, see LICENSE for more details.
"""

from contextlib import contextmanager
from werkzeug.test import Client, EnvironBuilder
from flask import _request_ctx_stack


class FlaskClient(Client):
    """Works like a regular Werkzeug test client but has some knowledge about
    how Flask works to defer the cleanup of the request context stack to the
    end of a with body when used in a with statement.  For general information
    about how to use this class refer to :class:`werkzeug.test.Client`.

    Basic usage is outlined in the :ref:`testing` chapter.
    """

    preserve_context = context_preserved = False

    @contextmanager
    def session_transaction(self, *args, **kwargs):
        """When used in combination with a with statement this opens a
        session transaction.  This can be used to modify the session that
        the test client uses.  Once the with block is left the session is
        stored back.

            with client.session_transaction() as session:
                session['value'] = 42

        Internally this is implemented by going through a temporary test
        request context and since session handling could depend on
        request variables this function accepts the same arguments as
        :meth:`~flask.Flask.test_request_context` which are directly
        passed through.
        """
        app = self.application
        environ_overrides = kwargs.pop('environ_overrides', {})
        if self.cookie_jar is not None:
            self.cookie_jar.inject_wsgi(environ_overrides)
        outer_reqctx = _request_ctx_stack.top
        with app.test_request_context(*args, **kwargs) as c:
            sess = app.open_session(c.request)
            if sess is None:
                raise RuntimeError('Session backend did not open a session. '
                                   'Check the configuration')

            # Since we have to open a new request context for the session
            # handling we want to make sure that we hide out own context
            # from the caller.  By pushing the original request context
            # (or None) on top of this and popping it we get exactly that
            # behavior.  It's important to not use the push and pop
            # methods of the actual request context object since that would
            # mean that cleanup handlers are called
            _request_ctx_stack.push(outer_reqctx)
            try:
                yield sess
            finally:
                _request_ctx_stack.pop()

            resp = app.response_class()
            if not app.session_interface.is_null_session(sess):
                app.save_session(sess, resp)
            if self.cookie_jar is not None:
                headers = resp.get_wsgi_headers(c.request.environ)
                self.cookie_jar.extract_wsgi(c.request.environ, headers)

    def open(self, *args, **kwargs):
        if self.context_preserved:
            _request_ctx_stack.pop()
            self.context_preserved = False
        kwargs.setdefault('environ_overrides', {}) \
            ['flask._preserve_context'] = self.preserve_context

        as_tuple = kwargs.pop('as_tuple', False)
        buffered = kwargs.pop('buffered', False)
        follow_redirects = kwargs.pop('follow_redirects', False)

        builder = EnvironBuilder(*args, **kwargs)

        if self.application.config.get('SERVER_NAME'):
            server_name = self.application.config.get('SERVER_NAME')
            if ':' not in server_name:
                http_host, http_port = server_name, None
            else:
                http_host, http_port = server_name.split(':', 1)
            if builder.base_url == 'http://localhost/':
                # Default Generated Base URL
                if http_port != None:
                    builder.host = http_host + ':' + http_port
                else:
                    builder.host = http_host
        old = _request_ctx_stack.top
        try:
            return Client.open(self, builder,
                               as_tuple=as_tuple,
                               buffered=buffered,
                               follow_redirects=follow_redirects)
        finally:
            self.context_preserved = _request_ctx_stack.top is not old

    def __enter__(self):
        self.preserve_context = True
        return self

    def __exit__(self, exc_type, exc_value, tb):
        self.preserve_context = False
        if self.context_preserved:
            _request_ctx_stack.pop()
