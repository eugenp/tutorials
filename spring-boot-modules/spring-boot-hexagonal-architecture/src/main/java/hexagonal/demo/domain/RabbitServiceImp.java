package hexagonal.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hexagonal.demo.port.RabbitRepo;
import hexagonal.demo.port.RabbitService;

import java.util.List;

public class RabbitServiceImp implements RabbitService {
    // use case
    @Autowired
    private RabbitRepo rabbitRepo;

    @Override
    public List<RabbitStore> getRabbits() {
        return rabbitRepo.getRabbits();

    }

    @Override
    public RabbitStore getRabbitById(Long rabbitid) {

        return rabbitRepo.getRabbitById(rabbitid);
    }

    @Override
    public RabbitStore newRabbit(RabbitStore rabbitStore) {

        return rabbitRepo.newRabbit(rabbitStore);
    }

    @Override
    public RabbitStore deleteRabbit(Long rabbitid) {

        return rabbitRepo.deleteRabbit(rabbitid);
    }

    @Override
    public RabbitStore findByBreed(String breed) {

        return rabbitRepo.findByBreed(breed);
    }

}
