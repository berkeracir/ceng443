/**
 *
 *
 */
public class SimulationController {
    private final double height;
    private final double width;

    public SimulationController(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    //Make sure to fill these methods for grading.
    public void stepAll() {
    
    }
    public void addSimulationObject(SimulationObject obj) {
        
    }
    
    public void removeSimulationObject(SimulationObject obj) {
        
    }
    
    public boolean isFinished() {
        
    }
}
