package com.buskify.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.buskify.entity.Project;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class StudentDao extends AbstractDao<Student> {

	public StudentDao() {
		super(Student.class);
	}

	public Student findByUsername(String username) {
		if (username == null || username.equals("")) {
			return null;
		}

		return ofy().query(Student.class).filter("username", username).get();
	}

	public List<Student> findByProject(Project project) {
		return ofy().query(Student.class).filter("assignedProject", project)
				.list();
	}

	public List<Project> getAllPreferences(Student student) {
		if (student == null) {
			return null;
		}

		Key<Project> projectKeys[] = student.getProjectPreferences();
		if (projectKeys == null  || projectKeys.length == 0) {
			return null;
		}
		Map<Key<Project>, Project> result = ofy().get(
				Arrays.asList(projectKeys));
		if (result == null) {
			return null;
		}
		List<Project> projects = new ArrayList<Project>();
		for (Project project : result.values()) {
			projects.add(project);
		}
		return projects;

	}
	
	public List<Student> findBySupervisor(Supervisor supervisor){
		Query<Student> query= ofy().query(Student.class).
				filter("assignedProject", new Key<Supervisor>(Supervisor.class, supervisor.getId()));
		return query.list();
	}
}