package com.buskify.pages.student;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.buskify.UserSession;
import com.buskify.dao.ProjectDao;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Project;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;

public class StudentProfilePanel extends Panel {
	public StudentProfilePanel(String id){
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		final Student student = (Student) UserSession.get().getAppUser();
		WebMarkupContainer emptyMessageContainer = new WebMarkupContainer("emptyProjectMessage"){

			@Override
			protected void onConfigure() {
				super.onConfigure();
				if(student.getAssignedProject() == null){
					setVisible(true);
				}else{
					setVisible(false);
				}
			}
			
		}; 
		emptyMessageContainer.setOutputMarkupId(true);
		emptyMessageContainer.setOutputMarkupPlaceholderTag(true);
		add(emptyMessageContainer);
		
		WebMarkupContainer projectDetailsContainer = new WebMarkupContainer("projectDetails"){

			@Override
			protected void onConfigure() {
				super.onConfigure();
				if(student.getAssignedProject() == null){
					setVisible(false);
				}else{
					setVisible(true);
				}
			}
		};
		projectDetailsContainer.setOutputMarkupId(true);
		projectDetailsContainer.setOutputMarkupPlaceholderTag(true);
		add(projectDetailsContainer);
		
		if(student.getAssignedProject()!= null){
			Project project = new ProjectDao().load(student.getAssignedProject().getId());
			Supervisor supervisor = null;
			if(project.getSupervisor() != null){
				supervisor = new SupervisorDao().load(project.getSupervisor().getId());
			}
			projectDetailsContainer.add(new Label("projectTitle", Model.of(project.getTitle())));
			projectDetailsContainer.add(new Label("supervisor", Model.of(supervisor == null? "N/A": supervisor.getFullName())));
		}else{
			projectDetailsContainer.add(new Label("projectTitle", Model.of("N/A")));
			projectDetailsContainer.add(new Label("supervisor", Model.of("N/A")));
		}
	}
	
}
