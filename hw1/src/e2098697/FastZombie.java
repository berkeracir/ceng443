package e2098697;

/**
 *  FastZombie class, extended from Zombie class, it represents fast zombie objects
 */
public class FastZombie extends Zombie {
    public FastZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, ZombieType.FAST);
    }

    /**
     * FastZombie step method, it sets the zombie's direction if it's null,
     * and calls state related step functions. It checks whether the zombie instance
     * can kill the closest soldier instance.
     *
     * @param controller    SimulationController object that the FastZombie instance exists in
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
     * FastZombie step method in WANDERING state, it simulates fast zombie's behaviour in the given
     * SimulationController:
     *
     * – Calculate the euclidean distance to the closest soldier.
     * – If the distance is shorter than or equal to the detection range of the zombie, change direction
     * to soldier, change state to FOLLOWING and return.
     * – If not calculate the next position of the zombie.
     * – If the position is out of bounds, change direction to random value.
     * – If the position is not out of bounds, change zombie position to the new position.
     *
     * @param controller    SimulationController object that the FastZombie instance exists in
     */
    private void wanderingStep(SimulationController controller) {
        double distance = this.calculateClosestSoldierDistance(controller);
        Soldier soldier = this.getClosestSoldier(controller);

        if (null != soldier && distance <= this.getDetectionRange()) {
            Position newDirection = this.calculateDirection(soldier.getPosition());
            this.setDirection(newDirection);
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
     * FastZombie step method in FOLLOWING state, it simulates fast zombie's behaviour in the given
     * SimulationController:
     *
     * – Calculate the next position of the zombie.
     * – If the position is out of bounds, change direction to random value.
     * – If the position is not out of bounds, change zombie position to the new position.
     * – Change state to WANDERING.
     *
     * @param controller    SimulationController object that the FastZombie instance exists in
     */
    private void followingStep(SimulationController controller) {
        Position newPosition = this.calculateNextPosition();

        if (controller.isValidPosition(newPosition)) {
            this.setPosition(newPosition);
        }
        else {
            this.setDirection(Position.generateRandomDirection(true));
        }

        this.setState(ZombieState.WANDERING);
    }
}
