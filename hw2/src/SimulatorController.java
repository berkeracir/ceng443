import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Berker Acir
 */
public class SimulatorController {
    private List<Smelter> smelterList;
    private List<Constructor> constructorList;
    private List<Transporter> transporterList;
    private List<Thread> threadList;
    private ExecutorService pool;
    private int numberOfSmelters, numberOfConstructors, numberOfTransporters;

    SimulatorController() {
        this.smelterList =  Collections.synchronizedList(new ArrayList<Smelter>());
        this.constructorList =  Collections.synchronizedList(new ArrayList<Constructor>());
        this.transporterList =  Collections.synchronizedList(new ArrayList<Transporter>());
        this.threadList = Collections.synchronizedList(new ArrayList<Thread>());
    }

    public void initialize() {
        Scanner input = new Scanner(System.in);

        numberOfSmelters = input.nextInt();
        input.nextLine();
        //System.out.println("Smelter:" + numberOfSmelters);

        for (int i=0; i<numberOfSmelters; i++) {
            int I = input.nextInt();
            int C = input.nextInt();
            int T = input.nextInt();
            int R = input.nextInt();
            input.nextLine();

            Smelter smelter = new Smelter(I, C, IngotType.getIngotType(T), R);
            Thread thread = new Thread(smelter);
            smelterList.add(smelter);
            threadList.add(thread);
        }

        numberOfConstructors = input.nextInt();
        input.nextLine();
        //System.out.println("Constructor:" + numberOfConstructors);

        for (int i=0; i<numberOfConstructors; i++) {
            int I = input.nextInt();
            int C = input.nextInt();
            int T = input.nextInt();
            input.nextLine();

            Constructor constructor = new Constructor(I, C, IngotType.getIngotType(T));
            Thread thread = new Thread(constructor);
            constructorList.add(constructor);
            threadList.add(thread);
        }

        numberOfTransporters = input.nextInt();
        input.nextLine();
        //System.out.println("Transporter:" + numberOfTransporters);

        for (int i=0; i<numberOfTransporters; i++) {
            int I = input.nextInt();
            int S = input.nextInt();
            int C = input.nextInt();
            input.nextLine();

            Transporter transporter = new Transporter(I, S, C, this);
            Thread thread = new Thread(transporter);
            transporterList.add(transporter);
            threadList.add(thread);

            if (smelterList.get(S-1).getIngotType() != constructorList.get(C-1).getIngotType()) {
                System.err.println("Ingot Type Mismatch!");
                System.exit(1);
            }
        }

        this.pool = Executors.newFixedThreadPool(numberOfSmelters + numberOfConstructors + numberOfTransporters);
    }

    public void start() {
        for (Thread thread: this.threadList) {
            this.pool.execute(thread);
        }
    }

    public void shutdown() {
        this.pool.shutdown();
    }

    public Smelter getSmelter(int smelterId) {
        return smelterList.get(smelterId - 1);
    }

    public Constructor getConstructor(int constructorId) {
        return constructorList.get(constructorId - 1);
    }
}


