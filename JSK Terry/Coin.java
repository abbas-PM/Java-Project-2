import java.awt.Graphics;
import java.awt.Rectangle;


public class Coin extends GameObject{

    public static int w = 45; 
    public static int h = 45; 

    Texture tex = Main.getInstance();

    private Animation CoinFlip;

    public Coin(float x, float y, ID id, int health) {
        super(x, y, id, health);
        CoinFlip = new Animation(10, tex.block[2], tex.block[3], tex.block[4], tex.block[5]);
    }

    
    public void tick() {
        CoinFlip.runAnimation();
    }

    
    public void render(Graphics g) {
          CoinFlip.drawAnimation(g, (int)x - 50, (int)y - 25, 150, 150);
    }

    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, w, h);
    }
    
}
