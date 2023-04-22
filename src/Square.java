import java.util.HashSet;
import java.util.Set;

public class Square {
    public Square() {
        hasQueen = false;
        dangerousSpots = new HashSet<>();
    }
    public Square(Square other) {
        //this should just make a deep copy of the "other" square
        this.hasQueen = other.hasQueen;
        this.dangerousSpots = new HashSet<>();
        this.dangerousSpots.addAll(other.dangerousSpots);
    }
    private boolean hasQueen;
    
    public void putQueen() { hasQueen = true; }
    //we don't need to have a remove queen because any bad boards just get thrown away and garbage-collected
    public boolean hasQueen() { return hasQueen; }
    
    
    private final Set<Integer> dangerousSpots;
    public Set<Integer> getDangerousSpots() { return dangerousSpots; }
    public void addDangerousSpot(int i) { dangerousSpots.add(i); }
    
    
    
}
