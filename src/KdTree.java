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
	private int N = 0;

	static class Node implements Comparable<Point2D> {
		final Point2D p;
		final boolean isVertical;
		Node left;
		Node right;
		final RectHV rect;
		public Node(Point2D p, boolean isVertical, RectHV r) {
			this.p = p;
			this.isVertical = isVertical;
			this.rect = r;
		}
		@Override
		public int compareTo(Point2D that) {
			if (isVertical) {
				return Point2D.X_ORDER.compare(this.p, that);
			} else {
				return Point2D.Y_ORDER.compare(this.p, that);
			}
		}
	}

	public KdTree() { }

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N; 
	}

	public void insert(Point2D p) {
		RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
		root = insert(root, p, true, r);
	}

	private Node insert(Node n, Point2D p, boolean vertical, RectHV r) {
		if (n == null) {
			//StdOut.println(p + ": adding " + (vertical?"vertical":"horizontal") + " node");
			N++;
			return new Node(p, vertical, r);
		} else {
			int cmp = n.compareTo(p);
			if (cmp > 0) {
				//StdOut.println(p + ": going left at " + n.p);
				if (n.isVertical) {
					r = new RectHV(r.xmin(), r.ymin(), n.p.x(), r.ymax());
				} else {
					r = new RectHV(r.xmin(), r.ymin(), r.xmax(), n.p.y());
				}
				n.left  = insert(n.left, p, !vertical, r);
			} else { 
				//StdOut.println(p + ": going right at " + n.p);
				if (n.isVertical) {
					r = new RectHV(n.p.x(), r.ymin(), r.xmax(), r.ymax());
				} else {
					r = new RectHV(r.xmin(), n.p.y(), r.xmax(), r.ymax());
				}
				n.right = insert(n.right, p, !vertical, r);
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
		explore(root, rect, result);
		return result;
	}
	
	private void explore(final Node n, final RectHV rect, final List<Point2D> result) {
		if (rect.contains(n.p)) {
			result.add(n.p);
		}
		if (n.left != null) {
			if (n.isVertical && n.p.x() >= rect.xmin()) {
				//System.out.println("explore left from vertical " + n.p);
				explore(n.left, rect, result);
			} else if (!n.isVertical && n.p.y() >= rect.ymin()) {
				//System.out.println("explore left from horizontal " + n.p);
				explore(n.left, rect, result);
			}
		}
		if (n.right != null) {
			if (n.isVertical && n.p.x() <= rect.xmax()) {
				//System.out.println("explore right from vertical " + n.p);
				explore(n.right, rect, result);
			} else if (!n.isVertical && n.p.y() <= rect.ymax()) {
				//System.out.println("explore right from horizontal " + n.p);
				explore(n.right, rect, result);
			}
		}
	}

	public Point2D nearest(Point2D p) {
		Point2D result = nearest(p, root, null);
		return result;
	}

	private Point2D nearest(final Point2D queryPoint, Node n, Point2D result) {
		double dist = n.p.distanceSquaredTo(queryPoint);
		double shortestDist;
		System.out.println("looking at " + n.p + ", dist = " + dist);
		if (result == null) {
			result = n.p;
			shortestDist = dist; 
		} else {
			shortestDist = result.distanceSquaredTo(queryPoint);
			if (dist < shortestDist) {
				result = n.p;
				shortestDist = dist; 
				System.out.println("yay, closest so far!");
			}
		}
		boolean searchLeft = false, searchRight = false;;
		if (n.left != null) {
			double ldist = n.left.rect.distanceSquaredTo(queryPoint);
			if (ldist < shortestDist) {
				searchLeft = true;
			}
		}
		if (n.right != null) {
			double rdist = n.right.rect.distanceSquaredTo(queryPoint);
			if (rdist < shortestDist) {
				searchRight = true;
			}
		}
		boolean leftFirst = true;
		if (n.isVertical) {
			if (queryPoint.x() > n.p.x()) {
				leftFirst = false;
			}
		} else {
			if (queryPoint.y() > n.p.y()) {
				leftFirst = false;
			}
		}
		
		if (leftFirst) {
			if (searchLeft)
				result = nearest(queryPoint, n.left, result);
			if (searchRight)
				result = nearest(queryPoint, n.right, result);
		}
		else {
			if (searchRight)
				result = nearest(queryPoint, n.right, result);
			if (searchLeft)
				result = nearest(queryPoint, n.left, result);
		}
		return result;
	}
	
	public static void main(String[] args) {
		KdTree tree = new KdTree();
		tree.insert(new Point2D(0.5, 0.5));
		tree.insert(new Point2D(0.3, 0.3));
		tree.insert(new Point2D(0.2, 0.2));
		
		Node p = tree.root.left.left;
		if(p.rect.equals(new RectHV(0.0, 0.0, 0.5, 0.3))) {
			StdOut.println("ok!");
		} else {
			StdOut.println("fail! rect = " + p.rect);
		}
	}
}
