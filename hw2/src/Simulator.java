/**
 *
 * @author Berker Acir
 */
public class Simulator {

    public static void main(String[] args) {
        SimulatorController simulatorController = new SimulatorController();

        HW2Logger.InitWriteOutput();
        simulatorController.initialize();
        simulatorController.start();
        simulatorController.shutdown();
    }
}
