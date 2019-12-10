import java.awt.Color;
import java.awt.Rectangle;

public class Entity {

	public static int SIZE = 45;

	protected int x, y, speedX, speedY;
	protected Rectangle area;
	private Color color;
	protected boolean isDead;
	
	public Entity(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		
		this.isDead = false;
		
		this.speedX = 0;
		this.speedY = 0;

		area = new Rectangle(x, y, SIZE, SIZE);
	}

	// Other classes can access this, not move
	public void update() {
		move();

		this.area = new Rectangle(this.x, this.y, SIZE, SIZE);
	}

	private void move() {
		this.x += speedX;
		this.y += speedY;
	}
	
	public void getHit() {
		this.isDead = true;
	}
	
	public boolean onScreen() {
		return (x < 0 || y < 0 || x > Window.WIDTH || y > Window.HEIGHT) ? false : true;
	}

	/* Getters and Setters */

	public boolean isDead() {
		return isDead;
	}
	
	public void setSpeed(int speedX, int speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Rectangle getArea() {
		return area;
	}

	public Color getColor() {
		return color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
