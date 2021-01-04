public class Room {
    private int roomNumber;
    private Item onNorthWall;
    private Item onSouthWall;
    private Item onEastWall;
    private Item onWestWall;
    private Room northAdjacentRoom ;
    private Room southAdjacentRoom ;
    private Room eastAdjacentRoom ;
    private Room westAdjacentRoom ;
    private boolean isTheRoomLit = false;

    public Room(int roomNumber, Item onNorthWall, Item onSouthWall, Item onEastWall, Item onWestWall, boolean isRoomLit) {
        this.roomNumber = roomNumber;
        this.onNorthWall = onNorthWall;
        this.onSouthWall = onSouthWall;
        this.onEastWall = onEastWall;
        this.onWestWall = onWestWall;
        this.isTheRoomLit = isRoomLit;
    }

    public Item getOnNorthWall() {
        return onNorthWall;
    }

    public Item getOnSouthWall() {
        return onSouthWall;
    }

    public Item getOnEastWall() {
        return onEastWall;
    }

    public Item getOnWestWall() {
        return onWestWall;
    }

    public boolean isTheRoomLit() {
        return isTheRoomLit;
    }

    public void makeTheRoomLit() {
        isTheRoomLit = true;
    }

    public void makeTheRoomDark(){
        isTheRoomLit=false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Room getNorthAdjacentRoom() {
        return northAdjacentRoom;
    }

    public void setNorthAdjacentRoom(Room northAdjacentRoom) {
        this.northAdjacentRoom = northAdjacentRoom;
    }

    public Room getSouthAdjacentRoom() {
        return southAdjacentRoom;
    }

    public void setSouthAdjacentRoom(Room southAdjacentRoom) {
        this.southAdjacentRoom = southAdjacentRoom;
    }

    public Room getEastAdjacentRoom() {
        return eastAdjacentRoom;
    }

    public void setEastAdjacentRoom(Room eastAdjacentRoom) {
        this.eastAdjacentRoom = eastAdjacentRoom;
    }

    public Room getWestAdjacentRoom() {
        return westAdjacentRoom;
    }

    public void setWestAdjacentRoom(Room westAdjacentRoom) {
        this.westAdjacentRoom = westAdjacentRoom;
    }

}
