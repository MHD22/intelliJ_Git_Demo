import com.google.gson.*;
import java.io.*;
import java.util.*;


public class MapGenerator {
    public List<Item> itemsOnNorthWalls =new ArrayList();
    public List<Item> itemsOnSouthWalls =new ArrayList();
    public List<Item> itemsOnEastWalls =new ArrayList();
    public List<Item> itemsOnWestWalls =new ArrayList();
    public List<Boolean> roomsLightStatus = new ArrayList();
    public List<Room> rooms = new ArrayList();
    public List <Integer> northAdjacentRooms = new ArrayList();
    public List <Integer> southAdjacentRooms = new ArrayList();
    public List <Integer> eastAdjacentRooms = new ArrayList();
    public int targetRoomNumber;

    public List<Item> getItemsOnNorthWalls() {
        return itemsOnNorthWalls;
    }

    public List<Item> getItemsOnSouthWalls() {
        return itemsOnSouthWalls;
    }

    public List<Item> getItemsOnEastWalls() {
        return itemsOnEastWalls;
    }

    public List<Item> getItemsOnWestWalls() {
        return itemsOnWestWalls;
    }

    public List<Boolean> getRoomsLightStatus() {
        return roomsLightStatus;
    }

    public List<Integer> getNorthAdjacentRooms() {
        return northAdjacentRooms;
    }

    public List<Integer> getSouthAdjacentRooms() {
        return southAdjacentRooms;
    }

    public List<Integer> getEastAdjacentRooms() {
        return eastAdjacentRooms;
    }

    public List<Integer> getWestAdjacentRooms() {
        return westAdjacentRooms;
    }

    public List <Integer> westAdjacentRooms = new ArrayList();
    public Player player = null;
    public JsonObject playerInfoJson= null;


