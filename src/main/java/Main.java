import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        MapGenerator mapMedium = new MapGenerator();
        startTheGame(mapMedium);
    }

    private static void startTheGame(MapGenerator map) throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("To start new game, write: new\n" +
                "To Resume your saved game, write: resume\n" +
                "Note:( for the first time, the saved is the hard map ).");
        String level = s.nextLine().toLowerCase().trim();
        if(level.equals("new")){
            System.out.println("Select the level: \nwrite: easy OR hard");
            level = s.nextLine().toLowerCase().trim();
        }
        map.generateMap(level);
        Player player = map.getPlayer();
        Command execute = new Command(player);
        int target = map.targetRoomNumber;



        System.out.println("\n------------------------------\nYou are in the room number "+player.getPosition()+")\n" +
                "Your target is the room number "+target+"\n you can start the game now.. Good luck" +
                "\n\n----------------------------\n");
        System.out.println("For turning right, write: tr");
        System.out.println("For turning left, write: tl");
        System.out.println("For moving forward, write: mvf");
        System.out.println("For moving backward, write: mvb");
        System.out.println("For Looking what is on the wall, write: look");
        System.out.println("For The player Status, write: status");
        System.out.println("For Checking The Faced Item, write: check");
        System.out.println("For open Door/Chest, write: open");
        System.out.println("For close Door/Chest, write: close");
        System.out.println("For enter the trade mode, write: trade");
        System.out.println("For turn the light of room on/off, write: use flash");
        System.out.println("For Asking about your current position, write: pos");
        System.out.println("For Restart The game, write: reset ");
        System.out.println("For save your status and close the game, write: save");
        System.out.println("For Quit the game press: Q \n---------------------------------\n");
        String inputCommand = s.nextLine().toLowerCase().trim();


        while (! inputCommand.equalsIgnoreCase("q")){

            if(inputCommand.equals("tr"))
                execute.turnRight();

            else if(inputCommand.equals("tl"))
                execute.turnLeft();

            else if(inputCommand.equals("mvf"))
                execute.moveForward();

            else if(inputCommand.equals("mvb"))
                execute.moveBackward();

            else if(inputCommand.equals("look"))
                System.out.println(execute.look());

            else if(inputCommand.equals("status"))
                execute.playerStatus();

            else if(inputCommand.equals("check"))
                execute.check();

            else if(inputCommand.equals("open"))
                execute.open();

            else if(inputCommand.equals("close"))
                execute.close();

            else if(inputCommand.equals("trade"))
                execute.trade();

            else if(inputCommand.equals("use flash"))
                execute.useFlashlight();

            else if(inputCommand.equals("pos"))
                System.out.println("you are in the room number "+player.getPosition());

            else if(inputCommand.equals("reset"))
                startTheGame(map);

            else if(inputCommand.equals("save")){
                MapSaver mapSaver = MapSaver.getInstanceOfMapSaver();
                mapSaver.saveData(map);
                System.out.println("Your status is saved successfully.");
                break;
            }
            else{

                System.out.println("Unknown command..!");
            }


            int position = player.getPosition();
            if(position == target){
                System.out.println("\n\t*** You Won The Game, Congrats. ***\n");
                break;
            }
            System.out.println("\t*Enter the next command...");
            inputCommand=s.nextLine().toLowerCase().trim();
        }
        if(inputCommand.equals("q"))
            System.out.println("you lose the game.");
    }
}
