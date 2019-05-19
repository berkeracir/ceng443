import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;



public class RMIClient {
    public static void main(String[] args) {
        try {
            IMazeHub mazeHub = (IMazeHub) Naming.lookup("MazeHubServer");
            IMaze selectedMaze = null;

            Scanner scanner = new Scanner(System.in);
            ParsedInput parsedInput = null;
            String input;

            int x, y, width, height, index, id, selectedMazeId;
            MazeObjectType type;

            boolean loop = true;

            while( loop ) {
                input = scanner.nextLine();
                try {
                    parsedInput = ParsedInput.parse(input);
                }
                catch (Exception ex) {
                    parsedInput = null;
                }
                if ( parsedInput == null ) {
                    System.out.println("Wrong input format. Try again.");
                    continue;
                }

                switch(parsedInput.getType()) {
                    case CREATE_MAZE:
                        width = (int) parsedInput.getArgs()[0];
                        height = (int) parsedInput.getArgs()[1];
                        mazeHub.createMaze(width, height);
                        break;
                    case DELETE_MAZE:
                        index = (int) parsedInput.getArgs()[0];
                        IMaze maze = mazeHub.getMaze(index);
                        /*if (maze == selectedMaze) {
                            System.out.println("Selected maze is deleted!");
                            selectedMaze = null;
                        }*/
                        if (mazeHub.removeMaze(index))
                            System.out.println("Operation Success.");
                        else
                            System.out.println("Operation Failed.");
                        break;
                    case SELECT_MAZE:
                        index = (int) parsedInput.getArgs()[0];
                        selectedMaze = mazeHub.getMaze(index);
                        if (selectedMaze == null)
                            System.out.println("Operation Failed.");
                        else
                            System.out.println("Operation Success.");
                        break;
                    case PRINT_MAZE:
                        if (selectedMaze == null)
                            System.out.println("Select a maze!");
                        else
                            System.out.println(selectedMaze.print());
                        break;
                    case CREATE_OBJECT:
                        x = (int) parsedInput.getArgs()[0];
                        y = (int) parsedInput.getArgs()[1];
                        type = (MazeObjectType) parsedInput.getArgs()[2];
                        if (selectedMaze == null)
                            System.out.println("Select a maze!");
                        else {
                            if (selectedMaze.createObject(new Position(x, y), type))
                                System.out.println("Operation Success.");
                            else
                                System.out.println("Operation Failed.");
                        }
                        break;
                    case DELETE_OBJECT:
                        x = (int) parsedInput.getArgs()[0];
                        y = (int) parsedInput.getArgs()[1];
                        if (selectedMaze == null)
                            System.out.println("Select a maze!");
                        else {
                            if (selectedMaze.deleteObject(new Position(x, y)))
                                System.out.println("Operation Success.");
                            else
                                System.out.println("Operation Failed.");
                        }
                        break;
                    case LIST_AGENTS:
                        if (selectedMaze == null)
                            System.out.println("Select a maze!");
                        else {
                            Agent[] agents = selectedMaze.getAgents();
                            for (Agent agent : agents) {
                                System.out.println(agent.print());
                            }
                        }
                        break;
                    case MOVE_AGENT:
                        id = (int) parsedInput.getArgs()[0];
                        x = (int) parsedInput.getArgs()[1];
                        y = (int) parsedInput.getArgs()[2];
                        if (selectedMaze == null)
                            System.out.println("Select a maze!");
                        else {
                            if (selectedMaze.moveAgent(id, new Position(x, y)))
                                System.out.println("Operation Success.");
                            else
                                System.out.println("Operation Failed.");
                        }
                        break;
                    case QUIT:
                        loop = false;
                        break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
