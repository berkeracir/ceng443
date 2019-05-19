package e2098697;

/**
 *  RegularZombie class, extended from Zombie class, it represents regular zombie objects
 */
public class RegularZombie extends Zombie {
    private int stepCount;

    public RegularZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, ZombieType.REGULAR);
        this.stepCount = 0;
    }

    /**
     * RegularZombie step method, it sets the zombie's direction if it's null,
     * and calls state related step functions. It checks whether the zombie instance
     * can kill the closest soldier instance.
     *
     * @param controller    SimulationController object that the RegularZombie instance exists in
     */
    public void step(SimulationController controller) {
        if (null == this.getDirection()) {
            this.setDirection(Position.generateRandomDirection(true));
        }

        if (!this.killSoldier(controller)) {
            if (ZombieState.WANDERING == this.getState()) {
                this.wanderingStep(controller);
            } else if (ZombieState.FOLLOWING == this.getState()) {
                this.followingStep(controller);
            }
        }
    }

    /**
     * RegularZombie step method in WANDERING state, it simulates regular zombie's behaviour in the given
     * SimulationController:
     *
     * – Calculate the next position of the zombie.
     * – If the position is out of bounds, change direction to random value.
     * – If the position is not out of bounds, change zombie position to the new position.
     * – Calculate the euclidean distance to the closest soldier.
     * – If the distance is shorter than or equal to the detection range of the zombie, change
     * direction to the soldier and change state to FOLLOWING.
     *
     * @param controller    SimulationController object that the RegularZombie instance exists in
     */
    private void wanderingStep(SimulationController controller) {
        Position newPosition = this.calculateNextPosition();

        if (controller.isValidPosition(newPosition)) {
            this.setPosition(newPosition);
        }
        else {
            this.setDirection(Position.generateRandomDirection(true));
        }

        double distance = this.calculateClosestSoldierDistance(controller);
        Soldier soldier = this.getClosestSoldier(controller);

        if (null != soldier && distance <= this.getDetectionRange()) {
            Position direction = this.calculateDirection(soldier.getPosition());
            this.setDirection(direction);
            this.setState(ZombieState.FOLLOWING);
        }
    }

    /**
     * RegularZombie step method in FOLLOWING state, it simulates regular zombie's behaviour in the given
     * SimulationController:
     *
     * – Calculate the next position of the zombie.
     * – If the position is out of bounds, change direction to random value.
     * – If the position is not out of bounds, change zombie position to the new position.
     * – Count the number of step zombie has been in FOLLOWING state.
     * – If the step count is 4 in FOLLOWING, change state to WANDERING.
     *
     * @param controller    SimulationController object that the RegularZombie instance exists in
     */
    private void followingStep(SimulationController controller) {
        Position newPosition = this.calculateNextPosition();

        if (controller.isValidPosition(newPosition)) {
            this.setPosition(newPosition);
            stepCount++;

            if (4 == stepCount) {
                stepCount = 0;
                this.setState(ZombieState.WANDERING);
            }
        }
        else {
            this.setDirection(Position.generateRandomDirection(true));
        }
    }
}
