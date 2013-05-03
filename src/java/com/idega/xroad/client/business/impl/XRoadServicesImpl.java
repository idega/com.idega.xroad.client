/**
 * @(#)XRoadServicesImpl.java    1.0.0 4:00:09 PM
 *
 * Idega Software hf. Source Code Licence Agreement x
 *
 * This agreement, made this 10th of February 2006 by and between 
 * Idega Software hf., a business formed and operating under laws 
 * of Iceland, having its principal place of business in Reykjavik, 
 * Iceland, hereinafter after referred to as "Manufacturer" and Agura 
 * IT hereinafter referred to as "Licensee".
 * 1.  License Grant: Upon completion of this agreement, the source 
 *     code that may be made available according to the documentation for 
 *     a particular software product (Software) from Manufacturer 
 *     (Source Code) shall be provided to Licensee, provided that 
 *     (1) funds have been received for payment of the License for Software and 
 *     (2) the appropriate License has been purchased as stated in the 
 *     documentation for Software. As used in this License Agreement, 
 *     Licensee shall also mean the individual using or installing 
 *     the source code together with any individual or entity, including 
 *     but not limited to your employer, on whose behalf you are acting 
 *     in using or installing the Source Code. By completing this agreement, 
 *     Licensee agrees to be bound by the terms and conditions of this Source 
 *     Code License Agreement. This Source Code License Agreement shall 
 *     be an extension of the Software License Agreement for the associated 
 *     product. No additional amendment or modification shall be made 
 *     to this Agreement except in writing signed by Licensee and 
 *     Manufacturer. This Agreement is effective indefinitely and once
 *     completed, cannot be terminated. Manufacturer hereby grants to 
 *     Licensee a non-transferable, worldwide license during the term of 
 *     this Agreement to use the Source Code for the associated product 
 *     purchased. In the event the Software License Agreement to the 
 *     associated product is terminated; (1) Licensee's rights to use 
 *     the Source Code are revoked and (2) Licensee shall destroy all 
 *     copies of the Source Code including any Source Code used in 
 *     Licensee's applications.
 * 2.  License Limitations
 *     2.1 Licensee may not resell, rent, lease or distribute the 
 *         Source Code alone, it shall only be distributed as a 
 *         compiled component of an application.
 *     2.2 Licensee shall protect and keep secure all Source Code 
 *         provided by this this Source Code License Agreement. 
 *         All Source Code provided by this Agreement that is used 
 *         with an application that is distributed or accessible outside
 *         Licensee's organization (including use from the Internet), 
 *         must be protected to the extent that it cannot be easily 
 *         extracted or decompiled.
 *     2.3 The Licensee shall not resell, rent, lease or distribute 
 *         the products created from the Source Code in any way that 
 *         would compete with Idega Software.
 *     2.4 Manufacturer's copyright notices may not be removed from 
 *         the Source Code.
 *     2.5 All modifications on the source code by Licencee must 
 *         be submitted to or provided to Manufacturer.
 * 3.  Copyright: Manufacturer's source code is copyrighted and contains 
 *     proprietary information. Licensee shall not distribute or 
 *     reveal the Source Code to anyone other than the software 
 *     developers of Licensee's organization. Licensee may be held 
 *     legally responsible for any infringement of intellectual property 
 *     rights that is caused or encouraged by Licensee's failure to abide 
 *     by the terms of this Agreement. Licensee may make copies of the 
 *     Source Code provided the copyright and trademark notices are 
 *     reproduced in their entirety on the copy. Manufacturer reserves 
 *     all rights not specifically granted to Licensee.
 *
 * 4.  Warranty & Risks: Although efforts have been made to assure that the 
 *     Source Code is correct, reliable, date compliant, and technically 
 *     accurate, the Source Code is licensed to Licensee as is and without 
 *     warranties as to performance of merchantability, fitness for a 
 *     particular purpose or use, or any other warranties whether 
 *     expressed or implied. Licensee's organization and all users 
 *     of the source code assume all risks when using it. The manufacturers, 
 *     distributors and resellers of the Source Code shall not be liable 
 *     for any consequential, incidental, punitive or special damages 
 *     arising out of the use of or inability to use the source code or 
 *     the provision of or failure to provide support services, even if we 
 *     have been advised of the possibility of such damages. In any case, 
 *     the entire liability under any provision of this agreement shall be 
 *     limited to the greater of the amount actually paid by Licensee for the 
 *     Software or 5.00 USD. No returns will be provided for the associated 
 *     License that was purchased to become eligible to receive the Source 
 *     Code after Licensee receives the source code. 
 */
