import java.awt.*;
import javax.swing.Timer;
import java.awt.image.BufferStrategy;
import java.text.DecimalFormat;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends Canvas implements Runnable{

    public static final int WIDTH = 1000, HEIGHT = 700;
    private Thread thread; 
    private boolean running = false; 
    public static int Level = 1; 
    public static String levelDisplay = "1A"; 
    public static int continues = 0; 
    public static int[] rank = new int[3]; //S: 14-15, A: 12-13, B: 10-11, C: 8-9, D: 6-7, F <= 5
    public static int totalCoins = 0; 

    //Instances
    private Handler handler;
    private HUD hud;
    Camera cam; 
    static Texture tex; 

    Timer timer; 
    int second, minute; 
    String ddSecond, ddMinute; 
    DecimalFormat dFormat = new DecimalFormat("00");

    public enum STATE{Menu, Game,Help,GameOver, Win, Loading};
    public STATE gameState = STATE.Menu; 
    Menu menu; 
    private int backgroundCounter = 0; 
    Timer timer2; 
    
    public Main(){
      this.setFocusable(true);
      second = 0; 
      minute = 0;

      tex = new Texture();

      cam = new Camera(0, 0); 
      handler = new Handler(this, cam);  
      hud = new HUD(handler);
      menu = new Menu(this, handler);
      Timer();
      BackgroundTimer();
      timer2.start();

      this.addKeyListener(new KeyInput(this, handler));
      this.addMouseListener(menu);

      
      new Window(WIDTH, HEIGHT, "Game", this);
    }

    public synchronized void start(){
      if(running) return; 

      thread = new Thread(this); 
      thread.start();
      running = true; 
    }

    public synchronized void stop(){
      try{
        thread.join();
        running = false; 
      }catch(Exception e){
        e.printStackTrace();
      }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime(); 
        double amountOfTicks = 60.0; 
        double ns = 1000000000 / amountOfTicks; 
        double delta = 0; 
        long timer = System.currentTimeMillis(); 
        int frames = 0; 
        while(running){
          long now = System.nanoTime(); 
          delta += ((now - lastTime) / ns);
          lastTime = now; 
          while(delta >= 1){
            tick(); 
            delta--;
          }
          if(running) {
            render(); 
            frames++; 
          }
          if((System.currentTimeMillis() - timer) > 1000){
            timer += 1000; 
            //System.out.println("FRAMES: " + frames);
            frames = 0; 
          }
          
        } 
        stop(); 
      }

      private void tick()
      {
        handler.tick();

        if (backgroundCounter == 15) backgroundCounter = 0; 

        if (gameState == STATE.Game){

          try{

              for(int i = 0; i < handler.object.size(); i++){

                  if(handler.object.get(i).getId() == ID.Player){

                       cam.tick(handler.object.get(i));
          }

        } 
      }catch(IndexOutOfBoundsException | NullPointerException exception) {}
      }
    }

      private void render(){

        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
          this.createBufferStrategy(3);
          return; 
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D)g;

        if (gameState == STATE.Game){

          if (0 < Level && Level < 5) g.drawImage(tex.background, 0, 0, WIDTH, HEIGHT, null);
          if (5 <= Level && Level < 9) g.drawImage(tex.background2, 0, 0, WIDTH, HEIGHT, null);
          if (Level >= 9) g.drawImage(tex.background3, 0, 0, WIDTH, HEIGHT, null);
        
          g2d.translate(cam.getX(), cam.getY());//Begin of cam 

          handler.render(g);

          hud.render(g);

          g2d.translate(-cam.getX(), -cam.getY());//End of cam

          g.setFont(new Font("Rockwell", Font.PLAIN, 20));

          if(second != 0 || minute != 0) g.drawString(ddMinute + ":" + ddSecond, 900, 29);
          g.drawString("Level: " + levelDisplay, 800, 29);
        }else if (gameState == STATE.Loading){
          g.drawImage(tex.background3, 0, 0, WIDTH, HEIGHT, null);
           menu.render(g);
        }
        else {
          if (0 <= backgroundCounter && backgroundCounter < 5) g.drawImage(tex.background, 0, 0, WIDTH, HEIGHT, null);
          else if (5 <= backgroundCounter && backgroundCounter < 10) g.drawImage(tex.background2, 0, 0, WIDTH, HEIGHT, null);
          else if (10 <= backgroundCounter && backgroundCounter < 15) g.drawImage(tex.background3, 0, 0, WIDTH, HEIGHT, null);
          menu.render(g);
        }
        
        g.dispose(); 
        bs.show(); 
      }
      
      
      public static float clamp(float val, float min, float max){
        return Math.max(min,(Math.min(max, val))); 
      }  

      public static Texture getInstance(){
        return tex; 
      }

      public void Timer(){

        timer = new Timer(1000, new ActionListener(){

          @Override
          public void actionPerformed(ActionEvent e) {
           second++; 
           ddSecond = dFormat.format(second);
           ddMinute = dFormat.format(minute);

           if(second == 60){
            second = 0; 
            minute++; 

            ddSecond = dFormat.format(second);
            ddMinute = dFormat.format(minute);
           }
          }
        });
      }

      public void BackgroundTimer(){

        timer2 = new Timer(1000, new ActionListener(){

          @Override
          public void actionPerformed(ActionEvent e) {
           backgroundCounter++;
          }
        });
      }

    
    public static void main(String[] args) {
      new Main();
    }
}
