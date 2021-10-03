package core.usecases;

import domain.Item;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ports.ItemRepository;
import ports.ItemService;

@Service
public class ItemServicesImplementation implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getItems() {
        return itemRepository.getItems();

    }

    @Override
    public Item getItem(int itemId) {
        return itemRepository.getItem(itemId);
    }

    @Override
    public Item addNewItem(Item item) {
        return itemRepository.addNewItem(item);
    }

    @Override
    public Item removeItem(int itemId) {
        return itemRepository.removeItem(itemId);
    }

}
