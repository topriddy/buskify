package com.buskify;

import org.apache.wicket.Session;
import org.apache.wicket.pageStore.memory.DataStoreEvictionStrategy;
import org.apache.wicket.pageStore.memory.MemorySizeEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.lang.Bytes;

import com.buskify.pages.SignInPage;
import com.buskify.pages.admin.AdminWelcomePage;
import com.buskify.pages.student.StudentWelcomePage;
import com.buskify.pages.supervisor.SupervisorWelcomePage;
import com.buskify.util.DataInitialiser;

public class BuskifyApplication extends WebApplication {

	protected void init() {
		super.init();
		// BasicConfigurator.configure();
		getResourceSettings().setResourcePollFrequency(null);
		initWithTestData();

		mountPackage("allocator", SignInPage.class);
		mountPackage("allocator/admin", AdminWelcomePage.class);
		mountPackage("allocator/supervisor", SupervisorWelcomePage.class);
		mountPackage("allocator/student", StudentWelcomePage.class);

//		getApplicationSettings().setPageExpiredErrorPage(SignInPage.class);
//		getApplicationSettings().setAccessDeniedPage(SignInPage.class);
//		getApplicationSettings().setInternalErrorPage(SignInPage.class);
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<SignInPage> getHomePage() {
		return SignInPage.class;
	}

	/**
	 * Setup custom eviction strategy for this application
	 */
	public DataStoreEvictionStrategy getEvictionStrategy() {
		return new MemorySizeEvictionStrategy(Bytes.megabytes(2));
	}

	public void initWithTestData() {
		DataInitialiser.initWithDefaultData();
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new UserSession(request);
	}
}
