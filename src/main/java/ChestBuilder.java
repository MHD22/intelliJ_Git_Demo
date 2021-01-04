import java.util.*;

public class ChestBuilder {

    private String keyName;
    private boolean isOpen;
    private List<String> keysInTheChest = new ArrayList<>();
    private boolean isThereFlashLight;
    private int amountOfGoldsInsideTheChest;

    public ChestBuilder setKeyName(String keyName) {
        this.keyName = keyName;
        return this;
    }

    public ChestBuilder setIsOpen(boolean open) {
        isOpen = open;
        return this;
    }

    public ChestBuilder setKeysInTheChest(List<String> keysInTheChest) {
        this.keysInTheChest = keysInTheChest;
        return this;
    }

    public ChestBuilder setIsThereFlashLight(boolean thereFlashLight) {
        isThereFlashLight = thereFlashLight;
        return this;
    }

    public ChestBuilder setAmountOfGoldsInsideTheChest(int amountOfGoldsInsideTheChest) {
        this.amountOfGoldsInsideTheChest = amountOfGoldsInsideTheChest;
        return this;
    }

    public Chest getChest(){
        return new Chest( keyName,  isOpen, keysInTheChest,  isThereFlashLight,  amountOfGoldsInsideTheChest);
    }
}
