//Importing
import java.awt.Color; 
import java.awt.Graphics; 
import java.awt.Rectangle;

public class FlagPole extends GameObject{

    Texture tex = Main.getInstance();
  
  //Flagpole constructor
  public FlagPole(int x, int y, ID id, int health){
    super(x,y,id, health);//Parent Class is GameObject 
  }
  
  //Empty tick method (object doesnt move)
  public void tick(){}
  
  //Render method
  public void render(Graphics g){
    
    //Creating Flagpole
    g.setColor(Color.yellow);
    g.drawImage(tex.block[7], (int)x - 50, (int)y - 45, 100, 105, null); 
  }
  
  //Flagpole hitbox
  public Rectangle getBounds(){
    return new Rectangle((int)x - 5,(int)y - 24,30,55);
  }
  
}
