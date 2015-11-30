import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* Game logic */

public class AntiPacman extends JPanel {

  


  public static final byte WALL = 1 << 0;
  public static final byte FREE = 1 << 1;
  public static final byte DOT = 1 << 2;
  public static final byte ENERGIZER = 1 << 3;
  public static final byte PLAYERGHOST = 1 << 4;
  public static final byte PACMEN = 1 << 5;
  public static final byte OUT = 1 << 6;
  
  private static final String SPACE = "     ";
  private final Queue<ThePacmen> pacPenQ = new LinkedList<ThePacmen>();
  private final int board[][] = getBoard();
  private final ThePacmen[] thePacmen = new ThePacmen[4];
  private final JLabel antiPacmanScoreLabel;
  private final JLabel pacmanLivesLabel;
  private final JLabel ghostModeLabel;
  private final JLabel nextGhostReleaseLabel;
  private Mode gameMode;
  private long modeStart;
  private ThePacmen pacOne, pacTwo, pacThree, pacFour;
  private GhostPlayer ghost;
  private Graphics2D theG;
  private LocationPoint pacReleasePoint;
  private LocationPoint pacSpawnPoint;
  private long ghostModeStart;
  private long hitEnergizerAt;
  private long pacReleasedAt;
  private long antiPacmanScore = 0;
  private int pacmanLives = 3;
  private boolean controlTouch;
  private boolean isChaseMode;
  private static final int SCALE = 20;
  private static final int PACMEN_SIZE = 15;
  private static final int GHOST_SIZE = 20;
  private static final int DOT_SIZE = 5;
  private static final int ENERGIZER_SIZE = DOT_SIZE * 2;
  private static final int TIME_CHASE = 5; //Seconds 10
  private static final int TIME_SCATTER = 7;
  private static final int TIME_FRIGHTENED = 10; 
  private static final int GHOST_RELEASE = 5;
  
  /** Constructor, initializes JPanel and board */
  public AntiPacman() {
    super();
    setSize(new Dimension(400, 400));
    setMinimumSize(new Dimension(400, 400));
    setFocusable(true);
    requestFocusInWindow();
    
    antiPacmanScoreLabel = new JLabel("Score: " + antiPacmanScore, JLabel.RIGHT);
    antiPacmanScoreLabel.setForeground(Color.white);
    add(antiPacmanScoreLabel);
    
    pacmanLivesLabel = new JLabel(SPACE + "Lives: " + pacmanLives, JLabel.LEFT);
    pacmanLivesLabel.setForeground(Color.WHITE);
    add(pacmanLivesLabel);
    
    ghostModeLabel = new JLabel(SPACE + "Normal", JLabel.LEFT);
    ghostModeLabel.setForeground(Color.WHITE);
    add(ghostModeLabel);
    
    nextGhostReleaseLabel = new JLabel(SPACE + "Ghost Release", JLabel.LEFT);
    nextGhostReleaseLabel.setForeground(Color.WHITE);
    add(nextGhostReleaseLabel);
    
    gameMode = Mode.CHASE;
    modeStart = System.currentTimeMillis();
    
    initializeVariables();
    addKeyListener(new ControlListener());
    start();
  }
  
  /** Start the other threads */
  public void start() { 
    new Thread(new GameLogic()).start();
  }
  
