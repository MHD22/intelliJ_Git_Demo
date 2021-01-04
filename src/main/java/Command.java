import java.util.Scanner;

public class Command {
    Player player ;
//    Room currentRoom = player.getCurrentRoom();

    public Command(Player player) {
        this.player = player;
    }

    public void faceNorth(){
        player.setDirection("north");
    }

    public void faceSouth(){
        player.setDirection("south");
    }

    public void faceEast(){
        player.setDirection("east");
    }

    public void faceWest(){
        player.setDirection("west");
    }

    public boolean isFacingNorth () {
        return player.getDirection().equalsIgnoreCase( "north");
    }

    public boolean isFacingSouth () {
        return player.getDirection().equalsIgnoreCase( "south");
    }

    public boolean isFacingEast () {
        return player.getDirection().equalsIgnoreCase("east");
    }

    public boolean isFacingWest () {
        return player.getDirection().equalsIgnoreCase( "west");
    }

    public void turnRight(){

        if(isFacingNorth())
            faceEast();

        else if(isFacingEast())
            faceSouth();

        else if(isFacingSouth())
            faceWest();

        else if(isFacingWest())
            faceNorth();
    }

    public void turnLeft(){

        if(isFacingNorth())
            faceWest();

        else if(isFacingEast())
            faceNorth();

        else if(isFacingSouth())
            faceEast();

        else if(isFacingWest())
            faceSouth();
    }

    public void turnAround(){
        turnRight();
        turnRight();
    }

    public void moveForward(){
        Item theFacedItem = getTheFacedItem();


        if(isTheFacedItemDoor(theFacedItem)){
            Door theFacedDoor = (Door) theFacedItem;

            if(theFacedDoor.isOpen()){
                Room currentRoom = player.getCurrentRoom();

                if(isFacingNorth()){
                    Room northAdjacentRoom = currentRoom.getNorthAdjacentRoom();
                    player.setCurrentRoom(northAdjacentRoom);
                }
                else if(isFacingEast()){
                    Room eastAdjacentRoom = currentRoom.getEastAdjacentRoom();
                    player.setCurrentRoom(eastAdjacentRoom);
                }
                else if(isFacingSouth()){
                    Room southAdjacentRoom = currentRoom.getSouthAdjacentRoom();
                    player.setCurrentRoom(southAdjacentRoom);
                }
                else if(isFacingWest()){
                    Room westAdjacentRoom = currentRoom.getWestAdjacentRoom();
                    player.setCurrentRoom(westAdjacentRoom);
                }

                player.setPosition(player.getCurrentRoom().getRoomNumber());
            }
            else{
                System.out.println( "The Door is locked, ( " + theFacedDoor.keyName + " ) key is needed to unlock");
            }
        }
        else{
            System.out.println("You can only move forward/backward through the doors.");
        }

    }

    public Item getTheFacedItem(){
        if(isFacingNorth()){
            return player.getCurrentRoom().getOnNorthWall();
        }
        if (isFacingSouth()){
            return player.getCurrentRoom().getOnSouthWall();
        }
        if (isFacingEast()){
            return player.getCurrentRoom().getOnEastWall();
        }
        if (isFacingWest()){
            return player.getCurrentRoom().getOnWestWall();
        }
        return null;
    }

    private boolean isTheFacedItemDoor(Item theFacedItem){
        return typeOfItem(theFacedItem) == "Door";
    }

    private String typeOfItem(Item obj){
        if(obj instanceof Door)
            return "Door";
        if(obj instanceof Plain)
            return "Wall";
        if(obj instanceof Chest)
            return "Chest";
        if(obj instanceof Painting)
            return "Painting";
        if(obj instanceof Seller)
            return "Seller";
        if(obj instanceof Mirror)
            return "You See a silhouette of you";

        return "Some Thing Went Wrong!";
    }

    private boolean isTheFacedItemSeller(Item theFacedItem){
        return typeOfItem(theFacedItem) == "Seller";
    }

    private boolean isTheFacedItemChest(Item theFacedItem){
        return typeOfItem(theFacedItem) == "Chest";

    }

    public void moveBackward(){
        turnAround();
        moveForward();
    }

    public String look(){
        if(player.getCurrentRoom().isTheRoomLit()){
            Item theFacedItem= getTheFacedItem();
            return theFacedItem.look();
        }
        return "Dark";
    }

    public void playerStatus() {
        String status= "You are in the room number: "+player.getCurrentRoom().getRoomNumber()+".\nYou are facing the "+player.getDirection()+".\nYou have these Items:\n";
        status += "\t* "+player.getGoldsWithPlayer()+" Golds.\n";

        if(player.keysWithPlayer.isEmpty()){
            status+="\t* No keys with you.\n";
        }
        else{
            status+="\t* this list of keys: \n";
            for(String keyName : player.keysWithPlayer){
                status+= "\t\t("+keyName+")\n";
            }
        }
        if(player.isHasFlashlight()){
            status+="\t* A flashlight";
        }
        else{
            status+="\t* No flashlight with you";
        }
        System.out.println(status);

    }

