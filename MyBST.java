package accidentpack;

import java.time.LocalDateTime;

public class MyBST {
	private long size = 0;
	static class Node {
		Report data;
		Node left, right;
		public int leftChildren = 0;
		public int rightChildren = 0;

		public Node(Report data2) {
			data = data2;
			left = right = null;

		}
	}

	Node root;

	public MyBST() {
		root = null;
	}

	public void insert(Report data) {
		root = insertRecursive(root, data);
	}

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

	 public int countReportsAfterDate(Report date) {
	        return countReportsAfterDateRecursive(root, date.getStartTime());
	    }

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
