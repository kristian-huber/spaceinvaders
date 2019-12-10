import java.awt.Color;
import java.awt.Rectangle;

public class DefensiveBarrier extends Entity {
    private int _health;
    
    public DefensiveBarrier(int x, int y) {
        super(x, y, Color.RED);
        area = new Rectangle(x, y, SIZE * 4, SIZE * 4);
    }    
    public int getHealth() {
        return this._health;
    }
    
    public void update() {
    	area = new Rectangle(this.getX(), this.getY(), SIZE * 3, SIZE * 3);
    }
    
    @Override
    public void getHit() {
        //0 health = full, 1 = damaged, 2 = critical, >2 = dead
        if (this._health < 2) {
            this._health++;
        }else {
        	this.isDead = true;
        }
    }    
}
