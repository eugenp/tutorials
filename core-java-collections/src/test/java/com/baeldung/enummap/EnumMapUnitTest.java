package com.baeldung.enummap;

import org.junit.Test;

import java.util.AbstractMap;
import java.util.EnumMap;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class EnumMapUnitTest {
    public enum Club {
        Juventus, PSG, Madrid, Liverpool, Milan, Lyon, Barcelona, MU
    }

    public enum Player {
        Ronaldo, Neymar, Bale, Salah, Donnarumma, Tolisso, Messi, Pogba
    }

    @Test
    public void whenContructedWithEnumType_ThenOnlyAcceptThatAsKey() {
        EnumMap<Club, String> clubMap = new EnumMap<>(Club.class);
        assertThatCode(() -> clubMap.put(Club.Juventus, "Juventus"))
            .doesNotThrowAnyException();
    }

    @Test
    public void whenConstructedWithEnumMap_ThenSameKeyTypeAndInitialMappings() {
        EnumMap<Club, String> clubMap = new EnumMap<>(Club.class);
        clubMap.put(Club.Juventus, "Juventus");
        clubMap.put(Club.PSG, "PSG");

        EnumMap<Club, String> newClubMap = new EnumMap<>(clubMap);
        assertThat(newClubMap.size()).isEqualTo(2);
        assertThat(newClubMap).containsExactly(
            new AbstractMap.SimpleEntry<>(Club.Juventus, "Juventus"),
            new AbstractMap.SimpleEntry<>(Club.PSG, "PSG")
        );
    }

    @Test
    public void givenEmptyMap_whenConstructedWithMap_ThenException() {
        HashMap ordinaryMap = new HashMap();
        assertThatCode(() -> new EnumMap(ordinaryMap))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Specified map is empty");
    }

    @Test
    public void givenMapWithEntries_whenConstructedWithMap_ThenSucceed() {
        HashMap ordinaryMap = new HashMap();
        ordinaryMap.put(Club.Juventus, "Juventus");
        assertThatCode(() -> new EnumMap(ordinaryMap))
            .doesNotThrowAnyException();
    }

    @Test
    public void givenMapWithMultiTypeEntries_whenConstructedWithMap_ThenException() {
        HashMap ordinaryMap = new HashMap();
        ordinaryMap.put(Club.Juventus, "Juventus");
        ordinaryMap.put(Player.Ronaldo, "Ronaldo");
        assertThatCode(() -> new EnumMap(ordinaryMap))
            .isInstanceOf(ClassCastException.class)
            .hasMessage("class com.baeldung.enummap.EnumMapUnitTest$Club != class com.baeldung.enummap.EnumMapUnitTest$Player");
    }

    @Test
    public void whenPut_thenGet() {
        EnumMap<Club, String> clubMap = new EnumMap(Club.class);
        clubMap.put(Club.Juventus, "Juventus");
        clubMap.put(Club.PSG, null);
        assertThat(clubMap.get(Club.Juventus)).isEqualTo("Juventus");
        assertThat(clubMap.get(Club.PSG)).isNull();
    }

    @Test
    public void givenMapping_whenContains_thenTrue() {
        EnumMap<Club, String> clubMap = new EnumMap(Club.class);
        assertThat(clubMap.containsKey(Club.Juventus)).isFalse();
        assertThat(clubMap.containsValue("Juventus")).isFalse();
        clubMap.put(Club.Juventus, "Juventus");
        assertThat(clubMap.containsKey(Club.Juventus)).isTrue();

        assertThat(clubMap.containsKey(Club.PSG)).isFalse();
        assertThat(clubMap.containsValue(null)).isFalse();
        clubMap.put(Club.PSG, null);
        assertThat(clubMap.containsKey(Club.PSG)).isTrue();
        assertThat(clubMap.containsValue(null)).isTrue();
    }

}
