package e2098697;

/**
 *  Commando class, extended from Soldier class, it represents commando objects
 */
public class Commando extends Soldier {
    private static double BULLET_SPEED = 40.0;

    public Commando(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, SoldierType.COMMANDO);
    }

    /**
     * Commando step method, it sets the commando's direction if its direction is null,
     * and calls state related step functions.
     *
     * @param controller    SimulationController object that the commando instance exists in
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
     * Commando step method in SEARCHING state, it simulates commando's behaviour in the given
     * SimulationController:
     *
     * – Calculate the euclidean distance to the closest zombie.
     * – If the distance is shorter than or equal to the shooting range of the soldier; change soldier
     * direction to zombie, change state to SHOOTING and return.
     * – Calculate the next position of the soldier.
     * – If the position is out of bounds, change direction to random value.
     * – If the position is not out of bounds, change soldier position to the new position.
     * – Calculate the euclidean distance to the closest zombie.
     * – If the distance is shorter than or equal to the shooting range of the soldier; change soldier
     * direction to zombie, change state to SHOOTING.
     *
     * @param controller    SimulationController object that the commando instance exists in
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
     * Commando step method in SHOOTING state, it simulates commando's behaviour in the given
     * SimulationController:
     *
     * – Create a bullet with soldier's current position and direction. Speed depends on the
     * soldier which for Commando is 40.0. Add the bullet to the simulation.
     * – Calculate the euclidean distance to the closest zombie.
     * – If the distance is shorter than or equal to the shooting range of the soldier, change
     * soldier direction to zombie.
     * – If not, randomly change soldier direction and change state to SEARCHING.
     *
     * @param controller    SimulationController object that the commando instance exists in
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
