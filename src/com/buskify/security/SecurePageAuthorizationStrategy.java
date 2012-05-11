package com.buskify.security;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.request.component.IRequestableComponent;

import com.buskify.UserSession;
import com.buskify.entity.Admin;
import com.buskify.entity.AppUser;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;
import com.buskify.pages.SignInPage;


public class SecurePageAuthorizationStrategy implements IAuthorizationStrategy, IUnauthorizedComponentInstantiationListener {

    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
        //if is not a page => authorize
    	if (!Page.class.isAssignableFrom(componentClass)) {
            return true;
        }
    	//if its not an SecurePage => authorize
        if (!SecurePage.class.isAssignableFrom(componentClass)) {
            return true;
        }
        //check to see if user is logged in.
        boolean isLogIn = UserSession.get().isLoggedIn();
        if (!isLogIn) {
        	throw new RestartResponseException(SignInPage.class);
        }
        //else user is logged in check for role
        AppUser appUser = UserSession.get().getAppUser();
        if(appUser instanceof Admin && AdminSecurePage.class.isAssignableFrom(componentClass)){
        	return true;
        }
        if(appUser instanceof Supervisor && SupervisorSecurePage.class.isAssignableFrom(componentClass)){
        	return true;
        }
        if(appUser instanceof Student && StudentSecurePage.class.isAssignableFrom(componentClass)){
        	return true;
        }
        throw new RestartResponseException(SignInPage.class);
    }

    public boolean isActionAuthorized(Component component, Action action) {
        return true;
    }

    public void onUnauthorizedInstantiation(Component component) {
        throw new RestartResponseException(SignInPage.class);
    }
}
