package com.baeldung.hexagonal.framework;

import com.baeldung.hexagonal.core.PlayList;
import com.baeldung.hexagonal.core.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayListRepo implements PlayListRepoAdapter {

    private Map<Long, PlayList> repo = new HashMap<>();

    public PlayListRepo() {
        initializePlayLists();
    }

    private void initializePlayLists() {
        Collection<Song> playList1Songs = new ArrayList<>();
        playList1Songs.add(new Song("Lady", "Kenny Rogers"));
        playList1Songs.add(new Song("Circles", "Post Malone"));
        createPlayList(playList1Songs);

        Collection<Song> playList2Songs = new ArrayList<>();
        playList2Songs.add(new Song("The Bones", "Maren Morris"));
        playList2Songs.add(new Song("Toosie Slide", "Drake"));
        createPlayList(playList2Songs);
    }

    @Override
    public PlayList getPlayList(Long id) {
        return repo.get(id);
    }

    @Override
    public PlayList createPlayList(Collection<Song> songs) {

        PlayList playList = repo.put((long) (repo.size() + 1), new PlayList(songs));
        return playList;
    }
}
