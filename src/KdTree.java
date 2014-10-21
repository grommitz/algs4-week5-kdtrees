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
	private int N;

	private class Node implements Comparable<Point2D> {
		Point2D p;
		boolean isVertical;
		Node left;
		Node right;
		@Override
		public int compareTo(Point2D that) {
			if (isVertical) {
				return Point2D.X_ORDER.compare(this.p, that);
			} else {
				return Point2D.Y_ORDER.compare(this.p, that);
			}
		}
		public Node(Point2D p, boolean isVertical) {
			this.p = p;
			this.isVertical = isVertical;
		}
	}

	public KdTree() { }

	public boolean isEmpty() {
		return true;
	}

	public int size() {
		return N; 
	}

	public void insert(Point2D p) {
		root = insert(root, p, true);
		N++;
	}

	private Node insert (Node n, Point2D p, boolean vertical) {
		if (n == null) {
			return new Node(p, vertical);
		} else {
			int cmp = n.compareTo(p);
			if (cmp <= 0) 
				n.left  = insert(n.left, p, !vertical);
			else 
				n.right = insert(n.right, p, !vertical);
			return n;
		}
	}

	public boolean contains(Point2D p) {
		return false; 
	}

	public void draw() {
		draw(root, 0.0, 0.0, 1.0, 1.0);
	}

	private void draw(Node n, double minX, double minY, double maxX, double maxY) {
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.point(n.p.x(), n.p.y());
		if (n.isVertical) {
			StdDraw.setPenColor(Color.RED);
			StdDraw.line(n.p.x(), minY, n.p.x(), maxY);
		} else {
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.line(minX, n.p.y(), maxX, n.p.y());
		}
		if (n.left != null) {
			if (n.isVertical) maxX = n.p.x();
			else              maxY = n.p.y();
			draw(n.left, minX, maxX, minY, maxY);
		}
		if (n.right != null) {
			if (n.isVertical) minX = n.p.x();
			else              minY = n.p.y();
			draw(n.right, minX, maxX, minY, maxY);
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

}
