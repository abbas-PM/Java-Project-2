import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class KeyInput extends KeyAdapter{

    private Handler handler; 
    private Main main; 
    public static boolean[] KeyDown = new boolean[2];
    public static ArrayList<Integer> keylist = new ArrayList<>();
    private Timer timer; 
    public static int cooldown; 

    public KeyInput(Main main, Handler handler){
        this.handler = handler; 
        this.main = main; 
        keylist.add(0);
        Timer();
        timer.start();
        cooldown = 0; 
    }
    

public void keyPressed(KeyEvent evt){

    int c = evt.getKeyCode();

    if (c == KeyEvent.VK_ESCAPE){
        System.exit(0);
      }
    
    for(int i = 0;i < handler.object.size();i++){
        GameObject tempObject = handler.object.get(i);

        if(tempObject.getId() == ID.Player){
            if (c == KeyEvent.VK_A || c == KeyEvent.VK_LEFT && Player.SEcaught == false && Player.hitFrames == 0 && main.gameState == Main.STATE.Game){
                tempObject.setVelX(-7);
                KeyDown[1] = true;
                KeyDown[0] = false; 
                keylist.add(1);
            }

            if (c == KeyEvent.VK_D || c == KeyEvent.VK_RIGHT && Player.SEcaught == false && Player.hitFrames == 0 && main.gameState == Main.STATE.Game){
                tempObject.setVelX(7);
                KeyDown[0] = true;
                KeyDown[1] = false;
                keylist.add(0);
            }

            if ((c == KeyEvent.VK_SPACE || c == KeyEvent.VK_UP) && !tempObject.isJumping() && Player.standing == true && Player.hitFrames == 0 && main.gameState == Main.STATE.Game) {
          
                tempObject.setJumping(true);  
                tempObject.setVelY(-10);
            }

            if((c == KeyEvent.VK_S || c == KeyEvent.VK_DOWN) && Bullet.allowed && Player.Ammo > 0 && cooldown == 0 && Player.hitFrames == 0 && main.gameState == Main.STATE.Game){
                if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 0) handler.addObject(new Bullet(tempObject.getX() + 15, tempObject.getY() + 13, ID.Bullet, handler, 1, 0)); 
                if (KeyInput.keylist.get(KeyInput.keylist.size()-1) == 1) handler.addObject(new Bullet(tempObject.getX() - 17, tempObject.getY() + 13, ID.Bullet, handler, -1, 0)); 
                cooldown = 10;  
                Bullet.allowed = false; 
                Player.shot = true; 
                Player.Ammo -= 1; 
            }

            if(c == KeyEvent.VK_1 && Player.buy_ammo && main.gameState == Main.STATE.Game){
                Player.Coins -= 0.5; 
                Player.Ammo = 10; 
                Player.buy_ammo = false; 
                HUD.displayOnceAmmo = false; 
            }

            if(c == KeyEvent.VK_2 && Player.Coins >= 1.5 && main.gameState == Main.STATE.Game){
                Player.Potions += 1;
                Player.Coins -= 1.5; 
                HUD.displayOnceHealth = false;  
            }

            if(c == KeyEvent.VK_3 && Player.Potions >= 1 && tempObject.getHealth() < 100 && main.gameState == Main.STATE.Game){
                tempObject.setHealth(tempObject.getHealth() + 50); 
                Player.Potions -= 1; 
            }
            


            }
        }
    }

public void keyReleased(KeyEvent evt){
    int c = evt.getKeyCode();

    for(int i = 0;i < handler.object.size();i++){
        GameObject tempObject = handler.object.get(i);

        if(tempObject.getId() == ID.Player){
            if(c == KeyEvent.VK_D || c == KeyEvent.VK_RIGHT) KeyDown[0] = false;  
            if(c == KeyEvent.VK_A || c == KeyEvent.VK_LEFT) KeyDown[1] = false;   
            
            //horizontal movement
            if((KeyDown[0] == false && KeyDown[1] == false)) tempObject.setVelX(0);
            
        }

        if(c == KeyEvent.VK_S || c == KeyEvent.VK_DOWN) Player.shot = false; 
    }

}

public void keyTyped(KeyEvent evt){}   

public void Timer(){

    timer = new Timer(40, new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            if (cooldown > 0) cooldown--; 
        }


    });
}
  

}
