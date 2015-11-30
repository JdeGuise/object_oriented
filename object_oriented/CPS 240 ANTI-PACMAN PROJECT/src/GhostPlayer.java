import java.awt.Color;

/* super representation of Pacman */

/* will we have to make a super for player ghosts? probably */

public class GhostPlayer extends CharacterObject {
  public GhostPlayer(int x, int y, Color theColor) {
    super(x, y, theColor);
  }
  
  @Override
  public String toString() { 
    return "GhostPlayer. X: " + this.x + "\tY: " + this.y;
  }
}
