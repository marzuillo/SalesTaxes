import java.io.IOException;

import org.apache.log4j.Logger;

import marzuillo.sales.taxes.controller.SalesTaxesController;
import marzuillo.sales.taxes.util.DataSetLoader;

public class Main {

	final static Logger logger = Logger.getLogger(Main.class);
	
	public Main() {
		
	}

	/**
	 * run the exercise
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=1;i<=3;i++) {
			printExample(i);
		}
		System.exit(0);
	}
	
	/**
	 * Prints out the receipt details for the shopping basket
	 * @param index
	 */
	public static void printExample(int index) {
		try {
			String input = DataSetLoader.getFile("assets/input/input"+index+".txt");
			String output=SalesTaxesController.evaluateShoppingList(input);
			logger.info("INPUT  "+index+":\n"+input+"\n");
			logger.info("OUTPUT "+index+":\n"+output+"\n\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
