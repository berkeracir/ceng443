package e2098697;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class SimulationRunner {

    public static void main(String[] args) {
        SimulationController simulation = new SimulationController(50, 50);

        
        simulation.addSimulationObject(new Sniper("Soldier", new Position(10, 10)));
        simulation.addSimulationObject(new FastZombie("Zombie", new Position(40, 40)));
        

        while (!simulation.isFinished()) {
            simulation.stepAll();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
