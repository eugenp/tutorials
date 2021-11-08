package com.baeldung.caching;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;

@Service
public class ItemService {

    private final ItemRepository repository;
    private final LoadingCache<String, Object> cache;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder()
                .build(this::getItem_withAddons);
    }

    @Cacheable("items")
    public Mono<Item> getItem(String id) {
        return repository.findById(id);
    }

    public Mono<Item> save(Item item) {
        return repository.save(item);
    }

    @Cacheable("items")
    public Mono<Item> getItem_withCache(String id) {
        return repository.findById(id).cache();
    }

    @Cacheable("items")
    public Mono<Item> getItem_withAddons(String id) {
        return CacheMono.lookup(cache.asMap(), id)
                .onCacheMissResume(() -> repository.findById(id).cast(Object.class)).cast(Item.class);
    }

}
