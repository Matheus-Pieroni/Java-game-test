package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity{
    
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }
    
    public void getPlayerImage() {
        try {
            up1 =  ImageIO.read(getClass().getResourceAsStream("/res/player_spr/player_up1.png"));
            up2 =  ImageIO.read(getClass().getResourceAsStream("/res/player_spr/player_up2.png"));
            down1 =  ImageIO.read(getClass().getResourceAsStream("/res/player_spr/player_down1.png"));
            down2 =  ImageIO.read(getClass().getResourceAsStream("/res/player_spr/player_down2.png"));
            left1 =  ImageIO.read(getClass().getResourceAsStream("/res/player_spr/player_left1.png"));
            left2 =  ImageIO.read(getClass().getResourceAsStream("/res/player_spr/player_left2.png"));
            right1 =  ImageIO.read(getClass().getResourceAsStream("/res/player_spr/player_right1.png"));
            right2 =  ImageIO.read(getClass().getResourceAsStream("/res/player_spr/player_right2.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //METODO UPDATE! CHAMADO NO GAMELOOP DO "GamePanel" um número de vezes correspondente ao FPS atual.
    public void update() {
        
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed == true) {
            direction = "up";
            y = y - speed;
        }
        else if (keyH.downPressed == true) {
            direction = "down";
            y = y + speed;
        }
        
        else if (keyH.leftPressed == true) {
            direction = "left";
            x = x - speed;
        }
        else if (keyH.rightPressed == true) {
            direction = "right";
            x = x + speed;
        }
        
        spriteCounter++;
        if (spriteCounter > 12){
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        }

    }

    public void draw(Graphics2D g2) {

        /*g2.setColor(Color.GREEN);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);*/
        
        BufferedImage image = null;
        
        switch (direction) {
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        
    }
}
