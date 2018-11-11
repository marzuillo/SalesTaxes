package marzuillo.sales.taxes.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the whole list of products
 * @author r.marzuillo
 *
 */
public class ShoppingBasket {
	
	private List<ShoppingItem> orderList;
	
	public ShoppingBasket() {
		orderList=new ArrayList<>();
	}
	
	public void addShoppingOrder(ShoppingItem order) {
		orderList.add(order);
	}
	
	public boolean isEmpty() {
		return orderList.isEmpty();
	}
	
	/**
	 * Calculate the total with taxes
	 * @return
	 */
	public BigDecimal getTotal() {
		BigDecimal ris = BigDecimal.ZERO;
		for (ShoppingItem item : orderList) {
			ris = ris.add(item.getfinalPrice());
		}
		return ris;
	}
	
	/**
	 * Calculate the total fees to be paid
	 * @return
	 */
	public BigDecimal getTotalTaxes() {
		BigDecimal ris = BigDecimal.ZERO;
		for (ShoppingItem item : orderList) {
			ris = ris.add(item.getTaxes());
		}
		return ris;
	}
	
	/**
	 * Evaluate the details of the purchase list, evaluate the taxes 
	 * and create a receipt in text format
	 * @return a receipt in text format.<br>Example:
	 * <br>1 book : 12.49
	 * <br>1 music CD: 16.49
	 * <br>1 chocolate bar: 0.85
	 * <br>Sales Taxes: 1.50
	 * <br>Total: 29.83
	 */
	public String getReceipt() {
		StringBuilder builder = new StringBuilder();

		for (ShoppingItem item : orderList) {
			String imported = "";
			if (ShoppingItem.ORIGIN.IMPORTED.equals(item.getOrigin())) {
				imported = " imported";
			}
			builder.append(item.getQuantity() + imported + " " + item.getProduct().getProductName() + ": "
					+ item.getfinalPrice() + "\n");
		}
		builder.append("Sales Taxes: " + getTotalTaxes() + "\n");
		builder.append("Total: " + getTotal());

		return builder.toString();
	}
	
	//allow you to check if the current list is equals to input provided
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (ShoppingItem item : orderList) {
			String imported = "";
			if (ShoppingItem.ORIGIN.IMPORTED.equals(item.getOrigin())) {
				imported = " imported";
			}
			builder.append(item.getQuantity() + imported + " " + item.getProduct().getProductName() + " at "
					+ item.getOriginalPrice() + "\n");
		}

		//remove the last two characters "\r\n"
		return builder.toString().substring(0, builder.toString().length() - 1);
	}

}
