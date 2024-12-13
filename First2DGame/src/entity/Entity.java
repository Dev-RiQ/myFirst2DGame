package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
// 플레이어 데이터 객체
public class Entity {
	// 위치 및 이동 속도
	public int worldX, worldY;
	public int speed;
	// 키 입력 모션
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction; // 방향
	
	public int spriteCounter = 0; // 화면 전환 횟수 (초당 FPS회)
	public int spriteNum = 1; // 모션 변경 시점 측정 변수
	
	public Rectangle solidArea; // 히트박스
	public int solidAreaDefaultX, solidAreaDefaultY; // 오브젝트용
	public boolean collisionOn = false;
}
