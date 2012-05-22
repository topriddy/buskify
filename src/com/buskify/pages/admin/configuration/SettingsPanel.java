package com.buskify.pages.admin.configuration;

import lombok.Data;
import lombok.extern.log4j.Log4j;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.buskify.components.ConfirmationLink;
import com.buskify.components.MyFeedbackPanel;
import com.buskify.dao.SettingsDao;
import com.buskify.entity.Settings;
import com.buskify.util.Util;

@Log4j
public class SettingsPanel extends Panel {
	public SettingsPanel(String id) {
		super(id);

		FeedbackPanel feedback = new MyFeedbackPanel("feedback",
				new ComponentFeedbackMessageFilter(this));
		add(feedback);

		add(new ConfirmationLink("initializeLink",
				"Are you sure you want to perform this irreversible action") {
			@Override
			public void onClick() {
				System.out
						.println("Beginning to Reset and re-initialize system");
				Util.resetAndInitializeSystemWithDB();
				SettingsPanel.this.info("Sucess!! Completed Initialization");
			}
		});

		add(new SupervisorSettingsForm("supervisorSettingsform"));
	}

	@Data
	private class SupervisorSettingsForm extends Form {
		private int maxNosOfCourses;

		public SupervisorSettingsForm(String id) {
			super(id);
			
			SettingsDao settingsDao = new SettingsDao();
			Settings settings = settingsDao.findByName("maxAssignableStudents");
			if (settings != null) {
				maxNosOfCourses = Integer.parseInt(settings.getValue());
			}
			
			add(new MyFeedbackPanel("feedback", new ComponentFeedbackMessageFilter(this)));
			add(new TextField("maxNosOfCourses", new PropertyModel<Integer>(
					this, "maxNosOfCourses")));
			add(new Button("save") {
				@Override
				public void onError() {
					SupervisorSettingsForm.this.error("Problem occured saving data");
				}

				@Override
				public void onSubmit() {
					SettingsDao settingsDao = new SettingsDao();
					Settings settings = settingsDao.findByName("maxAssignableStudents");
					if (settings != null) {
						settings.setValue(String.valueOf(maxNosOfCourses));
						settingsDao.save(settings);
						SupervisorSettingsForm.this.info("Changes Saved Successfully");
					}
				}
			});
			add(new Button("cancel") {
				@Override
				public void onError() {
					
				}

				@Override
				public void onSubmit() {
					SettingsDao settingsDao = new SettingsDao();
					Settings settings = settingsDao.findByName("maxAssignableStudents");
					if (settings != null) {
						maxNosOfCourses = Integer.parseInt(settings.getValue());
					}
				}
			});
		}
	}
}
