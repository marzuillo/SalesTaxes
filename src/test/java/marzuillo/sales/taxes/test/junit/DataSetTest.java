package marzuillo.sales.taxes.test.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import marzuillo.sales.taxes.util.DataSetLoader;


@RunWith(value = Parameterized.class)
public class DataSetTest {

	private int testNumber;
	private String lastLineOutput;
	
	public DataSetTest(int testNumber,String lastLineOutput) {
		this.testNumber=testNumber;
		this.lastLineOutput=lastLineOutput;
	}
	
    @Parameters(name = "validation of input{0}.txt and output{0}.txt")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1,"Total: 29.83"},
                {2,"Total: 65.15"},
                {3,"Total: 74.68"}
        });
    }

    @Test
    public void testRead_success() throws IOException {
    	String input=DataSetLoader.getFile("assets/input/input"+testNumber+".txt");
    	String output=DataSetLoader.getFile("assets/output/output"+testNumber+".txt");
        assertTrue(!input.isEmpty());
        assertTrue(!output.isEmpty());
        
        //check last line of output to make sure that we have the right result on tests
        String[] outlines=output.split("\n");
        String total=outlines[outlines.length-1];
        assertThat(total, is(lastLineOutput));
    }
}
