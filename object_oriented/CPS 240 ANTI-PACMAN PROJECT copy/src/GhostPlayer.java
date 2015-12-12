import java.awt.Color;

import javax.swing.ImageIcon;

/* super representation of Pacman */

// ghost player class

public class GhostPlayer extends CharacterObject {
  public GhostPlayer(int x, int y, Color theColor) {
    super(x, y, theColor);
  }
  
  @Override
  public String toString() { 
    return "GhostPlayer. X: " + this.x + "\tY: " + this.y;
  }
}
