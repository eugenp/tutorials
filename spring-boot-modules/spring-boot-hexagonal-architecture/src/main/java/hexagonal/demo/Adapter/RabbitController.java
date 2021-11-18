package hexagonal.demo.Adapter;

import hexagonal.demo.domain.RabbitStore;
import hexagonal.demo.port.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RabbitController {

    // primary adapter

    RabbitService rabbitService;

    @GetMapping("/rabbits")
    public List<RabbitStore> getRabbits() {
        return rabbitService.getRabbits();

    }

    @GetMapping("/rabbits/{id}")
    public RabbitStore getRabbitsId(@PathVariable long id) {
        return rabbitService.getRabbitById(id);

    }

    @PostMapping("/save")
    public RabbitStore saveRabbit(@RequestBody RabbitStore rabbit) {

        return rabbitService.newRabbit(rabbit);
    }

    @PutMapping("/saveu")
    public RabbitStore updateRabbit(@RequestBody RabbitStore rabbit) {
        RabbitStore rabbitstore = rabbitService.getRabbitById(rabbit.getId());
        rabbitstore.setName(rabbit.getName());
        rabbitstore.setAvailable(rabbit.isAvailable());
        rabbitstore.setDescription(rabbit.getDescription());
        rabbitstore.setPrice(rabbit.getPrice());
        rabbitstore.setSpecies(rabbit.getSpecies());
        RabbitStore update = rabbitService.newRabbit(rabbitstore);
        return update;

    }

    @DeleteMapping("/rabbits/{id}")
    public RabbitStore deleteRabiit(@PathVariable long id) {
        return rabbitService.deleteRabbit(id);
    }

}
