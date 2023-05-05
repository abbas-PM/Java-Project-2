import java.awt.image.BufferedImage;

public class Texture {

    SpriteSheet ps; 
    SpriteSheet bs; 
    SpriteSheet bg; 
    SpriteSheet am; 
    SpriteSheet em;
    SpriteSheet bf; 
    private BufferedImage player_sheet = null; 
    private BufferedImage block_sheet = null;
    private BufferedImage ammo_sheet = null; 
    private BufferedImage enemy_sheet = null;
    private BufferedImage boss_sheet = null;  
    public BufferedImage background = null; 
    public BufferedImage background2 = null; 
    public BufferedImage background3 = null; 

    public BufferedImage[] player = new BufferedImage[28];
    public BufferedImage[] block = new BufferedImage[15]; 
    public BufferedImage[] ammo = new BufferedImage[11];
    public BufferedImage[] enemy = new BufferedImage[18]; 
    public BufferedImage[] boss = new BufferedImage[3];

    public Texture(){
        BufferedImageLoader loader = new BufferedImageLoader(); 
        try{
            player_sheet = loader.loadImage("/player_sheet.png");
            block_sheet = loader.loadImage("/block_sheet.png");
            background = loader.loadImage("/Background.png");
            background2 = loader.loadImage("/Background2.gif");
            background3 = loader.loadImage("/Background3.gif");
            ammo_sheet = loader.loadImage("/ammo_sheet.png");
            enemy_sheet = loader.loadImage("/enemy_sheet.png");
            boss_sheet = loader.loadImage("/boss_sheet.png");
        }catch(Exception e){
            e.printStackTrace();
        }

        ps = new SpriteSheet(player_sheet);
        bs = new SpriteSheet(block_sheet);
        am = new SpriteSheet(ammo_sheet); 
        em = new SpriteSheet(enemy_sheet);
        bf = new SpriteSheet(boss_sheet); 

        getTextures();
    }

