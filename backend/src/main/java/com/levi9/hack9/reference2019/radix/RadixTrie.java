/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.radix;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Radix trie, allowing for partial lookup of values using string keys.
 * When a key is given, Radix Trie will descend along its characters as
 * deep in the tree, as possible. It will return the closest value found.
 * 
 * The step between tree levels is always 1 character.
 * 
 * @param <Entity> type of entity held by the tree.
 * @param <Criterion> criterion to be used for value selection
 * @author n.milutinovic
 */
public class RadixTrie<Entity> {
	/**
	 * Radix trie node. It holds a value, which can be updated.
	 * 
	 * @param <Entity> type of entity held by the node.
	 * @param <Criterion> type of criterion used to select the value
	 * @author n.milutinovic
	 *
	 */
	private static class Node<Entity> {
		final private Map<Character, Node<Entity>> next = new HashMap<>();
		private Entity value;
		
		/**
		 * Minimal constructor, initialize value to {@code null}.
		 * 
		 * @param matcher predicate to match the value as candidate to yield.
		 */
		Node() {
			this(null);
		}
		
		/**
		 * Construct node with given value.
		 * 
		 * @param value initial value to set
		 * @param matcher predicate to match the value as candidate to yield.
		 */
		Node(Entity value) {
			this.value = value;
		}
		
		/**
		 * Insert a new value at given key, recursively.
		 * 
		 * @param key key to map to.
		 * @param value value to insert.
		 */
		void insert(String key, Entity value) {
			if (key.isEmpty()) {
				this.value = value;
			} else {
				next.putIfAbsent(key.charAt(0), new Node<Entity>());
				next.get(key.charAt(0)).insert(key.substring(1), value);
			}
		}
		
		/**
		 * Get a value for the given key. The descent down the tree will go
		 * as deep as the tree or key allow it. It will return the last value
		 * on the chain, down the tree, that was not null.
		 * 
		 * @param key key to lookup
		 * @param c criterion used to select the candidate value.
		 * @return value found or {@code null}.
		 */
		Entity get(String key, Predicate<Entity> test) {
			if (key.isEmpty()) {
				return getValueIf(test);
			}
			final Character nextChar = key.charAt(0);
			final Node<Entity> nextHop = next.get(nextChar);
			if (nextHop == null) {
				return getValueIf(test);
			}
			final Entity nextValue = nextHop.get(key.substring(1), test);
			return (nextValue == null) ? getValueIf(test) : nextValue;
		}
		
		private Entity getValueIf(Predicate<Entity> test) {
			return (value != null && test.test(value)) ? value : null;
		}
	}
	
	private final Node<Entity> root;
	
	/**
	 * Construct initial Radix Trie. Set the matcher and initialize {@code root} node.
	 * 
	 * @param matcher predicate used to match candidate value.
	 */
	public RadixTrie() {
		root = new Node<Entity>();
	}
	
	/**
	 * Put a new value for the given key. The key will be sanitized, that is
	 * empty spaces will be removed.
	 * 
	 * @param key key to put the value under.
	 * @param value value to store under the given key.
	 */
	public void put(String key, Entity value) {
		root.insert(sanitize(key), value);
	}
	
	/**
	 * Get closest matching value. The key will be sanitized for whitespaces.
	 * 
	 * @param key key to match.
	 * @return value or {@code null}.
	 */
	public Entity get(String key, Predicate<Entity> test) {
		return root.get(sanitize(key), test);
	}
	
	private static String sanitize(final String key) {
		return key.replace(" ", "");
	}
}
