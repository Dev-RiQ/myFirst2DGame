package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	public Tile[] tile; // 배경 출력 이미지 선택용
	public int mapTileNum[][]; // 배경 출력 이미지 선택용
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10]; // 맵 데이터에서 필요한 타일 갯수 만큼 생성
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // 고정형 배경 데이터(화면 크기)
		getTileImage(); // 맵 타일에 해당하는 이미지 로드
		loadMap("/maps/world01.txt"); // 맵 데이터 로드
	}
	// 맵 이미지 로드
	public void getTileImage() {
		try{
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	// txt 기반 맵 정보 배열화
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath); // is에 txt 대치
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); // is 값 저장
			int col = 0;
			int row = 0;
			// 맵 정보 txt 기반 배열 생성
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine(); // 저장 값의 \n 까지 (한줄) 저장 후 (해당 줄) 삭제, 반복하면 자동 row 변경
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" "); // 인덱스 별 값 추출
					int num = Integer.parseInt(numbers[col]); // 해당 맵 정보 숫자화
					mapTileNum[col][row] = num; // 해당 맵 정보 저장
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(IOException e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		// 맵 정보 배열 기반 타일 깔기
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow]; // 타일 정보 로드
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			// 보이는 구간만 드로잉
			if(worldX > gp.player.worldX - gp.player.screenX - gp.tileSize&&
				worldX < gp.player.worldX + gp.player.screenX + gp.tileSize&& 
				worldY > gp.player.worldY - gp.player.screenY - gp.tileSize&& 
				worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null); // 해당하는 타일 출력
			}
			worldCol++;
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
}
