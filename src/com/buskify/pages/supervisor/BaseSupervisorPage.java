package com.buskify.pages.supervisor;

import org.apache.wicket.markup.html.WebPage;

import com.buskify.components.SignOutLink;

public class BaseSupervisorPage extends WebPage{
	public BaseSupervisorPage(){
		add(new SignOutLink("signOut"));
	}
	
}
