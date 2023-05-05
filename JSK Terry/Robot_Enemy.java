import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class Robot_Enemy extends GameObject {

    private int width = 70; 
    private int height = 70; 

    Main main; 
    Handler handler;//Creating an instance of the handler 
    private GameObject tempPlayer;//Creating an instance of the gameobject 

    Texture tex = Main.getInstance();

    public Robot_Enemy(float x, float y, ID id, Handler handler, int health, Main main) {
        super(x, y, id, health);
        this.main = main; 
        this.handler = handler; 

        tempPlayer = handler.getPlayer();
    }

    public void tick() {
      if(health <= 15 && health > 5 && main.gameState == Main.STATE.Game){
        x += velX * 3; 
        y += velY * 3; 
      }
      else if(health <= 5 && health > 0 && main.gameState == Main.STATE.Game){
        x += velX * 6; 
        y += velY * 6;
        
      } 
      else if(health == 0){
        handler.removeObject(this);
        if(tempPlayer.getHealth() <= 15) tempPlayer.setHealth(tempPlayer.getHealth() + 30); 
      } 
      
      //Algorithm to make the enemy follow you
      float diffX = x - tempPlayer.getX() - 8;  
      float diffY = y - tempPlayer.getY() - 8;
      float distance = (float)Math.sqrt((x - tempPlayer.getX())*(x - tempPlayer.getX()) + (y - tempPlayer.getY())*(y - tempPlayer.getY()));
      
      velX = (float)((-1.0/distance) * diffX);
      velY = (float)((-1.0/distance) * diffY);

      try{
        
      for(int i = 0; i < handler.object.size();i++){
        GameObject tempObject = handler.object.get(i);

        if(tempObject.getId() == ID.Bullet){

            if(getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds())|| getBounds().intersects(tempObject.getBounds())){
              handler.removeObject(tempObject);
              health -= 1;
              Bullet.allowed = true; 
            }

            else if(getBoundsTop().intersects(tempObject.getBounds())){
              handler.removeObject(tempObject);
              health -= 5; 
              Bullet.allowed = true; 
            }
        }

        if (tempObject.getId() == ID.Player){

          if(getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds())|| getBounds().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds())){
            Player.robotAffected = true; 
          }
        }

        if(tempObject.getId() == ID.Robot_Enemy){
          
          if(getBoundsLeft().intersects(tempObject.getBounds())){
            x = tempObject.getX() + 60; 
          }
  
          if(getBoundsRight().intersects(tempObject.getBounds())){
            x =  tempObject.getX() - 60; 
          }

          if(getBoundsTop().intersects(tempObject.getBounds())){
            y = tempObject.getY() + 60; 
          }
          
          
        }
      }
    }catch(IndexOutOfBoundsException | NullPointerException exception) {}   
    }

    public void render(Graphics g) {

        if(health <= 15 && health > 5){
            if(velX < 0)g.drawImage(tex.enemy[4], (int)x-18, (int)y-8, 112, 120, null);
            if (velX >= 0)g.drawImage(tex.enemy[5], (int)x-20, (int)y-8, 110, 120, null);
        }
        else if(health <= 5 && health > 0){
            if(velX < 0)g.drawImage(tex.enemy[6], (int)x-18, (int)y-8, 112, 120, null);
            if (velX >= 0)g.drawImage(tex.enemy[7], (int)x-20, (int)y-8, 110, 120, null);
        }

        //Used for making the health bar
        g.setColor(Color.gray);//Set the color
        g.fillRect((int)this.getX() + 13,(int)this.getY() - 15,45,10);//Fill the rectangle
        g.setColor(Color.getHSBColor( (1f * health) / 360, 1f, 1f));//Set the color
        g.fillRect((int)this.getX() + 13,(int)this.getY() - 15,(int)health * 3,10);//Fill the rectanngle
        g.setColor(Color.white);//Set the color
        g.drawRect((int)this.getX() + 13,(int)this.getY() - 15,45,10);//Draw a rectangle
        
    }

   
    public Rectangle getBounds(){
        return new Rectangle((int)(x+(width/2)-((width/2)/2)),(int)y+(height/2),width/2,(height/2)+4);
      }
      //Top Player Hitbox 
      public Rectangle getBoundsTop(){
        return new Rectangle((int)(x+(width/2)-((width/2)/2)),(int)y,width/2,height/2);
      }
      //Left Player Hitbox 
      public Rectangle getBoundsLeft(){
        return new Rectangle((int)x,(int)y+5,5,height-11);
      }
      //Right Player Hitbox 
      public Rectangle getBoundsRight(){
        return new Rectangle((int)x+width-5,(int)y+5,5,height-11);
      }   
    
}
