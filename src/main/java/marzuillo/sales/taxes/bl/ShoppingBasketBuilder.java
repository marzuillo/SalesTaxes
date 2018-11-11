package marzuillo.sales.taxes.bl;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import marzuillo.sales.taxes.dal.entity.Product;
import marzuillo.sales.taxes.dal.services.ServiceLocator;
import marzuillo.sales.taxes.dal.services.ShoppingItemService;
import marzuillo.sales.taxes.exception.ProductNotFoundException;
import marzuillo.sales.taxes.exception.ShoppingItemPatternException;

/**
 * Allows the creation of a model for calculations starting from a text
 * @author r.marzuillo
 *
 */
public class ShoppingBasketBuilder {
	
	final static Logger logger = Logger.getLogger(ShoppingBasketBuilder.class);
	
	private static String regexPattern = "([0-9]+)(([A-z]*\\s+)*)at ([0-9]+\\.{0,1}[0-9]*)";
	private static Pattern pattern = Pattern.compile(regexPattern);
	private static ShoppingItemService shoppingItemService =ServiceLocator.getInstance().getShoppingItemService();
	
	private static ShoppingItem.ORIGIN getOrigin(String orderName) {
		if (orderName != null && !orderName.isEmpty()) {
			if (orderName.toLowerCase().contains("imported")) {
				return ShoppingItem.ORIGIN.IMPORTED;
			}
		}
		return ShoppingItem.ORIGIN.LOCAL;
	}
	
	private static ShoppingItem createOrderFromLine(String line) throws ShoppingItemPatternException, ProductNotFoundException{
		if(logger.isDebugEnabled()){
		    logger.debug("create order from line:"+line);
		}
		Matcher m = pattern.matcher(line);
	      if (m.find( )) {
	    	  
	    	  //retrieve information through regular expression groups
	    	  String productName=m.group(2).trim().replaceAll("imported ", "");
	    	  int quantity=Integer.valueOf(m.group(1));
	    	  BigDecimal originalPrice=new BigDecimal(m.group(4));
	    	  
	    	  //add other information available from the text
	    	  ShoppingItem.ORIGIN origin = getOrigin(line);
	    	  Product product=shoppingItemService.findProduct(productName);
	    	  ShoppingItem result=new ShoppingItem(quantity, originalPrice, product, origin);
	    	  
	    	  return result;
	      }else {
	    	  throw new ShoppingItemPatternException(line+" is not valid");
	      }
	}
	
	/**
	 * takes input of the list of products purchased in text format and generates returns an object of type ShoppingList that allows the calculation of taxes.
	 * @param text a list of element in format {number}{product name} at {price}\r\n<br>example:<br>1 book at 12.49<br>1 music CD at 14.99<br>1 chocolate bar at 0.85
	 * @return ShoppingList
	 * @throws ShoppingItemPatternException
	 * @throws ProductNotFoundException 
	 */
	public static ShoppingBasket createShoppingList(String text) throws ShoppingItemPatternException, ProductNotFoundException {
		ShoppingBasket list=new ShoppingBasket();
		if(text!=null && !text.isEmpty()) {
			String[] lines=text.split("\n");
			for(String line:lines) {
				list.addShoppingOrder(createOrderFromLine(line));
			}
		}		
		return list;
	}

}
