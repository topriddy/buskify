package com.buskify.pages.student;

import org.apache.wicket.markup.html.WebPage;

import com.buskify.components.SignOutLink;

public class BaseStudentPage extends WebPage {
	public BaseStudentPage(){
		add(new SignOutLink("signOut"));
	}
}
