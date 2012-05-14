package com.buskify.util;

import java.util.logging.Logger;

import com.buskify.dao.AdminDao;
import com.buskify.dao.ProjectDao;
import com.buskify.dao.StudentDao;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Admin;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;

public class DataInitialiser {
	private static final Logger log = Logger.getLogger(DataInitialiser.class
			.getName());
	private final String[] supervisors = { "Victor Khan", "Maggie Striesand",
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
		// deleteAll();

		initAdminWithDefaultData();
		initStudentWithDefaultData();
		initSupervisorWithDefaultData();
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

}