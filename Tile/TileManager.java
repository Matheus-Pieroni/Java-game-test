package Tile;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];
    
    public TileManager(GamePanel gp) {
        this.gp = gp;
        
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        
        getTileImage();
        loadMap();
    }
    
    public void getTileImage() {
        
        try {
            
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass.png"));
            
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png"));
            
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Lembrete de comentar esse processo aqui! Super importante!!!!
    public void loadMap() {
        
        try {
            InputStream is = getClass().getResourceAsStream("/res/maps/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0, row = 0;
            
            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                
                String line = br.readLine();
                
                while (col < gp.maxScreenCol) {
                    
                    String numbers[] = line.split(" ");
                    
                    int num = Integer.parseInt(numbers[col]);
                    
                    mapTileNum[col][row] = num;
                    col++;
                }
                
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                    
                }
            }
            //Aqui eu finalizo o leitor, porque ele CONSOME RECURSOS!!!
            br.close();
            
        } catch (Exception e) {
        }
        
    }
    
    public void draw(Graphics2D g2){
        
        /* PRIMEIROS TILES DESENHADOS!!!
        g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 48, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[2].image, 96, 0, gp.tileSize, gp.tileSize, null);
        */
        
        int col = 0, row = 0;
        int x = 0, y = 0;
        
        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            
            int tileNum = mapTileNum[col][row];
            
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;
            
            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
