package adapter;

import domain.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ports.ItemRepository;

@Repository
public class ItemRepositoryImplementation implements ItemRepository {

    private static final Map<Integer, Item> itemMap = new HashMap<Integer, Item>(0);

    @Override
    public List<Item> getItems() {
        return new ArrayList<Item>(itemMap.values());
    }

    @Override
    public Item getItem(int itemId) {
        return itemMap.get(itemId);
    }

    @Override
    public Item addNewItem(Item item) {
        itemMap.put(item.getId(), item);
        return item;
    }

    @Override
    public Item removeItem(int itemId) {
        if (itemMap.get(itemId) != null) {
            Item item = itemMap.get(itemId);
            itemMap.remove(itemId);
            return item;
        } else {
            return null;
        }
    }

}
