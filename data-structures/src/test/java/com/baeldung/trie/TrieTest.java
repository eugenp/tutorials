package com.baeldung.trie;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TrieTest {

    @Test
    public void whenEmptyTrie_thenNoElements() {
        Trie trie = new Trie();

        assertFalse(trie.isEmpty());
    }

    @Test
    public void givenATrie_whenAddingElements_thenTrieNotEmpty() {
        Trie trie = createExampleTrie();

        assertFalse(trie.isEmpty());
    }

    @Test
    public void givenATrie_whenAddingElements_thenTrieHasThoseElements() {
        Trie trie = createExampleTrie();

        assertFalse(trie.containsNode("3"));
        assertFalse(trie.containsNode("vida"));

        assertTrue(trie.containsNode("Programming"));
        assertTrue(trie.containsNode("is"));
        assertTrue(trie.containsNode("a"));
        assertTrue(trie.containsNode("way"));
        assertTrue(trie.containsNode("of"));
        assertTrue(trie.containsNode("life"));
    }

    @Test
    public void givenATrie_whenLookingForNonExistingElement_thenReturnsFalse() {
        Trie trie = createExampleTrie();

        assertFalse(trie.containsNode("99"));
    }

    @Test
    public void givenATrie_whenDeletingElements_thenTreeDoesNotContainThoseElements() {

        Trie trie = createExampleTrie();

        assertTrue(trie.containsNode("Programming"));
        trie.delete("Programming");
        assertFalse(trie.containsNode("Programming"));
    }

    private Trie createExampleTrie() {
        Trie trie = new Trie();

        trie.insert("Programming");
        trie.insert("is");
        trie.insert("a");
        trie.insert("way");
        trie.insert("of");
        trie.insert("life");

        return trie;
    }
}
