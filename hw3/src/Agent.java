public class Agent extends MazeObject{
    
    private final int id;
    private int collectedGold;
    public Agent(Position position, int id) {
        super(position, MazeObjectType.AGENT);
        this.id = id;
        this.collectedGold = 0;
    }
}