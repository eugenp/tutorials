package hexagonal.demo.Adapter;

import hexagonal.demo.domain.RabbitStore;
import hexagonal.demo.port.RabbitRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RabbitRepoImplementation implements RabbitRepo {

    // secondary adapter
    static final Map<Integer, RabbitStore> rabbitImp = new HashMap<Integer, RabbitStore>(0);

    @Override
    public List<RabbitStore> getRabbits() {
        return new ArrayList<RabbitStore>(rabbitImp.values());
    }

    @Override
    public RabbitStore newRabbit(RabbitStore rabbitStore) {
        return rabbitImp.put((int) rabbitStore.getId(), rabbitStore);

    }

    @Override
    public RabbitStore getRabbitById(Long rabbitid) {
        return rabbitImp.get(rabbitid);

    }

    @Override
    public RabbitStore deleteRabbit(Long rabbitid) {
        if (rabbitImp.get(rabbitid) != null) {
            RabbitStore rabbitx = rabbitImp.get(rabbitid);
            rabbitImp.remove(rabbitid);
        } else
            return null;

        return null;
    }

    @Override
    public RabbitStore findByBreed(String breed) {
        return rabbitImp.get(breed);
    }
}