package com.baeldung.execption;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ActorService {

    List<String> actors = Arrays.asList("Jack Nicholson", "Marlon Brando", "Robert De Niro", "Al Pacino", "Tom Hanks");
    List<String> actress = Arrays.asList("Jennifer Lawrence", "Natalie Portman", "Scarlett Johansson", "Anne Hathaway", "Emma Stone");

    public String getActor(int index) throws ActorNotFoundException {
        if (index >= actors.size()) {
            throw new ActorNotFoundException("Actor Not Found in Repsoitory");
        }
        return actors.get(index);
    }

    public String getActress(int index) throws ActressNotFoundException {
        if (index >= actress.size()) {
            throw new ActressNotFoundException("Actress Not Found in Repsoitory");
        }
        return actors.get(index);
    }

    public String getLeadActor(int index) throws LeadActorNotFoundException {
        if (index >= actors.size()) {
            throw new LeadActorNotFoundException("Actor Not Found in Repsoitory");
        }
        return actors.get(index);
    }

    public String getLeadActress(int index) throws LeadActressNotFoundException {
        if (index >= actress.size()) {
            throw new LeadActressNotFoundException("Actress Not Found in Repsoitory");
        }
        return actors.get(index);
    }

}
