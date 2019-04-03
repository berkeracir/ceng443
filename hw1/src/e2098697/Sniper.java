package e2098697;

/**
 *  Sniper class, extended from Soldier class
 */
public class Sniper extends Soldier {
    private static double BULLET_SPEED = 100.0;

    public Sniper(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, SoldierType.SNIPER);
    }

    /**
     *  Sniper step method
     */
    public void step(SimulationController controller) {
        if (null == this.getDirection()) {
            this.setDirection(Position.generateRandomDirection(true));
        }

        if (SoldierState.SEARCHING == this.getState()) {
            this.searchingStep(controller);
        }
        else if (SoldierState.AIMING == this.getState()) {
            this.aimingStep(controller);
        }
        else if (SoldierState.SHOOTING == this.getState()) {
            this.shootingStep(controller);
        }
    }

    /**
     *  Sniper step method in SEARCHING state
     */
    private void searchingStep(SimulationController controller) {
        Position newPosition = this.calculateNextPosition();

        if (controller.isValidPosition(newPosition)) {
            this.setPosition(newPosition);
        }
        else {
            this.setDirection(Position.generateRandomDirection(true));
        }

        double distance = this.calculateClosestZombieDistance(controller);

        if (distance <= this.getShootingRange()) {
            this.setState(SoldierState.AIMING);
        }
    }

    /**
     *  Sniper step method in AIMING state
     */
    private void aimingStep(SimulationController controller) {
        double distance = this.calculateClosestZombieDistance(controller);
        Zombie zombie = this.getClosestZombie(controller);

        if (null != zombie && distance <= this.getShootingRange()) {
            Position newDirection = this.calculateDirection(zombie.getPosition());
            this.setDirection(newDirection);
            this.setState(SoldierState.SHOOTING);
        }
        else {
            this.setState(SoldierState.SEARCHING);
        }
    }

    /**
     *  Sniper step method in SHOOTING state
     */
    private void shootingStep(SimulationController controller) {
        Bullet bullet = new Bullet(this.getPosition(), this.getDirection(), this.BULLET_SPEED);
        controller.addSimulationObject(bullet);
        System.out.println(this.getName() + " fired " + bullet.getName() + " to direction " + bullet.getDirection() + ".");
        double distance = this.calculateClosestZombieDistance(controller);

        if (distance <= this.getShootingRange()) {
            this.setState(SoldierState.AIMING);
        }
        else {
            this.setDirection(Position.generateRandomDirection(true));
            this.setState(SoldierState.SEARCHING);
        }
    }
}

