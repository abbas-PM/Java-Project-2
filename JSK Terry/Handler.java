import java.awt.Graphics; 
import java.util.LinkedList; 
import java.awt.image.BufferedImage;

public class Handler {

    LinkedList<GameObject> object = new LinkedList<GameObject>(); 

    Main main;
    private Camera cam; 

    public BufferedImage level2 = null;
    public BufferedImage level3 = null; 
    public BufferedImage level4 = null; 
    public BufferedImage level5 = null;
    public BufferedImage level6 = null;
    public BufferedImage level7 = null;
    public BufferedImage level8 = null; 
    public BufferedImage level9 = null;
    public BufferedImage level10 = null;

    public Handler(Main main, Camera cam){
        this.cam = cam; 
        this.main = main; 

        BufferedImageLoader loader = new BufferedImageLoader(); 
        level2 = loader.loadImage("/Level2.png");
        level3 = loader.loadImage("/Level3.png");
        level4 = loader.loadImage("/Level4.png");
        level5 = loader.loadImage("/Level5.png");
        level6 = loader.loadImage("/Level6.png");
        level7 = loader.loadImage("/Level7.png");
        level8 = loader.loadImage("/Level8.png");
        level9 = loader.loadImage("/Level9.png");
        level10 = loader.loadImage("/Level10.png");
    }

    public void tick(){

        try{

            for(int i = 0; i < object.size();i++){

                GameObject tempObject = object.get(i);

                tempObject.tick(); 
            }

    }catch(IndexOutOfBoundsException | NullPointerException exception) {}
}

    public void render(Graphics g){

        try{

            for(int i = 0; i < object.size();i++){

                GameObject tempObject = object.get(i);

                tempObject.render(g); 
            }
        }catch(IndexOutOfBoundsException | NullPointerException exception) {}

    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    public GameObject getPlayer(){
        for(int i = 0; i < object.size();i++){

            if (object.get(i).getId() == ID.Player) return object.get(i); 
        }

        return null; 
    }

    public void ClearAll(){
        object.clear(); 
        
    }

    public void LoadImageLevel(BufferedImage image){
        int w = image.getWidth(); 
        int h = image.getHeight(); 

        for(int xx = 0; xx < h; xx++){
          for(int yy = 0; yy < w; yy++){
            int pixel = image.getRGB(xx, yy);
            int red = (pixel >> 16) & 0xff; 
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel) & 0xff;

            if(red == 255 && green == 255 && blue == 255) addObject(new Blocks(xx * 32,yy * 32,ID.Blocks, 0, "grass"));
            if(red == 211 && green == 211 && blue == 211) addObject(new Blocks(xx * 32,yy * 32,ID.Blocks, 0, "tile"));
            if(red == 120 && green == 0 && blue == 0) addObject(new Blocks(xx * 32,yy * 32,ID.Blocks, 0, "mushroom"));
            if(red == 170 && green == 0 && blue == 0) addObject(new Blocks(xx * 32,yy * 32,ID.Blocks, 0, "t2"));
            if(red == 0 && green == 0 && blue == 100) addObject(new Blocks(xx * 32,yy * 32,ID.Blocks, 0, "gb3"));
            if(red == 0 && green == 0 && blue == 255) addObject(new Player(xx*32, (yy * 32) + 20, ID.Player, cam, this, 100, main));
            if(red == 246 && green == 255 && blue == 0) addObject(new Coin(xx * 32, yy * 32, ID.Coin, 0));
            if(red == 255 && green == 0 && blue == 0) addObject(new Invisible_Wall(xx * 32, yy * 32,  ID.Invisible_Wall, 0));
            if(red == 0 && green == 255 && blue == 0) addObject(new FakeWall(xx * 32, yy * 32, ID.FakeWall, 0));
            if(red == 160 && green == 160 && blue == 160) addObject(new Platform(xx * 32, yy * 32, ID.Platform, this, 0));
            if(red == 255 && green == 216 && blue == 0) addObject(new FlagPole(xx * 32, yy * 32, ID.FlagPole, 0)); 
            if(red == 255 && green == 192 && blue == 203) addObject(new Scary_Enemy(xx * 32, yy * 32, ID.Scary_Enemy, this, 20));
            if(red == 255 && green == 106 && blue == 0) addObject(new Robot_Enemy(xx * 32, yy * 32, ID.Robot_Enemy, this, 15, main));
            if(red == 58 && green == 29 && blue == 0) addObject(new Beaver_Enemy(xx * 32, yy * 32, ID.Beaver_Enemy, this, 10));
            if(red == 75 && green == 0 && blue == 0) addObject(new Boss(xx * 32, yy * 32, ID.Boss, this, 10000, main));
          }
        }
      }

    public void switchLevel(int ogHealth){
        ClearAll();
        cam.setX(0);
        Bullet.allowed = true; 
        KeyInput.cooldown = 0; 
        try {
        switch (Main.Level) {
            case 1:
                LoadImageLevel(level2); 
                Main.Level += 1; 
                Main.levelDisplay = "1B";
                break;
            case 2:
                LoadImageLevel(level3);
                Main.Level += 1; 
                Main.levelDisplay = "1C";
                break; 
            case 3:
                LoadImageLevel(level4);
                getPlayer().setHealth(ogHealth);
                Main.Level += 1; 
                Main.levelDisplay = "1D";
                break;
            case 4:
                LoadImageLevel(level5);
                getPlayer().setHealth(ogHealth);
                Main.Level += 1; 
                Main.levelDisplay = "2A";
                break;
            case 5:
                LoadImageLevel(level6);
                getPlayer().setHealth(ogHealth);
                Main.Level += 1; 
                Main.levelDisplay = "2B";
                break;
            case 6:
                LoadImageLevel(level7);
                getPlayer().setHealth(ogHealth);
                Main.Level += 1; 
                Main.levelDisplay = "2C";
                break;
            case 7:
                LoadImageLevel(level8);
                getPlayer().setHealth(ogHealth);
                Main.Level += 1; 
                Main.levelDisplay = "2D";
                break;  
            case 8: 
                LoadImageLevel(level9);
                Player.Ammo = 10; 
                Main.Level += 1; 
                Main.levelDisplay = "3A";
                break;  
            case 9:
                main.gameState = Main.STATE.Loading;
                LoadImageLevel(level10);
                main.timer.stop();
                Menu.currminute = main.minute;
                Menu.currsecond = main.second;
                Menu.loadingTimer.start();
                Main.Level += 1; 
                Main.levelDisplay = "3B";
                break;  
            case 10: 
                main.gameState = Main.STATE.Win; 
                main.timer.stop();
                break;    
        }
    }catch(NullPointerException exception){}
    }

}
