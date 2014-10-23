import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Martin Charlesworth
 *
 */
public class KdTreeTest {

	private KdTree tree = new KdTree();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsert() {
		for (double x = 0.0; x <= 1.0; x += 0.1) {
			for (double y = 0.0; y <= 1.0; y += 0.1) {
				tree.insert(new Point2D(round(x,1), round(y,1)));
			}
		}
		assertThat(tree.size(), is(121));
	}

	@Test
	public void testRange() {
		tree.insert(new Point2D(0.5, 0.5));
		tree.insert(new Point2D(0.3, 0.3));
		tree.insert(new Point2D(0.3, 0.8));
		tree.insert(new Point2D(0.8, 0.8));
		tree.insert(new Point2D(0.8, 0.3));
		//tree.draw();
		//StdIn.readChar();
		assertThat(numPointsInRange(0.1, 0.1, 0.4, 0.4), is(1));
		assertThat(numPointsInRange(0.4, 0.4, 0.6, 0.6), is(1));
		assertThat(numPointsInRange(0.1, 0.1, 0.4, 0.9), is(2));
		assertThat(numPointsInRange(0.1, 0.6, 0.9, 0.9), is(2));
		assertThat(numPointsInRange(0.6, 0.1, 0.9, 0.9), is(2));
		assertThat(numPointsInRange(0.1, 0.1, 0.9, 0.4), is(2));
		assertThat(numPointsInRange(0.1, 0.1, 0.9, 0.6), is(3));
		assertThat(numPointsInRange(0.1, 0.1, 0.9, 0.9), is(5));
	}

	@Test
	public void testNearest() {
		for (double x = 0.0; x < 1.0; x += 0.1) {
			for (double y = 0.0; y < 1.0; y += 0.1) {
				tree.insert(new Point2D(round(x,1), round(y,1)));
			}
		}
		//assertThat(tree.nearest(new Point2D(0.89, 0.91)), is(new Point2D(0.9, 0.9)));
		assertThat(tree.nearest(new Point2D(0.44, 0.81)), is(new Point2D(0.4, 0.8)));
	}
	
	@Test
	public void testContains() {
		for (double x = 0.0; x < 1.0; x += 0.1) {
			for (double y = 0.0; y < 1.0; y += 0.1) {
				tree.insert(new Point2D(round(x,1), round(y,1)));
			}
		}
		assertThat(tree.contains(new Point2D(0.1, 0.4)), is(true));
		assertThat(tree.contains(new Point2D(0.1, 0.41)), is(false));
	}
	
	private int numPointsInRange(double xmin, double ymin, double xmax, double ymax) {
		int points = 0;
		Iterator<Point2D> i = tree.range(new RectHV(xmin, ymin, xmax, ymax)).iterator();
		while (i.hasNext()) {
			i.next();
			points++;
		}
		return points;
	}
	
	private List<Point2D> pointsInRange(double xmin, double ymin, double xmax, double ymax) {
		List<Point2D> points = new ArrayList<>();
		Iterator<Point2D> i = tree.range(new RectHV(xmin, ymin, xmax, ymax)).iterator();
		while (i.hasNext()) {
			points.add(i.next());
		}
		return points;
	}
	
	private static double round(double dbl, int dp) {
		BigDecimal bd = new BigDecimal(dbl).setScale(dp, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
