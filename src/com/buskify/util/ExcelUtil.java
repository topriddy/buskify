package com.buskify.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import lombok.extern.log4j.Log4j;

import com.buskify.entity.Project;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;

@Log4j
public class ExcelUtil {
	public static List<Project> extractProjects(InputStream in){
		int cols = 2;
		int rows = 0;
		Workbook workBook = null;
		List<Project> projectList = new ArrayList<Project>();
		try{
			workBook = Workbook.getWorkbook(in);
			Sheet sheet = workBook.getSheet(0);
			rows = sheet.getRows();
			for(int row = 1; row < rows; row++){
				String title = sheet.getCell(0,row).getContents();
				String maxAssignable = sheet.getCell(1,row).getContents();
				maxAssignable = (maxAssignable == null || maxAssignable.equals("") ? "1" : maxAssignable);
				Project project = new Project();
				project.setTitle(title);
				project.setMax(Integer.parseInt(maxAssignable));
				projectList.add(project);
			}
		}catch(Exception ex){
			log.error("Problem Reading Excel file", ex);
		}finally{
			workBook.close();
		}
		return projectList;
	}
	
	public static List<Supervisor> extractSupervisors(InputStream in){
		int cols = 1;
		int rows = 0;
		Workbook workBook = null;
		List<Supervisor> supervisorList = new ArrayList<Supervisor>();
		try{
			workBook = Workbook.getWorkbook(in);
			Sheet sheet = workBook.getSheet(0);
			rows = sheet.getRows();
			for(int row = 1; row < rows; row++){
				String fullName = sheet.getCell(0,row).getContents();
				
				String firstName = fullName.split(" ")[0];
				String lastName = fullName.split(" ")[1];
				String username = (firstName.substring(0,1) + lastName).toLowerCase();
				
				Supervisor supervisor = new Supervisor();
				supervisor.setFullName(fullName);
				supervisor.setUsername(username);
				supervisor.setPassword("password");
				supervisorList.add(supervisor);
			}
		}catch(Exception ex){
			log.error("Problem Reading Excel file", ex);
		}finally{
			workBook.close();
		}
		return supervisorList;
	}
	
	public static List<Student> extractStudents(InputStream in){
		int cols = 1;
		int rows = 0;
		Workbook workBook = null;
		List<Student> studentList = new ArrayList<Student>();
		try{
			workBook = Workbook.getWorkbook(in);
			Sheet sheet = workBook.getSheet(0);
			rows = sheet.getRows();
			for(int row = 1; row < rows; row++){
				String number = sheet.getCell(0,row).getContents();
				String course = sheet.getCell(1,row).getContents();
				String password = sheet.getCell(2,row).getContents();
				String stream = sheet.getCell(3, row).getContents();

				Student student = new Student();
				student.setNumber(number);
				student.setCourse(course);
				student.setUsername(number);
				student.setPassword(password);
				student.setStream(stream);
				
				studentList.add(student);
			}
		}catch(Exception ex){
			log.error("Problem Reading Excel file", ex);
		}finally{
			workBook.close();
		}
		return studentList;
	}
}