  /** Initalizes pacman and ghosts start locations */
  public void initializeVariables() {
    LocationPoint pacmenStart = null;
    
    for (int i = 0; i < board.length; i++) {
      for (int y = 0; y < board[i].length; y++) {
        
    	  
    	  // PlayerGhost starting location
        if (board[i][y] == PLAYERGHOST) {
        	
          ghost = new GhostPlayer(y, i, Color.WHITE);                    
          
        }
        
        // Pacmen starting location
        else if (board[i][y] == PACMEN) {
          pacmenStart = new LocationPoint(y, i);
        }
      }
    }
    
    final int x = (int) pacmenStart.getX();
    final int y = (int) pacmenStart.getY();
    
    // Left Inside
    pacOne = new ThePacmen(Color.YELLOW, x - 2, y, board, gameMode);
    board[pacOne.getY()][pacOne.getX()] = PACMEN;
    thePacmen[0] = pacOne;
    pacPenQ.add(pacOne);
    
    // Middle inside
    pacTwo = new ThePacmen(Color.YELLOW, x, y, board, gameMode);
    board[pacTwo.getY()][pacTwo.getX()] = PACMEN;
    thePacmen[1] = pacTwo;
    pacPenQ.add(pacTwo);
    pacSpawnPoint = new LocationPoint(pacTwo.getX(), pacTwo.getY());
    
    // Right inside
    pacThree = new ThePacmen(Color.YELLOW, x + 2, y, board, gameMode);
    board[pacThree.getY()][pacThree.getX()] = PACMEN;
    thePacmen[2] = pacThree;
    pacPenQ.add(pacThree);
    
    // Outside
    pacFour = new ThePacmen(Color.YELLOW, x, y - 2, board, gameMode);
    board[pacFour.getY()][pacFour.getX()] = PACMEN;
    thePacmen[3] = pacFour;
    pacPenQ.add(pacFour);
    
    pacReleasePoint = new LocationPoint(pacFour.getX(), pacFour.getY());
    pacReleasedAt = System.currentTimeMillis();
    pacFour.release();
    
    isChaseMode = true;
    ghostModeStart = System.currentTimeMillis();
  }
  
  
  /** Main method, creates frame and adds game to it */
  public static void main(String[] args) {
    JFrame theFrame = new JFrame("Anti-Pacman");
    theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    theFrame.setSize(500, 500);
    
    theFrame.add(new AntiPacman());
    theFrame.setVisible(true);
  }
  
  /**
   * Returns a int representing the item that the parameter's item will hit based on the parameter item's direction
   */
  private int getNextMoveObject(final CharacterObject movingItem, final CharacterObject.Direction theDirection) {
    try {
      switch (theDirection) {
        case UP:
          return board[movingItem.getY() - 1][movingItem.getX()];
          
        case DOWN:
          return board[movingItem.getY() + 1][movingItem.getX()];
          
        case LEFT:
          return board[movingItem.getY()][movingItem.getX() - 1];
          
        case RIGHT:
          return board[movingItem.getY()][movingItem.getX() + 1];
          
        default:
          return Integer.MAX_VALUE;
      }
    } catch (Exception e) {
      return OUT;
    }
  }
  
  /** Return int of item in that Point */
  public int getItemAtPoint(final LocationPoint thePoint) {
    return board[(int) thePoint.getY()][(int) thePoint.getX()];
  }
  
  /** Moves the item parameter based on the direction parameter */
  public void moveToObject(final GhostPlayer ghost, final CharacterObject.Direction theDirection) {
    controlTouch = false;
    
    if (theDirection == null) {
      return;
    }
    ghost.setFacingDirection(theDirection);
    final int itemInNextDirection = getNextMoveObject(ghost, theDirection);
    
    if (itemInNextDirection == OUT) {
      return;
    }
    
    if (itemInNextDirection == PACMEN) {
      if (isFrightened()) {
        eatGhost(theDirection);
      }
      else {
        hitPacman();
      }
      return;
    }
    
    if (itemInNextDirection == DOT) { 
      antiPacmanScore += 10;
    }
    
    if (itemInNextDirection != WALL) {
      board[ghost.getY()][ghost.getX()] = FREE;
      ghost.move(theDirection);
    }
    
    if (itemInNextDirection == ENERGIZER) {
      hitEnergizerAt = System.currentTimeMillis();
      antiPacmanScore += 100;
      gameMode = Mode.FRIGHTENED;
      modeStart = System.currentTimeMillis();
    }
    
    board[ghost.getY()][ghost.getX()] = PLAYERGHOST;
    
    updateLabels();
  }
  
