import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Boss extends GameObject{

    int width = 500;
    int height = 637; 
    
    Texture tex = Main.getInstance();
    private Animation Walk;
    
    private int stopFrames = 0; 
    Timer timer; 

    Handler handler; 

    Main main; 

    public Boss(float x, float y, ID id, Handler handler, int health, Main main) {
        super(x, y, id, health);
        this.handler = handler; 
        this.main = main; 
        Walk = new Animation(22, tex.boss[0], tex.boss[1], tex.boss[2], tex.boss[1]);
        Timer();
    }

    @Override
    public void tick() {

      if (main.gameState == Main.STATE.Game) timer.start();
        
        if (stopFrames > 2 && main.gameState == Main.STATE.Game) {
          x += velX; 
          timer.stop();
        } 

        velX = (float) 4.5; 
        Walk.runAnimation();
        Collision(); 
        
    }

    public void Collision(){

        try{

            for(int i = 0; i < handler.object.size();i++){
                GameObject tempObject = handler.object.get(i);

                if(tempObject.getId() == ID.Blocks){

                    if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds())){
                      handler.removeObject(tempObject);
                    }
                  }

                if(tempObject.getId() == ID.Platform){

                    if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds())){
                      handler.removeObject(tempObject);
                    }
                  }

                if(tempObject.getId() == ID.FlagPole){

                    if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds())){
                      handler.removeObject(tempObject);
                    }
                  }

                if(tempObject.getId() == ID.Scary_Enemy){

                    if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds())){
                      handler.removeObject(tempObject);
                    }
                  }

                if(tempObject.getId() == ID.Robot_Enemy){

                    if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds())){
                      handler.removeObject(tempObject);
                    }
                  }

                if(tempObject.getId() == ID.Beaver_Enemy){

                    if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds())){
                      handler.removeObject(tempObject);
                    }
                  }

                if(tempObject.getId() == ID.Bullet){

                    if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds())){
                      health -= 1; 
                      handler.removeObject(tempObject);
                      Bullet.allowed = true; 
                    }
                  }

                if(tempObject.getId() == ID.Player){

                    if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds())){
                      main.gameState = Main.STATE.GameOver; 
                      main.timer.stop();
                      main.timer2.start();
                    }
                  }
            }

        }catch (IndexOutOfBoundsException | NullPointerException exception) {}
    }

    @Override
    public void render(Graphics g) {

        if (velX > 0){
            Walk.drawAnimation(g, (int)x - 250, (int)y - 90, 1100, 1300);
        }
    }

    public Rectangle getBounds(){
        return new Rectangle((int)(x+(width/2)-((width/2)/2)),(int)y+(height/2),width/2,(height/2)+4);
      }
      //Top Hitbox 
      public Rectangle getBoundsTop(){
        return new Rectangle((int)(x+(width/2)-((width/2)/2)),(int)y,width/2,height/2);
      }
      //Left Hitbox 
      public Rectangle getBoundsLeft(){
        return new Rectangle((int)x+65,(int)y+5,5,height-11);
      }
      //Right Hitbox 
      public Rectangle getBoundsRight(){
        return new Rectangle((int)x+width-65,(int)y+5,5,height-11);
      } 

    private void Timer(){

        timer = new Timer(1000, new ActionListener(){
    
            @Override
            public void actionPerformed(ActionEvent e) {
                stopFrames++; 
            }
    
    
        });
    }  
    
}
