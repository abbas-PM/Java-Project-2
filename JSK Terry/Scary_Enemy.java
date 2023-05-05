import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Scary_Enemy extends GameObject{

    private int width = 155; 
    private int height = 100; 

    Handler handler; 
    private GameObject tempPlayer;
    private float gravity = 0.3f;
    private final float MAX_SPEED = 10; 
    private ArrayList<Integer> keylist = new ArrayList<>();

    Texture tex = Main.getInstance();

    Timer timer; 
    Timer timer2; 
    int pattern; 

    public Scary_Enemy(float x, float y, ID id, Handler handler, int health){
        super(x, y, id, health);
        this.handler = handler; 
        keylist.add(0);
         //Goes through the linked list
         for(int i=0;i < handler.object.size();i++){
             //If the id is the player, set the temp object as the player
            if(handler.object.get(i).getId() == ID.Player) tempPlayer = handler.object.get(i);

      }

      health = (int)Main.clamp(health, 0, 100);
      pattern = 1; 
      Timer();
      timer.start();
      
    }

    public void tick() {

       y += velY;
        
        if(falling){

          velY += gravity; 
  
          if(velY > MAX_SPEED) velY = MAX_SPEED; 
        }

        Collision();

        if (health <= 10 && health > 0){
          timer.stop();
          timer2.start();
        }
    }

    public void render(Graphics g) {

      
        if(health > 10){
          
          if (tempPlayer.getX() <= x+50){
            if(pattern % 2 == 0) g.drawImage(tex.enemy[0],(int)x-28,(int)y - 40,200,200,null);
            else g.drawImage(tex.enemy[8],(int)x-28,(int)y - 90,200,200,null);
          }
          else{
            if(pattern % 2 == 0) g.drawImage(tex.enemy[1],(int)x-21,(int)y - 40,200,200,null); 
            else g.drawImage(tex.enemy[9],(int)x-21,(int)y - 90,200,200,null); 
          }

      }

      else if(health <= 10 && health > 0){

        if (tempPlayer.getX() <= x){
          if(pattern % 2 == 0) g.drawImage(tex.enemy[2],(int)x-28,(int)y - 40,200,200,null);
          else g.drawImage(tex.enemy[10],(int)x-28,(int)y - 90,200,200,null);
        }
        else{
          if(pattern % 2 == 0) g.drawImage(tex.enemy[3],(int)x-21,(int)y - 40,200,200,null);  
          else g.drawImage(tex.enemy[11],(int)x-21,(int)y - 90,200,200,null);  
        }

      }

      else if (health <= 0){
        handler.removeObject(this);
        if(tempPlayer.getHealth() <= 15) tempPlayer.setHealth(tempPlayer.getHealth() + 30); 
      }

      //Used for making the health bar
      g.setColor(Color.gray);//Set the color
      g.fillRect((int)this.getX() + 43,(int)this.getY() - 17,60,10);//Fill the rectangle
      g.setColor(Color.getHSBColor( (1f * health) / 360, 1f, 1f));//Set the color
      g.fillRect((int)this.getX() + 43,(int)this.getY() - 17,(int)health * 3,10);//Fill the rectanngle
      g.setColor(Color.white);//Set the color
      g.drawRect((int)this.getX() + 43,(int)this.getY() - 17,60,10);//Draw a rectangle
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
                  y = tempObject.getY() - (height+4); 
                  velY = 0; 
                  falling = false; 
                }
                else{
                  falling = true;
                }
              }

              if(tempObject.getId() == ID.Player){
                if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) 
                ||getBoundsRight().intersects(tempObject.getBounds())||getBounds().intersects(tempObject.getBounds())){
                  Player.SEcaught = true; 
                  tempObject.setVelX(0);
                  if (health <= 10) tempObject.setHealth(tempObject.getHealth() - 1);
                }

                if (getBoundsExtended().intersects(tempObject.getBounds()) && pattern % 2 != 0){
                  Player.SEcaught = true; 
                  tempObject.setVelX(0);
                  if (health <= 10) tempObject.setHealth(tempObject.getHealth() - 1);
                }
              }

              if(tempObject.getId() == ID.Bullet){
                if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) 
                ||getBoundsRight().intersects(tempObject.getBounds())||getBounds().intersects(tempObject.getBounds())){
                  handler.removeObject(tempObject);
                  health -= 2;
                  Bullet.allowed = true; 
                }

                if(getBoundsExtended().intersects(tempObject.getBounds()) && pattern % 2 != 0){
                  handler.removeObject(tempObject);
                  health -= 2;
                  Bullet.allowed = true; 
                }
              }  
        }
      }catch(IndexOutOfBoundsException | NullPointerException exception) {}
    }

    public Rectangle getBounds(){
        return new Rectangle((int)(x+(width/2)-((width/2)/2)),(int)y+(height/2),width/2,(height/2)+4);
      }
      //Top Enemy Hitbox 
      public Rectangle getBoundsTop(){
        return new Rectangle((int)(x+(width/2)-((width/2)/2)),(int)y,width/2,height/2);
      }
      //Left Enemy Hitbox 
      public Rectangle getBoundsLeft(){
        return new Rectangle((int)x,(int)y-23,20,height-30);
      }
      //Right Enemy Hitbox 
      public Rectangle getBoundsRight(){
        return new Rectangle((int)x+width-25,(int)y-23,20,height-30);
      }
      //Extended Hitbox 
      public Rectangle getBoundsExtended(){
        return new Rectangle((int)x+15, (int)y - 84, 110, 55); 
      }
      
      public void Timer(){

        timer = new Timer(675, new ActionListener(){

          @Override
          public void actionPerformed(ActionEvent e) {
            pattern++;
          }
        });

        timer2 = new Timer(1050, new ActionListener(){

          @Override
          public void actionPerformed(ActionEvent e) {
            pattern++;
            
          } 
        });
      }
}
