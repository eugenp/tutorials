package com.baeldung.trie;

public class Trie {
    private TrieNode root;

    Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChildren()
              .get(ch);
            if (node == null) {
                node = new TrieNode();
                current.getChildren()
                  .put(ch, node);
            }
            current = node;
        }
        current.setEndOfWord(true);
    }

    public boolean find(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChildren()
              .get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.isEndOfWord();
    }

    public void delete(String word) {
        delete(root, word, 0);
    }

    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfWord()) {
                return false;
            }
            current.setEndOfWord(false);
            return current.getChildren()
              .size() == 0;
        }
        char ch = word.charAt(index);
        TrieNode node = current.getChildren()
          .get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(node, word, index + 1);

        if (shouldDeleteCurrentNode) {
            current.getChildren()
              .remove(ch);
            return current.getChildren().isEmpty();
        }
        return false;
    }

    public boolean containsNode(String word) {
        return find(word);
    }

    public boolean isEmpty() {
        return root == null;
    }
}