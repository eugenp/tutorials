package mocks;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemService {
    private final ItemProvider itemProvider;
    private final EventPublisher eventPublisher;


    public ItemService(ItemProvider itemProvider, EventPublisher eventPublisher) {
        this.itemProvider = itemProvider;
        this.eventPublisher = eventPublisher;
    }

    List<Item> getAllItemsSortedByName(List<String> itemIds) {
        List<Item> items;
        try{
            items = itemProvider.getItems(itemIds);
        } catch (RuntimeException ex) {
            throw new ExternalItemProviderException();
        }
        return items.stream()
                .sorted(Comparator.comparing(Item::getName))
                .collect(Collectors.toList());
    }

    void saveItems(List<String> itemIds) {
        List<String> notEmptyOfferIds = itemIds.stream()
                .filter(itemId -> !itemId.isEmpty())
                .collect(Collectors.toList());
        // save in database
        notEmptyOfferIds.forEach(eventPublisher::publish);
    }

}
