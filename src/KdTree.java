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
			//StdOut.println(p + ": adding " + (vertical?"vertical":"horizontal") + " node");
			return new Node(p, vertical);
		} else {
			int cmp = n.compareTo(p);
			if (cmp > 0) {
				//StdOut.println(p + ": going left at " + n.p);
				n.left  = insert(n.left, p, !vertical);
			} else { 
				//StdOut.println(p + ": going right at " + n.p);
				n.right = insert(n.right, p, !vertical);
			}
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
		StdDraw.setPenRadius(0.01);
		StdDraw.point(n.p.x(), n.p.y());
		StdDraw.setPenRadius();
		if (n.isVertical) {
			StdDraw.setPenColor(Color.RED);
			StdDraw.line(n.p.x(), minY, n.p.x(), maxY);
		} else {
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.line(minX, n.p.y(), maxX, n.p.y());
		}
		if (n.left != null) {
			double mxx = n.isVertical ? n.p.x() : maxX;
			double mxy = !n.isVertical ? n.p.y() : maxY;
			draw(n.left, minX, minY, mxx, mxy);
		}
		if (n.right != null) {
			double mnx = n.isVertical ? n.p.x() : minX;
			double mny = !n.isVertical ? n.p.y() : minY;
			draw(n.right, mnx, mny, maxX, maxY);
		}

	}

	public Iterable<Point2D> range(RectHV rect) {
		List<Point2D> result = new ArrayList<>();
		RectHV searchArea = new RectHV(0.0, 0.0, 1.0, 1.0);
		explore(root, rect, result, searchArea);
		return result;
	}
	
	private void explore(final Node n, final RectHV rect, final List<Point2D> result, RectHV searchArea) {
		if (rect.contains(n.p)) {
			result.add(n.p);
		}
		if (n.left != null) {
			searchArea = new RectHV(searchArea.xmin(), searchArea.ymin(), 
						(n.isVertical ? n.p.x() : searchArea.xmax()), 
						(n.isVertical ? searchArea.ymax() : n.p.y()));
			if (rect.intersects(searchArea)) {
				explore(n.left, rect, result, searchArea);
			}
		}
		if (n.right != null) {
			searchArea = new RectHV((n.isVertical ? n.p.x() : searchArea.xmax()), 
									(n.isVertical ? searchArea.ymax() : n.p.y()),
									searchArea.xmax(), searchArea.ymax());
			if (rect.intersects(searchArea)) {
				explore(n.right, rect, result, searchArea);
			}
		}
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty 
		return null;
	}

}
