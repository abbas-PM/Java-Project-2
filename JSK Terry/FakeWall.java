import java.awt.Graphics;
import java.awt.Rectangle;

public class FakeWall extends GameObject{

    public static int w = 32; 
    public static int h = 32; 

    Texture tex = Main.getInstance();

    public FakeWall(float x, float y, ID id, int health) {
        super(x, y, id, 0);
    }

    public void tick() {}


    public void render(Graphics g) {
        g.drawImage(tex.block[0], (int)x-2, (int)y, w+4, h+5, null);
        
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, w, h);
    }
    
}
