package com.buskify.pages;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import com.buskify.UserSession;
import com.buskify.dao.AdminDao;
import com.buskify.dao.StudentDao;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Admin;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;
import com.buskify.pages.admin.AdminWelcomePage;
import com.buskify.pages.student.StudentWelcomePage;
import com.buskify.pages.supervisor.SupervisorWelcomePage;

public class SignInPage extends WebPage {
	private static final Logger log = Logger.getLogger(SignInPage.class
			.getName());
	private final String[] roles = new String[] { "Administrator",
			"Supervisor", "Student" };

	public SignInPage() {
		log.debug("SignIn Page here :D");
		add(new SignInForm("form"));
	}

	private class SignInForm extends Form {
		private String username;
		private String password;
		private String role;

		public SignInForm(String id) {
			super(id);

			setModel(new CompoundPropertyModel(this));
			add(new TextField("username"));
			add(new PasswordTextField("password"));
			add(new DropDownChoice<String>("role", Arrays.asList(roles)));

			add(new Button("signIn") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 2902692991719403280L;

				@Override
				public void onError() {
					error("Fill all required fields");
					log.debug("Error Submitting Form");
					System.out.println("Submitting Form...");
				}

				@Override
				public void onSubmit() {
					System.out.println("Submitting Form...");
					log.debug("Submitting Form...");
					log.debug("Username: " + username);
					log.debug("Password: " + password);
					log.debug("Role: " + role);
					doSignIn(username, password, role);
				}
			});
		}

		private void doSignIn(String username, String password, String role){
			if(role.equalsIgnoreCase(roles[0])){
				doSignInAsAdministrator(username, password);
			}else if(role.equalsIgnoreCase(roles[1])){
				doSignInAsSupervisor(username,password);
			}else if(role.equalsIgnoreCase(roles[2])){
				doSignInAsStudent(username, password);
			}else{
				error("Select a role");
			}
		}

		private void doSignInAsAdministrator(String username, String password) {
			AdminDao adminDao = new AdminDao();
			Admin admin = null;
			admin = adminDao.findByUsername(username);
			if (admin != null && admin.getPassword().equals(password)) {
				signInAsAdmin(admin);
			}
		}
		
		private void signInAsAdmin(Admin admin){
			UserSession.get().bind();
            UserSession.get().setAppUser(admin);
            setResponsePage(AdminWelcomePage.class);
		}
		
		private void doSignInAsSupervisor(String username, String password) {
			SupervisorDao supervisorDao = new SupervisorDao();
			Supervisor supervisor = null;
			supervisor = supervisorDao.findByUsername(username);
			if (supervisor != null && supervisor.getPassword().equals(password)) {
				signInAsSupervisor(supervisor);
			}
		}
		
		private void signInAsSupervisor(Supervisor supervisor){
			UserSession.get().bind();
            UserSession.get().setAppUser(supervisor);
            setResponsePage(SupervisorWelcomePage.class);
		}
		
		private void doSignInAsStudent(String username, String password) {
			StudentDao studentDao = new StudentDao();
			Student student = null;
			student = studentDao.findByUsername(username);
			if (student != null && student.getPassword().equals(password)) {
				signInAsStudent(student);
			}
		}
		
		private void signInAsStudent(Student student){
			UserSession.get().bind();
            UserSession.get().setAppUser(student);
            setResponsePage(StudentWelcomePage.class);
		}
		
		//getters and setters
		
		
		
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
	}
}