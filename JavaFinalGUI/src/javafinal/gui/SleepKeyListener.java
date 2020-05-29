package javafinal.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SleepKeyListener implements KeyListener
{
	boolean pressed_s = false;
	boolean pressed_l = false;

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 's') {
			System.out.println("press s");
			pressed_s = true;
		}
		else if(e.getKeyChar() == 'l') {
			System.out.println("press l");
			pressed_l = true;
		}
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == 's') {
			 pressed_s = false;
		}
		else if(e.getKeyChar() == 'l') {
			 pressed_l = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		/**/
		
	}
	
}
