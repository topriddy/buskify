package com.buskify.pages.student;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.model.util.ListModel;

import com.buskify.UserSession;
import com.buskify.components.AlertPanel;
import com.buskify.components.AlertPanel.AlertType;
import com.buskify.dao.ProjectDao;
import com.buskify.dao.StudentDao;
import com.buskify.entity.Project;
import com.buskify.entity.Student;
import com.googlecode.objectify.Key;

@Log4j
public class ProjectSelectionPanel extends Panel {
	private AlertPanel alertPanel;
	public ProjectSelectionPanel(String id) {
		super(id);
		
		add(alertPanel = new AlertPanel("alertPanel", "Select Project Preference in desired Ordered", AlertType.INFO, true));
		add(new ProjectPreferenceForm("form"));
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
	}

	private class ProjectPreferenceForm extends Form {
		private ListModel<Project> preferenceModel;
		private CollectionModel<Project> projectsModel;

		public ProjectPreferenceForm(String id) {
			// palette.getDefaultModelObjectAsString());
			super(id);
			StudentDao studentDao = new StudentDao();
			
			List<Project> projects = new ProjectDao().findAll();
			final Student student = (Student) UserSession.get().getAppUser();
			List<Project> studentProjPrefs = studentDao
					.getAllPreferences(student);
			
			if (studentProjPrefs != null && !studentProjPrefs.isEmpty()) {
				log.debug("Total Saved Student Project Preference : " + studentProjPrefs.size());
				
				preferenceModel = new ListModel<Project>(studentProjPrefs);
			} else {
				log.debug("Empty Student Project Preference, setting to blank");
				preferenceModel = new ListModel<Project>(
						new ArrayList<Project>());
			}
			projectsModel = new CollectionModel<Project>(projects);

			IChoiceRenderer<Project> renderer = new ChoiceRenderer<Project>(
					"title", "id");

			final Palette<Project> palette = new Palette<Project>("palette",
					preferenceModel, projectsModel, renderer, 25, true);
			add(palette);

			add(new Button("savePreference") {

				@Override
				public void onError() {
					super.onError();
					log.error("Error submitting form");
					alertPanel.setAlertMessage("Problem occured while trying to save preference, contact site administrator");
				}

				@Override
				public void onSubmit() {
					StudentDao studentDao = new StudentDao();
					super.onSubmit();
					log.info("Form was submitted successfully");
					List<Project> selectedProjects = preferenceModel
							.getObject();
					// build Key List , convert to array and try to save
					Key<Project>[] projectKeys = new Key[selectedProjects
							.size()];
					for (int i = 0; i < projectKeys.length; i++) {
						projectKeys[i] = new Key<Project>(Project.class,
								selectedProjects.get(i).getId());
					}
					student.setProjectPreferences(projectKeys);
					studentDao.save(student);
					log.info("Saved Successfully");
					alertPanel.setAlertMessage("Saved Project Preferences Successfully");
					// log.info(palette.getDefaultModelObjectAsString());
				}

			});
		}

	}
}
