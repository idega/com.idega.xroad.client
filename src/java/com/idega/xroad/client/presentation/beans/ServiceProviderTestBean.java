package com.idega.xroad.client.presentation.beans;

import org.springframework.beans.factory.annotation.Autowired;

import com.idega.util.CoreUtil;
import com.idega.util.expression.ELUtil;
import com.idega.xroad.client.business.XRoadServices;
import com.idega.xroad.client.data.ServiceProviderEntity;

public class ServiceProviderTestBean extends ServiceProviderBean {

	public ServiceProviderTestBean() {}
	
	public ServiceProviderTestBean(ServiceProviderEntity provider) {
		super(provider);
	}

	public String[] getMethods() {		
		return getXRoadServices().getMethodsList(
				CoreUtil.getIWContext().getCurrentUser(), 
				String.valueOf(getProviderId()));
	}

	@Autowired
	private XRoadServices xRoadServices = null;

	protected XRoadServices getXRoadServices() {
		if (this.xRoadServices == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.xRoadServices;
	}
}
