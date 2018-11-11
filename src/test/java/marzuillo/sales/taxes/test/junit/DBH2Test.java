package marzuillo.sales.taxes.test.junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import marzuillo.sales.taxes.dal.entity.Category;
import marzuillo.sales.taxes.dal.services.ServiceLocator;
import marzuillo.sales.taxes.dal.services.ShoppingItemService;
import marzuillo.sales.taxes.exception.CategoryNotFoundException;
import marzuillo.sales.taxes.test.persistence.JPAHibernateTest;


public class DBH2Test extends JPAHibernateTest {

	@Test
    public void testRead_success() throws CategoryNotFoundException {
		
		ShoppingItemService shoppingItemService=ServiceLocator.getInstance().getShoppingItemService();
		Category category= shoppingItemService.findCategory("book");
		
        assertEquals(Category.BOOKS, category);
    }

}
