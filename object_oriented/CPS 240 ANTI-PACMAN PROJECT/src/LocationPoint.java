public class LocationPoint { 
  private int x;
  private int y;
  
  public LocationPoint(final int x, final int y) { 
    this.x = x;
    this.y = y;
  }
  
  public LocationPoint() { 
    this.x = 0;
    this.y = 0;
  }
  
  public int getX() { 
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public void setX(final int newX) { 
    this.x = newX;
  }
  
  public void setY(final int newY) { 
    this.y = newY;
  }
  
//  public void addX(final int addX) { 
//    this.x += addX;
//  }
//  
//  public void addY(final int addY) { 
//    this.y += addY;
//  }
  
  @Override
  public String toString() { 
    return "X: " + this.x + "\tY: " + y;
  }
  
  public boolean equals(final LocationPoint theOtherPoint) { 
    return (this.x == theOtherPoint.getX() && this.y == theOtherPoint.getY());
  }
}