package com.baeldung.hibernate.types;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.YearMonth;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HibernateTypesLiveTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;

    private void deleteAll() {
        albumRepository.deleteAll();
        songRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        deleteAll();
    }

    @BeforeEach
    public void tearDown() {
        setUp();
    }

    @Test
    void whenSavingHibernateTypes_thenTheCorrectJsonIsStoredInTheDatabase() {
        Album emptyAlbum = new Album();
        emptyAlbum = albumRepository.save(emptyAlbum);

        Song emptySong = new Song();
        emptySong = songRepository.save(emptySong);

        Artist superstarArtist = new Artist();
        superstarArtist.setCountry("England");
        superstarArtist.setGenre("Pop");
        superstarArtist.setName("Superstar");

        Song aHappySong = new Song();
        aHappySong.setArtist(superstarArtist);
        aHappySong.setName("A Happy Song");
        aHappySong.setLength(Duration.ofMinutes(4).getSeconds());
        aHappySong = songRepository.save(aHappySong);

        Song aSadSong = new Song();
        aSadSong.setArtist(superstarArtist);
        aSadSong.setName("A Sad Song");
        aSadSong.setLength(Duration.ofMinutes(2).getSeconds());
        aSadSong = songRepository.save(aSadSong);

        Song anotherHappySong = new Song();
        anotherHappySong.setArtist(superstarArtist);
        anotherHappySong.setName("Another Happy Song");
        anotherHappySong.setLength(Duration.ofMinutes(3).getSeconds());
        anotherHappySong = songRepository.save(anotherHappySong);

        Artist newcomer = new Artist();
        newcomer.setCountry("Jamaica");
        newcomer.setGenre("Reggae");
        newcomer.setName("Newcomer");

        Song aNewSong = new Song();
        aNewSong.setArtist(newcomer);
        aNewSong.setName("A New Song");
        aNewSong.setLength(Duration.ofMinutes(5).getSeconds());
        aNewSong = songRepository.save(aNewSong);

        CoverArt superstarAlbumCoverArt = new CoverArt();
        superstarAlbumCoverArt.setUpcCode(UUID.randomUUID().toString());
        superstarAlbumCoverArt.setFrontCoverArtUrl("http://fakeurl-0");
        superstarAlbumCoverArt.setBackCoverArtUrl("http://fakeurl-1");

        Album superstarAlbum = new Album();
        superstarAlbum.setCoverArt(superstarAlbumCoverArt);
        superstarAlbum.setName("The Superstar Album");
        superstarAlbum.setSong(Lists.newArrayList(aHappySong, aSadSong, anotherHappySong));
        superstarAlbum = albumRepository.save(superstarAlbum);

        CoverArt newcomerAlbumCoverArt = new CoverArt();
        newcomerAlbumCoverArt.setUpcCode(UUID.randomUUID().toString());
        newcomerAlbumCoverArt.setFrontCoverArtUrl("http://fakeurl-2");
        newcomerAlbumCoverArt.setBackCoverArtUrl("http://fakeurl-3");

        Album newcomerAlbum = new Album();
        newcomerAlbum.setCoverArt(newcomerAlbumCoverArt);
        newcomerAlbum.setName("The Newcomer Album");
        newcomerAlbum.setSong(Lists.newArrayList(aNewSong));
        albumRepository.save(newcomerAlbum);

        Iterable<Album> selectAlbumsQueryResult = albumRepository.findAll();
        assertThat(selectAlbumsQueryResult).hasSize(3);

        Iterable<Song> selectSongsQueryResult = songRepository.findAll();
        assertThat(selectSongsQueryResult).hasSize(5);

        Album selectAlbumQueryResult;

        selectAlbumQueryResult = albumRepository.findById(emptyAlbum.getId()).get();
        assertThat(selectAlbumQueryResult.getName()).isNull();
        assertThat(selectAlbumQueryResult.getCoverArt()).isNull();
        assertThat(selectAlbumQueryResult.getSongs()).isNullOrEmpty();

        selectAlbumQueryResult = albumRepository.findById(superstarAlbum.getId()).get();
        assertThat(selectAlbumQueryResult.getName()).isEqualTo("The Superstar Album");
        assertThat(selectAlbumQueryResult.getCoverArt().getFrontCoverArtUrl()).isEqualTo("http://fakeurl-0");
        assertThat(selectAlbumQueryResult.getCoverArt().getBackCoverArtUrl()).isEqualTo("http://fakeurl-1");
        assertThat(selectAlbumQueryResult.getSongs()).hasSize(3);
        assertThat(selectAlbumQueryResult.getSongs()).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(aHappySong, aSadSong, anotherHappySong);

        selectAlbumQueryResult = albumRepository.findById(newcomerAlbum.getId()).get();
        assertThat(selectAlbumQueryResult.getName()).isEqualTo("The Newcomer Album");
        assertThat(selectAlbumQueryResult.getCoverArt().getFrontCoverArtUrl()).isEqualTo("http://fakeurl-2");
        assertThat(selectAlbumQueryResult.getCoverArt().getBackCoverArtUrl()).isEqualTo("http://fakeurl-3");
        assertThat(selectAlbumQueryResult.getSongs()).hasSize(1);
        assertThat(selectAlbumQueryResult.getSongs()).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(aNewSong);

        Song selectSongQueryResult;

        selectSongQueryResult = songRepository.findById(emptySong.getId()).get();
        assertThat(selectSongQueryResult.getName()).isNull();
        assertThat(selectSongQueryResult.getLength()).isZero();
        assertThat(selectSongQueryResult.getArtist()).isNull();

        selectSongQueryResult = songRepository.findById(aHappySong.getId()).get();
        assertThat(selectSongQueryResult.getName()).isEqualTo("A Happy Song");
        assertThat(selectSongQueryResult.getLength()).isEqualTo(Duration.ofMinutes(4).getSeconds());
        assertThat(selectSongQueryResult.getArtist().getName()).isEqualTo("Superstar");
        assertThat(selectSongQueryResult.getArtist().getGenre()).isEqualTo("Pop");
        assertThat(selectSongQueryResult.getArtist().getCountry()).isEqualTo("England");

        selectSongQueryResult = songRepository.findById(aSadSong.getId()).get();
        assertThat(selectSongQueryResult.getName()).isEqualTo("A Sad Song");
        assertThat(selectSongQueryResult.getLength()).isEqualTo(Duration.ofMinutes(2).getSeconds());
        assertThat(selectSongQueryResult.getArtist().getName()).isEqualTo("Superstar");
        assertThat(selectSongQueryResult.getArtist().getGenre()).isEqualTo("Pop");
        assertThat(selectSongQueryResult.getArtist().getCountry()).isEqualTo("England");

        selectSongQueryResult = songRepository.findById(anotherHappySong.getId()).get();
        assertThat(selectSongQueryResult.getName()).isEqualTo("Another Happy Song");
        assertThat(selectSongQueryResult.getLength()).isEqualTo(Duration.ofMinutes(3).getSeconds());
        assertThat(selectSongQueryResult.getArtist().getName()).isEqualTo("Superstar");
        assertThat(selectSongQueryResult.getArtist().getGenre()).isEqualTo("Pop");
        assertThat(selectSongQueryResult.getArtist().getCountry()).isEqualTo("England");

        selectSongQueryResult = songRepository.findById(aNewSong.getId()).get();
        assertThat(selectSongQueryResult.getName()).isEqualTo("A New Song");
        assertThat(selectSongQueryResult.getLength()).isEqualTo(Duration.ofMinutes(5).getSeconds());
        assertThat(selectSongQueryResult.getArtist().getName()).isEqualTo("Newcomer");
        assertThat(selectSongQueryResult.getArtist().getGenre()).isEqualTo("Reggae");
        assertThat(selectSongQueryResult.getArtist().getCountry()).isEqualTo("Jamaica");
    }

    @Test
    void whenSavingAHibernateTypeYearMonth_thenTheCorrectValueIsStoredInTheDatabase() {
        Song mySong = new Song();
        YearMonth now = YearMonth.of(2019, 12);
        mySong.setArtist(new Artist());
        mySong.setName("My Song");
        mySong.setLength(Duration.ofMinutes(1).getSeconds());
        mySong.setRecordedOn(now);
        mySong = songRepository.save(mySong);

        Song selectSongQueryResult;
        selectSongQueryResult = songRepository.findById(mySong.getId()).get();
        assertThat(selectSongQueryResult.getRecordedOn().getYear()).isEqualTo(2019);
        assertThat(selectSongQueryResult.getRecordedOn().getMonthValue()).isEqualTo(12);
    }
}
