package com.baeldung.java_16_features.mapmulti;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Album {

    private String albumName;
    private int albumCost;
    private List<Artist> artists;

    Album(String name, int albumCost, List<Artist> artists) {
        this.albumName = name;
        this.artists = artists;
        this.albumCost = albumCost;
    }

    public void artistAlbumPairsToMajorLabels(Consumer<Pair<String, String>> consumer) {

        for (Artist artist : artists) {
            if (artist.isAssociatedMajorLabels()) {
                String concatLabels = artist.getMajorLabels()
                    .stream()
                    .collect(Collectors.joining(","));
                consumer.accept(new ImmutablePair<>(artist.getName() + ":" + albumName, concatLabels));
            }
        }
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getAlbumCost() {
        return albumCost;
    }

    List<Artist> getArtists() {
        return artists;
    }

    public String toString() {
        return albumName;
    }
}
