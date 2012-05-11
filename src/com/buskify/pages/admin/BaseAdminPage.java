package com.buskify.pages.admin;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import com.buskify.components.SignOutLink;
import com.buskify.security.AdminSecurePage;

public class BaseAdminPage extends WebPage implements AdminSecurePage{
	public BaseAdminPage(){
		add(new SignOutLink("signOut"));
		
		add(new BookmarkablePageLink<Page>("manageProjects", ProjectManagementPage.class));
		add(new BookmarkablePageLink<Page>("manageStudents", StudentManagementPage.class));
		add(new BookmarkablePageLink<Page>("manageSupervisors", SupervisorManagementPage.class));
	}
}
