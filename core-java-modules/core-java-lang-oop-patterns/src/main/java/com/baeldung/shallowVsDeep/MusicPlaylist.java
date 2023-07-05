package com.baeldung.shallowVsDeep;

import java.util.ArrayList;
import java.util.List;

public class MusicPlaylist {
    private String playlistName;
    private List<Song> songList;

    public MusicPlaylist(String playlistName, List<Song> songList) {
        this.playlistName = playlistName;
        this.songList = songList;
    }

    // Deep Copy Constructor
    public MusicPlaylist(MusicPlaylist musicPlaylist) {
        this.playlistName = musicPlaylist.getPlaylistName();
        this.songList = new ArrayList<>();
        for (Song song : musicPlaylist.getSongList()) {
            this.songList.add(new Song(song));
        }
    }

    // Shallow Copy Method
    public MusicPlaylist shallowCopy() {
        return new MusicPlaylist(this.playlistName, this.songList);
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public String toString() {
        return "MusicPlaylist{" + "playlistName='" + playlistName + '\'' + ", songList=" + songList + '}';
    }
}
