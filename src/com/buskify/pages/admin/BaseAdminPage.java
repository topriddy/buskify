package com.buskify.pages.admin;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;

import com.buskify.UserSession;
import com.buskify.components.SignOutLink;
import com.buskify.entity.AppUser;
import com.buskify.pages.admin.configuration.ConfigurationPage;
import com.buskify.pages.admin.project.ProjectManagementPage;
import com.buskify.pages.admin.student.StudentManagementPage;
import com.buskify.pages.admin.supervisor.SupervisorManagementPage;
import com.buskify.security.AdminSecurePage;

public class BaseAdminPage extends WebPage implements AdminSecurePage{
	public BaseAdminPage(){
		add(new SignOutLink("signOut"));
		AppUser appUser = UserSession.get().getAppUser();
		add(new Label("username", Model.of(appUser.getUsername())));
		
		add(new BookmarkablePageLink<Page>("manageProjects", ProjectManagementPage.class));
		add(new BookmarkablePageLink<Page>("manageStudents", StudentManagementPage.class));
		add(new BookmarkablePageLink<Page>("manageSupervisors", SupervisorManagementPage.class));
		add(new BookmarkablePageLink<Page>("configuration", ConfigurationPage.class));
	}
}
