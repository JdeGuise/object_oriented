//AntiPacman.java, CPS 240, John deGuise, Mi Gao, Emily Riley, 12/1/15


// AntiPacman game - This .java contains the game constructor, the projects main,
// 					 The while loop game logic, draw methods, 


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/* Game logic */

public class AntiPacman extends JPanel {

  private final byte board[][] = getBoard();
  private final Queue<ThePacmen> pacPenQ = new LinkedList<ThePacmen>();
  private final ThePacmen[] thePacmen = new ThePacmen[4];
  private ThePacmen pacOne, pacTwo, pacThree, pacFour;
  private GhostPlayer ghost;
  private Mode gameMode;
  private long modeStart;
  
  static JFrame theFrame = new JFrame("Anti-Pacman");
  private final JLabel antiPacmanScoreLabel;
  private final JLabel pacmanLivesLabel;
  private final JLabel ghostModeLabel;
  private final JLabel nextReleaseLabel;
  
  public static final byte WALL = 1 << 0;
  public static final byte FREE = 1 << 1;
  public static final byte DOT = 1 << 2;
  public static final byte ENERGIZER = 1 << 3;
  public static final byte PLAYERGHOST = 1 << 4;
  public static final byte PACMEN = 1 << 5;
  public static final byte OUT = 1 << 6;
  
  private Graphics2D Twodg;
  private LocationPoint pacReleasePoint;
  private LocationPoint pacSpawnPoint;
  
  private long ghostModeStart;
  private long hitEnergizerAt;
  private long pacReleasedAt;
  private long antiPacmanScore = 500;
  boolean controlTouch;
  private boolean isChaseMode;
  
  
  private static final int TIME_CHASE = 5; 
  private static final int TIME_SCATTER = 7;
  private static final int TIME_FRIGHTENED = 10; 
  private static final int PACMEN_SIZE = 15;
  private static final int GHOST_SIZE = 20;
  private static final int DOT_SIZE = 5;
  private static final int ENERGIZER_SIZE = DOT_SIZE * 2;
  private static final int PAC_RELEASE = 5;
  private static final String SPACE = "     ";
  private int pacmanLives = 12;
  private static final int SCALE = 20;


  
  // Constructor, initializes JPanels and board 
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
    
    nextReleaseLabel = new JLabel(SPACE + "Pacmen Release", JLabel.LEFT);
    nextReleaseLabel.setForeground(Color.WHITE);
    add(nextReleaseLabel);
    
    gameMode = Mode.CHASE;
    modeStart = System.currentTimeMillis();
    
