import java.awt.Color;
import java.awt.Rectangle;
import java.util.Iterator;

public class Projectile extends Entity {

	private Entity source;

	public Projectile(Entity source, int x, int y, Color color, int dir) {
		super(x, y, color);
		this.setSpeed(0, 30 * dir);
		this.source = source;
	}

	@Override
	public void update() {
		super.update();

		Rectangle inter = new Rectangle(this.x + 15, this.y, SIZE - 30, SIZE);
		
		Iterator<Entity> entities = Window.board.getEntities().iterator();
		while (entities.hasNext()) {
			Entity e = entities.next();
			if (inter.intersects(e.area) && source.getClass() != e.getClass() && this.getClass() != e.getClass()) {

				// Hit the thing that collided
				e.getHit();

				// Put it off the screen so it'll be deleted
				this.setCoordinates(-1, -1);

				// Don't need to check for collision
				break;
			}
		}
	}
}