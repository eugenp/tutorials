package com.baeldung.java_16_features.mapmulti;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

public class JavaStreamMapMultiUnitTest {

    private static final List<Album> albums = Arrays.asList(new Album("album1", 10, Arrays.asList(new Artist("bob", true, Arrays.asList("label1", "label3")), new Artist("tom", true, Arrays.asList("label2", "label3")))),
        new Album("album2", 20, Arrays.asList(new Artist("bill", true, Arrays.asList("label2", "label3")), new Artist("tom", true, Arrays.asList("label2", "label4")))));

    @Test
    public void givenAListOfintegers_whenMapMulti_thenGetListOfOfEvenDoublesPlusPercentage() {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        double percentage = .01;
        List<Double> evenDoubles = integers.stream()
            .<Double> mapMulti((integer, consumer) -> {
                if (integer % 2 == 0) {
                    consumer.accept((double) integer * (1 + percentage));
                }
            })
            .collect(toList());

        assertThat(evenDoubles).containsAll(Arrays.asList(2.02D, 4.04D));
    }

    @Test
    public void givenAListOfintegers_whenFilterMap_thenGetListOfOfEvenDoublesPlusPercentage() {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        double percentage = .01;
        List<Double> evenDoubles = integers.stream()
            .filter(integer -> integer % 2 == 0)
            .<Double> map(integer -> ((double) integer * (1 + percentage)))
            .collect(toList());

        assertThat(evenDoubles).containsAll(Arrays.asList(2.02D, 4.04D));
    }

    @Test
    public void givenAListOfintegers_whenMapMultiToDouble_thenGetSumOfEvenNumbersAfterApplyPercentage() {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        double percentage = .01;
        double sum = integers.stream()
            .mapMultiToDouble((integer, consumer) -> {
                if (integer % 2 == 0) {
                    consumer.accept(integer * (1 + percentage));
                }
            })
            .sum();

        assertThat(sum).isEqualTo(12.12, offset(0.001d));
    }

    @Test
    public void givenAListOfAlbums_whenFlatMap_thenGetListOfArtistAlbumPairs() {

        List<Pair<String, String>> artistAlbum = albums.stream()
            .flatMap(album -> album.getArtists()
                .stream()
                .map(artist -> new ImmutablePair<String, String>(artist.getName(), album.getAlbumName())))
            .collect(toList());

        assertThat(artistAlbum).contains(new ImmutablePair<String, String>("bob", "album1"), new ImmutablePair<String, 
            String>("tom", "album1"), new ImmutablePair<String, String>("bill", "album2"), new ImmutablePair<String, String>("tom", "album2"));
    }

    @Test
    public void givenAListOfAlbums_whenMapMulti_thenGetListOfPairsOfArtistAlbum() {

        List<Pair<String, String>> artistAlbum = albums.stream()
            .<Pair<String, String>> mapMulti((album, consumer) -> {
                for (Artist artist : album.getArtists()) {
                    consumer.accept(new ImmutablePair<String, String>(artist.getName(), album.getAlbumName()));
                }
            })
            .collect(toList());

        assertThat(artistAlbum).contains(new ImmutablePair<String, String>("bob", "album1"), new ImmutablePair<String, String>("tom", "album1"), 
            new ImmutablePair<String, String>("bill", "album2"), new ImmutablePair<String, String>("tom", "album2"));
    }

    @Test
    public void givenAListOfAlbums_whenFlatMap_thenGetListOfArtistAlbumjPairsBelowGivenCost() {

        int upperCost = 9;
        List<Pair<String, String>> artistAlbum = albums.stream()
            .flatMap(album -> album.getArtists()
                .stream()
                .filter(artist -> upperCost > album.getAlbumCost())
                .map(artist -> new ImmutablePair<String, String>(artist.getName(), album.getAlbumName())))
            .collect(toList());

        assertTrue(artistAlbum.isEmpty());
    }

    @Test
    public void givenAListOfAlbums_whenMapMulti_thenGetListOfArtistAlbumPairsBelowGivenCost() {

        int upperCost = 9;
        List<Pair<String, String>> artistAlbum = albums.stream()
            .<Pair<String, String>> mapMulti((album, consumer) -> {
                if (album.getAlbumCost() < upperCost) {
                    for (Artist artist : album.getArtists()) {
                        consumer.accept(new ImmutablePair<String, String>(artist.getName(), album.getAlbumName()));
                    }
                }
            })
            .collect(toList());

        assertTrue(artistAlbum.isEmpty());
    }

    @Test
    public void givenAListOfAlbums_whenMapMulti_thenGetPairsOfArtistMajorLabelsUsingMethodReference() {

        List<Pair<String, String>> artistLabels = albums.stream()
            .mapMulti(Album::artistAlbumPairsToMajorLabels)
            .collect(toList());

        assertThat(artistLabels).contains(new ImmutablePair<String, String>("bob:album1", "label1,label3"), new ImmutablePair<String, String>("tom:album1", "label2,label3"), 
            new ImmutablePair<String, String>("bill:album2", "label2,label3"), new ImmutablePair<String, String>("tom:album2", "label2,label4"));
    }
}