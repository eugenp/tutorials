package com.baeldung.commons.collections4;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.CollectionBag;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bag.TreeBag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BagTests {

	Bag<String> baseBag;
	TreeBag<String> treeBag;


	@Before
	public void before() {
		baseBag = new HashBag<String>();
		treeBag = new TreeBag<String>();
	}

	@Test
	public void whenAdd_thenRemoveFromBaseBag_thenContainsCorrect() {
		baseBag.add("apple", 2);
		baseBag.add("lemon", 6);
		baseBag.add("lime");

		baseBag.remove("lemon");
		Assert.assertEquals(3, baseBag.size());
		Assert.assertFalse(baseBag.contains("lemon"));

		Assert.assertTrue(baseBag.uniqueSet().contains("apple"));
		Assert.assertFalse(baseBag.uniqueSet().contains("lemon"));
		Assert.assertTrue(baseBag.uniqueSet().contains("lime"));

		List<String> containList = new ArrayList<String>();
		containList.add("apple");
		containList.add("lemon");
		containList.add("lime");
		Assert.assertFalse(baseBag.containsAll(containList));
	}

	@Test
	public void whenAdd_thenRemoveFromBaseCollectionBag_thenContainsCorrect() {
		baseBag.add("apple", 2);
		baseBag.add("lemon", 6);
		baseBag.add("lime");

		CollectionBag<String> baseCollectionBag = new CollectionBag<String>(
				baseBag);

		baseCollectionBag.remove("lemon");
		Assert.assertEquals(8, baseCollectionBag.size());
		Assert.assertTrue(baseCollectionBag.contains("lemon"));

		Assert.assertTrue(baseBag.uniqueSet().contains("apple"));
		Assert.assertTrue(baseBag.uniqueSet().contains("lemon"));
		Assert.assertTrue(baseBag.uniqueSet().contains("lime"));

		List<String> containList = new ArrayList<String>();
		containList.add("apple");
		containList.add("lemon");
		containList.add("lime");
		Assert.assertTrue(baseBag.containsAll(containList));
	}
	
	@Test
	public void whenAddtoTreeBag_thenRemove_thenContainsCorrect() {
		treeBag.add("banana", 8);
		treeBag.add("apple", 2);
		treeBag.add("lime");
		
		Assert.assertEquals(11, treeBag.size());
		Assert.assertEquals("apple", treeBag.first());
		Assert.assertEquals("lime", treeBag.last());
		
		treeBag.remove("apple");
		Assert.assertEquals(9, treeBag.size());
		Assert.assertEquals("banana", treeBag.first());
	}
}
