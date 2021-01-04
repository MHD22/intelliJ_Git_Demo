import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapSaver {
    public  static MapSaver obj = new MapSaver();

    private MapSaver() {
    }

    public static MapSaver getInstanceOfMapSaver() {
        return obj;
    }

    public static void saveData(MapGenerator map) throws IOException {
        Writer writer = new FileWriter("src/main/resources/savedMap.json");
        Map<String ,Object> jsonMap = new HashMap<>();

        List<Item> itemsOnNorthWalls = map.getItemsOnNorthWalls();
        List<Item> itemsOnSouthWalls = map.getItemsOnSouthWalls();
        List<Item> itemsOnEastWalls = map.getItemsOnEastWalls();
        List<Item> itemsOnWestWalls = map.getItemsOnWestWalls();
        List<Boolean> roomsLightStatus = map.getRoomsLightStatus();
        List<Integer> northAdjacentRooms = map.getNorthAdjacentRooms();
        List<Integer> southAdjacentRooms = map.getSouthAdjacentRooms();
        List<Integer> eastAdjacentRooms = map.getEastAdjacentRooms();
        List<Integer> westAdjacentRooms = map.getWestAdjacentRooms();
        int targetRoomNumber = map.targetRoomNumber;
        Player player = map.getPlayer();

        JsonArray northWalls = createJsonArray(itemsOnNorthWalls);
        JsonArray southWalls = createJsonArray(itemsOnSouthWalls);
        JsonArray eastWalls = createJsonArray(itemsOnEastWalls);
        JsonArray westWalls = createJsonArray(itemsOnWestWalls);
        JsonArray roomsLightStatusJson = createRoomsLightJsonArray(roomsLightStatus);

        JsonArray northAdjacentJson = createAdjacentRoomsJsonArray(northAdjacentRooms);
        JsonArray southAdjacentJson = createAdjacentRoomsJsonArray(southAdjacentRooms);
        JsonArray eastAdjacentJson = createAdjacentRoomsJsonArray(eastAdjacentRooms);
        JsonArray westAdjacentJson = createAdjacentRoomsJsonArray(westAdjacentRooms);

        JsonObject playerJson = createPlayerJson(player);

        jsonMap.put("northWalls",northWalls);
        jsonMap.put("southWalls",southWalls);
        jsonMap.put("eastWalls",eastWalls);
        jsonMap.put("westWalls",westWalls);
        jsonMap.put("roomsLightStatus",roomsLightStatusJson);

        jsonMap.put("northAdjacentRooms",northAdjacentJson);
        jsonMap.put("southAdjacentRooms",southAdjacentJson);
        jsonMap.put("eastAdjacentRooms",eastAdjacentJson);
        jsonMap.put("westAdjacentRooms",westAdjacentJson);

        jsonMap.put("playerInfo",playerJson);
        jsonMap.put("targetRoom",targetRoomNumber);

        Gson gson = new Gson();
        gson.toJson(jsonMap,writer);
        writer.flush();
        writer.close();
    }

    private static JsonObject createPlayerJson(Player player) {
        JsonObject playerJsonObject = new JsonObject();
        playerJsonObject.addProperty("position",player.getPosition());
        playerJsonObject.addProperty("goldsWithPlayer",player.getGoldsWithPlayer());
        playerJsonObject.addProperty("direction",player.getDirection());
        playerJsonObject.addProperty("hasFlashLight",player.isHasFlashlight());
        Set<String> keys = player.getKeysWithPlayer();
        JsonArray keysArrayJson = new JsonArray();
        for(String key : keys){
            keysArrayJson.add(key);
        }
        playerJsonObject.add("keysWithPlayer",keysArrayJson);
        return playerJsonObject;
    }

    private static JsonArray createAdjacentRoomsJsonArray(List<Integer> adjacentRoomsArray) {
        JsonArray adjacentRoomsJson = new JsonArray();
        for(int roomNumber : adjacentRoomsArray){
            adjacentRoomsJson.add(roomNumber);
        }
        return adjacentRoomsJson;
    }

    private static JsonArray createRoomsLightJsonArray(List<Boolean> roomsLightStatus) {
        JsonArray roomsLightsArray = new JsonArray();
        for(Boolean isLit : roomsLightStatus){
            roomsLightsArray.add(isLit);
        }
        return roomsLightsArray;
    }

    private static JsonArray createJsonArray(List<Item> itemsOnWalls) {
        JsonArray itemsOnWallsJson = new JsonArray();
        for( Item item : itemsOnWalls){
            JsonObject itemJson = null;
            if(item.look().equalsIgnoreCase("Painting")){
                itemJson =  createPaintitngJsonObject(item);
            }
            else if(item.look().equalsIgnoreCase("You See a silhouette of you")){
                itemJson = createMirrorJsonObject(item);
            }
            else if(item.look().equalsIgnoreCase("Door")){
                itemJson = createDoorJsonObject(item);
            }
            else if(item.look().equalsIgnoreCase("Wall")){
                itemJson = createPlainWallJsonObject(item);
            }
            else if(item.look().equalsIgnoreCase("Seller")){
                itemJson = createSellerJsonObject(item);
            }
            else if(item.look().equalsIgnoreCase("chest")){
                itemJson = createChestJsonObject(item);
            }
            itemsOnWallsJson.add(itemJson);
        }
        return itemsOnWallsJson;
    }

    private static JsonObject createChestJsonObject(Item item) {
        Chest chest = (Chest)item;
        JsonObject chestJson = new JsonObject();
        chestJson.addProperty("type","chest");
        chestJson.addProperty("keyName",chest.getKeyName());
        chestJson.addProperty("isOpen",chest.isOpen());
        chestJson.addProperty("isThereFlashlight",chest.isThereFlashLight());
        chestJson.addProperty("golds",chest.getAmountOfGoldsInsideTheChest());
        JsonArray keysJson = new JsonArray();
        List<String> keysInTheChest = chest.getKeysInTheChest();
        for(String key: keysInTheChest){
            keysJson.add(key);
        }
        chestJson.add("keys",keysJson);
        return chestJson;
    }

    private static JsonObject createSellerJsonObject(Item item) {
        Seller seller = (Seller) item;
        JsonObject  sellerJson = new JsonObject();
        sellerJson.addProperty("type","seller");
        JsonArray itemsJson = new JsonArray();
        Map <String,Integer> itemsForSale = seller.getItemsForSale();
        for(String key : itemsForSale.keySet()){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("itemName",key);
            jsonObject.addProperty("price",itemsForSale.get(key));
            itemsJson.add(jsonObject);
        }
        sellerJson.add("items",itemsJson);
        return sellerJson;
    }

    private static JsonObject createPlainWallJsonObject(Item item) {
        JsonObject wallJson = new JsonObject();
        wallJson.addProperty("type","plain");
        return wallJson;
    }

    private static JsonObject createDoorJsonObject(Item item) {
        Door door = (Door) item;
        JsonObject doorToJson = new JsonObject();
        doorToJson.addProperty("type","door");
        doorToJson.addProperty("keyName",door.getKeyName());
        doorToJson.addProperty("isOpen",door.isOpen());
        return doorToJson;
    }

    private static JsonObject createMirrorJsonObject(Item item) {
        Mirror mirror = (Mirror) item;
        JsonObject mirrorToJson = new JsonObject();
        mirrorToJson.addProperty("type","mirror");
        mirrorToJson.addProperty("isThereKey",mirror.isThereKey());
        mirrorToJson.addProperty("keyName",mirror.getKeyName());
        return mirrorToJson;
    }

    private static JsonObject createPaintitngJsonObject(Item item) {
        Painting painting = (Painting) item;
        JsonObject paintingToJson = new JsonObject();
        paintingToJson.addProperty("type","painting");
        paintingToJson.addProperty("isThereKey",painting.isThereKey());
        paintingToJson.addProperty("keyName",painting.getKeyName());
        return paintingToJson;
    }


}
