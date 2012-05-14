package com.buskify.pages.admin.project;

import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.buskify.components.MyFeedbackPanel;
import com.buskify.dao.ProjectDao;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Project;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;
import com.googlecode.objectify.Key;

@Log4j
public class NewProjectPanel extends Panel {
	public NewProjectPanel(String id) {
		super(id);
		add(new NewProjectForm("form"));
	}

	Key<Student> y;

	private class NewProjectForm extends Form {
		private Model<Project> projectModel;
		private List<Supervisor> supervisorList;
		private Supervisor supervisor;

		public NewProjectForm(String id) {
			super(id);
			projectModel = Model.of(new Project());
			supervisorList = new SupervisorDao().findAll();

			setModel(new CompoundPropertyModel<Project>(projectModel));
			add(new MyFeedbackPanel("feedback",
					new ComponentFeedbackMessageFilter(this)));
			add(new FormComponentFeedbackBorder("title_border")
					.add(new TextField<String>("title").setRequired(true)));
			add(new FormComponentFeedbackBorder("max_border")
					.add(new TextField<Integer>("max").setRequired(true)));
			add(new FormComponentFeedbackBorder("description_border")
					.add(new TextArea<String>("description").setRequired(false)));

			ChoiceRenderer supervisorCR = new ChoiceRenderer("fullName", "id");
			final DropDownChoice<Supervisor> supervisorChoice = new DropDownChoice<Supervisor>(
					"supervisor", new PropertyModel(this, "supervisor"),
					supervisorList, supervisorCR);
			supervisorChoice.setNullValid(true);
			add(supervisorChoice);

			add(new Button("saveAndNew") {

				@Override
				public void onError() {
					log.error("Error creating project");
					NewProjectForm.this.error("Error saving project.");
				}

				@Override
				public void onSubmit() {
					ProjectDao projectDao = new ProjectDao();
					Project project = projectModel.getObject();
					if (supervisor != null) {
						Key<Supervisor> supervisorKey = new Key<Supervisor>(
								Supervisor.class, supervisor.getId());
						project.setSupervisor(supervisorKey);
					}
					

					log.debug("Submitted Successfully");
					log.debug(project);

					projectDao.save(project);
					log.debug("Project Saved Successfully");
					NewProjectForm.this.info("Project Saved Successfully.");
					projectModel = Model.of(new Project());

					NewProjectForm.this
							.setModel(new CompoundPropertyModel<Project>(
									projectModel));
					supervisor = null;
				}
			});
		}
	}
}
