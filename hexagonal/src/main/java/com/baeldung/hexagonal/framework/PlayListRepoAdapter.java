package com.baeldung.hexagonal.framework;

import com.baeldung.hexagonal.core.PlayList;
import com.baeldung.hexagonal.core.Song;

import java.util.Collection;

public interface PlayListRepoAdapter {

    PlayList getPlayList(Long id);

    PlayList createPlayList(Collection<Song> songs);
}
