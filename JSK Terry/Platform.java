import java.awt.Graphics;
import java.awt.Rectangle;

public class Platform extends GameObject{
    public static int w = 224; 
    public static int h = 32; 

    Texture tex = Main.getInstance();
    Handler handler; 

    public Platform(float x, float y, ID id, Handler handler, int health){
        super(x, y, id, health);
        this.handler = handler; 
    }

    public void tick() {
        x += velX;
        
        try{
            
        for(int i = 0; i < handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Blocks){
                if(getBoundsLeft().intersects(tempObject.getBounds())) velX = 5; 
                if(getBoundsRight().intersects(tempObject.getBounds())) velX = -5;  
            }
        }
    }catch (IndexOutOfBoundsException | NullPointerException exception) {}
    }


    public void render(Graphics g) {

        g.drawImage(tex.block[6], (int)x, (int)y-18, w+8, h+50, null);

    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, w, h);
    }
    public Rectangle getBoundsLeft(){
        return new Rectangle((int)x-1,(int)y+5,5,h-11);
      }
      public Rectangle getBoundsRight(){
        return new Rectangle((int)x+w-4,(int)y+5,5,h-11);
      }   
}
    

