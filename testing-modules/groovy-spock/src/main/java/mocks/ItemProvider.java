package mocks;

import java.util.List;

public interface ItemProvider {

    List<Item> getItems(List<String> itemIds);

}
