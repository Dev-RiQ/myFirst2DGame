package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3; // 16픽셀 너무 작아서 3배 확대
	// 확대 후 타일 크기 및 화면 표시 타일 갯수 지정 (줌인 줌아웃 넣으려면 파이널 제거-> 타일 사이즈 변동)
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// 월드 세팅
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this); // 맵 세팅
	KeyHandler keyH = new KeyHandler(); // 키 조작 세팅
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this); // 콜리전 체크
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread; // 계속 진행되게 만듬
	// 캐릭터, 오브젝트
	public Player player = new Player(this,keyH); // 캐릭터 세팅
	public SuperObject obj[] = new SuperObject[10]; // 오트젝트 10개 쓸거임 (많아지면 느려짐)
	
	// 기본 화면 세팅
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight)); // 크기
		this.setBackground(Color.black); // 배경 (검정으로 주로함)
		this.setDoubleBuffered(true); // 화면 전환 부드럽게
		this.addKeyListener(keyH); // 키보드 먹게
		this.setFocusable(true); // 해당 패널이 우선으로 키 이벤트 포커싱
	}
	// 오브젝트 생성
	public void setupGame() {
		aSetter.setObject();
		playMusic(0); // 배경음 출력
	}
	// 시작하면 스레드 동작
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	// 스레드 동작 구간
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS; // 나노타임 너무 빨라서 화면 안나오니까 FPS기반 설정
		double delta = 0; // 1초 / FPS 마다 정보 및 화면 업데이트 위한 변수
		long lastTime = System.nanoTime(); // 1번 시간
		long currentTime; // 2번 시간
		long timer = 0; // 약 1초마다 FPS 정보 얻기 위함
		int drawCount = 0; // timer 1초 이상일때까지 업데이트된 횟수 (FPS)
		// 스레드 동작하는 동안 무한 반복
		while(gameThread != null) {
			currentTime = System.nanoTime(); // 반복 시작 시간
			delta += (currentTime - lastTime) / drawInterval; // 실제 흐른 시간 누적 측정
			timer += (currentTime - lastTime); // nanoTime 누적 측정
			lastTime = currentTime; // 측정 종료 후 시간
			if(delta >=1) { // 1초/FPS 와 같은지?
				update(); // 게임 정보 갱신
				repaint(); // 화면 출력 갱신, 명령 접수 -> update 확인 -> paint()
				delta --; // 초기화
				drawCount++; // 갱신 횟수 증가
			}
			if(timer >= 1000000000) { // 측정 시간이 1초 이상인지?
				System.out.println("FPS : "+drawCount); // 실 측정 FPS 출력
				drawCount = 0;
				timer = 0;
			}
		}
	}
	// 정보 갱신
	public void update() {
		player.update();
	}
	// 화면 출력 갱신
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // 부모 클래스 호출
		Graphics2D g2 = (Graphics2D)g; // 형 변환
		// 타일 먼저해야 레이어 상으로 캐릭터가 위로옴
		tileM.draw(g2); // 배경 그리기
		for(int i = 0; i < obj.length; i++) { // 오브젝트 그리기
			if(obj[i] != null) { // 에러 방지
				obj[i].draw(g2, this);
			}
		}
		player.draw(g2); // 캐릭터 그리기
		ui.draw(g2); // UI 그리기
		g2.dispose(); // 겹치기 방지 종료 (해당 컴포넌트만), exit() 하면 다꺼짐
	}
	
	public void playMusic(int i) { //베경음
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) { // 효과음 (단발성)
		se.setFile(i);
		se.play();
	}
}
