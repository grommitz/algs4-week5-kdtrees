import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Martin Charlesworth
 *
 */
public class PointSET {
	
	private SET<Point2D> set;

	public PointSET() {
		this.set = new SET<>();
	}
	
	public boolean isEmpty() {
		return set.isEmpty();
	}
	
	public int size() {
		return set.size(); 
	}
	
	public void insert(Point2D p) {
		set.add(p);
	}
	
	public boolean contains(Point2D p) {
		return set.contains(p); 
	}
	
	public void draw() {
		StdDraw.setXscale(0.0, 1.0);
		StdDraw.setYscale(0.0, 1.0);
		for (Point2D p : set) {
			StdDraw.point(p.x(), p.y());
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle - brute force!
		Set<Point2D> result = new HashSet<>();
		for (Point2D p : set) {
			if (rect.contains(p)) {
				result.add(p);
			}
		}
		return result;
	}
	
	public Point2D nearest(Point2D pt) {
		// a nearest neighbor in the set to point p; null if the set is empty 
		Point2D result = null;
		double shortestDistance = Double.MAX_VALUE;
		for (Point2D p : set) {
			if (p.distanceTo(pt) < shortestDistance) {
				result = p;
				shortestDistance = p.distanceTo(pt);
			}
		}
		return result;
	}

}
