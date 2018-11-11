package marzuillo.sales.taxes.exception;

public class ShoppingItemPatternException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2581842360201215556L;

	public ShoppingItemPatternException() {
	}

	public ShoppingItemPatternException(String message) {
		super(message);
	}

	public ShoppingItemPatternException(Throwable cause) {
		super(cause);
	}

	public ShoppingItemPatternException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShoppingItemPatternException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
