package gfx;

import java.awt.Graphics;

import javax.swing.JFrame;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 750;
	public static final int HEIGHT = 500;
	
	public Window() {
		this.setTitle("Space Invaders");
		this.setResizable(false);
		this.setSize(WIDTH, HEIGHT);
		
		
		
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
	public static void main(String[] args) {
		new Window();
	}
}
