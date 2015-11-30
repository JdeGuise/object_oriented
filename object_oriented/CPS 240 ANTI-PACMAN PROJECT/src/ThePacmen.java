import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/* ghost/player */

public class ThePacmen extends CharacterObject {
  
  private static final LocationPoint[] corners = {new LocationPoint(1, 21), new LocationPoint(21, 1), new LocationPoint(21, 21), new LocationPoint(1, 1)};
  
  private static final int WALL = -1;
  private static final int UNEXPLORED = Integer.MAX_VALUE;
  private static final int PACMEN = 0;
  private static final int PLAYERGHOST = -2;
  private static final int CORNER = -3;
  
  private static final int SIZE = 23;
  
  private final int[][] theBoard = new int[SIZE][SIZE];
  private final LocationPoint aPoint = new LocationPoint();
  private final Queue<LocationPoint> prospectivePoints = new LinkedList<LocationPoint>();
  
  private final Color startColor;
  
  private Mode gameMode;
  private Direction randomDirection;
  
  private LocationPoint cornerPoint;
  private LocationPoint pacmanLoc = null;
  private LocationPoint cornerLoc = null;
  
  private final int randomDistFromPacman;
  
  private int lookFor;
  private long startPenTime;
  private boolean isReleased = false;
  
  /** Constructor */
  public ThePacmen(Color theColor, int x, int y, final int[][] pacmanGrid, final Mode gameMode) {
    super((int)x, (int)y, theColor);
    this.startColor = theColor;
    randomDistFromPacman = 0; //(int) theGenerator.nextInt(4);
    cornerPoint = getCorner(new LocationPoint(x, y));
    cornerLoc = cornerPoint;
    startPenTime = System.currentTimeMillis();
    this.updateBoard(pacmanGrid);
  }
  
  /** Updates the ghost's internal board with the ghost's custom values */
  public void updateBoard(final int[][] pacmanBoard) {
    
    final LocationPoint pacmanP = new LocationPoint();
    
    for (int i = 0; i < pacmanBoard.length; i++) {
      for (int y = 0; y < pacmanBoard[i].length; y++) {
        if (pacmanBoard[i][y] == AntiPacman.WALL) {
          this.theBoard[i][y] = this.WALL;
        }
        else if(pacmanBoard[i][y] == AntiPacman.PLAYERGHOST) { 
          this.theBoard[i][y] = this.PACMEN;
          pacmanP.setX(i);
          pacmanP.setY(y);
        }
        else {
          this.theBoard[i][y] = this.UNEXPLORED;
        }
      }
    }
    this.theBoard[this.y][this.x] = PACMEN;
    this.theBoard[cornerPoint.getY()][cornerPoint.getX()] = CORNER;
    
    reassignPacman(pacmanP);
    
    aPoint.setX(x);
    aPoint.setY(y);
  }
  
  /** Re-assigns pacman to a point a random distance from Pacman's actual location
    * Use is so that all the ghosts don't go for exactly Pacman */
  private void reassignPacman(final LocationPoint pacmanPoint){
    for(Direction theDirection : theDirections) { 
      final LocationPoint directionDistance = getProspectivePoint(pacmanPoint, theDirection, randomDistFromPacman);
      if(itemAtPoint(directionDistance) != WALL) { 
        this.theBoard[pacmanPoint.getX()][pacmanPoint.getY()] = UNEXPLORED;
        this.theBoard[directionDistance.getX()][directionDistance.getY()] = PLAYERGHOST;
        return;
      }
    }
  }
  
  /** Move and change colors according to gameMode */
  public void move(final LocationPoint ghostLocation, final Mode gameMode) { 
    this.gameMode = gameMode;
    
    if(gameMode == Mode.CHASE) {
      lookFor = CORNER;
      startBreadthFirstAlgorithm(ghostLocation);
    }
    
    else if(gameMode == Mode.FRIGHTENED) {
      lookFor = PLAYERGHOST;
      startBreadthFirstAlgorithm(ghostLocation);
    }
    
    else if(gameMode == Mode.SCATTER) {
      scatterMode(ghostLocation);
    }
  }
  
  /**Scatter mode: Move randomly */
  private void scatterMode(final LocationPoint currentLoc) { 
    
    if(randomDirection == null) { 
      randomDirection = super.getRandomDirection();
    }
    
    if(itemAtPoint(randomDirection, currentLoc) == WALL) { 
      randomDirection = super.getRandomDirection();
      scatterMode(currentLoc);
    }
    else {
      super.move(randomDirection);
    }
  }
 
