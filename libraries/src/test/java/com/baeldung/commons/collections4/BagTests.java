package com.baeldung.commons.collections4;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.Bag;
<<<<<<< HEAD
import org.apache.commons.collections4.bag.CollectionBag;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bag.TreeBag;
=======
<<<<<<< HEAD
import org.apache.commons.collections4.TransformerUtils;
import org.apache.commons.collections4.bag.CollectionBag;
import org.apache.commons.collections4.bag.CollectionSortedBag;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bag.TreeBag;
=======
import org.apache.commons.collections4.bag.CollectionBag;
import org.apache.commons.collections4.bag.HashBag;
>>>>>>> 4660dd6a3e8c648adc0ff67abdf1a69ff948f340
>>>>>>> 58e6a39983991aa70448432ddcacadf3ea27c0df
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BagTests {

	Bag<String> baseBag;
<<<<<<< HEAD
	TreeBag<String> treeBag;

=======
<<<<<<< HEAD
	TreeBag<String> treeBag;
	
=======
>>>>>>> 4660dd6a3e8c648adc0ff67abdf1a69ff948f340
>>>>>>> 58e6a39983991aa70448432ddcacadf3ea27c0df

	@Before
	public void before() {
		baseBag = new HashBag<String>();
<<<<<<< HEAD
		treeBag = new TreeBag<String>();
=======
<<<<<<< HEAD
		treeBag = new TreeBag<String>();
=======
>>>>>>> 4660dd6a3e8c648adc0ff67abdf1a69ff948f340
>>>>>>> 58e6a39983991aa70448432ddcacadf3ea27c0df
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
<<<<<<< HEAD
=======

<<<<<<< HEAD
>>>>>>> 58e6a39983991aa70448432ddcacadf3ea27c0df
	
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
<<<<<<< HEAD
=======
	
=======
>>>>>>> 4660dd6a3e8c648adc0ff67abdf1a69ff948f340
>>>>>>> 58e6a39983991aa70448432ddcacadf3ea27c0df
}
