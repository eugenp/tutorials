package com.baeldung.map.incrementmapkey;

import java.util.HashMap;
import java.util.Map;

public class BenchmarkMapMethods {
    public static void main(String[] args) {
        BenchmarkMapMethods bmm = new BenchmarkMapMethods();
        Map<String, Long> map = new HashMap<>();
        map.put("Guava", bmm.benchMarkGuavaMap());
        map.put("ContainsKey", bmm.benchContainsKeyMap());
        map.put("MergeMethod", bmm.benchMarkMergeMethod());
        map.put("ComputeMethod", bmm.benchMarComputeMethod());
        map.put("GetOrDefault", bmm.benchMarkGetOrDefaultMethod());
    }

    private long benchMarkGuavaMap() {
        long startTime = System.nanoTime();
        IncrementMapValueWays im = new IncrementMapValueWays();
        im.charFrequencyUsingAtomicMap(getString());
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private long benchContainsKeyMap() {
        long startTime = System.nanoTime();
        IncrementMapValueWays im = new IncrementMapValueWays();
        im.charFrequencyUsingContainsKey(getString());
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private long benchMarComputeMethod() {
        long startTime = System.nanoTime();
        IncrementMapValueWays im = new IncrementMapValueWays();
        im.charFrequencyUsingCompute(getString());
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private long benchMarkMergeMethod() {
        long startTime = System.nanoTime();
        IncrementMapValueWays im = new IncrementMapValueWays();
        im.charFrequencyUsingMerge(getString());
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private long benchMarkGetOrDefaultMethod() {
        long startTime = System.nanoTime();
        IncrementMapValueWays im = new IncrementMapValueWays();
        im.charFrequencyUsingGetOrDefault(getString());
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private String getString() {
        return
          "Once upon a time in a quaint village nestled between rolling hills and whispering forests, there lived a solitary storyteller named Elias. Elias was known for spinning tales that transported listeners to magical realms and awakened forgotten dreams.\n"
            + "\n"
            + "His favorite spot was beneath an ancient oak tree, its sprawling branches offering shade to those who sought refuge from the bustle of daily life. Villagers of all ages would gather around Elias, their faces illuminated by the warmth of his stories.\n"
            + "\n" + "One evening, as dusk painted the sky in hues of orange and purple, a curious young girl named Lily approached Elias. Her eyes sparkled with wonder as she asked for a tale unlike any other.\n" + "\n"
            + "Elias smiled, sensing her thirst for adventure, and began a story about a forgotten kingdom veiled by mist, guarded by mystical creatures and enchanted by ancient spells. With each word, the air grew thick with anticipation, and the listeners were transported into a world where magic danced on the edges of reality.\n"
            + "\n" + "As Elias weaved the story, Lily's imagination took flight. She envisioned herself as a brave warrior, wielding a shimmering sword against dark forces, her heart fueled by courage and kindness.\n" + "\n"
            + "The night wore on, but the spell of the tale held everyone captive. The villagers laughed, gasped, and held their breaths, journeying alongside the characters through trials and triumphs.\n" + "\n"
            + "As the final words lingered in the air, a sense of enchantment settled upon the listeners. They thanked Elias for the gift of his storytelling, each carrying a piece of the magical kingdom within their hearts.\n" + "\n"
            + "Lily, inspired by the story, vowed to cherish the spirit of adventure and kindness in her own life. With a newfound spark in her eyes, she bid Elias goodnight, already dreaming of the countless adventures awaiting her.\n" + "\n"
            + "Under the star-studded sky, Elias remained beneath the ancient oak, his heart aglow with the joy of sharing tales that ignited imagination and inspired dreams. And as the night embraced the village, whispers of the enchanted kingdom lingered in the breeze, promising endless possibilities to those who dared to believe.";
    }
}
