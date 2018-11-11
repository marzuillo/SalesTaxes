package marzuillo.sales.taxes.test.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import marzuillo.sales.taxes.bl.ShoppingBasket;
import marzuillo.sales.taxes.bl.ShoppingBasketBuilder;
import marzuillo.sales.taxes.exception.ProductNotFoundException;
import marzuillo.sales.taxes.exception.ShoppingItemPatternException;
import marzuillo.sales.taxes.test.persistence.JPAHibernateTest;
import marzuillo.sales.taxes.util.DataSetLoader;

@RunWith(value = Parameterized.class)
public class ParsingTest  extends JPAHibernateTest {

	private int testNumber;

	public ParsingTest(int testNumber) {
		this.testNumber = testNumber;
	}
	
	@Parameters(name = "parsing input{0}.txt")
	public static Collection<Object[]> data() {
		//input 3 is not applicable in this test 
		//because there is a name with "imported" term in a different position
		return Arrays.asList(new Object[][] { { 1 }, { 2 } });
		
	}

	@Test
	public void testParsing_success() throws IOException, ShoppingItemPatternException, ProductNotFoundException {
		String input = DataSetLoader.getFile("assets/input/input" + testNumber + ".txt");
		ShoppingBasket shoppingList = ShoppingBasketBuilder.createShoppingList(input);
		
		assertTrue(! shoppingList.isEmpty());
		assertThat(shoppingList.toString(), is(input));
	}

}
