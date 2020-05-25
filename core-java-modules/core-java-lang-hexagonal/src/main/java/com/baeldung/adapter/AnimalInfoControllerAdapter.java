package baeldung.adapter;

import baeldung.port.input.AnimalInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animal")
public class AnimalInfoControllerAdapter {
    private AnimalInfo animalInfo;

    public AnimalInfoControllerAdapter(AnimalInfo animalInfo) {
        this.animalInfo = animalInfo;
    }

    @GetMapping("/class")
    public String getAnimalClassByName(String animalName) {
        return animalInfo.getClassByAnimalName(animalName);
    }
}
