package java_game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

public class KeyHandler implements KeyListener, Serializable {

	private GamePanel gp;

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean jumpPressed, jumpRight, jumpLeft;
	public boolean spacePressed;
	public boolean onePressed, twoPressed, threePressed;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_P) {
			if (gp.isPaused) {
				gp.resumeGame();
			} else {
				gp.pauseGame();
			}
	}else if(code==KeyEvent.VK_K && gp.isPaused)

	{
		gp.saveLoad.saveGame("save.sav");
	}
	if(code==KeyEvent.VK_W){
		if(gp.canUseWildcard())
		gp.startWildcardTimer();else System.out.println("kullanılamaz");
		}
	if(code==KeyEvent.VK_SPACE)
	{
		spacePressed = true;
	}if(code==KeyEvent.VK_1)
	{
		onePressed = true;
	}if(code==KeyEvent.VK_2)
	{
		twoPressed = true;
	}if(code==KeyEvent.VK_3)
	{
		threePressed = true;
	}

	if(code==KeyEvent.VK_UP)
	{
		jumpPressed = true;
	}if(code==KeyEvent.VK_UP&&code==KeyEvent.VK_RIGHT)
	{
		jumpRight = true;
	}if(code==KeyEvent.VK_UP&&code==KeyEvent.VK_LEFT)
	{
		jumpLeft = true;
	}if(code==KeyEvent.VK_DOWN)
	{
		downPressed = true;
	}if(code==KeyEvent.VK_LEFT)
	{
		leftPressed = true;
	}if(code==KeyEvent.VK_RIGHT)
	{
		rightPressed = true;
	}
}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
		if (code == KeyEvent.VK_1) {
			onePressed = false;
		}
		if (code == KeyEvent.VK_2) {
			twoPressed = false;
		}
		if (code == KeyEvent.VK_3) {
			threePressed = false;
		}

		if (code == KeyEvent.VK_UP && code == KeyEvent.VK_RIGHT) {
			jumpRight = false;
		}
		if (code == KeyEvent.VK_UP && code == KeyEvent.VK_LEFT) {
			jumpLeft = false;
		}
		if (code == KeyEvent.VK_UP) {
			jumpPressed = false;
		}
		if (code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_LEFT) {
			gp.player.playerDirectionX = -1;
			leftPressed = false;
		}
		if (code == KeyEvent.VK_RIGHT) {
			gp.player.playerDirectionX = 1;
			rightPressed = false;
		}

	}

}