  /**
   * Checks all 4 directions around the point If that item does not have my number and it's not a wall 
   * Add it to the queue and set its number to mine + 1
   */
  private void availableInDirections(final LocationPoint current) {
    for (Direction theDirection : theDirections) {
      final LocationPoint newPoint = getNewPoint(current, theDirection);
      
      if(newPoint.getX() >= theBoard.length || newPoint.getY() >= theBoard[0].length) { 
        //Skip
      }
      else if(newPoint.getX() < 0 || newPoint.getY() < 0) { 
        //Skip
      }
      
      else if(itemAtPoint(newPoint) == lookFor) {
        setValue(newPoint, itemAtPoint(current) + 1);
        
        if(gameMode == Mode.CHASE) {
          pacmanLoc = newPoint;
        }
        else if(gameMode == Mode.FRIGHTENED) { 
          cornerLoc = newPoint;
        }
        prospectivePoints.clear();
        return;
      }
      
      // If the value in that direction does not have the value I currently have
      // and is not a wall
      else if (itemAtPoint(newPoint) == UNEXPLORED) {
        // Add it to the queue
        prospectivePoints.add(newPoint);
        
        // Increment its value from mine
        setValue(newPoint, itemAtPoint(current) + 1);
      }
    }
  }
  
  /** Starts the BFA to find a point */
  private void startBreadthFirstAlgorithm(final LocationPoint startPoint) {
    if(startPoint.equals(cornerPoint)) {
      cornerPoint = getCorner(startPoint);
      return;
    }
    
    prospectivePoints.clear();
    prospectivePoints.add(startPoint);
    
    while (!prospectivePoints.isEmpty()) {
      availableInDirections(prospectivePoints.remove());
    }
    
    if(pacmanLoc != null) { 
      super.move(pacmanToGhost());
    }
  }
  
  /** Returns the direction to a get to the correct item. Item is based upon game mode */
  private Direction pacmanToGhost() { 
    int itemAtNow = 0;
    if(gameMode == Mode.CHASE) {
      itemAtNow = itemAtPoint(pacmanLoc);
    }
    else if(gameMode == Mode.FRIGHTENED) { 
      itemAtNow = itemAtPoint(cornerLoc);
    }
    
    LocationPoint workBackwards = null;
    if(gameMode == Mode.CHASE) {
      workBackwards = pacmanLoc;
    }
    else if(gameMode == Mode.FRIGHTENED) { 
      workBackwards = cornerLoc;
    }
    
    Direction moveDirection = null;
    
    while(itemAtNow > 0) { 
      for(Direction theDirection : theDirections) { 
        final LocationPoint newPoint = getNewPoint(workBackwards, theDirection);
        final int itemAtNewPoint = itemAtPoint(newPoint);
        if(itemAtNewPoint == (itemAtNow - 1) && itemAtNewPoint != this.WALL) {
          moveDirection = theDirection; 
          workBackwards = newPoint;
          itemAtNow = itemAtPoint(workBackwards);
        }
      }
    }
    return getOppositeDirection(moveDirection);
  }
  
  /** Updates the board at the given Point with the given number */
  private void updateBoard(final LocationPoint thePoint, final int num) {
    theBoard[thePoint.getY()][thePoint.getX()] = num;
  }
  
  /** Returns the item at a Point given the Point and its Direction */
  private int itemAtPoint(final Direction theDirection, final LocationPoint thePoint) {
    return itemAtPoint(super.getNewPoint(thePoint, theDirection));
  }
  
  /** Returns the item at a Point */
  private int itemAtPoint(final LocationPoint thePoint) {
    if(thePoint.getX() >= theBoard.length || thePoint.getY() >= theBoard[0].length
         ||  thePoint.getX() < 0 || thePoint.getY() < 0) { 
      if(gameMode == Mode.CHASE) {
        return CORNER;
      }
      else if(gameMode == Mode.FRIGHTENED) { 
        return PLAYERGHOST;
      }
      else { 
        return CORNER;
      }
    }
    return theBoard[(int) thePoint.getY()][(int) thePoint.getX()];
  }
  
  /** Set item at Point to a certain value */
  public void setValue(final LocationPoint thePoint, final int theNum) {
    theBoard[(int) thePoint.getY()][(int) thePoint.getX()] = (int) theNum;
  }
  
  /** Returns true if the ghost has been released from the pen */
  public boolean isReleased() { 
    return isReleased;
  }
  
  /** Release the ghost from the pen */
  public void release() { 
    isReleased = true;
  }
  
  /** Put the ghost in the pen */
  public void setInPen() { 
    isReleased = false;
  }
  
  /** @return timeTheGhost was in the pen */
  public long getPenTime() {
    return this.startPenTime;
  }
  
  /** Returns the Point for a random corner */
  private LocationPoint getCorner(final LocationPoint currentPosition) { 
    final LocationPoint newPoint = corners[theGenerator.nextInt(corners.length)];
    if(currentPosition.equals(newPoint)) {
      return getCorner(currentPosition);
    }
    return newPoint;
  }
  
  /** Print the board */
  public void printBoard() {
    final DecimalFormat df = new DecimalFormat("00");
    
    for (int i = 0; i < theBoard.length; i++) {
      for (int y = 0; y < theBoard[i].length; y++) {
        if (theBoard[i][y] == WALL) {
          System.out.print("XX\t");
        } 
        else if (theBoard[i][y] == UNEXPLORED) {
          System.out.print("--\t");
        } 
        else {
          System.out.print(df.format(theBoard[i][y]) + "\t");
        }
      }
      System.out.println();
    }
    System.out.println();
  }
  
  @Override
  public String toString() {
    return "PACMAN:\t" + name + "\tX: " + x + "\tY: " + y;
  }
}