package e2098697;

/**
 *  SlowZombie class, extended from Zombie class, it represents regular zombie objects
 */
public class SlowZombie extends Zombie {
    public SlowZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, ZombieType.SLOW);
    }

    /**
     * SlowZombie step method, it sets the zombie's direction if it's null,
     * and calls state related step functions. It checks whether the zombie instance
     * can kill the closest soldier instance.
     *
     * @param controller    SimulationController object that the SlowZombie instance exists in
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
     * SlowZombie step method in WANDERING state, it simulates regular zombie's behaviour in the given
     * SimulationController:
     *
     * – Calculate the euclidean distance to the closest soldier.
     * – If the distance is shorter than or equal to the detection range of the zombie, change state to
     * FOLLOWING and return.
     * – If not calculate the next position of the zombie.
     * – If the position is out of bounds, change direction to random value.
     * – If the position is not out of bounds, change zombie position to the new position.
     *
     * @param controller    SimulationController object that the SlowZombie instance exists in
     */
    private void wanderingStep(SimulationController controller) {
        double distance = this.calculateClosestSoldierDistance(controller);

        if (distance <= this.getDetectionRange()) {
            this.setState(ZombieState.FOLLOWING);
        }
        else {
            Position newPosition = this.calculateNextPosition();

            if (controller.isValidPosition(newPosition)) {
                this.setPosition(newPosition);
            }
            else {
                this.setDirection(Position.generateRandomDirection(true));
            }
        }
    }

    /**
     * SlowZombie step method in FOLLOWING state, it simulates regular zombie's behaviour in the given
     * SimulationController:
     *
     * – Calculate the euclidean distance to the closest soldier.
     * – If the distance is shorter than or equal to the detection range of the zombie, change direction
     * to soldier.
     * – Calculate the next position of the zombie.
     * – If the position is out of bounds, change direction to random value.
     * – If the position is not out of bounds, change zombie position to the new_position.
     * – Use the calculated distance to the closest soldier in the first step. If the distance is shorter
     * than or equal to the detection range of the zombie, change state to WANDERING.
     *
     * @param controller    SimulationController object that the SlowZombie instance exists in
     */
    private void followingStep(SimulationController controller) {
        double distance = this.calculateClosestSoldierDistance(controller);
        Soldier soldier = this.getClosestSoldier(controller);

        if (null != soldier && distance <= this.getDetectionRange()) {
            Position newDirection = this.calculateDirection(soldier.getPosition());
            this.setDirection(newDirection);
        }

        Position newPosition = this.calculateNextPosition();

        if (controller.isValidPosition(newPosition)) {
            this.setPosition(newPosition);
        }
        else {
            this.setDirection(Position.generateRandomDirection(true));
        }

        if (distance <= this.getDetectionRange()) {
            this.setState(ZombieState.WANDERING);
        }
    }
}
