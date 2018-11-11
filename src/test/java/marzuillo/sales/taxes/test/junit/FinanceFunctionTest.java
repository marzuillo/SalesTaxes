package marzuillo.sales.taxes.test.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import marzuillo.sales.taxes.bl.ShoppingItem;
import marzuillo.sales.taxes.dal.entity.Category;
import marzuillo.sales.taxes.dal.entity.Product;

@RunWith(value = Parameterized.class)
public class FinanceFunctionTest {
	
	@SuppressWarnings("unused") //used on @Parameters(name ....
	private String name;
	private ShoppingItem item;
	private BigDecimal expectedPrice;
	
	public FinanceFunctionTest(String name,ShoppingItem item, BigDecimal expectedPrice) {
		this.name=name;
		this.item = item;
		this.expectedPrice = expectedPrice;
	}
	
	@Parameters(name = "check price on \"{0}\"  expected:{2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"music CD",
                	new ShoppingItem(1, new BigDecimal("14.99"), 
                		new Product(null,"music CD", Category.GENERAL), 
        				ShoppingItem.ORIGIN.LOCAL),new BigDecimal("16.49")},
                {"book",
                	new ShoppingItem(1, new BigDecimal("12.49"), 
                			new Product(null,"book",Category.BOOKS), 
                			ShoppingItem.ORIGIN.LOCAL),new BigDecimal("12.49")},
                {"imported box of chocolates",
                	new ShoppingItem(1, new BigDecimal("10.00"),  
                			new Product(null,"imported box of chocolates",Category.FOOD),
                			ShoppingItem.ORIGIN.IMPORTED),new BigDecimal("10.50")},
                {"imported bottle of perfume",
                	new ShoppingItem(1, new BigDecimal("47.50"),  
                			new Product(null,"imported bottle of perfume",Category.GENERAL),
                			ShoppingItem.ORIGIN.IMPORTED),new BigDecimal("54.65")},
                {"bottle of perfume",
                    new ShoppingItem(1, new BigDecimal("18.99"),  
                           	new Product(null,"bottle of perfume",Category.GENERAL),
                            ShoppingItem.ORIGIN.LOCAL),new BigDecimal("20.89")}
        });
    }

    @Test
    public void testRounding_success() throws IOException {
    	BigDecimal ris=item.getfinalPrice();
        assertThat(ris, is(expectedPrice));
    }
	

}
