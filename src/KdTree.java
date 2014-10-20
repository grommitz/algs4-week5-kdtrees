import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Martin Charlesworth
 *
 */
public class KdTree {

	private Node root;

	private class Node implements Comparable<Point2D> {
		Point2D p;
		boolean isVertical;
		Node left;
		Node right;
		int N;
		@Override
		public int compareTo(Point2D that) {
			if (isVertical) {
				return Point2D.X_ORDER.compare(this.p, that);
			} else {
				return Point2D.Y_ORDER.compare(this.p, that);
			}
		}
		public Node(Point2D p, boolean isVertical, int N) {
			this.p = p;
			this.isVertical = isVertical;
			this.N = N;
		}
	}

	public KdTree() { }

	public boolean isEmpty() {
		return true;
	}

	public int size() {
		return size(root); 
	}

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }
    
	public void insert(Point2D p) {
		root = insert(root, p, true);
	}

	private Node insert (Node n, Point2D p, boolean vertical) {
		if (n == null) {
			return new Node(p, vertical, 1);
		} else {
			int cmp = n.compareTo(p);
			if (cmp <= 0) n.left  = insert(n.left, p, !vertical);
			else n.right = insert(n.right, p, !vertical);
	        n.N = 1 + size(n.left) + size(n.right);
			return n;
		}
	}

	public boolean contains(Point2D p) {
		return false; 
	}

	private List<Node> nodes() {
		return nodes(root);
	}
	
	private List<Node> nodes(Node n) {
		List<Node> result = new ArrayList<>();
		result.add(n);
		if (n.left != null) result.addAll(nodes(n.left));
		if (n.right != null) result.addAll(nodes(n.right));
		return result;
	}
	
	public void draw() {
		StdDraw.setXscale(0.0, 1.0);
		StdDraw.setYscale(0.0, 1.0);
//		Stack<Node> stack = new Stack<>();
		Node n = root;
		double minX = 0.0;
		double minY = 0.0;
		double maxX = 1.0;
		double maxY = 1.0;
		draw(root, minX, minY, maxX, maxY);
	}
	
	private void draw(Node n, double minX, double minY, double maxX, double maxY) {
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.point(n.p.x(), n.p.y());
		double splitX, splitY;
		if (n.isVertical) {
			StdDraw.setPenColor(Color.RED);
			StdDraw.line(n.p.x(), minY, n.p.x(), maxY);
			splitX = n.p.x();
		} else {
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.line(minX, n.p.y(), maxX, n.p.y());
		}
		if (n.left != null) {
			if (n.isVertical)
				draw(n.left, minX, minY, n.p.x(), maxY);
			else
				draw(n.left, minX, minY, maxX, n.p.y());
		}
		if (n.right != null) {
			if (n.isVertical)
				draw(n.right, n.p.x(), maxX, minY, maxY);
			else
				draw(n.right, minX, n.p.y(), minY, maxY);
		}
			
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle 
		return null;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty 
		return null;
	}

	public static void main(String[] args) {

	}


}
