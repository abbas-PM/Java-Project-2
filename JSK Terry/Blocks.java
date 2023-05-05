import java.awt.Graphics;
import java.awt.Rectangle;

public class Blocks extends GameObject{
    
    public static int w = 32; 
    public static int h = 32; 

    Texture tex = Main.getInstance();
    String type; 

    public Blocks(float x, float y, ID id, int health, String type) {
        super(x, y, id, health);
        this.type = type; 
    }

    public void tick() {}


    public void render(Graphics g) {
 
        if (type == "grass") g.drawImage(tex.block[0], (int)x-2, (int)y, w+4, h+5, null);
        if (type == "tile") g.drawImage(tex.block[1], (int)x-2, (int)y, w+4, h+5, null);
        if (type == "mushroom") g.drawImage(tex.block[12], (int)x-2, (int)y, w+4, h+5, null);
        if (type == "t2") g.drawImage(tex.block[13], (int)x-2, (int)y, w+4, h+5, null);
        if (type == "gb3") g.drawImage(tex.block[14], (int)x-2, (int)y, w+4, h+5, null);
        
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, w, h);
    }

}
