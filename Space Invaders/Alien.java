import java.awt.Color;

public class Alien extends Entity {

    public static final String TYPE_A = "AlienA";
    public static final String TYPE_B = "AlienB";
    public static final String TYPE_C = "AlienC";
    
    private String type;
    private int score;
    
    // Constructor
    public Alien(String type, int x, int y) {
        super(x, y, Color.WHITE);
        
        this.type = type;
        
        // Sets the color based on the type
        if(type.equals(TYPE_A)) {
            this.setColor(Color.GREEN);
            this.score = 10;
        }else if(type.equals(TYPE_B)) {
            this.setColor(Color.WHITE);
            this.score = 20;
        }else if(type.equals(TYPE_C)) {
            this.setColor(new Color(255, 25, 205));
            this.score = 30;
        }   
    }
    
    public int getScore() {
    	return score;
    }
    
    @Override
    public void getHit() {
    	super.getHit();
    	Window.board.addToScore(score);
    }
    
    public AlienProjectile fire() {
    	return new AlienProjectile(this, x, y, this.getColor());
    }
    
    public String getType() {
        return type;
    }    
}
