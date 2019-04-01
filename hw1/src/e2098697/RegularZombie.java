package e2098697;

/**
 *  RegularZombie class, extended from Zombie class
 */
public class RegularZombie extends Zombie {
    private int stepCount;

    public RegularZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, ZombieType.REGULAR);
        this.stepCount = 0;
    }

    /**
     *  RegularSoldier step method
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
     *  RegularZombie step method in WANDERING state
     */
    private void wanderingStep(SimulationController controller) {
        Position newPosition = this.calculateNextPosition();

        if (controller.isValidPosition(newPosition)) {
            this.setPosition(newPosition);
            double distance = this.calculateClosestSoldierDistance(controller);

            if (distance <= this.getDetectionRange()) {
                this.setState(ZombieState.FOLLOWING);
            }
        }
        else {
            this.setDirection(Position.generateRandomDirection(true));
        }
    }

    /**
     *  RegularZombie step method in FOLLOWING state
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
