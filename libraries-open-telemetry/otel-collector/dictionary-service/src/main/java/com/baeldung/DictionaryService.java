package com.baeldung;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    static final Map<String, String> dictionary = new HashMap<>();

    static {
        dictionary.put("serendipity", "The occurrence and development of events by chance in a happy or beneficial way.");
        dictionary.put("eloquent", "Fluent or persuasive in speaking or writing.");
        dictionary.put("quintessential", "Representing the most perfect or typical example of a quality or class.");
        dictionary.put("resilient", "Able to withstand or recover quickly from difficult conditions.");
        dictionary.put("peregrinate", "To travel or wander from place to place, especially on foot.");
        dictionary.put("susurrus", "A whispering or rustling sound, like that of leaves or the wind.");
        dictionary.put("quixotic", "Extremely idealistic, unrealistic, and impractical.");
        dictionary.put("lacuna", "An unfilled space or gap; a missing portion in a manuscript or text.");
        dictionary.put("ephemeral", "Lasting for a very short time; transient.");
        dictionary.put("syzygy", "The alignment of three celestial bodies in a straight line, commonly the Earth, Moon, and Sun.");
        dictionary.put("pusillanimous", "Showing a lack of courage or determination; timid.");
        dictionary.put("mellifluous", "(of a voice or words) Sweet or musical; pleasant to hear.");
        dictionary.put("defenestration", "The act of throwing someone out of a window.");
        dictionary.put("pulchritudinous", "Having great physical beauty or appeal.");
    }

    private final Random random = new Random();

    public String getRandomWord() {
        try {
            // Randomly pause for a duration between 1 and 5 seconds
            int delay = 1000 + random.nextInt(4000);
            logger.info("Mandatory pause period of {} milliseconds", delay);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Object[] words = dictionary.keySet().toArray();
        return (String) words[random.nextInt(words.length)];
    }

    public String getWordMeaning(String word) {
        return dictionary.getOrDefault(word, "Meaning not found.");
    }
}