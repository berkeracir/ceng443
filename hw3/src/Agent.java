public class Agent extends MazeObject{
    private final int id;
    private int collectedGold;

    public Agent(Position position, int id) {
        super(position, MazeObjectType.AGENT);
        this.id = id;
        this.collectedGold = 0;
    }

    public int getId() {
        return id;
    }

    public int getCollectedGold() {
        return collectedGold;
    }

    public void setCollectedGold(int collectedGold) {
        this.collectedGold = collectedGold;
    }

    public String print() {
        return "Agent"+ id + " at " + getPosition() +
                ". Gold collected: " + collectedGold + ".";
    }
}
