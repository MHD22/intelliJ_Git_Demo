import java.util.*;

public class Player {
    private int goldsWithPlayer = 15;
    private int position = 1;
    private String direction = "east";
    private Room currentRoom;
    public Set<String> keysWithPlayer = new HashSet<String>();
    private boolean hasFlashlight = false;

    public Player(int position, String direction, Room currentRoom) {
        this.position = position;
        this.direction = direction;
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Set<String> getKeysWithPlayer() {
        return keysWithPlayer;
    }

    public void setKeysWithPlayer(Set<String> keysWithPlayer) {
        this.keysWithPlayer = keysWithPlayer;
    }

    public boolean isHasFlashlight() {
        return hasFlashlight;
    }

    public void setHasFlashlight(boolean hasFlashlight) {
        this.hasFlashlight = hasFlashlight;
    }

    public int getGoldsWithPlayer() {
        return goldsWithPlayer;
    }

    public void setGoldsWithPlayer(int goldsWithPlayer) {
        this.goldsWithPlayer = goldsWithPlayer;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}

