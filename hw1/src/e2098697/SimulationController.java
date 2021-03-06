package e2098697;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SimulationController class for controlling SimulationObjects
 */
public class SimulationController {
    private final double height;
    private final double width;
    private List<SimulationObject> simulationObjects;
    private List<SimulationObject> tmpAddObjects, tmpDelObjects;
    private int zombieCount;
    private int soldierCount;

    public SimulationController(double width, double height) {
        this.width = width;
        this.height = height;
        this.simulationObjects = new ArrayList<SimulationObject>();
        this.tmpAddObjects = new ArrayList<SimulationObject>();
        this.tmpDelObjects = new ArrayList<SimulationObject>();
        this.zombieCount = 0;
        this.soldierCount = 0;
    }
    
    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public List<SimulationObject> getSimulationObjects() {
        return simulationObjects;
    }

    /**
     *  stepAll function for invoking every SimulationObject's step fuctions.
     */
    public void stepAll() {
        for (SimulationObject obj : this.tmpAddObjects) {
            this.simulationObjects.add(obj);
        }
        this.tmpAddObjects.removeAll(tmpAddObjects);

        for (SimulationObject obj : this.simulationObjects) {
            if (this.isFinished()) {
                return;
            }

            if (obj.isActive()) {
                obj.step(this);
            }
        }

        for (SimulationObject obj : this.simulationObjects) {
            if (!obj.isActive()) {
                this.removeSimulationObject(obj);
            }
        }

        this.simulationObjects.removeAll(this.tmpDelObjects);
        this.tmpDelObjects.removeAll(this.tmpDelObjects);
    }

    public void addSimulationObject(SimulationObject obj) {
        this.tmpAddObjects.add(obj);

        if (obj instanceof Zombie) {
            zombieCount++;
        }
        else if (obj instanceof Soldier) {
            soldierCount++;
        }
    }
    
    public void removeSimulationObject(SimulationObject obj) {
        this.tmpDelObjects.add(obj);

        if (obj instanceof Zombie) {
            zombieCount--;
        }
        else if (obj instanceof Soldier) {
            soldierCount--;
        }
    }

    /**
     * Checks whether the given position is valid in terms  of SimulationController.
     *
     * @param position  Position object for validity check
     * @return          If the given position is inside of boundaries returns true,
     *                  otherwise returns false
     */
    public boolean isValidPosition(Position position) {
        double x = position.getX();
        double y = position.getY();

        if (x >= 0 && x < this.getHeight()) {
            if (y >= 0 && y < this.getWidth()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether SimulationObject is finished in terms of SimulationObjects
     * that it contains.
     *
     * @return      Returns true if there are left any Zombie or Soldier class,
     *              otherwise returns false
     */
    public boolean isFinished() {
        if (zombieCount == 0 || soldierCount == 0) {
            return true;
        }

        return false;
    }
}
