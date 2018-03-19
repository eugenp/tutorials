package com.baeldung.tagging;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;

/**
 * Test for {@link TagRepository}.
 * 
 * @author Donato Rimenti
 *
 */
public class TaggingIntegrationTest {

	/**
	 * Object to test.
	 */
	private TagRepository repository;

	/**
	 * Sets the test up by instantiating the object to test.
	 */
	@Before
	public void setup() {
		repository = new TagRepository();
	}

	/**
	 * Tests {@link TagRepository#postsWithAtLeastOneTag(String...)} with 1 tag.
	 */
	@Test
	public void givenTagRepository_whenPostsWithAtLeastOneTagMongoDB_then3Results() {
		List<Post> results = repository.postsWithAtLeastOneTag("MongoDB");
		results.forEach(System.out::println);

		Assert.assertEquals(3, results.size());
		results.forEach(post -> {
			Assert.assertTrue(post.getTags().contains("MongoDB"));
		});
		
	}

	/**
	 * Tests {@link TagRepository#postsWithAtLeastOneTag(String...)} with 2
	 * tags.
	 */
	@Test
	public void givenTagRepository_whenPostsWithAtLeastOneTagMongoDBJava8_then4Results() {
		List<Post> results = repository.postsWithAtLeastOneTag("MongoDB", "Java 8");
		results.forEach(System.out::println);

		Assert.assertEquals(4, results.size());
		results.forEach(post -> {
			Assert.assertTrue(post.getTags().contains("MongoDB") || post.getTags().contains("Java 8"));
		});
	}

	/**
	 * Tests {@link TagRepository#postsWithAllTags(String...)} with 1 tag.
	 */
	@Test
	public void givenTagRepository_whenPostsWithAllTagsMongoDB_then3Results() {
		List<Post> results = repository.postsWithAllTags("MongoDB");
		results.forEach(System.out::println);

		Assert.assertEquals(3, results.size());
		results.forEach(post -> {
			Assert.assertTrue(post.getTags().contains("MongoDB"));
		});
	}

	/**
	 * Tests {@link TagRepository#postsWithAllTags(String...)} with 2 tags.
	 */
	@Test
	public void givenTagRepository_whenPostsWithAllTagsMongoDBJava8_then2Results() {
		List<Post> results = repository.postsWithAllTags("MongoDB", "Java 8");
		results.forEach(System.out::println);

		Assert.assertEquals(2, results.size());
		results.forEach(post -> {
			Assert.assertTrue(post.getTags().contains("MongoDB"));
			Assert.assertTrue(post.getTags().contains("Java 8"));
		});
	}

	/**
	 * Tests {@link TagRepository#postsWithoutTags(String...)} with 1 tag.
	 */
	@Test
	public void givenTagRepository_whenPostsWithoutTagsMongoDB_then1Result() {
		List<Post> results = repository.postsWithoutTags("MongoDB");
		results.forEach(System.out::println);

		Assert.assertEquals(1, results.size());
		results.forEach(post -> {
			Assert.assertFalse(post.getTags().contains("MongoDB"));
		});
	}

	/**
	 * Tests {@link TagRepository#postsWithoutTags(String...)} with 2 tags.
	 */
	@Test
	public void givenTagRepository_whenPostsWithoutTagsMongoDBJava8_then0Results() {
		List<Post> results = repository.postsWithoutTags("MongoDB", "Java 8");
		results.forEach(System.out::println);
		
		Assert.assertEquals(0, results.size());
		results.forEach(post -> {
			Assert.assertFalse(post.getTags().contains("MongoDB"));
			Assert.assertFalse(post.getTags().contains("Java 8"));
		});
	}

	/**
	 * Cleans up the test by deallocating memory.
	 * 
	 * @throws IOException
	 */
	@After
	public void teardown() throws IOException {
		repository.close();
	}

}
