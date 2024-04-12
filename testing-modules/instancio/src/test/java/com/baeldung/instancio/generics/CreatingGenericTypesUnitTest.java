package com.baeldung.instancio.generics;

import com.baeldung.instancio.student.model.Address;
import org.instancio.Instancio;
import org.instancio.TypeToken;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.allLongs;
import static org.instancio.Select.allStrings;

/**
 * Examples of creating generic types using {@link TypeToken}.
 */
class CreatingGenericTypesUnitTest {


    @Test
    void whenGivenTypeToken_shouldCreateItem() {
        Item<String> item = Instancio.create(new TypeToken<Item<String>>() {});

        assertThat(item.getValue()).isNotBlank();
    }

    @Test
    void whenGivenTypeToken_shouldCreateCustomizedItem() {
        Pair<String, Long> pair = Instancio.of(new TypeToken<Pair<String, Long>>() {})
                .generate(allStrings(), gen -> gen.oneOf("foo", "bar"))
                .generate(allLongs(), gen -> gen.longs().range(5L, 10L))
                .create();

        assertThat(pair.getLeft()).isIn("foo", "bar");
        assertThat(pair.getRight()).isBetween(5L, 10L);
    }

    @Test
    void whenGivenTypeToken_shouldCreateTriplet() {
        Triplet<String, UUID, Long> triplet = Instancio.create(new TypeToken<Triplet<String, UUID, Long>>() {});

        assertThat(triplet.getLeft()).isNotBlank();
        assertThat(triplet.getMiddle()).isNotNull();
        assertThat(triplet.getRight()).isNotNull();
    }

    @Test
    void whenGivenTypeToken_shouldCreateCollection() {
        List<String> list = Instancio.create(new TypeToken<List<String>>() {});

        assertThat(list).isNotEmpty().doesNotContainNull();
    }

    @Test
    void whenGivenTypeToken_shouldCreateMap() {
        Map<UUID, Address> map = Instancio.create(new TypeToken<Map<UUID, Address>>() {});

        assertThat(map).isNotEmpty();
    }

    /**
     * Using type token to create more complex generic objects.
     */
    @Test
    void whenGivenTypeTokenWithNestGenerics_shouldCreateAnInstanceOfSpecifiedType() {
        List<Triplet<Integer, LocalDate, Item<String>>> list = Instancio.create(
                new TypeToken<List<Triplet<Integer, LocalDate, Item<String>>>>() {});

        assertThat(list)
                .isNotEmpty()
                .allSatisfy(triplet -> {
                    assertThat(triplet.getLeft()).isInstanceOf(Integer.class);
                    assertThat(triplet.getMiddle()).isInstanceOf(LocalDate.class);
                    assertThat(triplet.getRight())
                            .isInstanceOf(Item.class)
                            .satisfies(item -> assertThat(item.getValue()).isNotBlank());
                });

        // Sample output
        list.forEach(System.out::println);
    }


    /**
     * Alternative way to create generic objects is using 'withTypeParameters'.
     * However, this approach generates an "unchecked assignment" warning.
     */
    @Test
    @SuppressWarnings("unchecked")
    void whenGivenClassWithTypeParameters_shouldCreateGenericType() {
        Map<UUID, Address> map = Instancio.of(Map.class)
                .withTypeParameters(UUID.class, Address.class)
                .create();

        assertThat(map).isNotEmpty();
    }
}
