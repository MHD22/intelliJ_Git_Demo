import java.util.HashMap;
import java.util.Map;

public class Seller implements Item {
    String itemName="Seller";
    Map<String,Integer> itemsForSale = new HashMap<>();

    public Seller(Map<String, Integer> itemsForSale) {
        this.itemsForSale = itemsForSale;
    }

    @Override
    public String look() {
        return itemName;
    }

    public Map<String, Integer> getItemsForSale() {
        return itemsForSale;
    }

    public void addItemToTheList(String key, int val ){
        itemsForSale.put(key,val);
    }

    public void removeItemFromTheList(String key){
        itemsForSale.remove(key);
    }
    public boolean hasItemForSale(String item){
        return itemsForSale.containsKey(item);
    }


    public void listItems() {
        String items="";
        if(itemsForSale.size()==0){
            items="There is nothing in the list.";
        }
        else {
            items="The list of items and prices:\n";
            for(String key : itemsForSale.keySet()){
                items+= "\t* "+key+" : "+itemsForSale.get(key)+" Golds.\n";
            }
        }
        System.out.println(items);
    }


}
