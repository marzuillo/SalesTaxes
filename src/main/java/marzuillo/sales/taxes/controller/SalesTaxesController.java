package marzuillo.sales.taxes.controller;

import org.apache.log4j.Logger;

import marzuillo.sales.taxes.bl.ShoppingBasket;
import marzuillo.sales.taxes.bl.ShoppingBasketBuilder;
import marzuillo.sales.taxes.exception.ProductNotFoundException;
import marzuillo.sales.taxes.exception.ShoppingItemPatternException;

public class SalesTaxesController {

	final static Logger logger = Logger.getLogger(SalesTaxesController.class);

	public static String evaluateShoppingList(String input) {
		if(logger.isDebugEnabled()){
		    logger.debug("input received:"+input);
		}
		
		ShoppingBasket shoppingList;
		String result=null;
		try {
			shoppingList = ShoppingBasketBuilder.createShoppingList(input);
			result=shoppingList.getReceipt();
		} catch (ShoppingItemPatternException | ProductNotFoundException e) {
			logger.error("Error during evaluateShoppingList", e);
		}
		
		if(logger.isDebugEnabled()){
		    logger.debug("output:"+result);
		}
		return result;
	}

}
