import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

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
		tree.draw();
		StdIn.readChar();
		int points = 0;
		Iterator<Point2D> i = tree.range(new RectHV(0.1, 0.1, 0.6, 0.6)).iterator();
		while (i.hasNext()) {
			i.next();
			points++;
		}
		assertThat(points, is(2));
	}
	
	private static double round(double dbl, int dp) {
		BigDecimal bd = new BigDecimal(dbl).setScale(dp, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
