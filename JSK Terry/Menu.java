import java.awt.event.MouseAdapter; 
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.Color; 
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Menu extends MouseAdapter{

    private Main main; 
    private Handler handler; 
    private BufferedImage level = null;
    private int winScreen = 0; 
    private int loadingScreen = 0; 
    Timer timer; 
    public static Timer loadingTimer; 
    public static int currminute = 0; 
    public static int currsecond = 0; 
    Texture tex = Main.getInstance(); 

    public Menu(Main main, Handler handler){
        this.main = main; 
        this.handler = handler; 
        BufferedImageLoader loader = new BufferedImageLoader(); 
        level = loader.loadImage("/Level1.png");//Loading first level
        Timer(); 
        LoadTimer();
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX(); 
        int my = e.getY(); 

        if(main.gameState == Main.STATE.Menu){
      
            //Play button
            if(mouseOver(mx, my,400,150,200,64)){
              //Start the game 
              main.gameState = Main.STATE.Game;
              handler.LoadImageLevel(level);
              handler.getPlayer().setHealth(100);
              main.timer.start();
              main.timer2.stop();
            }
            
            //Help button
            if(mouseOver(mx, my,400,250,200,64)){
              main.gameState = Main.STATE.Help;//Put the game in the menu 
            }
            
            //Quit button
            if(mouseOver(mx, my,400,350,200,64)){
              System.exit(1);//Exit the game
            }
          }

          else if (main.gameState == Main.STATE.GameOver){
            if(mouseOver(mx, my,400,150,200,64)){

              Main.continues += 1; 
              
              main.gameState = Main.STATE.Game;//Start the game 

              handler.ClearAll();

              if (Main.Level == 1) handler.LoadImageLevel(level);
              if (Main.Level == 2) handler.LoadImageLevel(handler.level2);
              if (Main.Level == 3) handler.LoadImageLevel(handler.level3);
              if (Main.Level == 4) handler.LoadImageLevel(handler.level4);
              if (Main.Level == 5) handler.LoadImageLevel(handler.level5);
              if (Main.Level == 6) handler.LoadImageLevel(handler.level6);
              if (Main.Level == 7) handler.LoadImageLevel(handler.level7);
              if (Main.Level == 8) handler.LoadImageLevel(handler.level8);
              if (Main.Level == 9) handler.LoadImageLevel(handler.level9);
              if (Main.Level == 10){
                 main.gameState = Main.STATE.Loading;
                 handler.LoadImageLevel(handler.level10);
                 currminute = main.minute; 
                 currsecond = main.second; 
                 main.timer.stop();
                 Menu.loadingTimer.start();
              }

              handler.getPlayer().setHealth(100);

              main.Timer();
              main.timer.start();
              main.timer2.stop();
            }

            //Help button
            if(mouseOver(mx, my,400,250,200,64)){
              main.gameState = Main.STATE.Menu;//Put the game in the menu 
              handler.getPlayer().setHealth(100);
              Player.Ammo = 10; 
              Player.Coins = 0; 
              Player.Potions = 0; 
              main.minute = 0; 
              main.second = 0; 
              Player.robotAffected = false; 
              Main.levelDisplay = "1A"; 
              Main.continues = 0; 
              Main.totalCoins = 0;
              Main.Level = 1;  
              handler.ClearAll();
            }

            //Quit button
            if(mouseOver(mx, my,400,350,200,64)){
              System.exit(1);//Exit the game
            }
          }

          else if (main.gameState == Main.STATE.Loading){
            if (mouseOver(mx, my, 400,580,200,64) && loadingScreen >= 5){
              loadingTimer.stop();
              loadingScreen = 0; 
              main.minute = currminute; 
              main.second = currsecond;
              main.timer.start();
              main.gameState = Main.STATE.Game;
            }
          }

          else if (main.gameState == Main.STATE.Win){
            if(mouseOver(mx, my, 400,580,200,64) && winScreen >= 10){
              main.gameState = Main.STATE.Menu;//Put the game in the menu 
              Player.Ammo = 10; 
              Player.Coins = 0; 
              Player.Potions = 0; 
              main.minute = 0; 
              main.second = 0; 
              Player.robotAffected = false; 
              Main.levelDisplay = "1A"; 
              Main.continues = 0; 
              Main.totalCoins = 0; 
              Main.Level = 1;  
              handler.ClearAll();
            }
          }

          else if (main.gameState == Main.STATE.Help){
            if(mouseOver(mx, my, 400,580,200,64)){
              main.gameState = Main.STATE.Menu;
            }
          }
        }
    

    public void mouseReleased(MouseEvent e) {}

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {

        //If the mouse is in the box in terms of the width
        if(mx > x && mx < (x + width)){

            //If the mouse is in the box is terns of the height
            if(my > y && my <(y + height)){

                return true;//Return true
                
            }else return false;//If not return false 
        }else return false;//If not return false 
            
    }

    public void tick() {}

    public void render(Graphics g){
    
    if(main.gameState == Main.STATE.Menu){
      
        //Create some fonts
        Font font = new Font("Rockwell", 1, 50);
        Font font2 = new Font("Times New Roman", 1, 30);
        
        g.setColor(Color.black);//Set the colr
        g.setFont(font);//Set the font
        //Draw the title and options you can choose
        g.drawString("JSK TERRY!", 375, 50);
        g.drawString("Play", 437, 200);
        g.drawString("Help", 437, 300);
        g.drawString("Quit", 437, 400);
        
        g.setFont(font2);//Set the font
        g.drawString("Created by Abbas Peer Mohammed", 5, 657);
        
        //Drawing the four rectangles
        g.setColor(Color.white);
        g.drawRect(400,350,200,64);
        g.drawRect(400,250,200,64);
        g.drawRect(400,150,200,64);
      }

      if (main.gameState == Main.STATE.GameOver){
        Font font = new Font("Rockwell", 1, 50);
        g.setColor(Color.black);//Set the colr
        g.setFont(font);//Set the font

        g.drawString("Game Over!", 375, 50);
        g.drawString("Retry", 437, 200);
        g.drawString("Return", 420, 300);
        g.drawString("Quit", 437, 400);
      
        //Drawing the four rectangles
        g.setColor(Color.white);
        g.drawRect(400,350,200,64);
        g.drawRect(400,250,200,64);
        g.drawRect(400,150,200,64);
      }

      if (main.gameState == Main.STATE.Win){
        timer.start();
        Font font = new Font("Rockwell", 1, 50);
        g.setColor(Color.green);//Set the colr
        g.setFont(font);//Set the font

        g.drawString("You Win!", 375, 50);

        g.setColor(Color.black);
        if (winScreen >= 2){ 
          g.drawString("You completed the game in:" , 160, 150);
          g.drawString(main.minute + " minutes" + " and " + main.second + " seconds", 210, 200);
      }
        if (winScreen >= 4) g.drawString("You collected " + Main.totalCoins + " coins", 250, 300);
        if (winScreen >= 6) g.drawString("You died " + Main.continues + " times", 290, 400);
        if (winScreen >= 8) g.drawString("Your Rank is: "  + getRank(), 310, 500);
        if (winScreen == 10){ 
          timer.stop();
          g.drawString("Return", 420, 630);
          g.drawRect(400,580,200,64);
        } 
      }

      if (main.gameState == Main.STATE.Loading){
        Font font2 = new Font("Times New Roman", 1, 30);
        g.setFont(font2);
        g.setColor(Color.white);
        g.drawString("Loading", 870, 655);

        if (loadingScreen >= 5){
          Font font = new Font("Rockwell", 1, 40);
          g.setFont(font);
          g.drawString("Continue", 410, 630);
          g.drawRect(400,580,200,64);
        }
      }

      if (main.gameState == Main.STATE.Help){
        Font f = new Font("Times New Roman", 1, 20);

        g.setFont(f);
        g.setColor(Color.black);
        g.drawString("This is Terry", 100, 65);
        g.drawImage(tex.player[0], 0, 11, 140, 145, null);

        g.drawString("Jump", 400, 65);
        g.drawImage(tex.player[8], 300, 11, 140, 145, null);

        g.drawString("Shoot", 675, 65);
        g.drawImage(tex.player[16], 550, 11, 140, 145, null);

        g.drawString("Kill", 900, 65);
        g.drawImage(tex.player[18], 775, 11, 140, 145, null);

        g.drawImage(tex.enemy[0], 110, 150, 200, 200, null);
        g.drawString("This is the Scary Enemy", 110, 315);
        g.drawString("Jump over him at the right time!", 80, 335);

        g.drawImage(tex.enemy[4], 445, 190, 112, 120, null);
        g.drawString("This is the Robot Enemy", 400, 315);
        g.drawString("He will chase you, avoid him!", 380, 335);

        g.drawImage(tex.enemy[13], 725, 190, 112, 120, null);
        g.drawString("This is the Beaver Enemy", 675, 315);
        g.drawString("He likes to jump a lot, avoid contact", 645, 335);

        g.drawImage(tex.block[2], 0, 350, 150, 150, null);
        g.drawString("Collect Coins", 15, 435);

        g.drawImage(tex.ammo[0], 300, 360, 80, 80, null);
        g.drawString("Replenish your ammo", 250, 455);

        g.drawImage(tex.block[11], 550, 350, 90, 90, null);
        g.drawString("Collect Potions", 530, 455);

        g.drawImage(tex.block[10], 770, 360, 100, 100, null);
        g.drawString("Use Potions to", 745, 435);
        g.drawString("replenish health", 740, 455);

        g.drawString("Use A/Left to move Left", 20, 600);
        g.drawString("Use D/Right to move Right", 20, 630);

        g.drawString("Use S/Down to Shoot", 785, 600);
        g.drawString("Use Space/Up to Jump", 785, 630);

        Font font = new Font("Rockwell", 1, 50);
        g.setColor(Color.black);//Set the colr
        g.setFont(font);//Set the font

        g.drawString("Return", 420, 630);
        g.drawRect(400,580,200,64);
      }

    }

    public void Timer(){

      timer = new Timer(1000, new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
         winScreen++;
        }
      });
    }

    public void LoadTimer(){

      loadingTimer = new Timer(1000, new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
         loadingScreen++;
        }
      });
    }

    private String getRank(){
      if (main.minute < 5) Main.rank[0] = 5; 
      else if (main.minute >= 5 && main.minute < 6) Main.rank[0] = 4; 
      else if (main.minute >= 6 && main.minute < 7) Main.rank[0] = 3; 
      else if (main.minute >= 7 && main.minute < 8) Main.rank[0] = 2; 
      else if (main.minute >= 8 && main.minute < 9) Main.rank[0] = 1; 
      else if (main.minute >= 9) Main.rank[0] = 0; 

      if (Main.continues <= 5) Main.rank[1] = 5; 
      else if (Main.continues > 5 && Main.continues <= 7) Main.rank[1] = 4;
      else if (Main.continues > 7 && Main.continues <= 9) Main.rank[1] = 3;
      else if (Main.continues > 9 && Main.continues <= 11) Main.rank[1] = 2;
      else if (Main.continues > 11 && Main.continues <= 13) Main.rank[1] = 1;
      else if (Main.continues > 13) Main.rank[1] = 0;

      if (Main.totalCoins <= 23 && Main.totalCoins >= 15) Main.rank[2] = 5; 
      else if (Main.totalCoins <= 14 && Main.totalCoins >= 10) Main.rank[2] = 4; 
      else if (Main.totalCoins <= 9 && Main.totalCoins >= 6) Main.rank[2] = 3;
      else if (Main.totalCoins <= 5 && Main.totalCoins >= 3) Main.rank[2] = 2;
      else if (Main.totalCoins <= 2 && Main.totalCoins >= 1) Main.rank[2] = 1;
      else if (Main.totalCoins == 0) Main.rank[2] = 0;

      int counter = 0; 

      for(int i = 0; i < Main.rank.length; i++){
        counter += Main.rank[i];
      }

      if (counter >= 14 && counter <= 15) return "S"; 
      else if (counter >= 12 && counter <= 13) return "A"; 
      else if (counter >= 10 && counter <= 11) return "B"; 
      else if (counter >= 8 && counter <= 9) return "C"; 
      else if (counter >= 6 && counter <= 7) return "D";
      else{
        return "F";
      } 
    }
    
}
