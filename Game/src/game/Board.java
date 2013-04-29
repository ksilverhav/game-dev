package game;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public class Board extends JPanel{
	public Board(){
		
		repaint();
	}
	//Funktionen som ritar ut allt i panelen
    @Override
	public void paintComponent(Graphics g)
    {
		super.paintComponent(g);
		g.drawRect(100, 100, 100, 100);
    }
	//Tar emot knapptryckningar
	public void keyPressed(KeyEvent arg0) {
		
		
	}
	//Tar emot när en knapp släpps upp
	public void keyReleased(KeyEvent arg0) {
		
		
	}
	
	public void keyTyped(KeyEvent arg0) {
		
		
	}
}