    public void check(){
        Item theFacedItem = getTheFacedItem();

        if(isTheItemCheckable(theFacedItem)){
            Checkable checkableFacedItem = (Checkable) theFacedItem;
            checkableFacedItem.check(player);
        }
        else{
            System.out.println("You can't check this item.\n " +
                    "you can only check the (Mirror, Painting, Door, And Chest).");
        }

    }

    private boolean isTheItemCheckable(Item item){
        return isTheItemNotWall(item) &&  isTheItemNotSeller(item);
    }

    private boolean isTheItemNotWall(Item item){
        return ! typeOfItem(item).equalsIgnoreCase("Wall");
    }

    private boolean isTheItemNotSeller(Item item) {
        return ! typeOfItem(item).equalsIgnoreCase("Seller");
    }

    public void open(){
        Item theFacedItem = getTheFacedItem();

        if(isTheFacedItemDoor(theFacedItem))
            openTheDoor(theFacedItem);

        else if (isTheFacedItemChest(theFacedItem))
            openTheChest(theFacedItem);

        else
            System.out.println("You can apply this command only on doors or Chest");
    }

    private void openTheDoor(Item theFacedItem){

        Door theFacingDoor = (Door) theFacedItem;
        String requiredKey = theFacingDoor.getKeyName();

        if(theFacingDoor.isOpen()){
            System.out.println("The door has already opened");
        }
        else{
            if(isTheKeyExistInPlayerKeys(requiredKey)){

                theFacingDoor.isOpen(true);
            }
            else{
                System.out.println("* ( " + requiredKey + " ) key required to unlock");
            }
        }
    }

    private void openTheChest(Item theFacedItem){

        Chest theFacingChest = (Chest) theFacedItem;
        String requiredKey = theFacingChest.getKeyName();
        if(theFacingChest.isOpen()){
            System.out.println("The chest has already opened");
        }
        else{
            if(isTheKeyExistInPlayerKeys(requiredKey)){

                theFacingChest.isOpen(true);
            }
            else{
                System.out.println("* ( " + requiredKey + " ) key required to unlock");
            }
        }

    }

    private boolean isTheKeyExistInPlayerKeys(String requiredKey ){
        return player.keysWithPlayer.contains(requiredKey);
    }

    public void close(){
        Item theFacedItem = getTheFacedItem();

        if(isTheFacedItemDoor(theFacedItem))
            closeTheDoor(theFacedItem);
        else if (isTheFacedItemChest(theFacedItem))
            closeTheChest(theFacedItem);
        else
            System.out.println("You can apply this command only on doors or Chest");
    }

    private void closeTheDoor(Item theFacedItem) {
        Door theFacingDoor = (Door) theFacedItem;
        if(theFacingDoor.isOpen()){
            theFacingDoor.isOpen(false);
        }
        else{
            System.out.println("The door has already closed");
        }
    }

    private void closeTheChest(Item theFacedItem) {
        Chest theFacingChest = (Chest) theFacedItem;
        if(theFacingChest.isOpen()){
            theFacingChest.isOpen(false);
        }
        else{
            System.out.println("The chest has already closed");
        }
    }

    public void trade(){
        Item theFacedItem = getTheFacedItem();
        if(isTheFacedItemSeller(theFacedItem)){
            Seller theFacedSeller = (Seller) theFacedItem;
            theFacedSeller.listItems();
            printTheTradeCommands();
            Scanner inputScanner = new Scanner(System.in);
            String inputCommand= inputScanner.nextLine();

            while(theCommandIsNotFinish(inputCommand)){
                executeTheCommand(inputCommand, theFacedSeller);
                System.out.println("You are in the trade mode, Enter your next command...");
                inputCommand=inputScanner.nextLine();
            }
        }
        else{
            System.out.println("You can execute this command only when you faced a seller.");
        }
    }

    public void printTheTradeCommands(){
        String commands = "\nyou are in the trade session.\nyou can buy or sell items from the seller.\n" +
                "The price of each key is 10 Golds, and the price of the flashlight is 5 Golds.\n" +
                "You can do your transactions by execute the following commands:\n\n " +
                "\t--- to buy a specific key, write: buy (key name)\n" +
                "\t--- to buy a flashlight, write: buy flash\n" +
                "\t--- to sell a specific key, write: sell (key name)\n" +
                "\t--- to sell a flashlight, write: sell flash\n" +
                "\t--- to list the items with the seller, write: list\n" +
                "\t--- to close the trade session, write: finish\n";
        System.out.println(commands);

    }

    public boolean theCommandIsNotFinish(String inputCommand) {
        return ! inputCommand.equalsIgnoreCase("finish");
    }

