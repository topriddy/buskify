package com.buskify.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import lombok.extern.log4j.Log4j;

import com.buskify.entity.Project;

@Log4j
public class ExcelUtil {
	public static List<Project> extractProjects(InputStream in){
		int cols = 2;
		int rows = 0;
		Workbook workBook = null;
		List<Project> projectList = new ArrayList<Project>();
//		try{
//			workBook = Workbook.getWorkbook(in);
//			Sheet sheet = workBook.getSheet(0);
//			rows = sheet.getRows();
//			for(int row = 1; row < rows; row++){
//				String title = sheet.getCell(0,row).getContents();
//				String maxAssignable = sheet.getCell(0,row).getContents();
//				maxAssignable = (maxAssignable == null || maxAssignable.equals("") ? "0" : maxAssignable);
//				Project project = new Project();
//				project.setTitle(title);
//				project.setMax(Integer.parseInt(maxAssignable));
//				projectList.add(project);
//			}
//		}catch(Exception ex){
//			log.error("Problem Reading Excel file", ex);
//		}finally{
//			workBook.close();
//		}
		return projectList;
	}
}
