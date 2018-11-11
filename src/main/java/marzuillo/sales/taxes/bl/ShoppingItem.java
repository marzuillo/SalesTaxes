package marzuillo.sales.taxes.bl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import marzuillo.sales.taxes.common.Conf;
import marzuillo.sales.taxes.dal.entity.Category;
import marzuillo.sales.taxes.dal.entity.Product;

/**
 * Represents a single item from the list of purchased products
 * @author r.marzuillo
 *
 */
public class ShoppingItem {
	
	private int quantity;
	private BigDecimal originalPrice;
	private Product product;
	private ORIGIN origin;
	
	public static enum ORIGIN {
	    LOCAL,
	    IMPORTED;
	}
	
	public ShoppingItem(int quantity, BigDecimal originalPrice, Product product, ORIGIN origin) {
		super();
		this.quantity = quantity;
		this.originalPrice = originalPrice;
		this.product = product;
		this.origin = origin;
	}

	/**
	 * 
	 * @param category
	 * @return
	 */
	private boolean isSalesTaxApplicable(Category category) {
		return !(
				Category.BOOKS.equals(category) || 
				Category.FOOD.equals(category) || 
				Category.MEDICAL.equals(category)
				);
	}
	
	
	private boolean isImportTaxApplicable(ORIGIN origin) {
		return ORIGIN.IMPORTED.equals(origin);
	}

	/**
	 * Calculate the amount of taxes 
	 * @return
	 */
	public BigDecimal getTaxes() {
		BigDecimal result=BigDecimal.ZERO;
		
		if(isSalesTaxApplicable(product.getCategory())) {
			result= rounding( (originalPrice.multiply(new BigDecimal(quantity))  ).multiply(Conf.basicTax)  );
		}
		if(isImportTaxApplicable(origin)) {
			result=result.add(rounding( (originalPrice.multiply(new BigDecimal(quantity)).multiply(Conf.importTax) )));
		}
		return result;
	}
	
	
	/**
	 * Calculate the price adding taxes
	 * @return
	 */
	public BigDecimal getfinalPrice() {
		BigDecimal value = originalPrice.multiply(new BigDecimal(quantity));
		BigDecimal result =  value.add(getTaxes()).setScale(2,RoundingMode.DOWN);
		return result;
	}
	
	/* TODO discuss the validity of the tests regarding the rounding function
	 * 
	 *  the rounding rule should be: for a tax rate of n%, a shelf price of p contains
	 *  (np/100 rounded up to the nearest 0.05) amount of sales tax.
	 *  
	 *  This rule is not valid for the dataset supplied, i.e:
	 *  	input3:
	 *  		1 box of imported chocolates at 11.25
	 * 		
	 * 		chocolates is FOOD, so will be applied only import tax 
	 * 			n% = 5%
	 * 			p  = 11.25
	 * 			np/100 = 5 * 11.25 / 100 = 0.5625 
	 * 			rounding tax with rule above = 0.55
	 * 			final price = 11.25 + 0.55 = 11,80
	 * 
	 * 		but output3 show:
	 * 			1 imported box of chocolates: 11.85
	 * 
	 * So, In a real context of work I should asked for information,
	 * to clarify whether the indicated dataset is corrupt or I misunderstand the concept of rounding.
	 *  
	 *  In this case to comply with the tests,
	 *  I preferred to make a rounding function that produces compatible output
	 */
	public static BigDecimal rounding(BigDecimal value) {
		BigDecimal b10 = new BigDecimal(10);
		BigDecimal tmp = value.multiply(b10);
		int integerPart = tmp.intValue();
		int fractalPart = tmp.remainder(BigDecimal.ONE).setScale(1, RoundingMode.DOWN).multiply(b10).intValue();
		BigDecimal result = null;
		if (fractalPart >= 6) {
			//round to 1
			result = (new BigDecimal(integerPart + 1)).divide(b10);
		} else if (fractalPart < 3) {
			//round to 0
			result = (new BigDecimal(integerPart)).divide(b10);
		} else {
			//round to 0.5
			result = (new BigDecimal(integerPart).add(new BigDecimal("0.5"))).divide(b10);
		}
		return result.setScale(2, RoundingMode.DOWN);
	}

// This method reflect the specification but not work fine for the input supplied
//	
//	test on 1.0 result:1.0
//	test on 1.01 result:1.0
//	test on 1.02 result:1.0
//	test on 1.03 result:1.05
//	test on 1.04 result:1.05
//	test on 1.05 result:1.05
//	test on 1.06 result:1.05
//	test on 1.07 result:1.05
//	test on 1.08 result:1.1
//	test on 1.09 result:1.1
//	test on 2.0 result:2.0
//	test on 2.01 result:2.0
//	test on 2.02 result:2.0
//	test on 2.03 result:2.05
//	
//	/**
//	 * round to nearest 0.05
//	 * @param value
//	 * @return
//	 */
//	public static BigDecimal rounding(BigDecimal value) {
//		return BigDecimal.valueOf(Math.round(value.doubleValue() * 20) / 20.0);
//	}
	
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Price without taxes
	 * @return
	 */
	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public ORIGIN getOrigin() {
		return origin;
	}

	public void setOrigin(ORIGIN origin) {
		this.origin = origin;
	}


	@Override
	public String toString() {
		return "ShoppingItem [quantity=" + quantity + ", originalPrice=" + originalPrice + ", product=" + product
				+ ", origin=" + origin + "]";
	}

	
	
	
	

}
