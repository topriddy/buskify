package com.buskify.pages.admin;

import org.apache.wicket.markup.html.WebPage;

import com.buskify.components.SignOutLink;

public class BaseAdminPage extends WebPage {
	public BaseAdminPage(){
		add(new SignOutLink("signOut"));
	}
}
