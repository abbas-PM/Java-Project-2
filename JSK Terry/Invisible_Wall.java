import java.awt.Graphics;
import java.awt.Rectangle;

public class Invisible_Wall extends GameObject{
    
    public static int w = 20; 
    public static int h = 20; 

    public Invisible_Wall(float x, float y, ID id, int health) {
        super(x, y, id, health);
    }

    public void tick() {}
    public void render(Graphics g) {}

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, w, h);
    }
}
