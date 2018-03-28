package com.hybridFramework.excelReader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hybridFramework.testBase.TestBase;


public class Excel_reader {
	
	public final Logger logger = Logger.getLogger(TestBase.class.getName());
	
	
public String[][] getExcelData(String excelLocation, String sheetName) throws Exception{
	
	logger.info("Creating excel object : "+excelLocation);
	
	String dataSets[][] = null;
	
	File f =new File(excelLocation);
	FileInputStream file = new FileInputStream(f);
	
	XSSFWorkbook workbook = new XSSFWorkbook(file);
	
	//Get first desired sheet from the workbook
	XSSFSheet sheet = workbook.getSheet(sheetName);
	
	System.out.println("Sheet name is read"+sheet.getSheetName());
	//count number of active rows
	int totalRow = sheet.getLastRowNum() + 1;
//	int totalRow = 8;
	System.out.println("Total rows : "+totalRow);
	
	//count number of active columns
	int totalColumn = sheet.getRow(0).getLastCellNum();
//	int totalColumn = 3;
	System.out.println("Total columns : "+totalColumn);
	
	//create array of rows and columns
	dataSets = new String[totalRow-1][totalColumn];
	
	//Iterate through each row one by one
	Iterator<Row> rowIterator = sheet.iterator();
	
	 int i=0;
	 int t=0;
	 
	 while(rowIterator.hasNext()){
		 
		 Row row = rowIterator.next();
		 if(i++ != 0){
			 int k = t;
			 t++;
			 
			 Iterator<Cell> cellIterator = row.cellIterator();
			 int j=0;
			 while(cellIterator.hasNext()){
				 
				 Cell cell = cellIterator.next();
				 
				 //check the cell type and format accordingly
				 switch(cell.getCellType()){
				 
				 case Cell.CELL_TYPE_NUMERIC: 
					 System.out.print(k+",");
					 System.out.print(j+",");
					 dataSets[k][j++] = cell.getStringCellValue();
					 System.out.println(cell.getNumericCellValue());
					 break;
				
				 case Cell.CELL_TYPE_STRING:
					 System.out.print(k+",");
					 System.out.print(j+",");
					 dataSets[k][j++] = cell.getStringCellValue();
					 System.out.println(cell.getStringCellValue());
					 break;
					 
				 case Cell.CELL_TYPE_BOOLEAN:
					 System.out.print(k+",");
					 System.out.print(j+",");
					 dataSets[k][j++] = cell.getStringCellValue();
					 System.out.println(cell.getStringCellValue());
					 break;
				
				 case Cell.CELL_TYPE_FORMULA:
					 System.out.print(k+",");
					 System.out.print(j+",");
					 dataSets[k][j++] = cell.getStringCellValue();
					 System.out.println(cell.getStringCellValue());
					 break;
				 }
			 }
			 
			 System.out.println("");
		 }
		 
	 }
	
	 file.close();
	 return dataSets;

}

public static void main(String args[]) throws Exception{
	
	String excelLocation = System.getProperty("user.dir")+"/src/main/java/com/hybridFramework/data/TestData.xlsx";
	String sheetName = "LoginTestData";
	Excel_reader excel = new Excel_reader();
	excel.getExcelData(excelLocation, sheetName);
}

}
