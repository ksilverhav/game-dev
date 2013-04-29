package game;

import java.awt.Dimension;

import javax.swing.JFrame;


public class Game extends JFrame{
	Board board = new Board();	//Gör ett nytt objekt av JPanel-klasen
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Game();

	}
	public Game(){
		this.setPreferredSize(new Dimension(100,100));
		this.setMinimumSize(new Dimension(100,100));
		this.setMaximumSize(new Dimension(100,100));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Gör så att JFrame kan stängas genom att trycka på kryss
		this.add(board);	//Lägger till en JPanel i denna JFrame
		this.setVisible(true);	//Gör JFrame synlig
	}
}
