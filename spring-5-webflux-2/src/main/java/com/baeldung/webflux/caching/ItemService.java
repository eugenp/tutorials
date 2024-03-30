package com.baeldung.webflux.caching;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import reactor.core.publisher.Mono;

@Service
public class ItemService {

    private final ItemRepository repository;
    private final LoadingCache<String, Mono<Item>> cache;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
        this.cache = Caffeine.newBuilder().build(this::getItem_withCaffeine);
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
    public Mono<Item> getItem_withCaffeine(String id) {
        return cache.asMap().computeIfAbsent(id, k -> repository.findById(id).cast(Item.class));
    }
}
