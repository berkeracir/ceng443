import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MazeHub extends UnicastRemoteObject implements IMazeHub {
    @Override
    public void createMaze(int width, int height) throws RemoteException {

    }

    @Override
    public IMaze getMaze(int index) throws RemoteException {
        return null;
    }

    @Override
    public boolean removeMaze(int index) throws RemoteException {
        return false;
    }
}
