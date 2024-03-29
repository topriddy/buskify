package com.buskify.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.buskify.dao.AdminDao;
import com.buskify.dao.ProjectDao;
import com.buskify.dao.SettingsDao;
import com.buskify.dao.StudentDao;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Admin;
import com.buskify.entity.Project;
import com.buskify.entity.Settings;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;

public class DataInitialiser {
	private static final Logger log = Logger.getLogger(DataInitialiser.class
			.getName());
	private static final String[] supervisors = { "Victor Khan",
			"Maggie Striesand", "James Watson", "William Bennet",
			"Phil Newton", "Jeremiah Alfie", "Mamoud Kamara", "Abiodun Davids",
			"Charles Walter", "Paul Reichard", "Peter Jacobs", "David Nkume",
			"John Lewis", "Sinan Xavi", "Tommy Brown", "Eric Johnson",
			"Joseph Yousef", "Jackie Taylor" };

	public static void initWithDefaultData() {
		log.info("Data Initializer.....");

		initAdminWithDefaultData();
		initStudentWithDefaultData();
		initSupervisorWithDefaultData();
		initSettingsWithDefaultData();
	}

	private static void deleteAll() {
		new SupervisorDao().deleteAllExemptDefaultSupervisor();
		new StudentDao().deleteAllExemptDefaultStudent();
		new ProjectDao().deleteAll();
		new SettingsDao().deleteAll();
	}

	public static void initAdminWithDefaultData() {
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

	public static void initStudentWithDefaultData() {
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

	public static void initSupervisorWithDefaultData() {
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

	public static void initSettingsWithDefaultData() {
		SettingsDao settingsDao = new SettingsDao();
		Settings settings = new Settings(null, "maxAssignableStudents", "5");
		if (settingsDao.findByName(settings.getName()) == null) {
			log.info("Settings Does Not Already Exists...now attempting to create");
			settingsDao.save(settings);
			log.info("Settings Saved Successfully");
		} else {
			log.info("Settings Already Exists...not recreating");
		}
	}

	public static void loadProjectsToDB() {
		InputStream in = DataInitialiser.class
				.getResourceAsStream("project_upload_sample.xls");
		ProjectDao projectDao = new ProjectDao();
		// projectDao.deleteAll();

		List<Project> projectList = ExcelUtil.extractProjects(in);
		projectDao.saveAll(projectList);
		log.info("Loaded All Projects Successfully");
	}

	private static void loadSupervisorsToDB() {
		InputStream in = DataInitialiser.class
				.getResourceAsStream("supervisor_upload_sample.xls");
		SupervisorDao supervisorDao = new SupervisorDao();

		List<Supervisor> supervisorList = ExcelUtil.extractSupervisors(in);
		supervisorDao.saveAll(supervisorList);
		log.info("Loaded All Supervisors Successfully");
	}

	public static void loadSupervisorsToDBVariant() {
		SupervisorDao supervisorDao = new SupervisorDao();

		List<Supervisor> supervisorList = getSupervisorList(supervisors);
		supervisorDao.saveAll(supervisorList);
		log.info("Loaded All Supervisors Successfully");
	}

	public static void loadStudentsToDB() {
		InputStream in = DataInitialiser.class
				.getResourceAsStream("student_upload_sample.xls");
		StudentDao studentDao = new StudentDao();

		List<Student> studentList = ExcelUtil.extractStudents(in);
		studentDao.saveAll(studentList);
		log.info("Loaded All Students Successfully");
	}

	private static List<Supervisor> getSupervisorList(
			String[] supervisorFullNames) {
		List<Supervisor> supervisorList = new ArrayList<Supervisor>();
		for (String fullName : supervisorFullNames) {

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