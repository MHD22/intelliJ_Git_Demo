public class Door implements Item, Checkable {
    String keyName;
    boolean isOpen;
    String itemName="Door";
    public Door(String keyName, boolean isOpen) {
        this.keyName =keyName;
        this.isOpen = isOpen;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }


    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isClosed(){return ! this.isOpen;}

    public void isOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public String look() {
        return itemName;
    }

    @Override
    public void check(Player player) {
       if(isClosed()){
           System.out.println( "The Door is locked, ( " + this.keyName + " ) key is needed to unlock");
       }
       else{
           System.out.println( "The Door is open");
       }

    }
}
