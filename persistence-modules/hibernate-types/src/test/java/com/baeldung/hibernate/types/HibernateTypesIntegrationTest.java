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
public class HibernateTypesIntegrationTest {

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
    void hibernateTypeJsonTest() {
        Album nullAlbum = new Album();
        nullAlbum = albumRepository.save(nullAlbum);

        Song nullSong = new Song();
        nullSong = songRepository.save(nullSong);

        Artist artist0 = new Artist();
        artist0.setCountry("England");
        artist0.setGenre("Pop");
        artist0.setName("Superstar");

        Song song0 = new Song();
        song0.setArtist(artist0);
        song0.setName("A Happy Song");
        song0.setLength(Duration.ofMinutes(4).getSeconds());
        song0 = songRepository.save(song0);

        Song song1 = new Song();
        song1.setArtist(artist0);
        song1.setName("A Sad Song");
        song1.setLength(Duration.ofMinutes(2).getSeconds());
        song1 = songRepository.save(song1);

        Song song2 = new Song();
        song2.setArtist(artist0);
        song2.setName("Another Happy Song");
        song2.setLength(Duration.ofMinutes(3).getSeconds());
        song2 = songRepository.save(song2);

        Artist artist1 = new Artist();
        artist1.setCountry("Jamaica");
        artist1.setGenre("Reggae");
        artist1.setName("Newcomer");

        Song song3 = new Song();
        song3.setArtist(artist1);
        song3.setName("A New Song");
        song3.setLength(Duration.ofMinutes(5).getSeconds());
        song3 = songRepository.save(song3);

        CoverArt album0CoverArt = new CoverArt();
        album0CoverArt.setUpcCode(UUID.randomUUID().toString());
        album0CoverArt.setFrontCoverArtUrl("http://fakeurl-0");
        album0CoverArt.setBackCoverArtUrl("http://fakeurl-1");

        Album album0 = new Album();
        album0.setCoverArt(album0CoverArt);
        album0.setName("Album 0");
        album0.setSong(Lists.newArrayList(song0, song1, song2));
        album0 = albumRepository.save(album0);

        CoverArt album1CoverArt = new CoverArt();
        album1CoverArt.setUpcCode(UUID.randomUUID().toString());
        album1CoverArt.setFrontCoverArtUrl("http://fakeurl-2");
        album1CoverArt.setBackCoverArtUrl("http://fakeurl-3");

        Album album1 = new Album();
        album1.setCoverArt(album1CoverArt);
        album1.setName("Album 1");
        album1.setSong(Lists.newArrayList(song3));
        albumRepository.save(album1);

        Iterable<Album> queryAlbumsResult = albumRepository.findAll();
        assertThat(queryAlbumsResult).hasSize(3);

        Iterable<Song> querySongsResult = songRepository.findAll();
        assertThat(querySongsResult).hasSize(5);

        Album queryAlbumResult;

        queryAlbumResult = albumRepository.findById(nullAlbum.getId()).get();
        assertThat(queryAlbumResult.getName()).isNull();
        assertThat(queryAlbumResult.getCoverArt()).isNull();
        assertThat(queryAlbumResult.getSongs()).isNullOrEmpty();

        queryAlbumResult = albumRepository.findById(album0.getId()).get();
        assertThat(queryAlbumResult.getName()).isEqualTo("Album 0");
        assertThat(queryAlbumResult.getCoverArt().getFrontCoverArtUrl()).isEqualTo("http://fakeurl-0");
        assertThat(queryAlbumResult.getCoverArt().getBackCoverArtUrl()).isEqualTo("http://fakeurl-1");
        assertThat(queryAlbumResult.getSongs()).hasSize(3);
        assertThat(queryAlbumResult.getSongs()).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(song0, song1, song2);

        queryAlbumResult = albumRepository.findById(album1.getId()).get();
        assertThat(queryAlbumResult.getName()).isEqualTo("Album 1");
        assertThat(queryAlbumResult.getCoverArt().getFrontCoverArtUrl()).isEqualTo("http://fakeurl-2");
        assertThat(queryAlbumResult.getCoverArt().getBackCoverArtUrl()).isEqualTo("http://fakeurl-3");
        assertThat(queryAlbumResult.getSongs()).hasSize(1);
        assertThat(queryAlbumResult.getSongs()).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(song3);

        Song querySongResult;

        querySongResult = songRepository.findById(nullSong.getId()).get();
        assertThat(querySongResult.getName()).isNull();
        assertThat(querySongResult.getLength()).isZero();
        assertThat(querySongResult.getArtist()).isNull();

        querySongResult = songRepository.findById(song0.getId()).get();
        assertThat(querySongResult.getName()).isEqualTo("A Happy Song");
        assertThat(querySongResult.getLength()).isEqualTo(Duration.ofMinutes(4).getSeconds());
        assertThat(querySongResult.getArtist().getName()).isEqualTo("Superstar");
        assertThat(querySongResult.getArtist().getGenre()).isEqualTo("Pop");
        assertThat(querySongResult.getArtist().getCountry()).isEqualTo("England");

        querySongResult = songRepository.findById(song1.getId()).get();
        assertThat(querySongResult.getName()).isEqualTo("A Sad Song");
        assertThat(querySongResult.getLength()).isEqualTo(Duration.ofMinutes(2).getSeconds());
        assertThat(querySongResult.getArtist().getName()).isEqualTo("Superstar");
        assertThat(querySongResult.getArtist().getGenre()).isEqualTo("Pop");
        assertThat(querySongResult.getArtist().getCountry()).isEqualTo("England");

        querySongResult = songRepository.findById(song2.getId()).get();
        assertThat(querySongResult.getName()).isEqualTo("Another Happy Song");
        assertThat(querySongResult.getLength()).isEqualTo(Duration.ofMinutes(3).getSeconds());
        assertThat(querySongResult.getArtist().getName()).isEqualTo("Superstar");
        assertThat(querySongResult.getArtist().getGenre()).isEqualTo("Pop");
        assertThat(querySongResult.getArtist().getCountry()).isEqualTo("England");

        querySongResult = songRepository.findById(song3.getId()).get();
        assertThat(querySongResult.getName()).isEqualTo("A New Song");
        assertThat(querySongResult.getLength()).isEqualTo(Duration.ofMinutes(5).getSeconds());
        assertThat(querySongResult.getArtist().getName()).isEqualTo("Newcomer");
        assertThat(querySongResult.getArtist().getGenre()).isEqualTo("Reggae");
        assertThat(querySongResult.getArtist().getCountry()).isEqualTo("Jamaica");
    }

    @Test
    void hibernateTypeYearMonthTest() {
        Song mySong = new Song();
        YearMonth now = YearMonth.of(2019, 12);
        mySong.setArtist(new Artist());
        mySong.setName("My Song");
        mySong.setLength(Duration.ofMinutes(1).getSeconds());
        mySong.setRecordedOn(now);
        mySong = songRepository.save(mySong);

        Song queryResult;
        queryResult = songRepository.findById(mySong.getId()).get();
        assertThat(queryResult.getRecordedOn().getYear()).isEqualTo(2019);
        assertThat(queryResult.getRecordedOn().getMonthValue()).isEqualTo(12);
    }
}
