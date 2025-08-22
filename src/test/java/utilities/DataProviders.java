package utilities;
import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

	// DataProvider 1

	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {
		String path = ".\\testData\\Opencart_LoginData.xlsx"; // taking exel file path from testData folder

		// Creating an object of ExcelUtility class so that we can use methods here...
		ExcelUtilityOG xlutil = new ExcelUtilityOG(path);

		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);

		// Created for two dimension array which can store the data user and password...
		String logindata[][] = new String[totalrows][totalcols];

		for (int i = 1; i < totalrows; i++) // 1 // read the data from xl storing in two dimensional array
		{
			for (int j = 0; j < totalcols; j++) // 0 // i is rows, j is for coloum
			{
				logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j); // 1, 0
			}
		}
		return logindata;	//returning two dimension array...
	}
	
	//DataProvider 2
	//DataProvider 3
	//DataProvider 4
	//DataProvider 5
	

}
