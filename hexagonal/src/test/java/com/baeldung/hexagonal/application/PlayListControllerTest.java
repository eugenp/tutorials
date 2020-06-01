package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.core.PlayList;
import com.baeldung.hexagonal.core.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

public class PlayListControllerTest {

    @Autowired
    private PlayListController controller;

    @Test
    public void test_playList_getPlayList() {
        PlayList playList = controller.getPlayList(1L);
        assertThat(playList).isNotNull();
        assertThat(playList.getAllSongs()
            .size()).isEqualTo(2);
        assertThat(playList.getSong("Kenny Rogers", "Lady")).isNotNull();
        assertThat(playList.getSong("Post Malone", "Circles")).isNotNull();
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void test_playList_addSong() {
        PlayList playList = controller.getPlayList(1L);
        assertThat(playList).isNotNull();
        assertThat(playList.getAllSongs()
            .size()).isEqualTo(2);
        controller.addSong(1L, new Song("Falling", "Trevor Daniels"));
        assertThat(playList.getAllSongs()
            .size()).isEqualTo(3);
        assertThat(playList.getSong("Kenny Rogers", "Lady")).isNotNull();
        assertThat(playList.getSong("Post Malone", "Circles")).isNotNull();
        assertThat(playList.getSong("Trevor Daniels", "Falling")).isNotNull();
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void test_playList_removeSong() {
        PlayList playList = controller.getPlayList(1L);
        assertThat(playList).isNotNull();
        assertThat(playList.getAllSongs()
            .size()).isEqualTo(2);
        controller.removeSong(1L, new Song("Lady", "Kenny Rogers"));
        assertThat(playList.getAllSongs()
            .size()).isEqualTo(1);
    }
}
