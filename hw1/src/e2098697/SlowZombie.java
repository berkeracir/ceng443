package e2098697;

/**
 *  SlowZombie class, extended from Zombie class
 */
public class SlowZombie extends Zombie {
    public SlowZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, ZombieType.SLOW);
    }

    /**
     *  SlowZombie step method
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
     *  SlowZombie step method in WANDERING state
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
     *  SlowZombie step method in FOLLOWING state
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
