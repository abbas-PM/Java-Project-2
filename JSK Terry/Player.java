import java.awt.Color; 
import java.awt.Graphics; 
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Player extends GameObject{

    Handler handler;
    Camera cam;  

    int width = 50; 
    int height = 70; 
    private float gravity = 0.3f;
    private final float MAX_SPEED = 10;
    public static int Ammo = 10; 
    public static int Potions = 0; 
    public static double Coins = 0; 
    public static boolean attack = false; 
    public static boolean standing; 
    public static boolean shot = false; 
    public static boolean SEcaught = false;  
    public static boolean buy_ammo = false;
    public static boolean hitRight = false;
    public static boolean hitLeft = false; 
    public static int hitFrames = 0; 
    public static boolean robotAffected = false;    
    public static Timer timer;
    Timer timer2;  
    
    Texture tex = Main.getInstance();

    private Animation playerWalkRight;
    private Animation playerWalkLeft;

    private Animation playerShootRight;
    private Animation playerShootLeft;

    Main main; 

    public Player(float x, float y, ID id, Camera cam, Handler handler, int health, Main main){
        super(x, y, id, health); 
        this.handler = handler; 
        this.cam = cam; 
        this.main = main; 
        playerWalkRight = new Animation(11, tex.player[2], tex.player[3], tex.player[4], tex.player[3]);
        playerWalkLeft = new Animation(11,  tex.player[5], tex.player[6], tex.player[7], tex.player[6]);

        playerShootRight = new Animation(11, tex.player[20], tex.player[21], tex.player[22], tex.player[21]);
        playerShootLeft = new Animation(11, tex.player[23], tex.player[24], tex.player[25], tex.player[24]);
        Timer(); 
        RobotHitTimer();
        timer2.start();
        
    }

    public void tick() {

      if(health <= 0){
        if (Potions >= 1){
          health += 50; 
          Potions -= 1; 
        }
        else{
          main.gameState = Main.STATE.GameOver; 
          main.timer.stop();
          main.timer2.start();
        }
      }

      health = (int)Main.clamp(health, 0, 100); 
      x = Main.clamp(x, 35, 50000);
      y = Main.clamp(y, -100, 566);
        
        x += velX; 
        y += velY;

        
        if(velY < -1 || velY > 1){
          standing = false; 
        }

        if(falling || jumping){

        velY += gravity; 

        if(velY > MAX_SPEED) velY = MAX_SPEED; 
    }
        
        Collision();
        
        playerWalkRight.runAnimation();
        playerWalkLeft.runAnimation();

        playerShootRight.runAnimation();
        playerShootLeft.runAnimation();

        if (Ammo == 0 && Coins >= 0.5){
          buy_ammo = true; 
        }
        
        if (hitFrames > 0){
          velX = 0; 
          velY = 0; 
        }

        if (hitFrames == 8){
          hitFrames = 0; 
          timer.stop();
          hitRight = false; 
          hitLeft = false; 
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
          y = tempObject.getY() - (height+4); 
          velY = 0; 
          falling = false;
          standing = true;  
          jumping = false;
        }
        else{
          falling = true;
        }
      }

      if(tempObject.getId() == ID.Invisible_Wall){

        if(getBoundsTop().intersects(tempObject.getBounds())){
          y = tempObject.getY() + tempObject.getBounds().height; 
          velY = 0; 
        }

        if(getBoundsLeft().intersects(tempObject.getBounds())){
          x =  tempObject.getX() + 25; 
        }

        if(getBoundsRight().intersects(tempObject.getBounds())){
          x =  tempObject.getX() - (width+3);   
        }

        if(getBounds().intersects(tempObject.getBounds())){
          y = tempObject.getY() - (height+4); 
          velY = 0; 
          falling = false;
          standing = true;  
          jumping = false;
        }
        else{
          falling = true;
        }
      }

      if(tempObject.getId() == ID.Platform){
        if(getBoundsTop().intersects(tempObject.getBounds())){
          y = tempObject.getY() + tempObject.getBounds().height; 
          velY = 0; 
        }

        if(getBoundsLeft().intersects(tempObject.getBounds())){
          x = tempObject.getX() + 227; 

          try{

            for(int j = 0; j < handler.object.size(); j++){

              GameObject tempObject2 = handler.object.get(j);

              if(tempObject2.getId() == ID.Blocks){

                if (getBoundsRight().intersects(tempObject2.getBounds())){
                  main.gameState = Main.STATE.GameOver; 
                  main.timer.stop();
                  main.timer2.start();
                }
              }

            } 
          }catch(IndexOutOfBoundsException | NullPointerException exception) {}
        }

        if(getBoundsRight().intersects(tempObject.getBounds())){
          x =  tempObject.getX() - (width+3); 

          try{

            for(int j = 0; j < handler.object.size(); j++){

              GameObject tempObject2 = handler.object.get(j);

              if(tempObject2.getId() == ID.Blocks){

                if (getBoundsLeft().intersects(tempObject2.getBounds())){
                  main.gameState = Main.STATE.GameOver; 
                  main.timer.stop();
                  main.timer2.start();
                }
              }

            } 
          }catch(IndexOutOfBoundsException | NullPointerException exception) {}
        }
        if(getBounds().intersects(tempObject.getBounds())){
          y = tempObject.getY() - (height+4); 
          velY = 0; 
          falling = false; 
          standing = true; 
          jumping = false; 
        }
        else{
          falling = true; 
        }    

      }

      if(tempObject.getId() == ID.Coin){
        if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) 
        ||getBoundsRight().intersects(tempObject.getBounds())||getBounds().intersects(tempObject.getBounds())){
          Coins += 1; 
          Main.totalCoins += 1; 
          handler.removeObject(handler.object.get(i));
        }
      }

      if(tempObject.getId() == ID.FlagPole){
        if(getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) 
        ||getBoundsRight().intersects(tempObject.getBounds())||getBounds().intersects(tempObject.getBounds())){
          handler.switchLevel(health);
        }
      }

      if(tempObject.getId() == ID.Scary_Enemy){
        if(!getBoundsTop().intersects(tempObject.getBounds()) && !getBoundsLeft().intersects(tempObject.getBounds()) 
        &&!getBoundsRight().intersects(tempObject.getBounds())&& !getBounds().intersects(tempObject.getBounds())){
          SEcaught = false; 
      }
    }

      if(tempObject.getId() == ID.Robot_Enemy){
        if(!getBoundsTop().intersects(tempObject.getBounds()) && !getBoundsLeft().intersects(tempObject.getBounds()) 
        && !getBoundsRight().intersects(tempObject.getBounds()) && !getBounds().intersects(tempObject.getBounds())){ 
          robotAffected = false; 
        } 
        
      }
    }
  }catch(IndexOutOfBoundsException | NullPointerException exception) {}
  }

    @Override
    public void render(Graphics g) {

        if ((jumping || standing == false) && hitFrames == 0){

          if(shot){

            if (velX > 0){
              g.drawImage(tex.player[18], (int)x - 40, (int)y - 11, 140, 145, null);
            }
    
            else if (velX < 0){
              g.drawImage(tex.player[19], (int)x - 40, (int)y - 11, 140, 145, null);
            }

            if (velX == 0){
              if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 0){
               g.drawImage(tex.player[18], (int)x - 40, (int)y - 11, 140, 145, null);
              }
              else if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 1){
               g.drawImage(tex.player[19], (int)x - 40, (int)y - 11, 140, 145, null);
              }
          }
        }

          else{
            if (velX > 0){
              g.drawImage(tex.player[8], (int)x - 40, (int)y - 11, 140, 145, null);
            }
    
            else if (velX < 0){
              g.drawImage(tex.player[9], (int)x - 40, (int)y - 11, 140, 145, null);
            }
    
            if (velX == 0){
              if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 0){
               g.drawImage(tex.player[8], (int)x - 40, (int)y - 11, 140, 145, null);
              }
              else if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 1){
               g.drawImage(tex.player[9], (int)x - 40, (int)y - 11, 140, 145, null);
              }
          }
          }

      }
        else if(standing && hitFrames == 0){

          if(shot){

            if(velX > 0){
              playerShootRight.drawAnimation(g, (int)x - 40, (int)y - 11, 140, 150);
            }

            if(velX < 0){
              playerShootLeft.drawAnimation(g, (int)x - 50, (int)y - 11, 140, 150);
            }
            if (velX == 0){
              if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 0){
               g.drawImage(tex.player[16], (int)x - 40, (int)y - 8, 140, 145, null);
              }
              else if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 1){
               g.drawImage(tex.player[17], (int)x - 40, (int)y - 8, 140, 145, null);
              }
            }
          }
          else{
            
        if (velX > 0 ){
          //g.drawImage(tex.player[0], (int)x - 40, (int)y - 11, 140, 145, null);
          playerWalkRight.drawAnimation(g, (int)x - 40, (int)y - 11, 140, 150);
       }
        else if (velX < 0){
        //g.drawImage(tex.player[1], (int)x - 40, (int)y - 11, 140, 145, null);
        playerWalkLeft.drawAnimation(g, (int)x - 50, (int)y - 11, 140, 150);
       }
   
        if (velX == 0){
         if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 0){
          g.drawImage(tex.player[0], (int)x - 40, (int)y - 11, 140, 145, null);
         }
         else if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 1){
          g.drawImage(tex.player[1], (int)x - 40, (int)y - 11, 140, 145, null);
         }
     }
  }

  }

  if (hitFrames > 0){
    if (hitRight) g.drawImage(tex.player[26], (int)x - 40, (int)y - 11, 140, 145, null);
    if (hitLeft ) g.drawImage(tex.player[27], (int)x - 40, (int)y - 11, 140, 145, null);
  }

  if(KeyInput.cooldown > 0){
      g.setColor(Color.getHSBColor( (3f * KeyInput.cooldown) / 360, 3f, 3f));//Set the color
      g.fillRect((int)this.getX() + 70,(int)this.getY() + 3*(10 - KeyInput.cooldown),10,3*KeyInput.cooldown);//Fill the rectanngle
      g.setColor(Color.black);//Set the color
      g.drawRect((int)this.getX() + 70,(int)this.getY(),10,30);//Draw a rectangle
  }

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
      
      public void Timer(){

        timer = new Timer(25, new ActionListener(){
    
            @Override
            public void actionPerformed(ActionEvent e) {
                hitFrames++; 
            }
    
    
        });
    }

    private void RobotHitTimer(){

      timer2 = new Timer(500, new ActionListener(){
    
        @Override
        public void actionPerformed(ActionEvent e) {
            if (robotAffected) health -= 25; 
            else health -= 0; 
        }


    });
    }

}