    private void getTextures(){
        player[0] = ps.grabImage(1, 1, 50, 50);//idle for player(Right)
        player[1] = ps.grabImage(6, 1, 50, 50);//idle for player(Left)

        player[2] = ps.grabImage(3, 1, 50, 50);//Walk animation 1(Right)
        player[3] = ps.grabImage(4, 1, 50, 50);//Walk animation 2(Right)
        player[4] = ps.grabImage(5, 1, 50, 50);//Walk animation 3(Right)
        player[5] = ps.grabImage(8, 1, 50, 50);//Walk animation 1(Left)
        player[6] = ps.grabImage(9, 1, 50, 50);//Walk animation 2(Left)
        player[7] = ps.grabImage(10, 1, 50, 50);//Walk animation 3(Left)

        player[8] = ps.grabImage(2, 1, 50, 50);//Jump(Right)
        player[9] = ps.grabImage(7, 1, 50, 50);//Jump(Left)

        player[10] = ps.grabImage(11, 1, 50, 50);//Punch animation 1(Right)
        player[11] = ps.grabImage(13, 1, 50, 50);//Punch animation 2(Right)
        player[12] = ps.grabImage(15, 1, 50, 50);//Punch animation 1(Right)
        player[13] = ps.grabImage(12, 1, 50, 50);//Punch animation 1(Left)
        player[14] = ps.grabImage(14, 1, 50, 50);//Punch animation 1(Left)
        player[15] = ps.grabImage(16, 1, 50, 50);//Punch animation 1(Left)

        //Shooting animation
        player[16] = ps.grabImage(17, 1, 50, 50);//Stance(Right)
        player[17] = ps.grabImage(18, 1, 50, 50);//Stance(Left)

        player[18] = ps.grabImage(27, 1, 50, 50);//Jump(Right)
        player[19] = ps.grabImage(28, 1, 50, 50);//Jump(Left)

        player[20] = ps.grabImage(19, 1, 50, 50);//Walk1(Right)
        player[21] = ps.grabImage(20, 1, 50, 50);//Walk2(Right)
        player[22] = ps.grabImage(21, 1, 50, 50);//Walk3(Right)

        player[23] = ps.grabImage(22, 1, 50, 50);//Walk1(Left)
        player[24] = ps.grabImage(23, 1, 50, 50);//Walk2(Left)
        player[25] = ps.grabImage(24, 1, 50, 50);//Walk3(Left)

        //Hit animation
        player[26] = ps.grabImage(25, 1, 50, 50);//(Right)
        player[27] = ps.grabImage(26, 1, 50, 50);//(Left)

        block[0] = bs.grabImage(1, 1, 32, 32);//grass block
        block[1] = bs.grabImage(2, 1, 32, 32);//tile
        block[12] = bs.grabImage(13, 1, 32, 32);//mushroom; 
        block[13] = bs.grabImage(14, 1, 32, 32);//tile2
        block[14] = bs.grabImage(15, 1, 32, 32);//grass block3;

        //Coin animation
        block[2] = bs.grabImage(3, 1, 32, 32);
        block[3] = bs.grabImage(4, 1, 32, 32);
        block[4] = bs.grabImage(5, 1, 32, 32);
        block[5] = bs.grabImage(6, 1, 32, 32);

        block[6] = bs.grabImage(7, 1, 32, 32);//platform
        block[7] = bs.grabImage(8, 1, 32, 32);//flag

        //Bullet
        block[8] = bs.grabImage(9, 1, 32, 32);
        block[9] = bs.grabImage(10, 1, 32, 32);

        //Heart sprite
        block[10] = bs.grabImage(11, 1, 32, 32); 

        //Potion sprite
        block[11] = bs.grabImage(12, 1, 32, 32); 

        //Ammo
        ammo[0] = am.grabImage(1, 1, 32, 32);//10
        ammo[1] = am.grabImage(2, 1, 32, 32);//9
        ammo[2] = am.grabImage(3, 1, 32, 32);//8
        ammo[3] = am.grabImage(4, 1, 32, 32);//7
        ammo[4] = am.grabImage(5, 1, 32, 32);//6
        ammo[5] = am.grabImage(6, 1, 32, 32);//5
        ammo[6] = am.grabImage(7, 1, 32, 32);//4
        ammo[7] = am.grabImage(8, 1, 32, 32);//3
        ammo[8] = am.grabImage(9, 1, 32, 32);//2
        ammo[9] = am.grabImage(10, 1, 32, 32);//1
        ammo[10] = am.grabImage(11, 1, 32, 32);//0

        //ScaryEnemy
        enemy[0] = em.grabImage(1, 1, 32, 32);
        enemy[1] = em.grabImage(2, 1, 32, 32);
        enemy[2] = em.grabImage(9, 1, 32, 32);//Angry(Left)
        enemy[3] = em.grabImage(10, 1, 32, 32);//Angry(Right)
        enemy[8] = em.grabImage(15, 1, 32, 32);//Extended(Left)
        enemy[9] = em.grabImage(16, 1, 32, 32);//Extended(Right)
        enemy[10] = em.grabImage(17, 1, 32, 32);//Extended(Left)Angry
        enemy[11] = em.grabImage(18, 1, 32, 32);//Extended(Right)Angry

        //RobotEnemy
        enemy[4] = em.grabImage(11, 1, 32, 32);
        enemy[5] = em.grabImage(12, 1, 32, 32);
        enemy[6] = em.grabImage(13, 1, 32, 32);//Angry(Left)
        enemy[7] = em.grabImage(14, 1, 32, 32);//Angry(Right)

        //BeaverEnemy
        enemy[12] = em.grabImage(19, 1, 32, 32);//(Right)
        enemy[13] = em.grabImage(20, 1, 32, 32);//(Left)
        enemy[14] = em.grabImage(21, 1, 32, 32);//(Right)Angry
        enemy[15] = em.grabImage(22, 1, 32, 32);//(Left)Angry
        //BeaverDeath
        enemy[16] = em.grabImage(23, 1, 32, 32);//(Part1)
        enemy[17] = em.grabImage(24, 1, 32, 32);//(Part1)

        //Boss Animations
        boss[0] = bf.grabImage(1, 1, 50, 50);
        boss[1] = bf.grabImage(2, 1, 50, 50);
        boss[2] = bf.grabImage(3, 1, 50, 50);
    }
    
}
