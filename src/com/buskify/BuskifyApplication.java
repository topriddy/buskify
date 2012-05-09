package com.buskify;

import org.apache.wicket.Session;
import org.apache.wicket.pageStore.memory.DataStoreEvictionStrategy;
import org.apache.wicket.pageStore.memory.MemorySizeEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.lang.Bytes;

import com.buskify.pages.SignInPage;
import com.buskify.util.DataInitialiser;

public class BuskifyApplication extends WebApplication {

	protected void init() {
		super.init();
		// BasicConfigurator.configure();
		getResourceSettings().setResourcePollFrequency(null);
		initWithTestData();

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
