import java.awt.Color;
/**
 * CommandShip class
 */
public class CommandShip extends Entity
{
    private int _pointValue;
    /**
     * Constructor for objects of class CommandShip
     */
    public CommandShip(int x, int y, int pointValue)
    {
        super(x, y, Color.RED);
        _pointValue = pointValue;
    }
    
    @Override
    public void getHit() {
    	super.getHit();
    	Window.board.addToScore(_pointValue);
    }
}
