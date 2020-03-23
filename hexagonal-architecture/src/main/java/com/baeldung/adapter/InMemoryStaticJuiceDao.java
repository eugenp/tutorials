package com.baeldung.adapter;

import com.baeldung.domain.Juice;
import com.baeldung.port.outbound.JuiceDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryStaticJuiceDao implements JuiceDao {

    private Map<String, Juice> juiceStore = new HashMap<>();

    public InMemoryStaticJuiceDao() {
        initJuiceStore();
    }

    private void initJuiceStore() {

        String[] orangeJuiceIngredients = new String[]{"Orange", "Sugar", "Water"};

        String orangeJuiceName = "orange";

        juiceStore.put(orangeJuiceName, new Juice(orangeJuiceName, orangeJuiceIngredients));

    }

    @Override
    public void addJuice(Juice juice) {
        juiceStore.put(juice.getName().toLowerCase(), juice);
    }

    @Override
    public Juice getJuice(String name) {
        return juiceStore.get(name.toLowerCase());
    }

    @Override
    public List<Juice> getAllJuice() {
        return new ArrayList<>(juiceStore.values());
    }
}
