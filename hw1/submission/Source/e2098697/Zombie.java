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

    /**
     * Kills the closes soldier from the given SimulationController.
     *
     * • Calculate the euclidean distance to the closest soldier.
     * • If the distance is shorter than or equal to the sum of collision distance of zombie and soldier
     *
     * @param controller    SimulationController object that the zombie instance exists in
     * @return              Returns true if the zombie kills the closest soldier, otherwise
     *                      returns false
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
