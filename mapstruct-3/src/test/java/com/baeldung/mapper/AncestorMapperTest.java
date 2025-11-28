package com.baeldung.mapper;

import com.baeldung.dto.AncestorDto;
import com.baeldung.entity.GrandDad;
import com.baeldung.entity.Identity;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AncestorMapperTest {

    @Test
    public void whenAncestorMapperToDtoMapsGrandad_thenSourceValuesAreUsed() {
        AncestorMapper mapper = Mappers.getMapper(AncestorMapper.class);
        GrandDad source = new GrandDad(
                new Identity("Thomas", "Tom"),
                "80.00",
                "1234556"
        );
        AncestorDto mapped = mapper.toDto(source);
        assertEquals("Thomas", mapped.getFirstName());
        assertEquals(80.0d, mapped.getWeight(), 0.01d);
        assertEquals("1234556", mapped.getVatNumber());
    }

    @Test
    public void whenAncestorMapperToDtoMapsGrandadWithEmptyValues_thenNullValuesAreMapped() {
        AncestorMapper mapper = Mappers.getMapper(AncestorMapper.class);
        GrandDad source = new GrandDad(
                null,
                null,
                "1234556"
        );
        AncestorDto mapped = mapper.toDto(source);
        assertNull(mapped.getFirstName());
        assertNull(mapped.getWeight());
        assertEquals("1234556", mapped.getVatNumber());
    }

}
