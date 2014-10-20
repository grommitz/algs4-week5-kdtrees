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
public class KdTreeTest {

	private KdTree tree = new KdTree();
	
	@Before
	public void setUp() throws Exception {
		for (double x = 0.0; x <= 1.0; x += 0.1) {
			for (double y = 0.0; y <= 1.0; y += 0.1) {
				tree.insert(new Point2D(round(x,1), round(y,1)));
			}
		}
	}

	@Test
	public void test() {
		assertThat(tree.size(), is(121));
	}

	private static double round(double dbl, int dp) {
		BigDecimal bd = new BigDecimal(dbl).setScale(dp, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
