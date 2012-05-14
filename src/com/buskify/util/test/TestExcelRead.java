package com.buskify.util.test;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;
import lombok.extern.log4j.Log4j;

import org.apache.log4j.BasicConfigurator;

@Log4j
public class TestExcelRead {
	public static void main(String args[]){
		BasicConfigurator.configure();
		processProjectFile();
		
	}
	
	private static void processProjectFile(){
		try{
			Workbook workBook = Workbook.getWorkbook(getFile("project_upload_sample.xls"));
			Sheet sheet = workBook.getSheet(0);
			int rows = sheet.getRows();
			int cols = 2;
			log.info("Number of Rows: " + rows);
			for(int row = 0; row < rows; row++){
				System.out.print("\n");
				for(int col = 0; col < cols; col++){
					String val = sheet.getCell(col, row).getContents().trim();
					val = (val == null || val.equals("") ? "0" : val);
					System.out.print(val);
					System.out.print("\t\t\t");
				}
			}
			workBook.close();
		}catch(Exception ex){
			log.error(ex);
		}
	}
	private static File getFile(String name){
		File file = new File(TestExcelRead.class.getResource(name).getFile());
		return file;
	}
}