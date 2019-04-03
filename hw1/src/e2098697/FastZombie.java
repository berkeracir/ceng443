package e2098697;

/**
 *  FastZombie class, extended from Zombie class
 */
public class FastZombie extends Zombie {
    public FastZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, ZombieType.FAST);
    }

    /**
     *  FastZombie step method
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
     *  FastZombie step method in WANDERING state
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
     *  FastZombie step method in FOLLOWING state
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
