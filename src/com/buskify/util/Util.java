package com.buskify.util;

import java.util.List;

import lombok.extern.log4j.Log4j;

import com.buskify.dao.ProjectDao;
import com.buskify.dao.StudentDao;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Student;

@Log4j
public class Util {
	
	public static void resetAndInitializeSystemWithDB(){
		resetAllocation();
		resetSystem();
		initializeSystemWithDB();
	}

	public static void initializeSystemWithDB(){
		DataInitialiser.loadSupervisorsToDBVariant();
		DataInitialiser.loadStudentsToDB();
		DataInitialiser.loadProjectsToDB();
		
		DataInitialiser.initAdminWithDefaultData();
		DataInitialiser.initStudentWithDefaultData();
		DataInitialiser.initSupervisorWithDefaultData();
	}
	
	public static void resetSystem() {
		log.debug("Resetting System");
		log.debug("Deleting All Data....");
		
		log.debug("Deleting all Projects");
		new ProjectDao().deleteAll();
		log.debug("Done, deleting all Projects");
		
		log.debug("Deleting all Students...");
		new StudentDao().deleteAllExemptDefaultStudent();
		log.debug("Done, deleting all Students");
		
		log.debug("Deleting all Supervisors...");
		new SupervisorDao().deleteAllExemptDefaultSupervisor();
		log.debug("Done, deleting all Supervisors");
	}

	public static void resetAllocation() {
		log.debug("Resetting Allocation");
		StudentDao studentDao = new StudentDao();
		List<Student> studentList = studentDao.findAll();
		for(Student student: studentList){
			student.setAssignedProject(null);
			studentDao.save(student);
		}
		
		log.debug("Reset Allocation Successfully");
	}
}