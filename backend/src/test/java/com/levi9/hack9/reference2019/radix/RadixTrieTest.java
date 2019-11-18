/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.radix;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Radix Trie implementation.
 * 
 * @author n.milutinovic
 */
class RadixTrieTest {
	private static final Predicate<Void> TRUE = x -> true;
	private RadixTrie<Integer, Void> trie;
	
	/**
	 * Initialize the radix trie of integers and a couple of prefixes.
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		trie = new RadixTrie<>(TRUE);
		trie.put("+381", 10);
		trie.put("+38121", 20);
		trie.put("+38164", 30);
		trie.put("+38164123456", 25);
	}

	/**
	 * Test initial state of the tree, as set up by the setUp() method.
	 */
	@Test
	void test_initial() {
		assertEquals(Integer.valueOf(10), trie.get("+381", null), "Serbia");
		assertEquals(Integer.valueOf(20), trie.get("+38121", null), "Novi Sad");
		assertEquals(Integer.valueOf(30), trie.get("+38164", null), "MT:S");
		assertEquals(Integer.valueOf(25), trie.get("+38164123456", null), "Talker");
	}

	/**
	 * Test if white spaces are removed.
	 */
	@Test
	void test_separator_removal() {
		assertEquals(Integer.valueOf(20), trie.get("+381 21", null), "Novi Sad");
		assertEquals(Integer.valueOf(30), trie.get("+381 64", null), "MT:S");
		assertEquals(Integer.valueOf(25), trie.get("+381 64 123456", null), "Talker");
	}

	/**
	 * Test partial lookup where the key exceeds the tree.
	 */
	@Test
	void test_partial_lookup() {
		assertEquals(Integer.valueOf(10), trie.get("+38111", null), "Belgrade");
		assertEquals(Integer.valueOf(20), trie.get("+38121400500", null), "Novi Sad 400-500");
		assertEquals(Integer.valueOf(30), trie.get("+381646543210", null), "MT:S");
	}
	
	/**
	 * Here we test for keys that will "end" in between real value nodes.
	 */
	@Test
	void test_partial_middle_lookup() {
		assertNull(trie.get("+38", null), "Wrong country code");
		assertEquals(Integer.valueOf(10), trie.get("+3812", null), "Vojvodina");
		assertEquals(Integer.valueOf(30), trie.get("+3816412", null), "MT:S partial");
	}
}
