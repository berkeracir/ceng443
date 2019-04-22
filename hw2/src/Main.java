/**
 *
 * @author Berker Acir
 */
public class Main {

    public static void main(String[] args) {
        Simulator simulator = new Simulator();

        HW2Logger.InitWriteOutput();
        simulator.initialize();
        simulator.start();
        simulator.shutdown();
    }
}
