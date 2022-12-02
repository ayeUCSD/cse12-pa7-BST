import java.util.ArrayList;
import java.util.Currency;
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
		if (current == null) {
			return null;
		}
		if (current.getKey().equals(key)) {
			// System.out.println(current.toString());
			return current;
		}
		if (key.compareTo(current.key) < 1) {
			// System.out.println(current.getRight().toString());
			return getKeyNode(current.getLeft(), key);
		} else { // else it HAS to be greater than!
			// System.out.println(current.getRight().toString());
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
		if (keyList.contains(key)) {
			return false;
		}
		if (root == null) {
			root = new Node<>(key, value, null);
			keyList.add(key);
			return true;
		}

		Node<K, V> temp = getParent(root, key);
		// temp should be the parent of where we need to put
		// use compareTo on the key to go left or right

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
		if (!keyList.contains(key) || size() == 0) {
			return false;
		}

		keyList.remove(key); // remove key early

		Node<K, V> current = getParent(root, key);
		Node<K, V> marked = getKeyNode(root, key); // MARKED FOR DEATH

		// CHECK FOR 2 CHILDREN AKA CASE 3
		if (marked.getLeft() != null && marked.getRight() != null) {
			Node<K, V> successor = findSuccessor(marked);
			// remove successor from its original place
			remove(successor.key);

			// copy values from sucessor to marked
			marked.key = successor.key;
			marked.value = successor.value;
			return true;
		}



		// case 1, no children, you die alone
		if (marked.left == null && marked.right == null) {
			if (marked == root) { // if we have no parent, then we HAVE to be the root
				root = null;
				return true;
			}

			marked.parent = null;
			if (current.left == marked) {
				current.left = null;
			} else {
				current.right = null;
			}
			return true;
		}


		// CASE 2, this is only for 1 child
		Node<K, V> child = marked.getLeft();
		if (child == null) {// if yoru left child DOESN'T exist get the right one instead
			child = marked.getRight();
		}

		//clone values
		marked.key = child.key;
		marked.value = child.value;
		marked.left = child.left;
		marked.right = child.right;
		return true;
		

	}

	public Node<K, V> findSuccessor(Node<K, V> current) {
		current = current.getRight();
		while (current.getLeft() != null) {
			current = current.getLeft();
		}
		return current;
	}

	public String nodeToString(Node<K, V> node) {
		return node.toString();
	}

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
		} else if (keyList.isEmpty()) {
			return null;
		}

		Node<K, V> temp = getKeyNode(root, key);
		if (temp == null || !temp.key.equals(key)) {
			return null;
		}
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
		List<K> list = keyRecursive(new ArrayList<K>(), root);
		return list;
	}
	public List<K> keyRecursive(List<K> list, Node<K,V> node){
		if(node == null){
			return list;
		}
		list.add(node.getKey());
		keyRecursive(list, node.getLeft());
		keyRecursive(list, node.getRight());
		return list;
	}

	public String toString(Node<K, V> node) {
		if (node == null) {
			return "";
		}
		String output = "";
		output = node.toString() + toString(node.getLeft()) + toString(node.getRight());
		return output;
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

		public String toString() {
			return "[" + key.toString() + ", " + value.toString() + "]";
		}

	}

}