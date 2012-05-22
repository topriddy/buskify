package com.buskify.allocation.algo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.extern.log4j.Log4j;

import com.buskify.dao.ProjectDao;
import com.buskify.dao.SettingsDao;
import com.buskify.dao.StudentDao;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Project;
import com.buskify.entity.Settings;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;
import com.googlecode.objectify.Key;

@Log4j
@Data
public class SMAlgorithm implements Serializable {
	transient private StudentDao studentDao = new StudentDao();
	transient private ProjectDao projectDao = new ProjectDao();
	transient private SupervisorDao supervisorDao = new SupervisorDao();
	transient private SettingsDao settingsDao = new SettingsDao();
	transient private int maxAssignableStudents = 0;

	private List<Student> students;
	private List<Project> projects;
	private Map<Long, Project> projectMap;
	private Map<Long, List<Project>> studentProjectPrefMap;
	private Long startTime, endTime;
	private Long timeTaken;
	private int iterations = 0;

	public void doWork() {
		log.debug("Starting allocation algorithm");
		startTime = System.currentTimeMillis();
		init();
		iterations = 0;
		for (int i = 0; i < projects.size(); i++) {
			iterations = (i + 1);
			log.debug("Iteration: " + iterations);
			// stop loop if all student have been assigned
			if (checkIfAllStudentIsAssigned()) {
				break;
			}
			for (Student student : students) {

				// check if student already assigned a project
				// in that case skip student
				if (student.getAssignedProject() != null) {
					continue;
				}

				// look up ProjectChoice
				// the projectChoice might not be same in the map memory,hence
				// additional lookup
				Project projectChoice = studentProjectPrefMap.get(
						student.getId()).get(i);

				Project project = projectMap.get(projectChoice.getId());

				Key<Supervisor> supervisorKey = project.getSupervisor();
				Supervisor supervisor = null;
				if (supervisorKey != null) {
					supervisor = supervisorDao.load(supervisorKey.getId());
				}

				// check if supervisor limit for particular project has been
				// exceeded
				if (supervisor != null) {
					if (maxAssignableStudents != 0
							&& supervisor.getAssignedCount() >= maxAssignableStudents) {
						continue;
					}
				}
				// check if project assigned limit hasnt been reached and then
				// assign
				if (project.getAssignedCount() < project.getMax()) {
					student.setAssignedProject(new Key<Project>(Project.class,
							project.getId()));
					project.setAssignedCount(project.getAssignedCount() + 1);
					if(supervisor != null){
						supervisor.setAssignedCount(supervisor.getAssignedCount() + 1);
					}
				}
			}
		}
		endTime = System.currentTimeMillis();
		timeTaken = endTime - startTime;
		log.debug("Ending allocation algorithm. Time Taken(ms): " + timeTaken);
	}

	/*
	 * neccessary initializations
	 */
	private void init() {
		log.debug("Performing Initialization...");
		students = studentDao.findAll();
		projects = projectDao.findAll();
		studentProjectPrefMap = new HashMap<Long, List<Project>>();

		projectMap = new HashMap<Long, Project>();
		// build projectMap for later use
		for (Project project : projects) {
			projectMap.put(project.getId(), project);
		}
		normalizePreferences(students);

		// get and set the max nos of assignable courses to a supervisor
		Settings settings = settingsDao.findByName("maxAssignableStudents");
		if (settings != null) {
			try {
				maxAssignableStudents = Integer.parseInt(settings.getValue());
			} catch (Exception ex) {
				log.debug(
						"Exception thrown trying to parse maxAssignableStudents",
						ex);
			}
		}
	}

	/*
	 * appends the remaining projects to student list. neccessary for stable
	 * marriage also it sets student assigned project to null. (i.e deallocates
	 * all student)
	 */
	private void normalizePreferences(List<Student> students) {
		for (Student student : students) {
			normalizePreference(student);
		}
	}

	/*
	 * normalizes the preference for a student
	 */
	private void normalizePreference(Student student) {
		student.setAssignedProject(null);
		List<Project> choiceProject = studentDao.getAllPreferences(student);
		if (choiceProject == null) {
			choiceProject = new ArrayList<Project>();
		}
		for (Project project : projects) {
			if (!choiceProject.contains(project)) {
				choiceProject.add(project);
			}
		}
		// store in map
		studentProjectPrefMap.put(student.getId(), choiceProject);

		Key<Project> projectKeys[] = new Key[choiceProject.size()];
		for (int i = 0; i < choiceProject.size(); i++) {
			projectKeys[i] = new Key<Project>(Project.class, choiceProject.get(
					i).getId());
		}
		student.setProjectPreferences(projectKeys);

	}

	public boolean checkIfAllStudentIsAssigned() {
		for (Student student : students) {
			if (student.getAssignedProject() == null) {
				return false;
			}
		}
		return true;
	}
}