  /** Gets eaten by the Ghost if it is not frightened mode, will eat ghost otherwise */
  private void eatGhost() { 
    if(gameMode != Mode.FRIGHTENED) { 
      hitPacman();
      return;
    }
    
    final LocationPoint pacmanOnGhostPoint = ghost.getPoint();
    for (ThePacmen pacman : thePacmen) {
    	
      if (pacman.getPoint().equals(pacmanOnGhostPoint)) {
        System.out.println("KILLED:\t" + ghost.toString());
        antiPacmanScore -= 200;
        ghost.returnToStartPosition();
        
        updateBoard(ghost.getPoint(), FREE);
        updateBoard(ghost.getPoint(), FREE);
        
        pacmanLives--;
        updateLabels();
        
        pacmanRespawn(pacman);
      }
    }    
  }
  
  /** If Pacman eats a ghost on frightened mode */
  private void eatGhost(final CharacterObject.Direction theDirection) {
    final LocationPoint pacmanOriginalPoint = ghost.getPoint();
    ghost.move(theDirection);
    final LocationPoint pacmanOnGhostPoint = ghost.getPoint();
    
    for (int i = 0; i < thePacmen.length; i++) {
      if (thePacmen[i].getPoint().equals(pacmanOnGhostPoint)) {
        antiPacmanScore -= 200;
        ghost.returnToStartPosition();
      }
    }
    
    // Make Pacman's old location free
    updateBoard(pacmanOriginalPoint, FREE);
    
    // Set Pacman's new location
    updateBoard(pacmanOnGhostPoint, PACMEN);
  }
  
  /** Paint method, called by repaint() */
  public void paintComponent(Graphics g) {
    theG = (Graphics2D) g;
    releasePacmen();
    updateLabels();
    drawSquares();
  }
  
  /** Thread that has most of the game logic
    * Handles updated ghosts' board and BFA
    * and movements */
  private class GameLogic implements Runnable { 
    @Override
    public void run() { 
    	
      while(true)  {
    	eatGhost();
        hitPacman();
        
        if(getNextMoveObject(ghost, ghost.getDesiredDirection()) != WALL) {
            ghost.setFacingDirection(ghost.getDesiredDirection());
        }
        
        moveToObject(ghost, ghost.getFacingDirection());


        eatGhost();
        hitPacman();
        final int modeTime = (int) ((System.currentTimeMillis() - modeStart)/1000);
        
        switch(gameMode) { 
          case FRIGHTENED:
            if(modeTime > TIME_FRIGHTENED) { 
            gameMode = Mode.CHASE;
            modeStart = System.currentTimeMillis();
          }
            break;
            
          case CHASE:
            if(modeTime > TIME_CHASE) { 
            gameMode = Mode.SCATTER;
            modeStart = System.currentTimeMillis();
          }
            break;
            
          case SCATTER:
            if(modeTime > TIME_SCATTER) {
            gameMode = Mode.CHASE;
            modeStart = System.currentTimeMillis();
          }
            break;
            
          default:
            break;
        }
        
        for(ThePacmen thePacmen : thePacmen) { 
          if(thePacmen.isReleased()) { 
            thePacmen.updateBoard(board);
            setValue(thePacmen.getPoint(), (int) (getItemAtPoint(thePacmen.getPoint()) & (~PACMEN)));
            thePacmen.move(thePacmen.getPoint(), gameMode);
            updateBoard(thePacmen.getPoint(), PACMEN);
          }
        }
        eatGhost();
        hitPacman();
        try { 
          Thread.sleep(100);
        }
        catch(Exception e) { 
          e.printStackTrace();
        }
        repaint();
      }
    }
  };
  
  /** Sets the value of the point to the value in the board[][] */
  private void setValue(final LocationPoint thePoint, final int theValue) { 
    board[thePoint.getY()][thePoint.getX()] = theValue;
  }
  
  /** 
   * If Pacman hits a ghost and it's not on frightened mode Move pacman back to initial position, decrement lives
   */
  public void hitPacman() {
    if(gameMode == Mode.FRIGHTENED) { 
      return;
    }
    final LocationPoint pacmanOnGhostPoint = ghost.getPoint();
    for (ThePacmen pacman : thePacmen) {
      if (pacman.getPoint().equals(pacmanOnGhostPoint)) {
    	  
    	updateBoard(pacman.getPoint(), PLAYERGHOST);
    	pacman.returnToStartPosition();
    	updateBoard(pacman.getPoint(), FREE);
                 
        updateLabels();
        return;
      }
    }   
  }
  
