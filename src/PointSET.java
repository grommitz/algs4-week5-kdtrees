/**
 * 
 * @author Martin Charlesworth
 *
 */
import java.util.TreeSet;

public class PointSET {
	
	private TreeSet<Point2D> tree;
	SET set;

	public PointSET() {
		this.tree = new TreeSet<>();
	}
	
	public boolean isEmpty() {
		return tree.isEmpty();
	}
	
	public int size() {
		return tree.size(); 
	}
	
	public void insert(Point2D p) {
		tree.add(p);
	}
	
	public boolean contains(Point2D p) {
		return tree.contains(p); 
	}
	
	public void draw() {
		StdDraw.setXscale(0.0, 1.0);
		StdDraw.setYscale(0.0, 1.0);
		for (Point2D p : tree) {
			StdDraw.point(p.x(), p.y());
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
