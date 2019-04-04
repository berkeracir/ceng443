package e2098697;

/**
 *  Bullet class, extended from SimulationObject class, it represents Bullets fired from soldiers
 */
public class Bullet extends SimulationObject {
    private static int id = 0;

    public Bullet(Position position, Position direction, double speed) {
        super("Bullet" + id, position, speed);
        this.setDirection(direction);
        id++;
    }

    /**
     * Bullet step method, it simulates bullets behaviour in the given SimulationController:
     *
     * • Divide the speed into 1.0 to calculate the N small steps.
     * • For every small steps between [0, N):
     *  - Calculate the euclidean distance to the closest zombie using bullet’s and zombie’s position.
     *  - If the distance is smaller than or equal to the collision distance of the zombie, remove the
     *  zombie and the bullet from the simulation and exit loop.
     *  - Calculate the next position of the bullet.
     *  - If the bullet moved out of bound, exit loop.
     *
     * @param controller    SimulationController object that the bullet instance exists in
     */
    public void step(SimulationController controller) {
        boolean isHit = false;

        for (int i=0; i < this.getSpeed(); i++) {
            double distance = this.calculateClosestZombieDistance(controller);
            Zombie zombie = this.getClosestZombie(controller);

            if (distance <= zombie.getCollisionRange()) {
                isHit = true;
                zombie.setActive(false);
                System.out.println(this.getName() + " hit " + zombie.getName() + ".");
                break;
            }

            Position position = new Position(this.getPosition());
            position.add(this.getDirection());

            if (controller.isValidPosition(position)) {
                this.setPosition(position);
            }
            else {
                System.out.println(this.getName() + " moved out of bounds.");
                isHit = true;
                break;
            }
        }

        if (!isHit) {
            System.out.println(this.getName() + " dropped to the ground at " + this.getPosition() + ".");
        }

        this.setActive(false);
    }
}
