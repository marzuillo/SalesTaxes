package marzuillo.sales.taxes.common;

import java.math.BigDecimal;

public interface Conf {
	public static final BigDecimal basicTax=new BigDecimal("0.1");
	public static final BigDecimal importTax=new BigDecimal("0.05");
}
