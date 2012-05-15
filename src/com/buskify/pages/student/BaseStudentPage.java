package com.buskify.pages.student;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import com.buskify.UserSession;
import com.buskify.components.SignOutLink;
import com.buskify.entity.AppUser;
import com.buskify.security.StudentSecurePage;

public class BaseStudentPage extends WebPage implements StudentSecurePage{
	public BaseStudentPage(){
		add(new SignOutLink("signOut"));
		
		AppUser appUser = UserSession.get().getAppUser();
		add(new Label("username", Model.of(appUser.getUsername())));
	}
}
