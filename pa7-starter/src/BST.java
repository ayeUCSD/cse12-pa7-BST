import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @param <K> The type of the keys of this BST. They need to be comparable by
 *            nature of the BST
 *            "K extends Comparable" means that BST will only compile with
 *            classes that implement Comparable
 *            interface. This is because our BST sorts entries by key. Therefore
 *            keys must be comparable.
 * @param <V> The type of the values of this BST.
 */
public class BST<K extends Comparable<? super K>, V> implements DefaultMap<K, V> {
	/*
	 * TODO: Add instance variables
	 * You may add any instance variables you need, but
	 * you may NOT use any class that implements java.util.SortedMap
	 * or any other implementation of a binary search tree
	 */

	public List<K> keyList = new ArrayList<>();
	public Node<K, V> root;

	/**
	 * create a BST with root == node with k,v
	 */
	public BST(K key, V value) {
		root = new Node<>(key, value, null);
	}

	/**
	 * Recursive method that should get the parent of the node with the given key
	 */

	public Node<K, V> getParent(Node<K, V> current, K key) {

		if (key.compareTo(current.key) < 1) {
			if (current.getLeft() == null) {
				return current;
			}
			return getParent(current.left, key);
		} else { // else it HAS to be greater than!
			if (current.getRight() == null) {
				return current;
			}
			return getParent(current.right, key);
		}
	}

	/**
	 * recursive method that should return the node with the given key
	 */
	public Node<K, V> getKeyNode(Node<K, V> current, K key) {
		if (current.getKey().equals(key)) {
			return current;
		}
		if (key.compareTo(current.key) < 1) {
			return getKeyNode(current.getLeft(), key);
		} else { // else it HAS to be greater than!
			return getKeyNode(current.getRight(), key);
		}
	}

	/**
	 * SO BASICALLY, LEFT = SMALLER KEY VALUE
	 * RIGHT = BIGGER KEY VALUE
	 * 
	 * Adds the specified key, value pair to this DefaultMap
	 * Note: duplicate keys are not allowed
	 * 
	 * @return true if the key value pair was added to this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		// Navigate to position
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		Node<K, V> temp = getParent(root, key);
		// temp should be the parent of where we need to put
		// use compareTo on the key to go left or right
		if (keyList.contains(key)) {
			return false;
		}

		if (key.compareTo(temp.key) < 1) {
			temp.left = new Node<>(key, value, temp);
			keyList.add(key);
			return true;
		} else { // else it HAS to be greater than!
			temp.right = new Node<>(key, value, temp);
			keyList.add(key);
			return true;
		}
	}

	/**
	 * Replaces the value that maps to the key if it is present
	 * 
	 * @param key      The key whose mapped value is being replaced
	 * @param newValue The value to replace the existing value with
	 * @return true if the key was in this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}

		if (!keyList.contains(key)) {
			return false;
		}

		Node<K, V> temp = getKeyNode(root, key);
		temp.setValue(newValue);
		return true;

	}

	// TODO:
	/**
	 * IDEA: 3 cases
	 * 1. no children, just remove it
	 * 2. one child, replace self with child
	 * 3. two children, replace self with sucessor (next item in ordering)
	 */
	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}

		if (!keyList.contains(key)) {
			return false;
		}

		Node<K, V> current = getKeyNode(root, key);
		Node<K, V> temp = current;

		// 3.) two children
		// go right
		if (temp.getRight() != null) { // if right child is not missing, we go
			temp = temp.getRight();
			// go all the way left
			while (temp.getLeft() != null) {
				temp = temp.getLeft();
			}
			// remove temp leaf
			temp.getParent().left = null;
			// nepotism
			temp.parent = current.parent;
			temp.left = current.left;
			temp.right = current.right;
			keyList.remove(key);
			return true;
			// ig java garbage collection picks up current??? since nothing is pointing to
			// it afterwards
		}

		// 2.) one child, replace self
		if (current.getLeft() != null) {
			temp = current.left;

			temp.parent = current.parent;
			temp.left = current.left;
			temp.right = current.right;
			keyList.remove(key);
			return true;
		}
		// case 1 no children
		// become your parent
		current = current.getParent();
		// kill your child
		if (current.getLeft().getKey().equals(key)) {
			current.left = null;
		} else {
			current.right = null;
		}
		keyList.remove(key);
		return true;
		
	}

	public Node<K, V> findSuccessor(Node<K, V> current) {
		current = current.getRight();
		while (current.getLeft() != null) {
			current = current.getLeft();
		}
		return current;
	}

	/**
	 * If n has a right child //Case 1
	 * Traverse to the right child and then attempt to traverse left as many times
	 * as possible
	 * Return current node
	 * Else //Case 2
	 * Traverse upwards until the current node is its parent's left child //Careful
	 * for null!
	 * Return current node's parent
	 * 
	 */

	/**
	 * Adds the key, value pair to this DefaultMap if it is not present,
	 * otherwise, replaces the value with the given value
	 * 
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		if (keyList.contains(key)) {
			replace(key, value);
		} else {
			put(key, value);
		}

	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		Node<K, V> temp = getKeyNode(root, key);
		return temp.value;
	}

	@Override
	public int size() {
		return keyList.size();
	}

	@Override
	public boolean isEmpty() {
		return keyList.isEmpty();
	}

	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		return keyList.contains(key);
	}

	// Keys must be in ascending sorted order
	// You CANNOT use Collections.sort() or any other sorting implementations
	// You must do inorder traversal of the tree
	@Override
	public List<K> keys() {
		return keyList;
	}

	private static class Node<K extends Comparable<? super K>, V>
			implements DefaultMap.Entry<K, V> {
		private K key;
		private V value;

		public Node<K, V> parent = null;
		public Node<K, V> left = null;
		public Node<K, V> right = null;

		public Node(K k, V v, Node<K, V> parent) {
			this.key = k;
			this.value = v;
			this.parent = parent;
		}

		public Node<K, V> getParent() {
			return parent;
		}

		public Node<K, V> getLeft() {
			return left;
		}

		public Node<K, V> getRight() {
			return right;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public void setValue(V value) {
			this.value = value;
		}

	}

}