    //call initVars method, add a keylistener for our player controls, call start() method
    initializeVariables();
    addKeyListener(new ControlListener());
    start();
  }
  
  //start other threads
  public void start() { 
    new Thread(new GameLogic()).start();
    
  }
  
  //initialize pacmen and ghost locations
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
    
    // First pacman
    pacOne = new ThePacmen(Color.RED, x - 2, y, board, gameMode);
    board[pacOne.getY()][pacOne.getX()] = PACMEN;
    thePacmen[0] = pacOne;
    pacPenQ.add(pacOne);
    
    // Second pacman
    pacTwo = new ThePacmen(Color.BLUE, x, y, board, gameMode);
    board[pacTwo.getY()][pacTwo.getX()] = PACMEN;
    thePacmen[1] = pacTwo;
    pacPenQ.add(pacTwo);
    pacSpawnPoint = new LocationPoint(pacTwo.getX(), pacTwo.getY());
    
    // Third pacman
    pacThree = new ThePacmen(Color.GREEN, x + 2, y, board, gameMode);
    board[pacThree.getY()][pacThree.getX()] = PACMEN;
    thePacmen[2] = pacThree;
    pacPenQ.add(pacThree);
    
    // Fourth pacman
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
  
  
  //main creates frame and adds game
  public static void main(String[] args) {
    theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    theFrame.setSize(500, 500);
    
    theFrame.add(new AntiPacman());

    theFrame.setVisible(true);
  }
  
  //Returns a int representing the item that the parameter's item will hit based on the parameter item's direction
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
  
  // Return int of item in that LocationPoint
  public int getItemAtPoint(final LocationPoint thePoint) {
    return board[(int) thePoint.getY()][(int) thePoint.getX()];
  }
  
  // Moves the item parameter based on the direction parameter
  public void moveToObject(final GhostPlayer ghost, final CharacterObject.Direction theDirection) {
    controlTouch = false;
    boolean Plausible = true;
    ghost.setFacingDirection(theDirection);
    final int itemInNextDirection = getNextMoveObject(ghost, theDirection);
    byte temp = FREE;
    
    
    //if forecasted item is a wall, we can't move there.
    if (itemInNextDirection == WALL) {
    	Plausible = false;
    }

    //if theDirection is null or OUT (repping our left and right warp spots), return;
    if (theDirection == null || itemInNextDirection == OUT) {
      return;
    }


    if(Plausible){
    	//track old location for replacing items
        int oldY, oldX;
        oldY = ghost.getY();
        oldX = ghost.getX();
        
        if (itemInNextDirection == DOT || itemInNextDirection == ENERGIZER || itemInNextDirection == FREE){
        	
        	//store the temp value of the location, move our ghost player, and then put the temp value back
        	temp = (byte) itemInNextDirection;
  	        board[oldY][oldX] = temp;
  	        ghost.move(theDirection);

  	        board[ghost.getY()][ghost.getX()] = PLAYERGHOST;

        }
        
	    if (itemInNextDirection == PACMEN) {
	  	  //if forecasted item is an enemy, checks game condition and either eats or is eaten and return;
	  	  if (isFrightened()) {
	            eatGhost(theDirection);
	      }
	      else {
	            hitPacman();
	      }
	      return;
	      
	    }
    }
   
    //update labels
    updateLabels();
  }
  
  //special move circumstance for ThePacmen
  public void moveToObject(final ThePacmen thePacmen, final CharacterObject.Direction theDirection) {
	    
	    controlTouch = false;
	    thePacmen.setFacingDirection(theDirection);
	    final int itemInNextDirection = getNextMoveObject(thePacmen, theDirection);
	    
	    //if direction is null or warp zone for item direction, return;
	    if (theDirection == null || itemInNextDirection == OUT || itemInNextDirection == PACMEN || itemInNextDirection == WALL) {
	      return;
	    }

	    
	    //if AI sees our player, checks game mode, either eats or is eaten
	    if (itemInNextDirection == PLAYERGHOST){
	    	if (isFrightened()) {
	            eatGhost(theDirection);
	          }
	          else {
	            hitPacman();
	          }
	          return;
	    }
	    
	    //if it sees a dot, eats the dot and lowers score
	    if (itemInNextDirection == DOT) { 
	      antiPacmanScore -= 5;
	    }
	    
	    //as long as item != wall, pacmen can move and the spot will be freed up
	    if (itemInNextDirection != WALL) {
	      board[thePacmen.getY()][thePacmen.getX()] = FREE;
	      thePacmen.move(theDirection);
	    }
	    
	    //handling pacmen eating energizers
	    if (itemInNextDirection == ENERGIZER) {
	      hitEnergizerAt = System.currentTimeMillis();
	      antiPacmanScore -= 20;
	      gameMode = Mode.FRIGHTENED;
	      modeStart = System.currentTimeMillis();
	    }
	    
	    //set board location for pacmen and update labels
	    board[thePacmen.getY()][thePacmen.getX()] = PACMEN;
	    
	    updateLabels();
	  }
  
  //ghost eats if it is not frightened mode, ghost is eaten otherwise
  private void eatGhost() { 
    if(gameMode != Mode.FRIGHTENED) { 
      hitPacman();
      return;
    }
    
    //find ghost point
    final LocationPoint pacmanOnGhostPoint = ghost.getPoint();
    for (ThePacmen pacman : thePacmen) {
    	
      //check if any of the pacmen are on top of our ghost
      if (pacman.getPoint().equals(pacmanOnGhostPoint)) {
        System.out.println("KILLED:\t" + ghost.toString());
        antiPacmanScore += 200;
        //if so, killed and back to pen
        
        updateBoard(ghost.getPoint(), FREE);
        
        ghost.returnToStartPosition();

        updateBoard(ghost.getPoint(), PLAYERGHOST);
        
        updateLabels();
        
      }
    }    
  }
  
  // If a ghost is on frightened mode and eaten by a pacmen*/
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
  
  //Paint method, called by repaint()
  public void paintComponent(Graphics g) {
    Twodg = (Graphics2D) g;
    releasePacmen();
    updateLabels();
	drawSquares();

  }
  
  /* Thread that has most of the game logic
     Handles updated ghosts' board and BFA
     and movements */
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
        moveToObject(pacOne, pacOne.getFacingDirection());
        moveToObject(pacTwo, pacTwo.getFacingDirection());
        moveToObject(pacThree, pacThree.getFacingDirection());
        moveToObject(pacFour, pacFour.getFacingDirection());



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
        if(antiPacmanScore <= 0){
        	theFrame.dispose();
        	System.out.println("Game Over! Score depleted. Final Score: " + antiPacmanScore);

        	System.exit(0);
        }
        if(pacmanLives <= 0){
        	theFrame.dispose();
        	System.out.println("You win! Pacmen lives depleted. Final Score: " + antiPacmanScore);
        	
        	System.exit(0);
        }
        
      }

 
    }
    
  };
  
  // Sets the value of the point to the value in the board[][]
  private void setValue(final LocationPoint thePoint, final int theValue) { 
    board[thePoint.getY()][thePoint.getX()] = (byte) theValue;
  }
  
  //If ghost hits pacman and it's not on frightened mode Move pacmen back to initial position, decrement lives
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
        pacmanLives--;
        updateLabels();
        return;
      }
    }   
  }
  
  // Draws the entire board, including ghosts and pacman 
  public void drawSquares() {
    for (int i = 0; i < board.length; i++) {
      for (int y = 0; y < board[i].length; y++) {
        switch (board[i][y]) {
          case WALL:
            Twodg.setColor(Color.BLUE);
            Twodg.fillRect(y * SCALE, i * SCALE, SCALE, SCALE);
            break;
            
          case FREE:
            drawBlackSquare(i, y);
            break;
            
          case DOT:
            drawBlackSquare(i, y);
            Twodg.setColor(Color.WHITE);
            Twodg.fillOval(y * SCALE + 5, i * SCALE + 7, DOT_SIZE, DOT_SIZE);
            break;
            
          case ENERGIZER:
            drawBlackSquare(i, y);
            Twodg.setColor(Color.WHITE);
            Twodg.fillOval(y * SCALE + 5, i * SCALE + 7, ENERGIZER_SIZE, ENERGIZER_SIZE);
            break;
            
          case PLAYERGHOST:
        	drawGhost(ghost);
            break;
            
          case PACMEN:
              drawBlackSquare(i, y);
              Twodg.fillOval(y * SCALE, i * SCALE, PACMEN_SIZE, PACMEN_SIZE);
            break;
            
          default:
            drawBlackSquare(i, y);
            break;
        }
      }
    }
    
    drawPacmen(thePacmen[0], Color.RED);
    drawPacmen(thePacmen[1], Color.YELLOW);
    drawPacmen(thePacmen[2], Color.BLUE);
    drawPacmen(thePacmen[3], Color.GREEN);


  }
  
  //Listens to keyboard events, sets the facing direction based on those events 
  //Then moves the item in regards to the facing direction
  private class ControlListener implements KeyListener {
    public void keyPressed(KeyEvent e) {
      
      controlTouch = true;
      
      // Direction item will move in
      CharacterObject.Direction movingDirection;
      
      if(board[ghost.getY()][ghost.getX()] == FREE){
          board[ghost.getY()][ghost.getX()] = FREE;
      }
      if(board[ghost.getY()][ghost.getX()] == DOT){
          board[ghost.getY()][ghost.getX()] = DOT;
      }
      if(board[ghost.getY()][ghost.getX()] == ENERGIZER){
          board[ghost.getY()][ghost.getX()] = ENERGIZER;

      }

      
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
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
   
  }
  

  //If it is time, removes next ghost from pen and places ghost at initial pacReleasePoint
  private void releasePacmen() {
    if (pacPenQ.size() != 0) {
      if ((System.currentTimeMillis() - pacReleasedAt) / 1000 == PAC_RELEASE)
        pacsLeavePen(pacPenQ.remove());
    }
  }
  
  //Removes ghost from its position on the board, updates ghosts coordinates to that of initial point, updates board to that value
  private void pacsLeavePen(final ThePacmen thePacmen) {
    board[thePacmen.getY()][thePacmen.getX()] = FREE;
    thePacmen.setX((int) pacReleasePoint.getX());
    thePacmen.setY((int) pacReleasePoint.getY());
    board[thePacmen.getY()][thePacmen.getX()] = (byte) (PACMEN | board[thePacmen.getY()][thePacmen.getX()]);
    pacReleasedAt = System.currentTimeMillis();
    thePacmen.release();
  }
  
  // Moves Pacmen back to pen 
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
  
  // Update board location with that Pacman type
  public void updateBoard(final LocationPoint thePoint, final int theItem) {
    if(thePoint.getX() >= (board.length) || thePoint.getY() >= (board[0].length) || 
       thePoint.getX() < 0 || thePoint.getY() < 0) {
      System.out.println("UpdateBoard\t" + thePoint);
      return;
    }
    board[thePoint.getY()][thePoint.getX()] = (byte) (theItem | board[thePoint.getY()][thePoint.getX()]);
  }
  
  // true if chase mode
  public boolean isChaseMode() {
    return gameMode == Mode.CHASE;
  }
  
  // true if frightened
  private boolean isFrightened() { 
    return gameMode == Mode.FRIGHTENED;
  }
  
  // draws pacmen AI within parameter
  private void drawPacmen(ThePacmen thePacmen, Color theColor) {
		Twodg.setColor(theColor);
        Twodg.fillOval(thePacmen.getX() * SCALE, thePacmen.getY() * SCALE, PACMEN_SIZE, PACMEN_SIZE);
  }
  
  // draw Ghost char within parameter
  private void drawGhost(GhostPlayer ghost) {

	  Twodg.setColor(Color.WHITE);
	  Twodg.fillRect(ghost.getX() * SCALE, ghost.getY() * SCALE, GHOST_SIZE, GHOST_SIZE);
    
  }
  
  
  // Draws a black square at X and Y
  private void drawBlackSquare(int x, int y) {
    Twodg.setColor(Color.BLACK);
    Twodg.fillRect(y * SCALE, x * SCALE, SCALE, SCALE);
  }
  
  // Returns the board as a 2D array
  public static byte[][] getBoard() {
	  
	//using supporting graphics jar, initialize int 2d array for board getter
    final int[][] theMap = cs015.fnl.PacmanSupport.SupportMap.getMap();
    final byte[][] theMapb = new byte[theMap.length][theMap[0].length];
    
    for(int i = 0; i < theMap.length; i++) { 
      for(int y = 0; y < theMap[i].length; y++) { 
    	  
    	
        theMapb[(int)i][(int)y] = (byte) (1 << theMap[i][y]);
        
        
      }
    }
    return theMapb;
  }
  
  // Prints the board as a 2D array
  public void printBoard() {
    for (int y = 0; y < board.length; y++) {
      for (int i = 0; i < board[y].length; i++) {
        System.out.print(board[y][i] + " ");
      }
      System.out.println();
    }
  }
  
  // Updates the score, num lives, pacpen release countdown, and ghost mode labels
  private void updateLabels() {
	  
    antiPacmanScoreLabel.setText("Score: " + antiPacmanScore);
    pacmanLivesLabel.setText(SPACE + "Lives: " + pacmanLives + "     ");
    
    final int timeToRelease = (int) PAC_RELEASE - (int) ((System.currentTimeMillis() - pacReleasedAt) / 1000);
    if (pacPenQ.size() >= 0 && timeToRelease >= 0) {
      nextReleaseLabel.setText(SPACE + "Pen Release: " + timeToRelease);
    } 
    else if (pacPenQ.size() < 0 || (PAC_RELEASE - ((System.currentTimeMillis() - pacReleasedAt) / 1000)) < 0) {
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