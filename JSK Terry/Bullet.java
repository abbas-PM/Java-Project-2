import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject{

    Handler handler;

    private int width = 60; 
    private int height = 30; 

    int direction; 

    Texture tex = Main.getInstance();

    public static boolean allowed = true; 

    public Bullet(float x, float y, ID id, Handler handler, int direction, int health){
        super(x, y, id, health);
        this.handler = handler; 
        this.direction = direction; 
    }
    public void tick() {

        velX = 15; 

        velX *= direction; 

        x += velX; 
        
        Collision();

        if (KeyInput.cooldown == 0) allowed = true; 
   
    }


    public void Collision(){

        try{

        for(int i = 0; i < handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Blocks || tempObject.getId() == ID.Invisible_Wall){

                if(getBounds().intersects(tempObject.getBounds())){
                    handler.removeObject(this);
                    allowed = true; 
                } 
            }
        }
    }catch(IndexOutOfBoundsException | NullPointerException exception) {}
    }

    public void render(Graphics g) {
        if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 0) g.drawImage(tex.block[8], (int)x - 13, (int)y - 10, 95, 95, null);
        if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 1) g.drawImage(tex.block[9], (int)x - 20, (int)y - 10, 95, 95, null);
        
    }

   
    public Rectangle getBounds() {
        return new Rectangle((int)(x+(width/2)-((width/2)/2)),(int)y+(height/2),width/2,(height/2)+4);
    }
    
}
