package accidentpack;

import java.time.LocalDateTime;
/**
 * MyBST (My Binary Search Tree) class is a binary search tree data structure
 * designed to store instances of the Report class.
 */
public class MyBST {
	private long size = 0;
	/**
	 * A class to declare each node in binary search tree
	 */
	static class Node {
		Report data;
		Node left, right;
		public int leftChildren = 0;
		public int rightChildren = 0;
/*
 * A new node with the given Report data
 */
		public Node(Report data2) {
			data = data2;
			left = right = null;

		}
	}

	Node root;
/**
 * Empty binary search tree
 */
	public MyBST() {
		root = null;
	}
/**
 * Adds a new report instance into the binary search tree
 * 
 * @param data, the data being inserted to the BST
 */
	public void insert(Report data) {
		root = insertRecursive(root, data);
	}
	/*
	 * Adds a new report instance recursively into the BTS
	 * @param root, node of subtree
	 * @data data, the data from CSV file to be inserted
	 * @return the root
	 */
	private Node insertRecursive(Node root, Report data) {
		if (root == null) {
			return new Node(data);
		}

		if (data.getStartTime().compareTo(root.data.getStartTime()) < 0) {
			root.left = insertRecursive(root.left, data);
			root.leftChildren++;
		} else {
			root.right = insertRecursive(root.right, data);
			root.rightChildren++;
		}
		size++;
		return root;
	}
/*
 * Method to count the number reports after a given date in BTS
 * @date, the date user inputs 
 * @return the number of reports after date
 */
	 public int countReportsAfterDate(Report date) {
	        return countReportsAfterDateRecursive(root, date.getStartTime());
	    }
/**
 * Recursive method to count the number of reports after a given date in BTS
 * @param root the root node of the search
 * @param localDateTime, the date inside the data
 * @return the number of reports after date
 */
	    int countReportsAfterDateRecursive(Node root, LocalDateTime localDateTime) {
	        if (root == null) {
	            return 0;
	        }

	        if (root.data.getStartTime().compareTo(localDateTime) >= 0) {
	            return 1 + root.rightChildren + countReportsAfterDateRecursive(root.left, localDateTime);
	        } else {
	            return countReportsAfterDateRecursive(root.right, localDateTime);
	        }
	    }

	
	public long size() {
		return size;
	}
}
