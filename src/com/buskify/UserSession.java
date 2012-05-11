package com.buskify;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import com.buskify.entity.AppUser;

public class UserSession extends WebSession {
	AppUser appUser = null;
	
	public UserSession(Request request) {
        super(request);
        setLocale(Locale.ENGLISH);
    }

    public static UserSession get() {
        return (UserSession) Session.get();
    }
    
    public boolean isLoggedIn(){
    	return appUser != null;
    }
    
    public void setAppUser(AppUser appUser){
    	this.appUser = appUser;
    }
    public AppUser getAppUser(){
    	return appUser;
    }
}