    public void executeTheCommand(String inputCommand,Seller theFacedSeller){

        String order = getTheOrderPartFromTheCommand(inputCommand);
        String item = getTheItemPartFromTheCommand(inputCommand);

        if(isTheOrderBuy(order)){
            if(item.equalsIgnoreCase("flash")){
                buyFlashlight(theFacedSeller);
            }
            else{
                String keyName = item.toLowerCase();
                buyKey(keyName,theFacedSeller);
            }
        }
        else if(isTheOrderSell(order)){
            if(item.equalsIgnoreCase("flash")){
                sellFlash(theFacedSeller);
            }
            else{
                String keyName = item.toLowerCase();
                sellKey(keyName, theFacedSeller);
            }
        }
        else if(order.equalsIgnoreCase("list")){
            theFacedSeller.listItems();
        }
    }

    private String getTheItemPartFromTheCommand(String inputCommand) {
        int theFirstSpacePosition= inputCommand.indexOf(' ');
        if(theFirstSpacePosition == -1)
            return "";
        String itemName = inputCommand.substring(theFirstSpacePosition).trim();
        return itemName;
    }

    private String getTheOrderPartFromTheCommand(String inputCommand) {
        int theFirstSpacePosition= inputCommand.indexOf(' ');
        if(theFirstSpacePosition == -1)
            theFirstSpacePosition = inputCommand.length();
        String order = inputCommand.substring(0,theFirstSpacePosition).trim();
        return order;
    }

    private boolean isTheOrderBuy(String order) {
        return order.equalsIgnoreCase("buy");
    }

    private void buyFlashlight(Seller theFacedSeller) {
        int priceOfFlashlight=5;

        if(player.isHasFlashlight()){
            System.out.println("You already have a flashlight.");
        }
        else{
            if(hasEnoughGolds(priceOfFlashlight)){

                if(theFacedSeller.hasItemForSale("flashlight")){

                    int goldsBeforeThePurchase = player.getGoldsWithPlayer();
                    player.setGoldsWithPlayer( goldsBeforeThePurchase - priceOfFlashlight);
                    player.setHasFlashlight(true);
                    theFacedSeller.removeItemFromTheList("flashlight");
                }
                else{
                    System.out.println("The seller doesn't have flashlight for sale.");
                }
            }
            else{
                System.out.println("You don't have enough Golds to buy the flashlight.");
            }
        }
    }

    private boolean hasEnoughGolds(int priceOfItem) {
        return player.getGoldsWithPlayer() >= priceOfItem;
    }

    private void buyKey(String keyName, Seller theFacedSeller) {
        int priceOfKey=10;
        if(hasTheKey(keyName)){
            System.out.println("You already have this key");
        }
        else{
            if(hasEnoughGolds(priceOfKey)){
                if(theFacedSeller.hasItemForSale(keyName)){
                    int goldsBeforeThePurchase = player.getGoldsWithPlayer();
                    player.setGoldsWithPlayer(goldsBeforeThePurchase - priceOfKey);
                    player.keysWithPlayer.add(keyName);
                    theFacedSeller.removeItemFromTheList(keyName);
                }
                else {
                    System.out.println("The seller doesn't have this key for sale.");
                }
            }
            else{
                System.out.println("you don't have enough Golds to buy this key ( "+keyName+" ).");
            }

        }
    }

    private boolean hasTheKey(String keyName) {
        return player.keysWithPlayer.contains(keyName);
    }

    private boolean isTheOrderSell(String order) {
        return order.equalsIgnoreCase("sell");
    }

    private void sellFlash(Seller theFacedSeller) {
        int priceOfFlashlight =5;
        if(player.isHasFlashlight()){
            player.setHasFlashlight(false);
            int goldsBeforeTheSale = player.getGoldsWithPlayer();
            player.setGoldsWithPlayer(goldsBeforeTheSale + priceOfFlashlight);
            theFacedSeller.addItemToTheList("flashlight",priceOfFlashlight);
        }
        else {
            System.out.println("You can't sell the flashlight, because you don't have one.");
        }
    }

    private void sellKey(String keyName, Seller theFacedSeller) {
        int priceOfKey=10;

        if(hasTheKey(keyName)){
            player.keysWithPlayer.remove(keyName);
            int goldsBeforeTheSale = player.getGoldsWithPlayer();
            player.setGoldsWithPlayer(goldsBeforeTheSale + priceOfKey);
            theFacedSeller.addItemToTheList(keyName,priceOfKey);
        }
        else {
            System.out.println("You can't sell this key, because you don't have it.");
        }
    }

    public void useFlashlight(){
        if(player.isHasFlashlight()){
            if(player.getCurrentRoom().isTheRoomLit()){
                turTheLightOff();
            }
            else {
                turnTheLightOn();
            }
        }
        else{
            System.out.println("You don't have a flashlight.");
        }
    }

    private void turTheLightOff() {
        player.getCurrentRoom().makeTheRoomDark();
    }

    private void turnTheLightOn() {
        player.getCurrentRoom().makeTheRoomLit();
    }
}
