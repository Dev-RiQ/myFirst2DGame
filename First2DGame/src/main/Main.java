package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// 프레임 설정
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 누르면 꺼짐
		window.setResizable(false); // 창 크기 조절 가능?
		window.setTitle("2D Adventure"); // 창 타이틀 변경
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel); // 프레임에 게임 화면 띄우기
		window.pack(); // 프레임 출력에 맞게 크기 조절 (설정 화면 크기로 자동 변경)
		
		window.setLocationRelativeTo(null); // 화면 가운데 뜸 (뜨는데 지정 null)
		window.setVisible(true); // 창 띄우기
		
		gamePanel.setupGame(); // 오브젝트 띄우기
		gamePanel.startGameThread(); // 패널 켜지면 스레드 돌리기
	}

}