  /** Draws the entire board, including ghosts and pacman */
  public void drawSquares() {
    for (int i = 0; i < board.length; i++) {
      for (int y = 0; y < board[i].length; y++) {
        switch (board[i][y]) {
          case WALL:
            theG.setColor(Color.BLUE);
            theG.fillRect(y * SCALE, i * SCALE, SCALE, SCALE);
            break;
            
          case FREE:
            drawBlackSquare(i, y);
            break;
            
          case DOT:
            drawBlackSquare(i, y);
            theG.setColor(Color.WHITE);
            theG.fillOval(y * SCALE + 5, i * SCALE + 7, DOT_SIZE, DOT_SIZE);
            break;
            
          case ENERGIZER:
            drawBlackSquare(i, y);
            theG.setColor(Color.WHITE);
            theG.fillOval(y * SCALE + 5, i * SCALE + 7, ENERGIZER_SIZE, ENERGIZER_SIZE);
            break;
            
          case PLAYERGHOST:
        	drawGhost(ghost);
            break;
            
          case PACMEN:
              drawBlackSquare(i, y);
              theG.setColor(Color.YELLOW);
              theG.fillOval(y * SCALE, i * SCALE, PACMEN_SIZE, PACMEN_SIZE);
            break;
            
          default:
            drawBlackSquare(i, y);
            break;
        }
      }
    }
    for (int i = 0; i < thePacmen.length; i++) {
      drawPacmen(thePacmen[i]);
    }
  }
  
  /**
   * Listens to keyboard events, sets the facing direction based on those events Then moves the item in regards to the
   * facing direction
   */
  private class ControlListener implements KeyListener {
    public void keyPressed(KeyEvent e) {
      
      controlTouch = true;
      
      // Direction item will move in
      CharacterObject.Direction movingDirection;
      
      // Current location becomes nothing for Pacman
      board[ghost.getY()][ghost.getX()] = FREE;
      
      switch (e.getKeyCode()) {
        // LEFT
        case KeyEvent.VK_LEFT:
          movingDirection = CharacterObject.Direction.LEFT;
          break;
          
          // RIGHT
        case KeyEvent.VK_RIGHT:
          movingDirection = CharacterObject.Direction.RIGHT;
          break;
          
          // UP
        case KeyEvent.VK_UP:
          movingDirection = CharacterObject.Direction.UP;
          break;
          
          // DOWN
        case KeyEvent.VK_DOWN:
          movingDirection = CharacterObject.Direction.DOWN;
          break;
          
        default:
          movingDirection = null;
          break;
      }
      ghost.setDesiredDirection(movingDirection);
      //moveToObject(pacman, movingDirection);
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
   
  }
  

  /**
   * If it is time, removes next ghost from pen and places ghost at initial pacReleasePoint
   */
  private void releasePacmen() {
    if (pacPenQ.size() != 0) {
      if ((System.currentTimeMillis() - pacReleasedAt) / 1000 == GHOST_RELEASE)
        pacsLeavePen(pacPenQ.remove());
    }
  }
  
  /**
   * Removes ghost from its position on the board, updates ghosts coordinates to that of initial point, updates board
   * to that value
   */
  private void pacsLeavePen(final ThePacmen thePacmen) {
    board[thePacmen.getY()][thePacmen.getX()] = FREE;
    thePacmen.setX((int) pacReleasePoint.getX());
    thePacmen.setY((int) pacReleasePoint.getY());
    board[thePacmen.getY()][thePacmen.getX()] = (int) (PACMEN | board[thePacmen.getY()][thePacmen.getX()]);
    pacReleasedAt = System.currentTimeMillis();
    thePacmen.release();
  }
  
  /** Moves Ghost back to pen */
  public void pacmanRespawn(final ThePacmen theEaten) {
    if(pacPenQ.size() == 0) { 
      theEaten.setPoint(pacSpawnPoint);
    }
    else { 
      theEaten.returnToStartPosition();
    }
    pacPenQ.add(theEaten);
    updateBoard(theEaten.getPoint(), PACMEN);
    pacReleasedAt = System.currentTimeMillis();
    theEaten.setInPen();
  }
  
