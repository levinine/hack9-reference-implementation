/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.radix;

import java.util.HashMap;
import java.util.Map;

/**
 * Radix trie, allowing for partial lookup of values using string keys.
 * When a key is given, Radix Trie will descend along its characters as
 * deep in the tree, as possible. It will return the closest value found.
 * 
 * The step between tree levels is always 1 character.
 * 
 * @author n.milutinovic
 */
public class RadixTrie<T> {
	/**
	 * Radix trie node. It holds a value, which can be updated.
	 * @author n.milutinovic
	 *
	 */
	private static class Node<T> {
		final private Map<Character, Node<T>> next = new HashMap<>();
		private T value;
		
		/**
		 * Default constructor, init value to {@code null}
		 */
		Node() {
			this(null);
		}
		
		/**
		 * Construct node with given value.
		 * 
		 * @param value initial value to set
		 */
		Node(T value) {
			this.value = value;
		}
		
		/**
		 * Insert a new value at given key, recursively.
		 * 
		 * @param key key to map to.
		 * @param value value to insert.
		 */
		void insert(String key, T value) {
			if (key.isEmpty()) {
				this.value = value;
			} else {
				next.putIfAbsent(key.charAt(0), new Node<T>(null));
				next.get(key.charAt(0)).insert(key.substring(1), value);
			}
		}
		
		/**
		 * Get a value for the given key. The descent down the tree will go
		 * as deep as the tree or key allow it. It will return the last value
		 * on the chain, down the tree, that was not null.
		 * 
		 * @param key key to lookup
		 * @return value found or {@code null}.
		 */
		T get(String key) {
			if (key.isEmpty()) {
				return value;
			}
			final Character nextChar = key.charAt(0);
			final Node<T> nextHop = next.get(nextChar);
			if (nextHop == null) {
				return value;
			}
			final T deepValue = nextHop.get(key.substring(1));
			return (deepValue == null) ? value : deepValue;
		}
	}
	
	private final Node<T> root;
	
	public RadixTrie() {
		root = new Node<T>();
	}
	
	/**
	 * Put a new value for the given key. The key will be sanitized, that is
	 * empty spaces will be removed.
	 * 
	 * @param key key to put the value under.
	 * @param value value to store under the given key.
	 */
	public void put(String key, T value) {
		root.insert(sanitize(key), value);
	}
	
	/**
	 * Get closest matching value. The key will be sanitized for whitespaces.
	 * 
	 * @param key key to match.
	 * @return value or {@code null}.
	 */
	public T get(String key) {
		return root.get(sanitize(key));
	}
	
	private static String sanitize(final String key) {
		return key.replace(" ", "");
	}
}
