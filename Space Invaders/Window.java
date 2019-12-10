import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * 
 * Window is responsible for drawing all the graphics It also contains the main
 * method
 */

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final int HEIGHT = 750;
	public static final int WIDTH = 1000;

	/*
	 * Game information (This is static because there is only one of them and we
	 * don't want to pass it around everywhere)
	 */
	public static GameBoard board = new GameBoard();

	// Store all the images in a HashMap so we can get them by name
	private HashMap<String, BufferedImage> images;

	// FBO to render to the JFrame
	private BufferedImage screen;

	// Timing variables
	private Timer timer;
	private long elapsedTime = 0;

	// Screen variable
	private boolean isPaused = false;
	private boolean gameOver = false;

	public Window() {
		board.reset();

		// Initialize the screen
		screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

		// Load all the images for the game
		images = new HashMap<String, BufferedImage>();
		this.loadResources();

		// JFrame stuff
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Space Invaders");
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);

		JFrame instance = this;

		// Key listener for user input
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				Player player = board.getPlayer();

				// Move Player
				if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
					player.setSpeed(-15, 0);
				} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
					player.setSpeed(15, 0);
				}

				if (player.getX() < 0) {
					player.setCoordinates(0, player.getY());
				}
				if (player.getX() > WIDTH - Entity.SIZE) {
					player.setCoordinates(WIDTH - Entity.SIZE, player.getY());
				}

				// Fire Projectile
				if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
					if (!gameOver) {
						player.fire();
					} else {
						board.reset();
						instance.dispose();
						new Window();
					}
				}

				// Pausing
				if (arg0.getKeyCode() == KeyEvent.VK_P) {
					isPaused = !isPaused;
					if (isPaused)
						timer.stop();
					else
						timer.start();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				board.getPlayer().setSpeed(0, 0);
			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}
		});

		// Game updates 10 times every second
		timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				elapsedTime++;

				gameOver = board.update(elapsedTime);
				if (gameOver)
					timer.stop();

				repaint();
			}
		});
		timer.start();

		// Show the JFrame
		this.setVisible(true);
	}

	private void loadResources() {
		try {
			BufferedImage alienA1 = ImageIO.read(getClass().getResourceAsStream("./res/AlienA1.png"));
			if (alienA1 != null) {
				System.out.println("AlienA1 was loaded properly");
			}
			images.put("AlienA1", alienA1);

			BufferedImage alienA2 = ImageIO.read(getClass().getResourceAsStream("./res/AlienA2.png"));
			images.put("AlienA2", alienA2);

			BufferedImage alienB1 = ImageIO.read(getClass().getResourceAsStream("./res/AlienB1.png"));
			images.put("AlienB1", alienB1);

			BufferedImage alienB2 = ImageIO.read(getClass().getResourceAsStream("./res/AlienB2.png"));
			images.put("AlienB2", alienB2);

			BufferedImage alienC1 = ImageIO.read(getClass().getResourceAsStream("./res/AlienC1.png"));
			images.put("AlienC1", alienC1);

			BufferedImage alienC2 = ImageIO.read(getClass().getResourceAsStream("./res/AlienC2.png"));
			images.put("AlienC2", alienC2);

			BufferedImage barrierFull = ImageIO.read(getClass().getResourceAsStream("./res/Barrier1.png"));
			images.put("Barrier0", barrierFull);

			BufferedImage barrierOne = ImageIO.read(getClass().getResourceAsStream("./res/Barrier2.png"));
			images.put("Barrier1", barrierOne);

			BufferedImage barrierTwo = ImageIO.read(getClass().getResourceAsStream("./res/Barrier3.png"));
			images.put("Barrier2", barrierTwo);

			BufferedImage commandShip = ImageIO.read(getClass().getResourceAsStream("./res/Commandship.png"));
			images.put("CommandShip", commandShip);

			BufferedImage defenseCannon = ImageIO.read(getClass().getResourceAsStream("./res/DefenseCannon.png"));
			images.put("DefenseCannon", defenseCannon);

			BufferedImage projectile = ImageIO.read(getClass().getResourceAsStream("./res/Projectile.png"));
			images.put("Projectile", projectile);

			BufferedImage projectileAlien = ImageIO.read(getClass().getResourceAsStream("./res/ProjectileAlien.png"));
			images.put("AlienProjectile", projectileAlien);

			BufferedImage explosion = ImageIO.read(getClass().getResourceAsStream("./res/Explosion.png"));
			images.put("Boom", explosion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processEntityImage(Entity e) {

		// Update the entities before we draw them
		e.update();

		// Get the correct image
		BufferedImage image = null;

		if (e.isDead()) {
			image = images.get("Boom");
		} else {
			if (e instanceof Alien) {

				// Need to switch between textures, so use the time to alternate
				long spriteNum = (elapsedTime / 10) % 2 + 1;
				image = images.get(((Alien) e).getType() + spriteNum);

			} else if (e instanceof DefensiveBarrier) {

				// Select the Barrier Image based on the health
				DefensiveBarrier d = (DefensiveBarrier) e;
				if (d.getHealth() <= 2) {
					image = images.get("Barrier" + d.getHealth());
				}

			} else if (e instanceof AlienProjectile) {
				image = images.get("AlienProjectile");
			} else if (e instanceof Projectile) {
				image = images.get("Projectile");
			} else if (e instanceof Player) {
				image = images.get("DefenseCannon");
			} else if (e instanceof CommandShip) {
				image = images.get("CommandShip");
			}
		}

		// Store the edited image in another bufferedimage with transparency
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		// Fill in the colors for the output image
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {

				// Get the color from the image using bitwise math
				int color = image.getRGB(i, j);
				int red = (color & 0x00ff0000) >> 16;
				int green = (color & 0x0000ff00) >> 8;
				int blue = color & 0x000000ff;

				// Colorize the white and make the black transparent
				if (red > 25 && green > 25 && blue > 25) {
					output.setRGB(i, j, e.getColor().getRGB());
				} else {
					output.setRGB(i, j, 0x00000000);
				}
			}
		}

		// Draw the edited entity image on the screen
		screen.getGraphics().drawImage(output, e.getX(), e.getY() + 32, e.area.width, e.area.height, this);
	}

	public void drawGameScreen(Graphics g) {

		// Clear the screen
		Graphics bufferG = screen.getGraphics();
		bufferG.setColor(Color.BLACK);
		bufferG.fillRect(0, 0, WIDTH, HEIGHT);

		// Draw all the entities on the screen
		Iterator<Entity> iter = board.getEntities().iterator();
		while (iter.hasNext()) {
			Entity e = iter.next();
			if (e.onScreen() || e instanceof Player) {

				processEntityImage(e);

				if (e.isDead()) {
					iter.remove();

					if (e instanceof Alien) {
						board.removeAlienFromGrid((Alien) e);
					}
				}

			} else {
				// Remove the entity if its not on the screen
				iter.remove();
			}
		}

		// Draw the Strings
		Font font = new Font("Consolas", Font.PLAIN, 24);
		bufferG.setFont(font);
		bufferG.setColor(Color.WHITE);

		bufferG.drawString("Score: " + board.getScore(), 10, 60);
		bufferG.drawString("Lives: " + board.getPlayer().getLives(), WIDTH - 125, 60);

		for (int i = 0; i < board.getPlayer().getLives(); i++) {
			bufferG.drawImage(images.get("DefenseCannon"), WIDTH - 150 + i * 30, HEIGHT - 30, 25, 25, null);
		}

		// Render the screen on the JFrame
		g.drawImage(screen, 0, 0, WIDTH, HEIGHT, this);
	}

	public void drawGameOverScreen(Graphics g) {
		// Clear screen
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Set Font
		Font font = new Font("Consolas", Font.PLAIN, 24);
		FontMetrics metrics = g.getFontMetrics(font);
		g.setFont(font);
		g.setColor(Color.WHITE);

		// Coordinates for centering
		int x = (WIDTH - metrics.stringWidth(" Game Over ")) / 2;
		int y = HEIGHT / 2;

		// Draw Strings
		g.drawString(" Game Over ", x, y - metrics.getHeight());
		g.drawString("Your Score:" + board.getScore(), x, y);
		g.drawString("Continue? [Space]", x - 25, y + metrics.getHeight() * 3);
	}

	@Override
	public void paint(Graphics g) {
		// Figure out which screen to render
		if (!gameOver)
			this.drawGameScreen(g);
		else
			this.drawGameOverScreen(g);
	}

	public static void main(String[] args) {
		new Window();
	}
}