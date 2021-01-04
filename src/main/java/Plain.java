public class Plain implements Item{
    String itemName="Wall";

    @Override
    public String look() {
        return itemName;
    }
}
