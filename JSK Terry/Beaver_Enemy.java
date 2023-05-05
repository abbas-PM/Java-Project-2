import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Beaver_Enemy extends GameObject{

    private int width = 80; 
    private int height = 50; 

    Handler handler; 
    Texture tex = Main.getInstance(); 

    private boolean faceRight = false; 
    private float initialX;  

    private Animation Death;
    private int deathTimer; 
    Timer timer; 

    public Beaver_Enemy(float x, float y, ID id, Handler handler,  int health) {
        super(x, y, id, health);
        this.handler = handler; 
        this.initialX = x; 

        Death = new Animation(10, tex.enemy[16], tex.enemy[17]);
        deathTimer = 0;  
        Deathtimer(); 
    }

    public void tick() {


        Collision();

        Death.runAnimation();

        if (deathTimer == 1) handler.removeObject(this);

        if (!faceRight && health > 0) velX *= -1; 
        else if (faceRight && health > 0) velX *= 1; 

        x += velX;
        y += velY;  
 
        if (!faceRight){

          if (x > initialX - 300){

            if (health <= 10 && health > 5){
              velY = -3; 
              velX = 6; 
            }
            else if (health <= 5 && health > 0){
              velY = -4; 
              velX = 7; 
            }

          } 
        
        else if (x <= initialX - 300){

          if (health <= 10 && health > 5){
            velY = 3; 
            velX = 6; 
          }
          else if (health <= 5 && health > 0){
            velY = 4; 
            velX = 7; 
          }
        }
      }

      else {

        if (x <= initialX - 300){
          
          if (health <= 10 && health > 5){
            velY = -3; 
            velX = 6; 
          }
          else if (health <= 5 && health > 0){
            velY = -4; 
            velX = 7; 
          }

        }
        
        else if (x > initialX - 300){
          
          if (health <= 10 && health > 5){
            velY = 3; 
            velX = 6; 
          }
          else if (health <= 5 && health > 0){
            velY = 4; 
            velX = 7; 
          }
      }

      }
    }

    public void Collision(){

        try{

            for(int i = 0; i < handler.object.size();i++){
                GameObject tempObject = handler.object.get(i);
    
                if(tempObject.getId() == ID.Blocks){
                    if(getBoundsTop().intersects(tempObject.getBounds())){
                        y = tempObject.getY() + tempObject.getBounds().height; 
                        velY = 0; 
                      }
              
                      if(getBoundsLeft().intersects(tempObject.getBounds())){
                        x =  tempObject.getX() + 35;   
                      }
              
                      if(getBoundsRight().intersects(tempObject.getBounds())){
                        x =  tempObject.getX() - (width+3);  
                      }
      
                      if(getBounds().intersects(tempObject.getBounds())){
                        y = tempObject.getY() - (height+14); 
                        velY = 0;
                        if (faceRight && x != initialX) x = initialX; 
                        faceRight = !faceRight; 
                        falling = false; 
                        jumping = false; 
                      }
                      else{
                        falling = true;
                      }
                }

                if(tempObject.getId() == ID.Bullet){
                    if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) 
                    ||getBoundsRight().intersects(tempObject.getBounds())||getBounds().intersects(tempObject.getBounds())){
                      handler.removeObject(tempObject);
                      health -= 2;
                      Bullet.allowed = true; 
                    }
                }

                if(tempObject.getId() == ID.Player){
                    if (getBoundsLeft().intersects(tempObject.getBounds())){
                      if (Player.hitFrames == 0){
                        Player.timer.start(); 
                        health = 0; 
                        tempObject.setHealth(tempObject.getHealth() - 30);
                        tempObject.setX(tempObject.getX() - 90);
                        Player.hitRight = true;
                      }
                    }

                    if (getBoundsRight().intersects(tempObject.getBounds())){
                      if (Player.hitFrames == 0){
                        Player.timer.start();
                        health = 0; 
                        tempObject.setHealth(tempObject.getHealth() - 30);
                        tempObject.setX(tempObject.getX() + 90); 
                        Player.hitLeft = true; 
                      }
                    }

                    if (getBoundsTop().intersects(tempObject.getBounds())){
                      if (Player.hitFrames == 0){
                        Player.timer.start();
                        health = 0; 
                        tempObject.setHealth(tempObject.getHealth() - 30);
                        if (tempObject.getX() < x){
                          Player.hitRight = true; 
                          tempObject.setX(tempObject.getX() - 90);
                        } 
                        else if (tempObject.getX() >= x){
                          Player.hitLeft = true;
                          tempObject.setX(tempObject.getX() + 90); 
                        }  
                      }
                    }

                    if (getBounds().intersects(tempObject.getBounds())){
                      if (Player.hitFrames == 0){
                        Player.timer.start();
                        health = 0; 
                        tempObject.setHealth(tempObject.getHealth() - 30);
                        if (tempObject.getX() < x){
                          Player.hitRight = true; 
                          tempObject.setX(tempObject.getX() - 90);
                        } 
                        else if (tempObject.getX() >= x){
                          Player.hitLeft = true;
                          tempObject.setX(tempObject.getX() + 90); 
                        } 
                      }
                    }
                }
            }

                  
        }catch(IndexOutOfBoundsException | NullPointerException exception) {}

    }

  
    public void render(Graphics g) {

        if (health <= 10 && health > 5){
            if (!faceRight) g.drawImage(tex.enemy[13], (int)x, (int)y+4, 100, 90, null);
            if (faceRight) g.drawImage(tex.enemy[12], (int)x-23, (int)y+4, 100, 90, null);
        }

        else if (health <= 5 && health > 0){
            if (!faceRight) g.drawImage(tex.enemy[15], (int)x, (int)y+4, 100, 90, null);
            if (faceRight) g.drawImage(tex.enemy[14], (int)x-23, (int)y+4, 100, 90, null);
        }

        else if (health <= 0){
            Death.drawAnimation(g, (int)x, (int)y+4, 80, 70);
            timer.start();
            velX = 0; 
            velY = 0; 
          }

        if (health > 0){
          //Used for making the health bar
          g.setColor(Color.gray);//Set the color
          g.fillRect((int)this.getX() + 25,(int)this.getY() + 5,30,10);//Fill the rectangle
          g.setColor(Color.getHSBColor( (1f * health) / 360, 1f, 1f));//Set the color
          g.fillRect((int)this.getX() + 25,(int)this.getY() + 5,(int)health * 3,10);//Fill the rectanngle
          g.setColor(Color.white);//Set the color
          g.drawRect((int)this.getX() + 25,(int)this.getY() + 5,30,10);//Draw a rectangle
        } 
        
    }

    public Rectangle getBounds(){
        return new Rectangle((int)(x+(width/2)-((width/2)/2)),(int)y+(height/2) + 10,width/2,(height/2)+4);
      }
      //Top Player Hitbox 
      public Rectangle getBoundsTop(){
        return new Rectangle((int)(x+(width/2)-((width/2)/2)),(int)y+12,width/2,height/2);
      }
      //Left Player Hitbox 
      public Rectangle getBoundsLeft(){
        if (!faceRight) return new Rectangle((int)x,(int)y+25,10,height-28);
        if (faceRight) return new Rectangle((int)x+3,(int)y+25,10,height-15);
        else return null; 
        
      }
      //Right Player Hitbox 
      public Rectangle getBoundsRight(){
        if (!faceRight) return new Rectangle((int)x+width-15,(int)y+25,10,height-15);
        if (faceRight) return new Rectangle((int)x+width-15,(int)y+25,10,height-28);
        else return null;
      }  

      public void Deathtimer(){

        timer = new Timer(203, new ActionListener(){
    
            @Override
            public void actionPerformed(ActionEvent e) {
                deathTimer++; 
            }
    
    
        });
    }   
}
