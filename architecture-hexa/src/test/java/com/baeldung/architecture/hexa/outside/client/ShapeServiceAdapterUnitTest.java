package com.baeldung.architecture.hexa.outside.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.architecture.hexa.inside.DummyShapeService;
import com.baeldung.architecture.hexa.outside.repo.DummyShapeRepositoryAdapter;

public class ShapeServiceAdapterUnitTest {

    private ShapeServiceAdapter shapeServiceAdapter;

    @Before
    public void setUp() {
        shapeServiceAdapter = new ShapeServiceAdapter(new DummyShapeService(new DummyShapeRepositoryAdapter()));
    }

    @Test
    public void whenDispalyRedShapes_thenAddToModel() {
        Map<String, Object> model = new HashMap<>();

        shapeServiceAdapter.dispalyRedShapes(model);

        assertThat(model.get("shapes")).isInstanceOf(List.class);
        assertThat(((List) model.get("shapes")).size()).isEqualTo(2);
    }

}
