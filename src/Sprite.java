import java.awt.Graphics;

public interface Sprite {
    final int WIDTH=1280, HEIGHT=900;
    public void draw(Graphics g);
    //public void move();
    public int getY();
    public int getX();
}