package com.idega.xroad.client.business.impl;

import java.rmi.RemoteException;
import java.util.logging.Level;

import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import com.idega.core.business.DefaultSpringBean;
import com.idega.user.data.User;
import com.idega.util.expression.ELUtil;
import com.idega.xroad.client.business.XRoadServices;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Consumer;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetServiceList;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetServiceListE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetServiceListResponse;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetServiceListResponseE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Id;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Issue;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Producer;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Request_type9;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Response_type10;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Service;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.ServiceEntry_type0;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.UserId;
import com.idega.xroad.data.XRoadDAO;

/**
 * @see XRoadServices
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Apr 17, 2013
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
@org.springframework.stereotype.Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class XRoadServicesImpl extends DefaultSpringBean implements XRoadServices {

	@Autowired
	private XRoadDAO xroadDAO;

	protected XRoadDAO getXRoadDAO() {
		if (xroadDAO == null)
			ELUtil.getInstance().autowire(this);
		return xroadDAO;
	}
	
	protected static org.apache.axis2.databinding.ADBBean getTestObject(
			java.lang.Class type) throws java.lang.Exception {
		return (org.apache.axis2.databinding.ADBBean) type.newInstance();
	}

	@Override
	public ServiceEntry_type0[] getServiceEntries(
			String serviceProviderID,
			User user) throws AxisFault, RemoteException {
//		if (user == null || StringUtil.isEmpty(serviceProviderID)) {
//			return null;
//		}
		
		EhubserviceServiceStub stub = new EhubserviceServiceStub();
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(900000);
		
		GetServiceListE getServiceList102 = null;
		GetServiceList list = null;
		UserId userId105 = null;
		Issue issue108 = null;
		Id id106 = null;
		Consumer consumer103 = null;
		Service service107 = null;
		Producer producer104 = null;
		
		try {
			list = (GetServiceList) getTestObject(GetServiceList.class);
			getServiceList102 = (GetServiceListE) getTestObject(GetServiceListE.class);
			userId105 = (UserId) getTestObject(UserId.class);
			issue108 = (Issue) getTestObject(Issue.class);
			id106 = (Id) getTestObject(Id.class);
			consumer103 = (Consumer) getTestObject(Consumer.class);
			service107 = (Service) getTestObject(Service.class);
			producer104 = (Producer) getTestObject(Producer.class);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Unable to create request: ", e);
			return null;
		}
		
		Request_type9 type = new Request_type9();
		type.setServiceProviderId("0");
		
		list.setRequest(type);
		getServiceList102.setGetServiceList(list);
		producer104.setProducer("ehubservice");
		consumer103.setConsumer("ehub");
		id106.setId("ehubservice.1333026172.84603");
		service107.setService("ehubservice.GetServiceList");
		issue108.setIssue("test");
		userId105.setUserId("EE12345678900");
		
		GetServiceListResponseE serviceListResponseE = stub.getServiceList(
				getServiceList102, consumer103, producer104, userId105, id106, 
				service107, issue108);		
		if (serviceListResponseE == null) {
			getLogger().warning("Unable to get " + 
					GetServiceListResponseE.class.getName() + 
					" from provider by id: " + serviceProviderID);
			return null;
		}
		
		GetServiceListResponse serviceListResponse = serviceListResponseE
				.getGetServiceListResponse();
		if (serviceListResponse == null) {
			getLogger().warning("Unable to get " + 
					GetServiceListResponse.class.getName() + 
					" from provider by id: " + serviceProviderID);
			return null;
		}
		
		Response_type10 response = serviceListResponse.getResponse();
		if (response == null) {
			getLogger().warning("Unable to get " + 
					Response_type10.class.getName() + 
					" from provider by id: " + serviceProviderID);
			return null;
		}

		return response.getServiceEntry();
	}
}
