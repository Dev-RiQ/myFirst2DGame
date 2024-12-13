package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {

	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48); // 오브젝트 범위
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	// 오브젝트 출력
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		// 보이는 구간만 드로잉
		if(worldX > gp.player.worldX - gp.player.screenX - gp.tileSize&&
			worldX < gp.player.worldX + gp.player.screenX + gp.tileSize&& 
			worldY > gp.player.worldY - gp.player.screenY - gp.tileSize&& 
			worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // 해당하는 타일 출력
		}
	}
}
