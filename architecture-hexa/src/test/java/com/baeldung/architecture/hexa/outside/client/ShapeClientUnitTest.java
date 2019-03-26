package com.baeldung.architecture.hexa.outside.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.architecture.hexa.inside.DummyShapeService;
import com.baeldung.architecture.hexa.outside.repo.DummyShapeRepository;

public class ShapeClientUnitTest {

    private ShapeClient shapeClient;

    @Before
    public void setUp() {
        shapeClient = new ShapeClient(new DummyShapeService(new DummyShapeRepository()));
    }

    @Test
    public void whenDispalyRedShapes_thenAddToModel() {
        Map<String, Object> model = new HashMap<>();

        shapeClient.dispalyRedShapes(model);

        assertThat(model.get("shapes")).isInstanceOf(List.class);
        assertThat(((List) model.get("shapes")).size()).isEqualTo(2);
    }

}
