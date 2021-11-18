package hexagonal.demo.port;

import hexagonal.demo.domain.RabbitStore;

import java.util.List;

import org.springframework.stereotype.Repository;

public interface RabbitRepo {
    // secondary port
    List<RabbitStore> getRabbits();

    RabbitStore getRabbitById(Long rabbitid);

    RabbitStore newRabbit(RabbitStore rabbitStore);

    RabbitStore deleteRabbit(Long rabbitid);

    RabbitStore findByBreed(String breed);
}
