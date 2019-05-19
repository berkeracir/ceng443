package e2098697;

/**
 *
 *
 */
public abstract class SimulationObject {
    private final String name;
    private Position position;
    private Position direction;
    private final double speed;
    private boolean active;

    public SimulationObject(String name, Position position, double speed) {
        this.name = name;
        this.position = position;
        this.speed = speed;
        this.direction = null;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getDirection() {
        return direction;
    }

    public void setDirection(Position direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Method to calculate next position after moving one step.
     *
     * @return      Returns the calculated position.
     */
    public Position calculateNextPosition() {
        Position nextPosition = new Position(this.getDirection());
        nextPosition.mult(this.getSpeed());
        nextPosition.add(this.getPosition());

        return nextPosition;
    }

    /**
     * Method to calculate direction between the given position and the simulation object's position
     *
     * @param position  Position object to calculate the direction to it.
     * @return          Returns the direction to given position.
     */
    public Position calculateDirection(Position position) {
        Position newPosition = new Position(this.getPosition());
        newPosition.mult(-1);
        newPosition.add(position);
        newPosition.normalize();

        return newPosition;
    }

    /**
     * Method to calculate distance between the simulation object and the closest zombie
     *
     * @param controller    SimulationController object for checking the zombies' distances
     * @return              Returns the distance to the closest zombie, if there is no zombie
     *                      then returns Integer.MAX_VALUE
     */
    public double calculateClosestZombieDistance(SimulationController controller) {
        double distance = Integer.MAX_VALUE;

        for (SimulationObject obj : controller.getSimulationObjects()) {
            if (obj instanceof Zombie && obj.isActive()) {
                Zombie zombie = (Zombie) obj;
                double d = this.getPosition().distance(zombie.getPosition());

                distance = Math.min(distance, d);
            }
        }

        return distance;
    }

    /**
     * Method to get the closest zombie to the simulation object
     *
     * @param controller    SimulationController object for checking the closest zombie
     * @return              Returns the closest zombie, if there is no zombie then returns null
     */
    public Zombie getClosestZombie(SimulationController controller) {
        double distance = Integer.MAX_VALUE;
        Zombie zombie = null;

        for (SimulationObject obj : controller.getSimulationObjects()) {
            if (obj instanceof Zombie && obj.isActive()) {
                Zombie z = (Zombie) obj;
                double d = this.getPosition().distance(z.getPosition());

                if (distance > d) {
                    zombie = z;
                    distance = d;
                }
            }
        }

        return zombie;
    }

    /**
     * Method to calculate distance between the simulation object and the closest soldier
     *
     * @param controller    SimulationController object for checking the soldiers' distances
     * @return              Returns the distance to the soldier zombie, if there is no soldier
     *                      then returns Integer.MAX_VALUE
     */
    public double calculateClosestSoldierDistance(SimulationController controller) {
        double distance = Integer.MAX_VALUE;

        for (SimulationObject obj : controller.getSimulationObjects()) {
            if (obj instanceof Soldier && obj.isActive()) {
                Soldier soldier = (Soldier) obj;
                double d = this.getPosition().distance(soldier.getPosition());

                distance = Math.min(distance, d);
            }
        }

        return distance;
    }

    /**
     * Method to get the closest soldier to the simulation object
     *
     * @param controller    SimulationController object for checking the closest soldier
     * @return              Returns the closest soldier, if there is no soldier then returns null
     */
    public Soldier getClosestSoldier(SimulationController controller) {
        double distance = Integer.MAX_VALUE;
        Soldier soldier = null;

        for (SimulationObject obj : controller.getSimulationObjects()) {
            if (obj instanceof Soldier && obj.isActive()) {
                Soldier s = (Soldier) obj;
                double d = this.getPosition().distance(s.getPosition());

                if (distance > d) {
                    soldier = s;
                    distance = d;
                }
            }
        }

        return soldier;
    }
    
    public abstract void step(SimulationController controller);
}
