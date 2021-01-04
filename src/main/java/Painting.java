public class Painting implements Item, Checkable {
    String itemName="Painting";
    String keyName="";
    boolean isThereKey;

    public Painting(boolean isThereKey, String keyName ) {
        this.isThereKey = isThereKey;
        this.keyName = keyName;
    }

    public boolean isThereKey() {
        return this.isThereKey;
    }


    public String getKeyName() {
        return keyName;
    }

    @Override
    public String look() {
        return itemName;
    }

    @Override
    public void check(Player player) {
        if(isThereKey){
            player.keysWithPlayer.add(this.keyName);
            isThereKey=false;
            System.out.println("The (" +keyName+ ") key was acquired");
        }
        else{
            System.out.println("Nothing behind this Painting.");
        }
    }
}
