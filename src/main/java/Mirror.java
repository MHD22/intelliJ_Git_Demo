public class Mirror implements Item,Checkable {
    String itemName="You See a silhouette of you";
    String keyName="";
    boolean isThereKey;

    public Mirror(boolean isThereKey,String keyName ) {
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
            System.out.println( "The (" +keyName+ ") key was acquired");
        }
        else {
            System.out.println( "Nothing behind this Mirror.");
        }
    }
}
