package com.baeldung.trie;

import java.util.HashMap;

public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private String content;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<Character, TrieNode>();
        content = "";
        isWord = false;
    }

    public TrieNode(String content) {
        this();
        this.content = content;
    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setIsWord(boolean isWord) {
        this.isWord = isWord;
    }

    public void setChildren(HashMap<Character, TrieNode> children) {
        this.children = children;
    }
}
