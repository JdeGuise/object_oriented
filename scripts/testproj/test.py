from flask import Flask, Module, render_template


mod = Module(__name__)
mod2 = Module(__name__, 'testmod2')
mod3 = Module(__name__, name='somemod', subdomain='meh')


@app.after_request
def after_request(response):
    g.db.close()
    return response


@mod.route('/')
def index():
    return render_template('test/index.html')


@mod2.route('/')
def mod2_index():
    return render_template('testmod2/index.html')


@mod3.route('/')
def mod3_index():
    return render_template('something-else/index.html')


app = Flask(__name__)
app.register_module(mod)
app.register_module(mod2)


def handle_404(error):
    return 'Testing', 404
app.error_handlers[404] = handle_404
