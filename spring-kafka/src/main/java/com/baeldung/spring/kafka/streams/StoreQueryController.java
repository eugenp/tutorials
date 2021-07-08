package com.baeldung.spring.kafka.streams;

import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreQueryController {

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @GetMapping("/shop/{id}")
    public String getStoreCount(@PathVariable("id") long storeId) {
        final ReadOnlyKeyValueStore<Long, String> storeTable
            = interactiveQueryService.getQueryableStore("shop-order-table", QueryableStoreTypes.keyValueStore());
        return storeTable.get(storeId);
    }
}
