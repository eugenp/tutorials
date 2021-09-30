package ports;

import domain.Item;
import java.util.List;

public interface ItemRepository {

    List<Item> getItems();

    Item getItem(int itemId);

    Item addNewItem(Item item);

    Item removeItem(int itemId);
}
