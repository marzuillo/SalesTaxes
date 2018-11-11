package marzuillo.sales.taxes.test.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import marzuillo.sales.taxes.controller.SalesTaxesController;
import marzuillo.sales.taxes.exception.ProductNotFoundException;
import marzuillo.sales.taxes.exception.ShoppingItemPatternException;
import marzuillo.sales.taxes.test.persistence.JPAHibernateTest;
import marzuillo.sales.taxes.util.DataSetLoader;

@RunWith(value = Parameterized.class)
public class ShoppingBasketEvaluationTest extends JPAHibernateTest{

	private int testNumber;

	public ShoppingBasketEvaluationTest(int testNumber) {
		this.testNumber = testNumber;
	}
	
	@Parameters(name = "evaluate input{0}.txt with output{0}.txt")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{ 1 }, 
			{ 2 }, 
			{ 3 } });
	}

	@Test
	public void testReceipt_success() throws IOException, ShoppingItemPatternException, ProductNotFoundException {
		String input = DataSetLoader.getFile("assets/input/input" + testNumber + ".txt");
		String output = DataSetLoader.getFile("assets/output/output" + testNumber + ".txt");
		
		assertThat(SalesTaxesController.evaluateShoppingList(input), is(output));
	}

}