package e2098697;

public class Bullet extends SimulationObject {
    private static int id = 0;

    public Bullet(Position position, Position direction, double speed) {
        super("Bullet" + id, position, speed);
        this.setDirection(direction);
        id++;
    }

    /**
     *  Bullet step method
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
