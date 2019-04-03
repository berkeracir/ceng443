package e2098697;

/**
 *  Commando class, extended from Soldier class
 */
public class Commando extends Soldier {
    private static double BULLET_SPEED = 40.0;

    public Commando(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, SoldierType.COMMANDO);
    }

    /**
     *  Commando step method
     */
    public void step(SimulationController controller) {
        if (null == this.getDirection()) {
            this.setDirection(Position.generateRandomDirection(true));
        }

        if (SoldierState.SEARCHING == this.getState()) {
            this.searchingStep(controller);
        }
        else if (SoldierState.SHOOTING == this.getState()) {
            this.shootingStep(controller);
        }
    }

    /**
     *  Commando step method in SEARCHING state
     */
    private void searchingStep(SimulationController controller) {
        double distance = this.calculateClosestZombieDistance(controller);
        Zombie zombie = this.getClosestZombie(controller);

        if (null != zombie && distance <= this.getShootingRange()) {
            Position newDirection = this.calculateDirection(zombie.getPosition());
            this.setDirection(newDirection);
            this.setState(SoldierState.SHOOTING);
        }
        else {
            Position newPosition = this.calculateNextPosition();

            if (controller.isValidPosition(newPosition)) {
                this.setPosition(newPosition);
            }
            else {
                this.setDirection(Position.generateRandomDirection(true));
            }

            distance = this.calculateClosestZombieDistance(controller);
            zombie = this.getClosestZombie(controller);

            if (null != zombie && distance <= this.getShootingRange()) {
                Position newDirection = this.calculateDirection(zombie.getPosition());
                this.setDirection(newDirection);
                this.setState(SoldierState.SHOOTING);
            }
        }
    }

    /**
     *  Commando step method in SHOOTING state
     */
    private void shootingStep(SimulationController controller) {
        Bullet bullet = new Bullet(this.getPosition(), this.getDirection(), this.BULLET_SPEED);
        controller.addSimulationObject(bullet);
        System.out.println(this.getName() + " fired " + bullet.getName() + " to direction " + bullet.getDirection() + ".");
        double distance = this.calculateClosestZombieDistance(controller);
        Zombie zombie = this.getClosestZombie(controller);

        if (null != zombie && distance <= this.getShootingRange()) {
            this.setDirection(zombie.getPosition());
        }
        else {
            this.setDirection(Position.generateRandomDirection(true));
            this.setState(SoldierState.SEARCHING);
        }
    }
}
