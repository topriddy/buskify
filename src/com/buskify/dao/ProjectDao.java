package com.buskify.dao;

import java.util.ArrayList;
import java.util.List;

import com.buskify.entity.Project;
import com.buskify.entity.Supervisor;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class ProjectDao extends AbstractDao<Project> {
	public ProjectDao() {
		super(Project.class);
	}

	public List<Project> findBySupervisor(Supervisor supervisor) {
		List<Project> projectList = null;
		if (supervisor == null) {
			projectList = new ArrayList<Project>();
		} else {
			Query<Project> queryProject = ofy().query(Project.class).filter(
					"supervisor",
					new Key<Supervisor>(Supervisor.class, supervisor.getId()));
			projectList = queryProject.list();
		}
		return projectList;
	}
}
