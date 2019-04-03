package e2098697;

/**
 *
 *
 */
public abstract class Zombie extends SimulationObject {
    private final ZombieType type;
    private final double collisionRange;
    private final double detectionRange;
    private ZombieState state;

    public Zombie(String name, Position position, ZombieType type) {
        super(name, position, ZombieType.getSpeed(type));
        this.type = type;
        this.collisionRange = ZombieType.getCollisionRange(type);
        this.detectionRange = ZombieType.getDetectionRange(type);
        this.state = ZombieState.WANDERING;
    }

    public ZombieType getType() {
        return type;
    }

    public double getCollisionRange() {
        return collisionRange;
    }

    public double getDetectionRange() {
        return detectionRange;
    }

    public ZombieState getState() {
        return state;
    }

    public void setState(ZombieState state) {
        this.state = state;
        System.out.println(this.getName() + " changed state to " + state + ".");
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        System.out.println(this.getName() + " moved to " + position + ".");
    }

    @Override
    public void setDirection(Position position) {
        super.setDirection(position);
        System.out.println(this.getName() + " changed direction to " + position + ".");
    }

    /**
     *  Method to kill the closest soldier if the soldier is within the kill range
     */
    public boolean killSoldier(SimulationController controller) {
        double distance = this.calculateClosestSoldierDistance(controller);
        Soldier soldier = this.getClosestSoldier(controller);

        if (null != soldier && distance <= this.getCollisionRange() + soldier.getCollisionRange()) {
            soldier.setActive(false);
            System.out.println(this.getName() + " killed " + soldier.getName() + ".");
            return true;
        }
        else {
            return false;
        }
    }
}
