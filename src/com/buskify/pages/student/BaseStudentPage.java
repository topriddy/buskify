package com.buskify.pages.student;

import org.apache.wicket.markup.html.WebPage;

import com.buskify.components.SignOutLink;
import com.buskify.security.StudentSecurePage;

public class BaseStudentPage extends WebPage implements StudentSecurePage{
	public BaseStudentPage(){
		add(new SignOutLink("signOut"));
	}
}
