package marzuillo.sales.taxes.test.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import marzuillo.sales.taxes.bl.ShoppingItem;

@RunWith(value = Parameterized.class)
public class RoundingFunctionTest {

	private double input;
	private double expected;
	
	public RoundingFunctionTest(double input, double expected) {
		this.input = input;
		this.expected = expected;
	}
	
	@Parameters(name = "check rounding on value:{0}  expected:{1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {18.99,19.00},
                {17.07,17.10},
                {17.08,17.10}
        });
    }

    @Test
    public void testRounding_success() throws IOException {
    	BigDecimal ris=ShoppingItem.rounding(new BigDecimal(input));
        assertThat(ris, is(new BigDecimal(expected).setScale(2, RoundingMode.DOWN)));
    }
	
   
}
