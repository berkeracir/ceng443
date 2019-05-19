package e2098697;


/**
 *  Abstract Soldier class for different Soldier Types such as
 *  RegularSoldier, Commando, Sniper
 */
public abstract class Soldier extends SimulationObject {
    private final SoldierType type;
    private final double collisionRange;
    private final double shootingRange;
    private SoldierState state;

    public Soldier(String name, Position position, SoldierType type) {
        super(name, position, SoldierType.getSpeed(type));
        this.type = type;
        this.collisionRange = SoldierType.getCollisionRange(type);
        this.shootingRange = SoldierType.getShootingRange(type);
        this.state = SoldierState.SEARCHING;
    }

    public SoldierType getType() {
        return type;
    }

    public double getCollisionRange() {
        return collisionRange;
    }

    public double getShootingRange() {
        return shootingRange;
    }

    public SoldierState getState() {
        return state;
    }

    public void setState(SoldierState state) {
        this.state = state;
        System.out.println(this.getName() + " changed state to " + state + ".");
    }

    /**
     * Overrided setPosition function for extra printing feature
     */
    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        System.out.println(this.getName() + " moved to " + position + ".");
    }

    /**
     * Overrided setDirection function for extra printing feature
     */
    @Override
    public void setDirection(Position position) {
        super.setDirection(position);
        System.out.println(this.getName() + " changed direction to " + position + ".");
    }
}
