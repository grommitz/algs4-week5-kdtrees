import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Martin Charlesworth
 *
 */
public class PointSETTest {

	private PointSET set = new PointSET();

	@Before
	public void setUp() throws Exception {
		for (double x = 0.0; x < 1.0; x += 0.1) {
			for (double y = 0.0; y < 1.0; y += 0.1) {
				set.insert(new Point2D(round(x,1), round(y,1)));
			}
		}
	}

	@Test
	public void testNearest() {
		assertThat(set.nearest(new Point2D(0.47, 0.47)), is(new Point2D(0.5, 0.5)));
		assertThat(set.nearest(new Point2D(0.99, 0.01)), is(new Point2D(1.0, 0.0)));
	}

	@Test
	public void testContains() {
		assertThat(set.contains(new Point2D(0.3, 0.4)), is(true));
		assertThat(set.contains(new Point2D(0.35, 0.45)), is(false));
	}
	
	private static double round(double dbl, int dp) {
		BigDecimal bd = new BigDecimal(dbl).setScale(dp, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
