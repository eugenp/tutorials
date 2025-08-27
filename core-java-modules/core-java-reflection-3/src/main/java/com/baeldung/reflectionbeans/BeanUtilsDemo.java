package com.baeldung.reflectionbeans;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class BeanUtilsDemo {

    public static void main(String[] args) throws Exception {
        Post post = new Post();
        BeanUtils.setProperty(post, "title", "Commons BeanUtils Rocks");

        Map<String, Object> data = Map.of("title", "Map → Bean", "author", "Baeldung Team");
        BeanUtils.populate(post, data);
        Post source = new Post();
        source.setTitle("Source");
        source.setAuthor("Alice");

        Post target = new Post();
        BeanUtils.copyProperties(target, source);
        if (target.getMetadata() == null) {
            target.setMetadata(new Post.Metadata());
        }
        BeanUtils.setProperty(target, "metadata.wordCount", 850);
        System.out.println(target.getTitle());
        System.out.println(target.getMetadata()
            .getWordCount());
    }

    public static void safeCopy(Object target, Object source) {
        try {
            BeanUtils.copyProperties(target, source);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
