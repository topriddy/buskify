package com.buskify.components;

import org.apache.wicket.markup.html.link.Link;

import com.buskify.UserSession;
import com.buskify.pages.SignInPage;

public class SignOutLink extends Link {
	public SignOutLink(String id) {
		super(id);
	}

	private static final long serialVersionUID = 8099561413985319972L;

	@Override
	public void onClick() {
		UserSession.get().setAppUser(null);
		UserSession.get().clear();
        UserSession.get().invalidateNow();
        setResponsePage(SignInPage.class);
	}
	
}