  /** Update board location with that Pacman type */
  public void updateBoard(final LocationPoint thePoint, final int theItem) {
    if(thePoint.getX() >= (board.length) || thePoint.getY() >= (board[0].length) || 
       thePoint.getX() < 0 || thePoint.getY() < 0) {
      System.out.println("UpdateBoard\t" + thePoint);
      return;
    }
    board[thePoint.getY()][thePoint.getX()] = (int) (theItem | board[thePoint.getY()][thePoint.getX()]);
  }
  
  /** @return true if chase mode */
  public boolean isChaseMode() {
    return gameMode == Mode.CHASE;
  }
  
  /** Returns true if frightened */
  private boolean isFrightened() { 
    return gameMode == Mode.FRIGHTENED;
  }
  
  /** Draws the Pacmen in the parameter */
  private void drawPacmen(ThePacmen thePacmen) {
		theG.setColor(Color.YELLOW);
        theG.fillOval(thePacmen.getX() * SCALE, thePacmen.getY() * SCALE, PACMEN_SIZE, PACMEN_SIZE);
  }
  
  private void drawGhost(GhostPlayer ghost) {

    theG.setColor(Color.WHITE);
    theG.fillRect(ghost.getX() * SCALE, ghost.getY() * SCALE, GHOST_SIZE, GHOST_SIZE);
	  
  }
  
  
  /** Draws a black square at X and Y */
  private void drawBlackSquare(int x, int y) {
    theG.setColor(Color.BLACK);
    theG.fillRect(y * SCALE, x * SCALE, SCALE, SCALE);
  }
  
  /** Returns the board as a 2D array */
  public static int[][] getBoard() {
	  
	//using supporting graphics jar found online, initialize int 2d array for board getter
    final int[][] theMap = pacMap.pacMap.fnl.PacmanSupport.getMap();
    final int[][] theMapint = new int[theMap.length][theMap[0].length];
    
    for(int i = 0; i < theMap.length; i++) { 
      for(int y = 0; y < theMap[i].length; y++) { 
    	  
    	
        theMapint[(int)i][(int)y] = (byte) (1 << theMap[i][y]);
        
        
      }
    }
    return theMapint;
  }
  
  /** Prints the board as a 2D array */
  public void printBoard() {
    for (int y = 0; y < board.length; y++) {
      for (int i = 0; i < board[y].length; i++) {
        System.out.print(board[y][i] + " ");
      }
      System.out.println();
    }
  }
  
  /** Updates the score, num lives, ghost pen release countdown, and ghost mode labels */
  private void updateLabels() {
    antiPacmanScoreLabel.setText("Score: " + antiPacmanScore);
    pacmanLivesLabel.setText(SPACE + "Lives: " + pacmanLives + "     ");
    
    final int timeToRelease = (int) GHOST_RELEASE - (int) ((System.currentTimeMillis() - pacReleasedAt) / 1000);
    if (pacPenQ.size() >= 0 && timeToRelease >= 0) {
      nextGhostReleaseLabel.setText(SPACE + "Ghost Release: " + timeToRelease);
    } 
    else if (pacPenQ.size() < 0 || (GHOST_RELEASE - ((System.currentTimeMillis() - pacReleasedAt) / 1000)) < 0) {
      nextGhostReleaseLabel.setText(SPACE + "Ghost Release: N/A");
    }
    
    final int currentTime = (int) ((System.currentTimeMillis() - modeStart) / 1000);
    if (gameMode == Mode.FRIGHTENED) {
      ghostModeLabel.setText(SPACE + "Frightened Mode: " + (TIME_FRIGHTENED - currentTime));
    }
    else if (gameMode == Mode.CHASE) {
      ghostModeLabel.setText(SPACE + "Chase Mode: " + (TIME_CHASE - currentTime));
    }
    else if(gameMode == Mode.SCATTER) {
      ghostModeLabel.setText(SPACE + "Scatter Mode: " + (TIME_SCATTER - currentTime));
    }
  }
}