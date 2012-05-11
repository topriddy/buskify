package com.buskify.pages.admin;

import org.apache.wicket.markup.html.WebPage;

import com.buskify.components.SignOutLink;
import com.buskify.security.AdminSecurePage;

public class BaseAdminPage extends WebPage implements AdminSecurePage{
	public BaseAdminPage(){
		add(new SignOutLink("signOut"));
	}
}
