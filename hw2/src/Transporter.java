/**
 *
 * @author Berker Acir
 */
public class Transporter implements Runnable {
    private static int ID = 1;
    private final int id;
    private final int interval;
    private final int smelterId;
    private final int constructorId;
    private final SimulatorController simulatorController;
    private Smelter smelter;
    private Constructor constructor;
    private Storage smelterStorage, constructorStorage;
    private boolean continueFlag = true;
    private boolean killFlag = false;

    Transporter(int interval, int smelterId, int constructorId, SimulatorController simulatorController) {
        this.id = this.ID++;
        this.interval = interval;
        this.smelterId = smelterId;
        this.constructorId = constructorId;
        this.simulatorController = simulatorController;
    }

    @Override
    public void run() {
        smelter = simulatorController.getSmelter(smelterId);
        smelterStorage = smelter.getStorage();
        constructor = simulatorController.getConstructor(constructorId);
        constructorStorage = constructor.getStorage();

        HW2Logger.WriteOutput(0, id, 0, Action.TRANSPORTER_CREATED);

        while ((smelter.isAlive() || smelter.getTotalIngot() + smelterStorage.getIngotCount() > 0)
                && constructor.isAlive() && continueFlag) {
            try {
                WaitNextLoad();
            } catch (InterruptedException ex) {}
            if (killFlag) {
                break;
            }
            HW2Logger.WriteOutput(smelterId, id, 0, Action.TRANSPORTER_TRAVEL);
            Utility.threadSleep(interval);
            HW2Logger.WriteOutput(smelterId, id, 0, Action.TRANSPORTER_TAKE_INGOT);
            Utility.threadSleep(interval);
            Loaded();
            try {
                WaitConstructor();
            } catch (InterruptedException ex) {}
            HW2Logger.WriteOutput(0, id, constructorId, Action.TRANSPORTER_TRAVEL);
            Utility.threadSleep(interval);
            HW2Logger.WriteOutput(0, id, constructorId, Action.TRANSPORTER_DROP_INGOT);
            Utility.threadSleep(interval);
            Unloaded();
        }


        HW2Logger.WriteOutput(0, id, 0, Action.TRANSPORTER_STOPPED);
    }

    // Wait if the smelter has no ingots in storage. Continue if there are available ingots to be transported. Reserves
    // the storage space so that other transporters cannot take the ingots.
    private void WaitNextLoad() throws InterruptedException {
        synchronized (smelterStorage) {
            while (smelterStorage.isEmpty()) {
                smelterStorage.wait(3000);

                if (smelter.getTotalIngot() + smelterStorage.getIngotCount() == 0) {
                    killFlag = true;
                    break;
                }
            }

            if (!smelterStorage.isEmpty()) {
                smelterStorage.takeIngot();

                if (smelter.getTotalIngot() + smelterStorage.getIngotCount() == 0) {
                    continueFlag = false;
                }
            }
        }
    }

    // Signals the smelter to inform that new storage space is available.
    private void Loaded() {
        synchronized (smelterStorage) {
            //smelterStorage.takeIngot();
            smelterStorage.notifyAll();
        }
    }

    // Waits if the constructor has no storage space available. Continue if there is space storage for the ingot. Once
    // unblocked, storage is reserved so that no other transporters can fill that storage space.
    private void WaitConstructor() throws  InterruptedException {
        synchronized (constructorStorage) {
            while (constructorStorage.isFull() && constructor.isAlive()) {
                constructorStorage.wait(3000);
            }
        }
    }

    // Signals the constructor to inform that new ingots have arrived so that if the constructor has reached the
    // required number of ingots, it can continue production.
    private void Unloaded() {
        synchronized (constructorStorage) {
            constructorStorage.dropIngot();
            constructorStorage.notifyAll();
        }
    }
}
