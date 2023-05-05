import java.awt.Color; 
import java.awt.Graphics; 
import java.awt.*;

public class HUD {

    Texture tex = Main.getInstance();
    Handler handler;
    public static boolean displayOnceAmmo = true; 
    public static boolean displayOnceHealth = true; 

    public HUD(Handler handler){
        this.handler = handler;
    }
    
    public void tick(){
    }
    public void render(Graphics g){

        for(int i = 0;i < handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);
    
            if(tempObject.getId() == ID.Player){

                g.setFont(new Font("Rockwell", Font.PLAIN, 20));


                g.drawImage(tex.block[10], (int)tempObject.getX() - 460, 8, 50, 50, null);
                g.setColor(Color.black);
                g.drawString(": " + tempObject.getHealth(), (int)tempObject.getX() - 425, 32);

                g.drawImage(tex.block[2], (int)tempObject.getX() - 480, 28, 90, 90, null);
                g.drawString(": " + Player.Coins, (int)tempObject.getX() - 425, 62);

                g.drawImage(tex.block[11], (int)tempObject.getX() - 380, 18, 45, 45, null);
                g.drawString(": " + Player.Potions, (int)tempObject.getX() - 340, 48);
                

                if(Player.Ammo == 10) g.drawImage(tex.ammo[0], (int)tempObject.getX() - 510, 0, 80, 80, null);
                if(Player.Ammo == 9) g.drawImage(tex.ammo[1], (int)tempObject.getX() - 510, 0, 80, 80, null);
                if(Player.Ammo == 8) g.drawImage(tex.ammo[2], (int)tempObject.getX() - 510, 0, 80, 80, null);
                if(Player.Ammo == 7) g.drawImage(tex.ammo[3], (int)tempObject.getX() - 510, 0, 80, 80, null);
                if(Player.Ammo == 6) g.drawImage(tex.ammo[4], (int)tempObject.getX() - 510, 0, 80, 80, null);
                if(Player.Ammo == 5) g.drawImage(tex.ammo[5], (int)tempObject.getX() - 510, 0, 80, 80, null);
                if(Player.Ammo == 4) g.drawImage(tex.ammo[6], (int)tempObject.getX() - 510, 0, 80, 80, null);
                if(Player.Ammo == 3) g.drawImage(tex.ammo[7], (int)tempObject.getX() - 510, 0, 80, 80, null);
                if(Player.Ammo == 2) g.drawImage(tex.ammo[8], (int)tempObject.getX() - 510, 0, 80, 80, null);
                if(Player.Ammo == 1) g.drawImage(tex.ammo[9], (int)tempObject.getX() - 510, 0, 80, 80, null);
                if(Player.Ammo == 0) g.drawImage(tex.ammo[10], (int)tempObject.getX() - 510, 0, 80, 80, null);

                if(Player.buy_ammo && displayOnceAmmo){
                    g.drawString("Press 1 to buy ammo", (int)tempObject.getX() + 200, 60);
                    g.drawString("for only half a coin!", (int)tempObject.getX() + 220, 80); 
                }

                if(Player.Coins >= 1.5 && displayOnceHealth){
                    g.drawString("Press 2 to buy a potion for 1.5 coins", (int)tempObject.getX() - 160, 60);
                    g.drawString("press 3 to use the potion", (int)tempObject.getX() - 160, 80); 
                }

            }
        }
    }
}
