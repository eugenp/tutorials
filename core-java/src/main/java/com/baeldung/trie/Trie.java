package com.baeldung.trie;

import java.util.Map;
import java.util.Set;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public boolean insert(String word) {
        TrieNode trie = root;
        if (trie == null || word == null)
            return false;
        char[] chars = word.toCharArray();
        int counter = 0;
        while (counter < chars.length) {
            Set<Character> children = trie.getChildren()
                .keySet();
            if (!children.contains(chars[counter])) {
                insertChar(trie, chars[counter]);
                if (counter == chars.length - 1) {
                    getChild(trie, chars[counter]).setIsWord(true);
                    return true;
                }
            }
            trie = getChild(trie, chars[counter]);
            if (trie.getContent()
                .equals(word) && !trie.isWord()) {
                trie.setIsWord(true);
                return true;
            }
            counter++;
        }
        return false;
    }

    public boolean find(String string) {
        Map<Character, TrieNode> children = root.getChildren();
        TrieNode t = null;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.getChildren();
            } else
                return false;
        }
        return true;
    }

    public boolean containsNode(String string) {
        return find(string);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean delete(String str) {
        return findNode(root, str);
    }

    private TrieNode getChild(TrieNode trie, Character c) {
        return trie.getChildren()
            .get(c);
    }

    private TrieNode insertChar(TrieNode trie, Character c) {
        if (trie.getChildren()
            .containsKey(c)) {
            return null;
        }
        TrieNode next = new TrieNode(trie.getContent() + c.toString());
        trie.getChildren()
            .put(c, next);
        return next;
    }

    private boolean findNode(TrieNode trie, String string) {
        Map<Character, TrieNode> children = root.getChildren();
        TrieNode parent = null;
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if (children.containsKey(character)) {
                parent = trie;
                trie = children.get(character);
                children = trie.getChildren();
                if (trie.getContent()
                    .equals(string)) {

                    parent.getChildren()
                        .remove(character);
                    trie = null;
                    return true;
                }
            }
        }
        return false;
    }
}
