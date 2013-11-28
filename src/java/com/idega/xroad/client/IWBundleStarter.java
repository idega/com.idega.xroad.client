package com.idega.xroad.client;

import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.xroad.client.presentation.XRoadClientViewManager;

public class IWBundleStarter implements IWBundleStartable {

	public static final String IW_BUNDLE_IDENTIFIER = "com.idega.xroad.client";

	public void start(IWBundle starterBundle) {
		 XRoadClientViewManager.initializeStandardNodes(starterBundle);
	}

	public void stop(IWBundle starterBundle) {
	}

}
