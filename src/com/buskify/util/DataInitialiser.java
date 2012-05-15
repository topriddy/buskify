package com.buskify.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.buskify.dao.AdminDao;
import com.buskify.dao.ProjectDao;
import com.buskify.dao.StudentDao;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Admin;
import com.buskify.entity.Project;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;

public class DataInitialiser {
	private static final Logger log = Logger.getLogger(DataInitialiser.class
			.getName());
	private static final String[] supervisors = { "Victor Khan", "Maggie Striesand",
			"James Watson", "William Bennet", "Phil Newton", "Jeremiah Alfie",
			"Mamoud Kamara", "Abiodun Davids", "Charles Walter",
			"Paul Reichard", "Peter Jacobs", "David Nkume", "John Lewis",
			"Sinan Xavi", "Tommy Brown", "Eric Johnson", "Joseph Yousef",
			"Jackie Taylor" };

	// static{
	// initWithDefaultData();
	// }

	public static void initWithDefaultData() {
		log.info("Data Initializer.....");
		deleteAll();

		initAdminWithDefaultData();
		initStudentWithDefaultData();
		initSupervisorWithDefaultData();
		
//		loadSupervisorsToDBVariant();
		loadSupervisorsToDB();
		loadStudentsToDB();
		loadProjectsToDB();
	}

	private static void deleteAll() {
		new StudentDao().deleteAll();
		new ProjectDao().deleteAll();
		new SupervisorDao().deleteAll();
	}

	private static void initAdminWithDefaultData() {
		AdminDao adminDao = new AdminDao();
		Admin admin = new Admin("admin", "password");
		if (adminDao.findByUsername(admin.getUsername()) == null) {
			log.info("Admin Does Not Already Exists...now attempting to create");
			adminDao.save(admin);
			log.info("Admin Saved Successfully");
		} else {
			log.info("Admin already Exists...not recreating");
		}
	}

	private static void initStudentWithDefaultData() {
		StudentDao studentDao = new StudentDao();
		Student student = new Student("student", "password");
		student.setFullName("Default User");
		student.setNumber("DEFAULT_USER");
		student.setCourse("DEFAULT_USER");
		student.setStream("DEFAULT_USER");
		
		if (studentDao.findByUsername(student.getUsername()) == null) {
			log.info("Student Does Not Already Exists...now attempting to create");
			studentDao.save(student);
			log.info("Student Saved Successfully");
		} else {
			log.info("Student Already Exists...not recreating");
		}
	}

	private static void initSupervisorWithDefaultData() {
		SupervisorDao supervisorDao = new SupervisorDao();
		Supervisor supervisor = new Supervisor("supervisor", "password");
		if (supervisorDao.findByUsername(supervisor.getUsername()) == null) {
			log.info("Supervisor Does Not Already Exists...now attempting to create");
			supervisorDao.save(supervisor);
			log.info("Supervisor Saved Successfully");
		} else {
			log.info("Supervisor Already Exists...not recreating");
		}
	}

	private static void loadProjectsToDB(){
		InputStream in = DataInitialiser.class.getResourceAsStream("project_upload_sample.xls");
		ProjectDao projectDao = new ProjectDao();
//		projectDao.deleteAll();
		
		List<Project> projectList = ExcelUtil.extractProjects(in);
		projectDao.saveAll(projectList);
		log.info("Loaded All Projects Successfully");
	}
	
	private static void loadSupervisorsToDB(){
		InputStream in = DataInitialiser.class.getResourceAsStream("supervisor_upload_sample.xls");
		SupervisorDao supervisorDao = new SupervisorDao();
//		supervisorDao.deleteAll();
		
		List<Supervisor> supervisorList = ExcelUtil.extractSupervisors(in);
		supervisorDao.saveAll(supervisorList);
		log.info("Loaded All Supervisors Successfully");
	}
	
	private static void loadSupervisorsToDBVariant(){
		SupervisorDao supervisorDao = new SupervisorDao();
//		supervisorDao.deleteAll();
		
		List<Supervisor> supervisorList = getSupervisorList(supervisors);
		supervisorDao.saveAll(supervisorList);
		log.info("Loaded All Supervisors Successfully");
	}
	
	private static void loadStudentsToDB(){
		InputStream in = DataInitialiser.class.getResourceAsStream("student_upload_sample.xls");
		StudentDao studentDao = new StudentDao();
//		studentDao.deleteAll();
		
		List<Student> studentList = ExcelUtil.extractStudents(in);
		studentDao.saveAll(studentList);
		log.info("Loaded All Students Successfully");
	}
	
	private static List<Supervisor> getSupervisorList(String[] supervisorFullNames){
		List<Supervisor>supervisorList = new ArrayList<Supervisor>();
		for(String fullName: supervisorFullNames){

			String firstName = fullName.split(" ")[0];
			String lastName = fullName.split(" ")[1];
			String username = (firstName.substring(0, 1) + lastName)
					.toLowerCase();

			Supervisor supervisor = new Supervisor();
			supervisor.setFullName(fullName.trim());
			supervisor.setUsername(username);
			supervisor.setPassword("password");

			supervisorList.add(supervisor);
		}
		return supervisorList;
	}
}