import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class RMIServer {
    public static void main(String[] args) {
        try {
            IMazeHub mazeHub = new MazeHub();
            Naming.rebind("//localhost/MazeHubServer", mazeHub);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
