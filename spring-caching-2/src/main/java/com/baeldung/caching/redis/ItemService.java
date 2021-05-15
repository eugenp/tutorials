package com.baeldung.caching.redis;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Cacheable(value = "itemCache")
    public Item getItemForId(String id) {
        return itemRepository.findById(id)
          .orElseThrow(RuntimeException::new);
    }

}
