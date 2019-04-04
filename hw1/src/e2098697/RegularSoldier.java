package e2098697;

/**
 *  RegularSoldier class, extended from Soldier class, it represents regular soldier objects
 */
public class RegularSoldier extends Soldier {
    private static double BULLET_SPEED = 40.0;

    public RegularSoldier(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, SoldierType.REGULAR);
    }

    /**
     * RegularSoldier step method, it sets the regular soldier's direction if its direction is null,
     * and calls state related step functions.
     *
     * @param controller    SimulationController object that the regular soldier instance exists in
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
     * RegularSoldier step method in SEARCHING state, it simulates regular soldier's behaviour in the given
     * SimulationController:
     *
     * – Calculate the next position of the soldier.
     * – If the position is out of bounds, change direction to random value.
     * – If the position is not out of bounds, change soldier position to the new position.
     * – Calculate the euclidean distance to the closest zombie.
     * – If the distance is shorter than or equal to the shooting range of the soldier, change state to
     * AIMING.
     *
     * @param controller    SimulationController object that the regular soldier instance exists in
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
     * RegularSoldier step method in AIMING state, it simulates regular soldier's behaviour in the given
     * SimulationController:
     *
     * – Calculate the euclidean distance to the closest zombie.
     * – If the distance is shorter than or equal to the shooting range of the soldier, change soldier
     * direction to zombie and change state to SHOOTING.
     * – If not, change state to SEARCHING
     *
     * @param controller    SimulationController object that the regular soldier instance exists in
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
     * RegularSoldier step method in SHOOTING state, it simulates regular soldier's behaviour in the given
     * SimulationController:
     *
     * – Create a bullet. Bullet’s position and direction is same as soldier’s. Speed depends on the soldier
     * which for RegularSoldier is 40.0. Add the bullet to the simulation.
     * – Calculate the euclidean distance to the closest zombie.
     * – If the distance is shorter than or equal to the shooting range of the soldier, change state to
     * AIMING.
     * – If not, randomly change soldier direction and change state to SEARCHING.
     *
     * @param controller    SimulationController object that the regular soldier instance exists in
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
