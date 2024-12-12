package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	//보여지는 화면
	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	// 화면 표시와 행동에 영향을 주는 요인 적용
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		//캐릭터 중앙위치 (배경이 이동)
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		// 캐릭터 히트박스 범위
		solidArea = new Rectangle();
		solidArea.x = 0 + 23;
		solidArea.y = 0 + 23;
		solidArea.width = gp.tileSize - 46;
		solidArea.height = gp.tileSize - 30;
		
		setDefaultValues(); // 현재 캐릭터 정보 로드
		getPlayerImage(); // 요인에 따른 이미지 출력
	}
	// 플레이어 데이터
	public void setDefaultValues() {
		// 월드 맵 크기
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	// 행동에 따른 이미지 로드
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	// 키 입력에 따른 명령 및 캐릭터 정보 변경
	public void update() { // => 현재 else if라서 상위 키 중 하위 키로 변경 불가
		if(keyH.upPressed == true || keyH.downPressed == true ||
				keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if(keyH.upPressed == true) {
				direction = "up";
			}else if(keyH.downPressed == true) {
				direction = "down";
			}else if(keyH.leftPressed == true) {
				direction = "left";
			}else if(keyH.rightPressed == true) {
				direction = "right";
			}
			// 콜리젼 체크
			collisionOn = false;
			gp.cChecker.checkTile(this);
			// 콜리젼 아닌 곳 이동 가능
			if(collisionOn == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			// 움직일때만 이미지 교차
			spriteCounter++;
			if(spriteCounter > 15) { // 15프레임 마다 이미지 교체 ( 걷는 모션 )
				if(spriteNum == 1) {
					spriteNum = 2;
				}else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
				
			}
		}else {
			// 키 떼면 살짝 더가서 멈추기모션 (달리기 모션으로 멈추기 X)
			standCounter++;
			if(standCounter == 20) {
				spriteNum = 1;
				standCounter = 0;
			}
		}
	}
	// update 정보에 따라 보여질 캐릭터 이미지 설정
	public void draw(Graphics2D g2) {
//		캐릭터 출력 초기 모델 테스트
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
		}
		// 최종 이미지 종류 및 위치대로 출력 -> GamePanel로 넘어감
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
