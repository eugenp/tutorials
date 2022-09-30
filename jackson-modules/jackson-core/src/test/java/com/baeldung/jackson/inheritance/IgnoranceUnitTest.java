package com.baeldung.jackson.inheritance;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class IgnoranceUnitTest {
    private static abstract class CarMixIn {
        @JsonIgnore
        public String make;
        @JsonIgnore
        public String topSpeed;
    }

    private static class IgnoranceIntrospector extends JacksonAnnotationIntrospector {
        private static final long serialVersionUID = 1422295680188892323L;

        public boolean hasIgnoreMarker(AnnotatedMember m) {
            return m.getDeclaringClass() == IgnoranceMixinOrIntrospection.Vehicle.class && m.getName() == "model" || m.getDeclaringClass() == IgnoranceMixinOrIntrospection.Car.class || m.getName() == "towingCapacity" || super.hasIgnoreMarker(m);
        }
    }

    @Test
    public void givenAnnotations_whenIgnoringProperties_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        IgnoranceAnnotationStructure.Sedan sedan = new IgnoranceAnnotationStructure.Sedan("Mercedes-Benz", "S500", 5, 250.0);
        IgnoranceAnnotationStructure.Crossover crossover = new IgnoranceAnnotationStructure.Crossover("BMW", "X6", 5, 250.0, 6000.0);

        List<IgnoranceAnnotationStructure.Vehicle> vehicles = new ArrayList<>();
        vehicles.add(sedan);
        vehicles.add(crossover);

        String jsonDataString = mapper.writeValueAsString(vehicles);

        assertThat(jsonDataString, containsString("make"));
        assertThat(jsonDataString, not(containsString("model")));
        assertThat(jsonDataString, not(containsString("seatingCapacity")));
        assertThat(jsonDataString, not(containsString("topSpeed")));
        assertThat(jsonDataString, containsString("towingCapacity"));
    }

    @Test
    public void givenMixIns_whenIgnoringProperties_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(IgnoranceMixinOrIntrospection.Car.class, CarMixIn.class);

        String jsonDataString = instantiateAndSerializeObjects(mapper);

        assertThat(jsonDataString, not(containsString("make")));
        assertThat(jsonDataString, containsString("model"));
        assertThat(jsonDataString, containsString("seatingCapacity"));
        assertThat(jsonDataString, not(containsString("topSpeed")));
        assertThat(jsonDataString, containsString("towingCapacity"));
    }

    @Test
    public void givenIntrospection_whenIgnoringProperties_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new IgnoranceIntrospector());

        String jsonDataString = instantiateAndSerializeObjects(mapper);

        assertThat(jsonDataString, containsString("make"));
        assertThat(jsonDataString, not(containsString("model")));
        assertThat(jsonDataString, not(containsString("seatingCapacity")));
        assertThat(jsonDataString, not(containsString("topSpeed")));
        assertThat(jsonDataString, not(containsString("towingCapacity")));
    }

    private String instantiateAndSerializeObjects(ObjectMapper mapper) throws JsonProcessingException {
        IgnoranceMixinOrIntrospection.Sedan sedan = new IgnoranceMixinOrIntrospection.Sedan("Mercedes-Benz", "S500", 5, 250.0);
        IgnoranceMixinOrIntrospection.Crossover crossover = new IgnoranceMixinOrIntrospection.Crossover("BMW", "X6", 5, 250.0, 6000.0);

        List<IgnoranceMixinOrIntrospection.Vehicle> vehicles = new ArrayList<>();
        vehicles.add(sedan);
        vehicles.add(crossover);

        return mapper.writeValueAsString(vehicles);
    }
}