package com.baeldung.cloud.openfeign.client;

import com.baeldung.cloud.openfeign.model.Album;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "albumClient", url = "https://jsonplaceholder.typicode.com/albums/")
public interface AlbumClient {
    @GetMapping(value = "/{id}")
    Album getAlbumById(@PathVariable(value = "id") Integer id);

    @GetMapping(value = "/{id}")
    Album getAlbumByIdAndDynamicUrl(@PathVariable(name = "id") Integer id);
}
