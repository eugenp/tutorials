package com.baeldung.trie;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TrieTest {
    @Test
    public void givenATrie_WhenAddingElements_ThenTrieNotEmpty() {
        Trie trie = createTrie();

        assertFalse(trie.isEmpty());
    }

    @Test
    public void givenATrie_WhenAddingElements_ThenTrieContainsThoseElements() {
        Trie trie = createTrie();

        assertFalse(trie.containsNode("3"));
        assertFalse(trie.containsNode("vida"));

        assertTrue(trie.containsNode("life"));
    }

    @Test
    public void givenABinaryTree_WhenLookingForNonExistingElement_ThenReturnsFalse() {
        Trie trie = createTrie();

        assertFalse(trie.containsNode("99"));
    }

    @Test
    public void givenABinaryTree_WhenDeletingElements_ThenTreeDoesNotContainThoseElements() {

        Trie trie = createTrie();

        assertTrue(trie.containsNode("Programming"));
        trie.delete("Programming");
        assertFalse(trie.containsNode("Programming"));
    }

    @Test
    public void testDelete() {
        Trie trie = createTrie();

        trie.delete("Programming");

        assertFalse(trie.find("Programming"));

    }

    private Trie createTrie() {
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
