package marzuillo.sales.taxes.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class DataSetLoader {

	private static DataSetLoader singleton=null;
	private DataSetLoader() {
	}
	
	public static DataSetLoader getInstance() {
		if(singleton==null) {
			singleton=new DataSetLoader();
		}
		return singleton;
	}
	
	@SuppressWarnings("static-access")
	public static String getFile(String filename) throws IOException  {
		return (getInstance().getFileWithUtil(filename));
	}
	private String getFileWithUtil(String fileName) {
	String result = "";
		
	ClassLoader classLoader = getClass().getClassLoader();
	try {
	    result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
	} catch (IOException e) {
		e.printStackTrace();
	}
	return result;
  }
}
