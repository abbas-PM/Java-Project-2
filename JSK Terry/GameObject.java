import java.awt.Graphics; 
 import java.awt.Rectangle;

public abstract class GameObject {
    
    protected float x;
	protected float y;
    protected ID id; 
    protected float velX, velY; 
    protected boolean falling, jumping;
    protected int health;  
    
    public GameObject(float x, float y, ID id, int health){
        this.x = x;
        this.y = y;
        this.id = id; 
        this.health = health; 
    }

    public abstract void tick(); 
    public abstract void render(Graphics g); 
    public abstract Rectangle getBounds(); 

    //SetX method
    public void setX(float x){
        this.x = x;  
    }
    //SetY method
    public void setY(float y){
        this.y = y; 
    }
    //GetX method
    public float getX(){
        return x; 
    }
    //GetY method
    public float getY(){
        return y;  
    }
    //SetID method
    public void setID(ID id){
        this.id = id;  
    }
    //GetID method
    public ID getId(){
        return id; 
    }
    //SetVelX method
    public void setVelX(float velX){
        this.velX = velX; 
    }
    //SetVelY method
    public void setVelY(float velY){
        this.velY = velY; 
    }
    //GetVelX method
    public float getVelX(){
        return velX; 
    }
    //GetVelY method
    public float getVelY(){
        return velY; 
    }

    public boolean isFalling(){
        return falling; 
      }
    
    public void setFalling(boolean falling){
        this.falling = falling; 
      }
    
    public boolean isJumping(){
        return jumping; 
      }
    
    public void setJumping(boolean jumping){
        this.jumping = jumping; 
      }

    public int getHealth(){
        return health; 
      }

    public void setHealth(int health){
        this.health = health; 
    }
}
