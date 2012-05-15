package com.buskify.pages.supervisor;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import com.buskify.UserSession;
import com.buskify.components.SignOutLink;
import com.buskify.entity.AppUser;
import com.buskify.security.SupervisorSecurePage;

public class BaseSupervisorPage extends WebPage implements SupervisorSecurePage{
	public BaseSupervisorPage(){
		add(new SignOutLink("signOut"));
		AppUser appUser = UserSession.get().getAppUser();
		add(new Label("username", Model.of(appUser.getUsername())));
	}
	
}
