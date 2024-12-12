package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean upPressed1, downPressed1, leftPressed1, rightPressed1;
	int cnt = 0;
	int cnt2 = 0;
	@Override
	public void keyTyped(KeyEvent e) { // 문자만 반응함 (그래서 안씀)
	}

	@Override
	public void keyPressed(KeyEvent e) { // 키보드 누르면 호출 (모든 키보드)
		int code = e.getKeyCode(); // 누른 키 아스키코드 리턴 (getKeyChar->문자 리턴/getModifiers->shift,ctrl,alt 인식 1,2,8 리턴
		if(code==KeyEvent.VK_UP) {
			upPressed = true;
		}
		if(code==KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if(code==KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if(code==KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { // 키보드 떼면 호출 (모든 키보드)
		int code = e.getKeyCode();
		if(code==KeyEvent.VK_UP) {
			upPressed = false;
		}
		if(code==KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if(code==KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if(code==KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
	}

}
