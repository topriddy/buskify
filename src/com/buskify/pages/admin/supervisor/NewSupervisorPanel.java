package com.buskify.pages.admin.supervisor;

import lombok.extern.log4j.Log4j;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.buskify.components.MyFeedbackPanel;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Supervisor;

@Log4j
public class NewSupervisorPanel extends Panel {
	public NewSupervisorPanel(String id) {
		super(id);
		
		add(new NewSupervisorForm("form"));
	}

	private class NewSupervisorForm extends Form {
		private IModel<Supervisor> supervisorModel;

		public NewSupervisorForm(String id) {
			super(id);
			supervisorModel = new CompoundPropertyModel<Supervisor>(
					Model.of(new Supervisor()));
			setModel(supervisorModel);
			
			add(new MyFeedbackPanel("feedback", new ComponentFeedbackMessageFilter(this)));
			
			add(new FormComponentFeedbackBorder("username_border")
					.add(new TextField<String>("username").setRequired(true)));
			add(new FormComponentFeedbackBorder("password_border")
					.add(new PasswordTextField("password").setRequired(true)));
			add(new FormComponentFeedbackBorder("fullName_border")
					.add(new TextField<String>("fullName").setRequired(true)));
			
			add(new Button("saveAndNew"){
				@Override
				public void onError() {
					log.error("Error creating project");
					NewSupervisorForm.this.error("Error saving project.");
				}

				@Override
				public void onSubmit() {
					SupervisorDao supervisorDao = new SupervisorDao();
					Supervisor supervisor = supervisorModel.getObject();
					
					log.debug("Submitted Successfully");
					log.debug(supervisor);
					
					supervisorDao.save(supervisor);
					log.debug("Supervisor Saved Successfully");
					NewSupervisorForm.this.info("Supervisor Saved Successfully.");
					
					supervisorModel = new CompoundPropertyModel<Supervisor>(
							Model.of(new Supervisor()));
					NewSupervisorForm.this.setModel(supervisorModel);
				}
			});
		}

	}
}
