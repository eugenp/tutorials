package com.bealdung.graphqlDGS;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class AlbumsDataFetcher {

    private final List<Album> albums = Arrays.asList(
      new Album("Rumours", "Fleetwood Mac", 20),
      new Album("What's Going On", "Marvin Gaye", 10),
      new Album("Pet Sounds", "The Beach Boys", 12)
    );

    @DgsQuery
    public List<Album> albums(@InputArgument String titleFilter) {
        if(titleFilter == null) {
            return albums;
        }
        return albums.stream()
                .filter(s -> s.getTitle().contains(titleFilter))
                .collect(Collectors.toList());
    }
}