    public void generateMap(String level){
        emptyTheLists();
        readMapFromJsonFile(level);
        createRooms();
        createPlayer(playerInfoJson);
    }
    public Player getPlayer(){
        return this.player;
    }
    public void emptyTheLists(){
        itemsOnNorthWalls.clear();
        itemsOnSouthWalls.clear();
        itemsOnEastWalls.clear();
        itemsOnWestWalls.clear();
        roomsLightStatus.clear();
        rooms.clear();
        northAdjacentRooms.clear();
        southAdjacentRooms.clear();
        eastAdjacentRooms.clear();
        westAdjacentRooms.clear();


    }
    public void readMapFromJsonFile(String level){
        JsonParser parser = new JsonParser();
        ClassLoader classLoader = getClass().getClassLoader();
        String filePath = "hardMap.json";
        if(level.equals("easy")){
            filePath = "easyMap.json";
        }
        else if (level.equals("hard"))
            filePath= "hardMap.json";
        else if(level.equals("resume")){
            filePath = "savedMap.json";
        }
        else
            System.out.println("Unknown command, will start the hard map as default one");
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        Reader reader = new InputStreamReader(inputStream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();

        JsonArray northWalls = rootObject.getAsJsonArray("northWalls");
        JsonArray southWalls = rootObject.getAsJsonArray("southWalls");
        JsonArray eastWalls = rootObject.getAsJsonArray("eastWalls");
        JsonArray westWalls = rootObject.getAsJsonArray("westWalls");
        JsonArray roomsLightStatus = rootObject.getAsJsonArray("roomsLightStatus");
        JsonArray northAdjacentJson = rootObject.getAsJsonArray("northAdjacentRooms");
        JsonArray southAdjacentJson = rootObject.getAsJsonArray("southAdjacentRooms");
        JsonArray eastAdjacentJson = rootObject.getAsJsonArray("eastAdjacentRooms");
        JsonArray westAdjacentJson = rootObject.getAsJsonArray("westAdjacentRooms");
        JsonElement targetRoom = rootObject.getAsJsonPrimitive("targetRoom");
        this.targetRoomNumber = targetRoom.getAsInt();
        this.playerInfoJson = rootObject.getAsJsonObject("playerInfo");

        createArrayOfItemsOnTheWall(northWalls, this.itemsOnNorthWalls);
        createArrayOfItemsOnTheWall(southWalls, this.itemsOnSouthWalls);
        createArrayOfItemsOnTheWall(eastWalls, this.itemsOnEastWalls);
        createArrayOfItemsOnTheWall(westWalls, this.itemsOnWestWalls);
        createArrayOfLights(roomsLightStatus);

        createAdjacentArrays(northAdjacentJson, northAdjacentRooms );
        createAdjacentArrays(southAdjacentJson, southAdjacentRooms );
        createAdjacentArrays(eastAdjacentJson, eastAdjacentRooms );
        createAdjacentArrays(westAdjacentJson, westAdjacentRooms );




    }

    private void createPlayer(JsonObject playerInfoJson) {
        int position = playerInfoJson.get("position").getAsInt();
        int goldsWithPlayer = playerInfoJson.get("goldsWithPlayer").getAsInt();
        String direction = playerInfoJson.get("direction").getAsString();
        boolean hasFlashLight = playerInfoJson.get("hasFlashLight").getAsBoolean();
        JsonArray keysJson = playerInfoJson.getAsJsonArray("keysWithPlayer");
        Set<String> keys = new HashSet<String>();
        for(JsonElement element : keysJson){
            String key = element.getAsString();
            keys.add(key);
        }
        this.player = new Player(position,direction,rooms.get(position));
        this.player.setHasFlashlight(hasFlashLight);
        this.player.setGoldsWithPlayer(goldsWithPlayer);
        this.player.setKeysWithPlayer(keys);
    }

    private void createAdjacentArrays(JsonArray adjacentRoomsJson, List<Integer> adjacentRoomsArray) {
        for(JsonElement element : adjacentRoomsJson){
            int currentAdjacentRoom = element.getAsInt();
            adjacentRoomsArray.add(currentAdjacentRoom);
        }
    }

    public void createRooms(){
        setRoomsWithItems();
        setAdjacentRooms();
    }

    private void setRoomsWithItems() {
        int numberOfRooms = itemsOnNorthWalls.size();
        rooms.add(null);
        for (int counter = 0 ; counter < numberOfRooms; counter++){
            int roomNumber = counter +1;
            Room room = new Room(
                    roomNumber,
                    itemsOnNorthWalls.get(counter),
                    itemsOnSouthWalls.get(counter),
                    itemsOnEastWalls.get(counter),
                    itemsOnWestWalls.get(counter),
                    roomsLightStatus.get(counter)
            );
            rooms.add(room);

        }
    }

    private void setAdjacentRooms() {
        int numberOfRooms = itemsOnNorthWalls.size();
        for (int counter = 0; counter < numberOfRooms; counter++) {
            int currentRoomNumber = counter+1;
            int indexOfNorthAdjacentRoom = northAdjacentRooms.get(counter);
            int indexOfSouthAdjacentRoom = southAdjacentRooms.get(counter);
            int indexOfEastAdjacentRoom = eastAdjacentRooms.get(counter);
            int indexOfWestAdjacentRoom = westAdjacentRooms.get(counter);

            Room currentRoom = rooms.get(currentRoomNumber);
            currentRoom.setNorthAdjacentRoom(rooms.get(indexOfNorthAdjacentRoom));
            currentRoom.setSouthAdjacentRoom(rooms.get(indexOfSouthAdjacentRoom));
            currentRoom.setEastAdjacentRoom(rooms.get(indexOfEastAdjacentRoom));
            currentRoom.setWestAdjacentRoom(rooms.get(indexOfWestAdjacentRoom));
        }
    }

    private void createArrayOfLights(JsonArray roomsLightStatus) {
        for(JsonElement element : roomsLightStatus){
           boolean currentRoomIsLit = element.getAsBoolean();
           this.roomsLightStatus.add(currentRoomIsLit);
        }
    }

    private void createArrayOfItemsOnTheWall(JsonArray jsonItems, List<Item> arrayOfItemsOnTheWall) {
        for(JsonElement item : jsonItems ){
            JsonObject jsonObject = (JsonObject)item;
            String type = jsonObject.get("type").getAsString();
            Item currentItem = createItem(jsonObject,type);
            arrayOfItemsOnTheWall.add(currentItem);
        }

    }

    private Item createItem(JsonObject jsonObject, String type) {
        Item currentItem = null;
        if(type.equalsIgnoreCase("painting")){
            currentItem = createPainting(jsonObject);
        }
        else if(type.equalsIgnoreCase("mirror")){
            currentItem = createMirror(jsonObject);
        }
        else if(type.equalsIgnoreCase("door")){
            currentItem = createDoor(jsonObject);
        }
        else if(type.equalsIgnoreCase("plain")){
            currentItem = new Plain();
        }
        else if(type.equalsIgnoreCase("seller")){
            currentItem = createSeller(jsonObject);
        }
        else if(type.equalsIgnoreCase("chest")){
            currentItem = createChest(jsonObject);
        }
        return currentItem;
    }

    private Item createChest(JsonObject jsonObject) {

        String keyName = jsonObject.get("keyName").getAsString();
        boolean isOpen = jsonObject.get("isOpen").getAsBoolean();
        boolean isThereFlashlight = jsonObject.get("isThereFlashlight").getAsBoolean();
        int golds = jsonObject.get("golds").getAsInt();
        List <String> keysInTheChest = new ArrayList<>();
        JsonArray keysJson = jsonObject.getAsJsonArray("keys");

        for( JsonElement element : keysJson){
            String key = element.getAsString();
            keysInTheChest.add(key);
        }

        ChestBuilder chestBuilder = new ChestBuilder();
        chestBuilder.setKeyName(keyName);
        chestBuilder.setAmountOfGoldsInsideTheChest(golds);
        chestBuilder.setIsThereFlashLight(isThereFlashlight);
        chestBuilder.setIsOpen(isOpen);
        chestBuilder.setKeysInTheChest(keysInTheChest);

        Item chest = chestBuilder.getChest();
        return chest;
    }

    private Item createSeller(JsonObject jsonObject) {
        Map<String,Integer> itemsForSale = new HashMap<>();
        JsonArray jsonItemsArray = jsonObject.getAsJsonArray("items");
        for(JsonElement element : jsonItemsArray){
            JsonObject item = (JsonObject) element;
            String itemName = item.get("itemName").getAsString();
            int price = item.get("price").getAsInt();
            itemsForSale.put(itemName, price);
        }
        Item seller = new Seller(itemsForSale);
        return seller;
    }

    private Item createDoor(JsonObject jsonObject) {
        String keyName= jsonObject.get("keyName").getAsString();
        boolean isOpen = jsonObject.get("isOpen").getAsBoolean();
        Item door = new Door(keyName,isOpen);
        return door;
    }

    private Item createPainting(JsonObject jsonObject){
        boolean isThereKey = jsonObject.get("isThereKey").getAsBoolean();
        String keyName= jsonObject.get("keyName").getAsString();
        Item painting = new Painting(isThereKey,keyName);
        return painting;
    }

    private Item createMirror(JsonObject jsonObject) {
        boolean isThereKey = jsonObject.get("isThereKey").getAsBoolean();
        String keyName= jsonObject.get("keyName").getAsString();
        Item mirror = new Mirror(isThereKey,keyName);
        return mirror;
    }
}
