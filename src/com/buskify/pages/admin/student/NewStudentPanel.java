package com.buskify.pages.admin.student;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.buskify.components.MyFeedbackPanel;
import com.buskify.dao.StudentDao;
import com.buskify.entity.Project;
import com.buskify.entity.Student;

@Log4j
public class NewStudentPanel extends Panel {
	public NewStudentPanel(String id) {
		super(id);
		add(new NewStudentForm("form"));
	}

	private class NewStudentForm extends Form {
		private final List<String> courseList = Arrays.asList(
				"Communications Network Planning and Management",
				"Communications System Engineering",
				"Communication Network Planning and Management",
				"Communications Network Administration and Management");
		private final List<String> streamList = Arrays.asList("September",
				"January");
		private IModel<Student> studentModel;
		@Getter @Setter private  String course;
		@Getter @Setter private  String stream;

		public NewStudentForm(String id) {
			super(id);
			studentModel = new CompoundPropertyModel<Student>(
					Model.of(new Student()));
			setModel(studentModel);

			add(new MyFeedbackPanel("feedback", new ComponentFeedbackMessageFilter(this)));
			add(new FormComponentFeedbackBorder("number_border")
					.add(new TextField<String>("number").setRequired(true)));
			add(new FormComponentFeedbackBorder("username_border")
					.add(new TextField<String>("username").setRequired(true)));
			add(new FormComponentFeedbackBorder("password_border")
					.add(new PasswordTextField("password").setRequired(true)));
			add(new FormComponentFeedbackBorder("fullName_border")
					.add(new TextField<String>("fullName").setRequired(true)));

			add(new FormComponentFeedbackBorder("course_border")
					.add(new DropDownChoice("course",
							new PropertyModel<String>(this, "course"),
							courseList).setRequired(true)));
			add(new FormComponentFeedbackBorder("stream_border")
			.add(new DropDownChoice("stream",
					new PropertyModel<String>(this, "stream"),
					streamList).setRequired(true)));
			add(new Button("saveAndNew"){
				@Override
				public void onError() {
					log.error("Error creating project");
					NewStudentForm.this.error("Error saving project.");
				}

				@Override
				public void onSubmit() {
					StudentDao studentDao = new StudentDao();
					Student student = studentModel.getObject();
					student.setStream(stream);
					student.setCourse(course);
					
					log.debug("Submitted Successfully");
					log.debug(student);
					
					studentDao.save(student);
					log.debug("Student Saved Successfully");
					NewStudentForm.this.info("Student Saved Successfully.");
					
					studentModel = new CompoundPropertyModel<Student>(
							Model.of(new Student()));
					NewStudentForm.this.setModel(studentModel);
					course = stream = null;
				}
			});
		}
	}
}