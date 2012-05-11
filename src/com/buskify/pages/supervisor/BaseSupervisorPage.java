package com.buskify.pages.supervisor;

import org.apache.wicket.markup.html.WebPage;

import com.buskify.components.SignOutLink;
import com.buskify.security.SupervisorSecurePage;

public class BaseSupervisorPage extends WebPage implements SupervisorSecurePage{
	public BaseSupervisorPage(){
		add(new SignOutLink("signOut"));
	}
	
}
