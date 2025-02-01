package com.baeldung.filterbyanyfield;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FilterListByAnyMatchingFieldUnitTest {

    // @formatter:off
    private static final Book JAVA = new Book(
        "The Art of Java Programming",
        List.of("Tech", "Java"),
        "Java is a powerful programming language.",
        400);
    private static final Book KOTLIN = new Book(
        "Let's Dive Into Kotlin Codes",
        List.of("Tech", "Java", "Kotlin"),
        "It is big fun learning how to write Kotlin codes.",
        300);
    private static final Book PYTHON = new Book(
        "Python Tricks You Should Know",
        List.of("Tech", "Python"),
        "The path of being a Python expert.",
        200);
    private static final Book GUITAR = new Book(
        "How to Play a Guitar",
        List.of("Art", "Music"),
        "Let's learn how to play a guitar.",
        100);

    // @formatter:on
    private static final List<Book> BOOKS = List.of(JAVA, KOTLIN, PYTHON, GUITAR);

    List<Book> fullTextSearchByLogicalOr(List<Book> books, String keyword) {
        return books.stream()
            .filter(book -> book.getTitle()
                .contains(keyword) || book.getIntro()
                .contains(keyword) || book.getTags()
                .stream()
                .anyMatch(tag -> tag.contains(keyword)))
            .toList();
    }

    @Test
    void whenFilteringInAllFields_thenCorrect() {
        List<Book> byJava = fullTextSearchByLogicalOr(BOOKS, "Java");
        assertThat(byJava).containsExactlyInAnyOrder(JAVA, KOTLIN);

        List<Book> byArt = fullTextSearchByLogicalOr(BOOKS, "Art");
        assertThat(byArt).containsExactlyInAnyOrder(JAVA, GUITAR);

        List<Book> byLets = fullTextSearchByLogicalOr(BOOKS, "Let's");
        assertThat(byLets).containsExactlyInAnyOrder(KOTLIN, GUITAR);
    }

    @Test
    void whenCallingBookStrForFiltering_thenCorrect() {
        String expected = """
            Let's Dive Into Kotlin Codes
            It is big fun learning how to write Kotlin codes.
            Tech
            Java
            Kotlin""";
        assertThat(KOTLIN.strForFiltering()).isEqualTo(expected);
    }

    List<Book> fullTextSearchByStrForFiltering(List<Book> books, String keyword) {
        return books.stream()
            .filter(book -> book.strForFiltering()
                .contains(keyword))
            .toList();
    }

    @Test
    void whenFilteringInAllFieldsUsingStrForFiltering_thenCorrect() {
        List<Book> byJava = fullTextSearchByStrForFiltering(BOOKS, "Java");
        assertThat(byJava).containsExactlyInAnyOrder(JAVA, KOTLIN);

        List<Book> byArt = fullTextSearchByStrForFiltering(BOOKS, "Art");
        assertThat(byArt).containsExactlyInAnyOrder(JAVA, GUITAR);

        List<Book> byLets = fullTextSearchByStrForFiltering(BOOKS, "Let's");
        assertThat(byLets).containsExactlyInAnyOrder(KOTLIN, GUITAR);
    }

    boolean fullTextSearchOnObject(Object obj, String keyword, String... excludedFields) {
        Field[] fields = obj.getClass()
            .getDeclaredFields();
        for (Field field : fields) {
            if (Arrays.stream(excludedFields)
                .noneMatch(exceptName -> exceptName.equals(field.getName()))) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value != null) {
                        if (value.toString()
                            .contains(keyword)) {
                            return true;
                        }
                        if (!field.getType()
                            .isPrimitive() && !(value instanceof String) && fullTextSearchOnObject(value, keyword, excludedFields)) {
                            return true;
                        }
                    }
                } catch (InaccessibleObjectException | IllegalAccessException ignored) {
                    //ignore
                }
            }
        }
        return false;
    }

    List<Book> fullTextSearchByReflection(List<Book> books, String keyword, String... excludeFields) {
        return books.stream()
            .filter(book -> fullTextSearchOnObject(book, keyword, excludeFields))
            .toList();
    }

    @Test
    void whenFilteringInAllFieldsUsingReflection_thenCorrect() {
        List<Book> byJava = fullTextSearchByReflection(BOOKS, "Java", "pages");
        assertThat(byJava).containsExactlyInAnyOrder(JAVA, KOTLIN);

        List<Book> byArt = fullTextSearchByReflection(BOOKS, "Art", "pages");
        assertThat(byArt).containsExactlyInAnyOrder(JAVA, GUITAR);

        List<Book> byLets = fullTextSearchByReflection(BOOKS, "Let's", "pages");
        assertThat(byLets).containsExactlyInAnyOrder(KOTLIN, GUITAR);

        List<Book> byArtExcludeTag = fullTextSearchByReflection(BOOKS, "Art", "tags", "pages");
        assertThat(byArtExcludeTag).containsExactlyInAnyOrder(JAVA);
    }
}

class Book {

    private String title;
    private List<String> tags;
    private String intro;
    private int pages;

    public Book(String title, List<String> tags, String intro, int pages) {
        this.title = title;
        this.tags = tags;
        this.intro = intro;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String strForFiltering() {
        String tagsStr = String.join("\n", tags);
        return String.join("\n", title, intro, tagsStr);
    }
}
