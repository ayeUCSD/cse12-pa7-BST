import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.*;

public class BSTTest {
	final String testKey = "testKey";
	final Integer testVal = 1;

	@Test
	public void makeBST() {
		BST<Integer, Integer> bst = new BST<>();
		assertTrue(bst.put(1, testVal));
		assertTrue(bst.put(2, testVal + 1));
		assertTrue(bst.put(3, testVal + 2));
	}

	@Test
	public void sanityTest(){
		BST<Integer, Integer> bst = makeBalancedTree();
		//System.out.println(bst.toString(bst.root));
		assertTrue(bst.remove(2));
		assertTrue(bst.remove(4));
		assertTrue(bst.remove(6));
		assertTrue(bst.remove(8));
		//System.out.println(bst.toString(bst.root));
		
		bst = new BST<>();
		bst.put(1,1);
		bst.remove(1);
	}
	
	@Test
	public void keysTests() {
		// testing keys_balancedTree
		BST<Integer, Integer> bst = makeBalancedTree();
		System.out.println(bst.keys());
	}
	
	@Test
	public void testRemoveLeaf() {
		BST<Integer, Integer> bst = makeTestTree();
		System.out.println(bst.toString(bst.root));
		assertTrue(bst.remove(1));
		assertTrue(bst.remove(9));
		System.out.println(bst.toString(bst.root));
		assertTrue(bst.remove(5));
		System.out.println(bst.toString(bst.root));
		}


	@Test
	public void testRemove() {
		// System.out.println("STARTING REMOVE TESTS");
		// BST<Integer, Integer> bst = makeTestTree();
		// //test KeyNotFound
		// assertFalse(bst.remove(99999));
		
		// //testRemove_nullKey
		// //SEE BELOW
		
		// //test remove_nullValue
		// System.out.println("REMOVE NULL");
		// bst.put(666,null);
		// //System.out.println(bst.toString(bst.root));
		// assertTrue(bst.remove(666));
		// //System.out.println(bst.toString(bst.root));

		// //test onlyRoot
		// System.out.println("TEST ONLY ROOT");
		// bst = new BST<>();
		// bst.put(1,1);
		// //System.out.println(bst.toString(bst.root));
		// assertTrue(bst.remove(1));
		// //System.out.println("empty: "+bst.toString(bst.root));

		// //test subrootOnlyLeft
		// System.out.println("TEST SUBROOT ONLY LEFT");
		// bst.put(5,5);
		// bst.put(4,4);
		// bst.put(3,3);
		// System.out.println(bst.toString(bst.root));
		// assertTrue(bst.remove(4));
		// //ok theres a loop here causing a stack overflow
		// System.out.println(bst.toString(bst.root));
		// //assertTrue(bst.remove(3));
		// //assertTrue(bst.remove(5));
		// //assertTrue(bst.root == bst.getKeyNode(bst.root, 6));



		// bst = new BST<>();
		// bst.put(1,1);
		// bst.remove(1);
		// assertNull(bst.root);

		// bst.put(1,null);
		// assertTrue(bst.remove(1));
	}

	@Test(expected = IllegalArgumentException.class)
    public void testRemoveNull() {
		BST<Integer, Integer> bst = new BST<>();
		bst.remove(null);
    }

	@Test
	public void testGet() {
		BST<Integer, Integer> bst = makeTestTree();
		assertTrue(5 == bst.get(5));
		assertTrue(1 == bst.get(1));
		assertTrue(12 == bst.get(12));
		assertTrue(7 == bst.get(7));
		assertNull(bst.get(444444));
	}


	@Test
	public void testKeyList() {
		BST<Integer, Integer> bst = makeTestTree();
		List<Integer> keyList = bst.keys();
		for(int i = 1; i <= 12; i++){
			assertEquals(true, keyList.contains(i));
			assertTrue(i == bst.get(i));
		}

	}


	public BST<Integer, Integer> makeTestTree() {
		BST<Integer, Integer> bst = new BST<>();
		bst.put(5, testVal+4);
		bst.put(10, testVal+9);
		bst.put(7, testVal+6);
		bst.put(6, testVal+5);
		bst.put(11, testVal+10);
		bst.put(12, testVal+11);
		bst.put(3, testVal+2);
		bst.put(4, testVal+3);
		bst.put(2, testVal+1);
		bst.put(1, testVal);
		bst.put(8, testVal+7);
		bst.put(9, testVal+8);
		return bst;
	}


	public BST<Integer, Integer> makeBalancedTree() {
		//leaves: 2,4 , 6,8
		BST<Integer, Integer> bst = new BST<>();
		bst.put(5, 5);
		bst.put(7, 7);
		bst.put(8, 8);
		bst.put(6, 6);
		bst.put(3, 3);
		bst.put(2, 2);
		bst.put(4, 4);
		return bst;
	}
}
