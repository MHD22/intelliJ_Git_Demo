import java.util.*;

public class Chest implements Item, Checkable {
    String itemName="Chest";
    String keyName;
    boolean isOpen;
    List<String> keysInTheChest = new ArrayList<>();
    boolean isThereFlashLight;
    int amountOfGoldsInsideTheChest;

    public Chest(String keyName, boolean isOpen, List<String> keysInTheChest, boolean isThereFlashLight, int amountofGoldsInsideTheChest) {
        this.keyName = keyName;
        this.isOpen = isOpen;
        this.keysInTheChest = keysInTheChest;
        this.isThereFlashLight = isThereFlashLight;
        this.amountOfGoldsInsideTheChest = amountofGoldsInsideTheChest;
    }

    public List<String> getKeysInTheChest() {
        return keysInTheChest;
    }

    public boolean isThereFlashLight() {
        return isThereFlashLight;
    }

    public String getKeyName() {
        return keyName;
    }

    public int getAmountOfGoldsInsideTheChest() {
        return amountOfGoldsInsideTheChest;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void isOpen(boolean open) {
        isOpen = open;
    }
    public boolean isClosed(){
        return ! isOpen;
    }

    @Override
    public String toString() {
        String itemsFound = "* These items are acquired:{\n";

        for(String str : keysInTheChest){
            itemsFound+="\t* (" + str + ") Key\n";
        }

        if(isThereFlashLight){
            itemsFound+="\t* Flash Light.\n";
        }
        itemsFound+= "\t* " + amountOfGoldsInsideTheChest +" Golds.\n" +'}';
        return itemsFound;
    }

    @Override
    public String look() {
        return itemName;
    }


    public void setAmountOfGoldsInsideTheChest(int amountOfGoldsInsideTheChest) {
        this.amountOfGoldsInsideTheChest = amountOfGoldsInsideTheChest;
    }

    @Override
    public void check(Player player) {

        if(this.isOpen()){
            System.out.println(this.toString());
            int totalGolds = player.getGoldsWithPlayer() + this.amountOfGoldsInsideTheChest;
            player.setGoldsWithPlayer(totalGolds);
            this.amountOfGoldsInsideTheChest = 0;
            boolean flashStatus = player.isHasFlashlight() || isThereFlashLight ;
            player.setHasFlashlight(flashStatus);
            for(String key : keysInTheChest )
                player.keysWithPlayer.add(key);
            keysInTheChest.clear();
        }
        else{
            System.out.println("chest closed ( "+keyName+" ) key is needed to unlock");
        }
    }
}
