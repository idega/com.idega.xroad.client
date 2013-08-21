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

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.logging.Level;

import javax.activation.DataHandler;
import javax.ejb.FinderException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.idega.block.form.data.XForm;
import com.idega.block.process.data.Case;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.core.business.DefaultSpringBean;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.CoreUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;
import com.idega.util.xml.XmlUtil;
import com.idega.xroad.client.XRoadClientConstants;
import com.idega.xroad.client.business.XRoadServices;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.CaseProcessingStep_type0;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Case_type0;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Consumer;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetCaseDetails;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetCaseDetailsE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetCaseDetailsRequest;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetCaseDetailsResponse;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetCaseDetailsResponseE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetCaseList;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetCaseListE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetCaseListRequest;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetCaseListResponse;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetCaseListResponseE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetDocument;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetDocumentE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetDocumentRequest;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetDocumentResponse;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetDocumentResponseE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetMessagesList;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetMessagesListE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetMessagesListRequest;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetMessagesListResponse;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetMessagesListResponseE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetNotifications;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetNotificationsE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetNotificationsRequest;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetNotificationsResponse;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetNotificationsResponseE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetPrefilledDocument;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetPrefilledDocumentE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetPrefilledDocumentRequest;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetPrefilledDocumentResponse;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetPrefilledDocumentResponseE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetServiceList;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetServiceListE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetServiceListRequest;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetServiceListResponse;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetServiceListResponseE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetXFormLabels;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetXFormLabelsE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetXFormLabelsRequest;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetXFormLabelsResponse;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.GetXFormLabelsResponseE;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Id;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Issue;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.LabelPair_type0;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.LangType;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Message_type0;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Notification_type0;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Producer;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Response_type10;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Response_type12;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Response_type3;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Response_type5;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Response_type6;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Response_type7;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Response_type8;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Response_type9;
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

	private UserBusiness userBusiness = null;

	private EhubserviceServiceStub stub = null;

	@Autowired
	private XRoadDAO xroadDAO;

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getPreffiledDocument(java.lang.String, java.lang.String, com.idega.user.data.User, java.lang.String)
	 */
	@Override
	public InputStream getPreffiledDocument(String applicationID,
			String taskID, User user, String language) {
		if (user == null || StringUtil.isEmpty(applicationID)) {
			return null;
		}

		if (taskID == null) {
			taskID = CoreConstants.EMPTY;
		}

		GetPrefilledDocumentRequest request = getInstantiatedObject(GetPrefilledDocumentRequest.class);
		request.setCitizenId(user.getPersonalID());
		request.setServiceId(applicationID);
		request.setStepId(taskID);

		LangType langType = getInstantiatedObject(LangType.class);
		langType.setLangType(language);

		request.setLanguageId(langType);

		GetPrefilledDocument prefilledDocument = getInstantiatedObject(GetPrefilledDocument.class);
		prefilledDocument.setRequest(request);

		GetPrefilledDocumentE prefilledDocumentE = getInstantiatedObject(GetPrefilledDocumentE.class);
		prefilledDocumentE.setGetPrefilledDocument(prefilledDocument);

		GetPrefilledDocumentResponseE preffiledDocumentResponseE = null;
		try {
			 preffiledDocumentResponseE = getEhubserviceServiceStub().getPrefilledDocument(
					prefilledDocumentE ,
					getConsumer(),
					getProducer(),
					getUserId(user),
					getServiceID(applicationID),
					getService(XRoadClientConstants.SERVICE_GET_PREFILLED_DOCUMENT),
					getIssue("Some issue"));
		} catch (RemoteException e) {
			getLogger()	.log(Level.WARNING,
					"Unable to get " + GetPrefilledDocumentResponseE.class +
					" cause of: ", e);
		}

		if (preffiledDocumentResponseE == null) {
			getLogger().warning("Unable to get: " + GetPrefilledDocumentResponseE.class +
					" by service provider ID: " + applicationID +
					" and user personal id: " + user.getPersonalID() +
					" and language: " + language);
			return null;
		}

		GetPrefilledDocumentResponse preffiledDocumentResponse = preffiledDocumentResponseE
				.getGetPrefilledDocumentResponse();
		if (preffiledDocumentResponse == null) {
			getLogger().warning("Unable to get: " + GetPrefilledDocumentResponse.class +
					" by service provider ID: " + applicationID +
					" and user personal id: " + user.getPersonalID() +
					" and language: " + language);
		}

		Response_type9 response = preffiledDocumentResponse.getResponse();
		if (response == null) {
			getLogger().warning("Unable to get: " + Response_type9.class +
					" by service provider ID: " + applicationID +
					" and user personal id: " + user.getPersonalID() +
					" and language: " + language);
		}

		DataHandler documentHandler = response.getDocument();
		if (documentHandler == null) {
			getLogger().warning("Unable to get: " + Response_type9.class +
					" by service provider ID: " + applicationID +
					" and user personal id: " + user.getPersonalID() +
					" and language: " + language);
		}

		try {
			return documentHandler.getInputStream();
		} catch (IOException e) {
			getLogger().log(Level.WARNING,
					"Failed to get " + InputStream.class, e);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getPreffiledDocumentInXML(java.lang.String, java.lang.String, com.idega.user.data.User, java.lang.String)
	 */
	@Override
	public Document getPreffiledDocumentInXML(String applicationID,
			String taskID, User user, String language) {
		InputStream documentInputStream = getPreffiledDocument(applicationID, taskID, user, language);
		if(documentInputStream == null){
			return null;
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setXIncludeAware(true);
		DocumentBuilder documentBuilder;
		Document document = null;
		try {
			documentBuilder = factory.newDocumentBuilder();
			document = documentBuilder.parse(documentInputStream);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getPreffiledDocumentInXML(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Document getPreffiledDocumentInXML(String serviceProviderID, String taskID,
			String userId, String language) {
		return XmlUtil.getDocument(
				getPreffiledDocument(serviceProviderID, taskID, userId, language));
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getPreffiledDocument(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public InputStream getPreffiledDocument(String applicationID, String taskID,
			String userId, String language) {
		return getPreffiledDocument(applicationID, taskID, getUser(userId), language);
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getProcessedDocument(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public InputStream getProcessedDocument(String serviceProviderID,
			String documentID, String userId) {
		return getProcessedDocument(serviceProviderID, documentID, getUser(userId));
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getDocument(java.lang.String, java.lang.String, com.idega.user.data.User)
	 */
	@Override
	public java.io.InputStream getProcessedDocument(
			String serviceProviderID, String documentID, User user) {
		Response_type6 response = getDocument(serviceProviderID, documentID, user);
		if (response == null) {
			getLogger().warning("Unable to get: " + Response_type6.class +
					" by service provider ID: " + serviceProviderID +
					" and document id: " + documentID);
			return null;
		}

		DataHandler documentHandler = response.getDocument();
		try {
			return documentHandler.getInputStream();
		} catch (IOException e) {
			getLogger().log(Level.WARNING,
					"Unable to open stream for document reading: ", e);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getProcessedDocumentInXML(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Document getProcessedDocumentInXML(
			String serviceProviderID, String documentID, String userID) {
		return getProcessedDocumentInXML(
				serviceProviderID, documentID, getUser(userID));
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getProcessedDocumentInXML(java.lang.String, java.lang.String, com.idega.user.data.User)
	 */
	@Override
	public Document getProcessedDocumentInXML(
			String serviceProviderID, String documentID, User user) {
		InputStream inputStream = getProcessedDocument(
				serviceProviderID, documentID, user);
		if (inputStream == null) {
			return null;
		}

		return XmlUtil.getDocument(inputStream);
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getXFormsDocumentTemplate(java.lang.String, java.lang.String, com.idega.user.data.User)
	 */
	@Override
	public java.io.InputStream getXFormsDocumentTemplate(
			String serviceProviderID, String documentID, User user) {
		Response_type6 response = getDocument(serviceProviderID, documentID, user);
		if (response == null) {
			getLogger().warning("Unable to get: " + Response_type6.class +
					" by service provider ID: " + serviceProviderID +
					" and document id: " + documentID);
			return null;
		}

		DataHandler documentHandler = response.getXFormsTemplate();
		try {
			return documentHandler.getInputStream();
		} catch (IOException e) {
			getLogger().log(Level.WARNING,
					"Unable to open stream for document reading: ", e);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getXFormsDocumentTemplate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public java.io.InputStream getXFormsDocumentTemplate(
			String serviceProviderID, String documentID, String userID) {
		return getXFormsDocumentTemplate(serviceProviderID, documentID, getUser(userID));
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getXFormsDocumentTemplateInXML(java.lang.String, java.lang.String, com.idega.user.data.User)
	 */
	@Override
	public Document getXFormsDocumentTemplateInXML(
			String serviceProviderID, String documentID, User user) {
		InputStream inputStream = getXFormsDocumentTemplate(
				serviceProviderID, documentID, user);
		if (inputStream == null) {
			return null;
		}

		return XmlUtil.getDocument(inputStream);
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getXFormsDocumentTemplateInXML(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Document getXFormsDocumentTemplateInXML(
			String serviceProviderID, String documentID, String userID) {
		return getXFormsDocumentTemplateInXML(
				serviceProviderID, documentID, getUser(userID));
	}

	/**
	 *
	 * <p>Queries X-Road for {@link Document} of {@link XForm}
	 * by given {@link User} and {@link XForm#getFormId()}.</p>
	 * @param serviceProviderID
	 * @param documentID - {@link XForm#getFormId()},
	 * not <code>null</code>;
	 * @param user who can access {@link Document}, not null;
	 * @return response from service or
	 * <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	protected Response_type6 getDocument(
			String serviceProviderID, String documentID, User user) {
		if (StringUtil.isEmpty(serviceProviderID) || StringUtil.isEmpty(documentID)) {
			return null;
		}

		GetDocumentRequest request = getInstantiatedObject(GetDocumentRequest.class);
		request.setDocumentId(documentID);
		request.setServiceProviderId(serviceProviderID);

		GetDocument document =  getInstantiatedObject(GetDocument.class);
		document.setRequest(request);

		GetDocumentE documentE = getInstantiatedObject(GetDocumentE.class);
		documentE.setGetDocument(document);

		GetDocumentResponseE documentResponseE = null;
		try {
			documentResponseE = getEhubserviceServiceStub().getDocument(
					documentE,
					getConsumer(),
					getProducer(),
					getUserId(user),
					getServiceID(serviceProviderID),
					getService(XRoadClientConstants.SERVICE_GET_DOCUMENT),
					getIssue("Issue"));
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING,
					"Unable to get: " + GetDocumentResponseE.class + " cause of: ", e);
		}

		if (documentResponseE == null) {
			getLogger().warning("Unable to get: " + GetDocumentResponseE.class +
					" by service provider ID: " + serviceProviderID +
					" and document id: " + documentID);
			return null;
		}

		GetDocumentResponse documentResponse = documentResponseE
				.getGetDocumentResponse();
		if (documentResponse == null) {
			getLogger().warning("Unable to get: " + GetDocumentResponse.class +
					" by service provider ID: " + serviceProviderID +
					" and document id: " + documentID);
			return null;
		}

		return documentResponse.getResponse();
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getMessageEntries(java.lang.Object, java.lang.String)
	 */
	@Override
	public Message_type0[] getMessageEntries(String serviceProviderID,
			String personalID) {
		return getMessageEntries(serviceProviderID, getUser(personalID), null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getMessageEntries(java.lang.String, com.idega.user.data.User)
	 */
	@Override
	public Message_type0[] getMessageEntries(String serviceProviderID, User user, String caseId) {
		if (user == null) {
			return null;
		}

		GetMessagesListRequest request = getInstantiatedObject(GetMessagesListRequest.class);
		request.setCitizenId(user.getPersonalID());
		request.setServiceProviderId(serviceProviderID);

		GetMessagesList messageList = getInstantiatedObject(GetMessagesList.class);
		messageList.setRequest(request);

		GetMessagesListE messagesListE = getInstantiatedObject(GetMessagesListE.class);
		messagesListE.setGetMessagesList(messageList);

		GetMessagesListResponseE messageListResponseE = null;
		try {
			messageListResponseE = getEhubserviceServiceStub().getMessagesList(
					messagesListE,
					getConsumer(),
					getProducer(),
					getUserId(user),
					getServiceID(serviceProviderID),
					getService(XRoadClientConstants.SERVICE_GET_MESSAGES_LIST),
					getIssue("Some issue")
			);
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING,
					"Unable to get " + GetMessagesListResponseE.class.getName(), e);
		}

		if  (messageListResponseE == null) {
			getLogger().warning("Unable to get: " + GetMessagesListResponseE.class +
					" by service provider ID: " + serviceProviderID +
					" and user: " + user);
			return null;
		}

		GetMessagesListResponse messageListResponse = messageListResponseE
				.getGetMessagesListResponse();
		if (messageListResponse == null) {
			getLogger().warning("Unable to get: " + GetMessagesListResponse.class +
					" by service provider ID: " + serviceProviderID +
					" and user: " + user);
			return null;
		}

		Response_type5 response = messageListResponse.getResponse();
		if (response == null) {
			getLogger().warning("Unable to get: " + Response_type5.class +
					" by service provider ID: " + serviceProviderID +
					" and user: " + user);
			return null;
		}

		return response.getMessage();
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getXFormsLabels(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public LabelPair_type0[] getXFormsLabels(String serviceProviderID, String xFormID, String language) {
		if (StringUtil.isEmpty(xFormID) || StringUtil.isEmpty(language) || StringUtil.isEmpty(serviceProviderID)) {
			return null;
		}

		GetXFormLabelsRequest request = new GetXFormLabelsRequest();
		request.setXFormId(xFormID);
		request.setServiceProviderId(serviceProviderID);
		LangType langType = new LangType();
		langType.setLangType(language);
		request.setLang(langType);

		GetXFormLabels xFormsLabels = getInstantiatedObject(GetXFormLabels.class);
		xFormsLabels.setRequest(request);

		GetXFormLabelsE xFormLabelsE = getInstantiatedObject(GetXFormLabelsE.class);
		xFormLabelsE.setGetXFormLabels(xFormsLabels);

		GetXFormLabelsResponseE xFormLabelsResponseE = null;
		try {
			xFormLabelsResponseE = getEhubserviceServiceStub().getXFormLabels(
					xFormLabelsE , getConsumer(), getProducer(),
					getUserId(getLegacyUser(getCurrentUser())), getServiceID(serviceProviderID),
					getService(XRoadClientConstants.SERVICE_GET_XFORMS_LABELS),
					getIssue("Test issue"));
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to get: " +
					GetXFormLabelsResponseE.class.getName() + " cause of: ", e);
		}

		if (xFormLabelsResponseE == null) {
			getLogger().warning("Unable to get: " + GetXFormLabelsResponseE.class +
					" by service provider ID: " + serviceProviderID +
					" and XForm ID: " + xFormID);
			return null;
		}

		GetXFormLabelsResponse xFromLabelsResponse = xFormLabelsResponseE
				.getGetXFormLabelsResponse();
		if (xFromLabelsResponse == null) {
			getLogger().warning("Unable to get: " + GetXFormLabelsResponse.class +
					" by service provider ID: " + serviceProviderID +
					" and XForm ID: " + xFormID);
			return null;
		}

		Response_type12 response = xFromLabelsResponse.getResponse();
		if (response == null) {
			getLogger().warning("Unable to get: " + Response_type12.class +
					" by service provider ID: " + serviceProviderID +
					" and XForm ID: " + xFormID);
			return null;
		}

		return response.getLabelPair();
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getCaseEntries(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public CaseProcessingStep_type0[] getCaseEntries(String serviceProviderID,
			String caseIdentifier, String personalID) {
		return getCaseEntries(serviceProviderID, caseIdentifier, getUser(personalID));
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getCaseEntries(java.lang.String, java.lang.String, com.idega.user.data.User)
	 */
	@Override
	public CaseProcessingStep_type0[] getCaseEntries(String serviceProviderID,
			String caseIdentifier, User user) {
		if (StringUtil.isEmpty(caseIdentifier) ||
				StringUtil.isEmpty(serviceProviderID) || user == null) {
			return null;
		}

		GetCaseDetailsRequest request = getInstantiatedObject(GetCaseDetailsRequest.class);
		request.setServiceProviderId(serviceProviderID);
		request.setCaseId(caseIdentifier);

		GetCaseDetails caseDetails = getInstantiatedObject(GetCaseDetails.class);
		caseDetails.setRequest(request);

		GetCaseDetailsE caseDetailsE = getInstantiatedObject(GetCaseDetailsE.class);
		caseDetailsE.setGetCaseDetails(caseDetails);

		GetCaseDetailsResponseE caseDetailsResponseE = null;
		try {
			caseDetailsResponseE = getEhubserviceServiceStub().getCaseDetails(
					caseDetailsE ,
					getConsumer(),
					getProducer(),
					getUserId(user),
					getServiceID(serviceProviderID),
					getService(XRoadClientConstants.SERVICE_GET_CASE_DETAILS),
					getIssue("Some issue")
			);
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to get " +
					GetCaseDetailsResponseE.class + " cause of: ", e);
		}

		if (caseDetailsResponseE == null) {
			getLogger().warning("Unable to get: " + GetCaseDetailsResponseE.class +
					" by service provider ID: " + serviceProviderID +
					" and " + Case.class +  " identifier: " + caseIdentifier +
					" and user: " + user);
			return null;
		}

		GetCaseDetailsResponse caseDetailsResponse = caseDetailsResponseE.getGetCaseDetailsResponse();
		if (caseDetailsResponse == null) {
			getLogger().warning("Unable to get: " + GetCaseDetailsResponse.class +
					" by service provider ID: " + serviceProviderID +
					" and " + Case.class +  " identifier: " + caseIdentifier +
					" and user: " + user);
			return null;
		}

		Response_type8 reponse = caseDetailsResponse.getResponse();
		if (reponse == null) {
			getLogger().warning("Unable to get: " + Response_type8.class +
					" by service provider ID: " + serviceProviderID +
					" and " + Case.class +  " identifier: " + caseIdentifier +
					" and user: " + user);
			return null;
		}

		return reponse.getCaseProcessingStep();
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getCasesEntries(java.lang.String, java.lang.String)
	 */
	@Override
	public Case_type0[] getCasesEntries(String serviceProviderID,
			String personalID) {
		return getCasesEntries(serviceProviderID, getUser(personalID));
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getCasesEntries(java.lang.String, com.idega.user.data.User)
	 */
	@Override
	public Case_type0[] getCasesEntries(String serviceProviderID, User user) {
		if (user == null || StringUtil.isEmpty(serviceProviderID)) {
			return null;
		}

		GetCaseListRequest request = getInstantiatedObject(GetCaseListRequest.class);
		request.setCitizenId(user.getPersonalID());
		request.setServiceProviderId(serviceProviderID);

		GetCaseList caseList = getInstantiatedObject(GetCaseList.class);
		caseList.setRequest(request);

		GetCaseListE caseListE =  getInstantiatedObject(GetCaseListE.class);
		caseListE.setGetCaseList(caseList);

		GetCaseListResponseE caseListResponseE = null;
		try {
			caseListResponseE = getEhubserviceServiceStub().getCaseList(
					caseListE,
					getConsumer(),
					getProducer(),
					getUserId(user),
					getServiceID(serviceProviderID),
					getService(XRoadClientConstants.SERVICE_GET_CASE_LIST),
					getIssue("Some issue"));
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to get case list: ", e);
		}

		if (caseListResponseE == null) {
			getLogger().warning("Unable to get: " + GetCaseListResponseE.class +
					" by service provider ID: " + serviceProviderID +
					" and user: " + user);
			return null;
		}

		GetCaseListResponse caseListResponse = caseListResponseE.getGetCaseListResponse();
		if (caseListResponse == null) {
			getLogger().warning("Unable to get: " + GetCaseListResponse.class +
					" by service provider ID: " + serviceProviderID +
					" and user: " + user);
			return null;
		}

		Response_type3 reponse = caseListResponse.getResponse();
		if (reponse == null) {
			getLogger().warning("Unable to get: " + Response_type3.class +
					" by service provider ID: " + serviceProviderID +
					" and user: " + user);
			return null;
		}

		return reponse.get_case();
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getServiceEntries(java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceEntry_type0[] getServiceEntries(String serviceProviderID,
			String personalID) {
		return getServiceEntries(serviceProviderID, getUser(personalID));
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getServiceEntries(java.lang.String, com.idega.user.data.User)
	 */
	@Override
	public ServiceEntry_type0[] getServiceEntries(
			String serviceProviderID, User user) {
		if (user == null || StringUtil.isEmpty(serviceProviderID)) {
			return null;
		}

		GetServiceListRequest type = getInstantiatedObject(GetServiceListRequest.class);
		type.setServiceProviderId(serviceProviderID);

		GetServiceList serviceList = getInstantiatedObject(GetServiceList.class);
		serviceList.setRequest(type);

		GetServiceListE serviceListE = getInstantiatedObject(GetServiceListE.class);
		serviceListE.setGetServiceList(serviceList);

		GetServiceListResponseE serviceListResponseE = null;
		try {
			serviceListResponseE = getEhubserviceServiceStub().getServiceList(
					serviceListE,
					getConsumer(),
					getProducer(),
					getUserId(user),
					getServiceID(serviceProviderID),
					getService(XRoadClientConstants.SERVICE_GET_SERVICE_LIST),
					getIssue("Some issue")
			);
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING,
					"Unable to find " + GetServiceListResponseE.class, e);
		}

		if (serviceListResponseE == null) {
			getLogger().warning("Unable to get " + GetServiceListResponseE.class +
					" from provider by id: " + serviceProviderID +
					" and user: " + user);
			return null;
		}

		GetServiceListResponse serviceListResponse = serviceListResponseE
				.getGetServiceListResponse();
		if (serviceListResponse == null) {
			getLogger().warning("Unable to get " + GetServiceListResponse.class +
					" from provider by id: " + serviceProviderID +
					" and user: " + user);
			return null;
		}

		Response_type10 response = serviceListResponse.getResponse();
		if (response == null) {
			getLogger().warning("Unable to get " + Response_type10.class +
					" from provider by id: " + serviceProviderID +
					" and user: " + user);
			return null;
		}

		return response.getServiceEntry();
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.xroad.client.business.XRoadServices#getNotifications(com.idega.user.data.User)
	 */
	@Override
	public Notification_type0[] getNotifications(User user, String serviceProviderId) {
		if (user == null) {
			return null;
		}

		GetNotificationsRequest notificationsRequest = getInstantiatedObject(
				GetNotificationsRequest.class);
		notificationsRequest.setCitizenId(user.getPersonalID());
		notificationsRequest.setServiceProviderId(serviceProviderId);

		GetNotifications notifications = getInstantiatedObject(
				GetNotifications.class);
		notifications.setRequest(notificationsRequest);

		GetNotificationsE notificationsE = getInstantiatedObject(
				GetNotificationsE.class);
		notificationsE.setGetNotifications(notifications);

		GetNotificationsResponseE notificationResponseE = null;
		try {
			notificationResponseE = getEhubserviceServiceStub().getNotifications(
					notificationsE,
					getConsumer(),
					getProducer(),
					getUserId(user),
					getServiceID(serviceProviderId),
					getService(XRoadClientConstants.SERVICE_GET_NOTIFICATIONS),
					getIssue("Testing"));
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Failed to get notifications: ", e);
		}

		if (notificationResponseE == null) {
			getLogger().warning("Unable to get " + GetNotificationsResponseE.class +
					" by user: " + user);
			return null;
		}

		GetNotificationsResponse notificationsResponse = notificationResponseE
				.getGetNotificationsResponse();
		if (notificationsResponse == null) {
			getLogger().warning("Unable to get " + GetNotificationsResponse.class +
					" by user: " + user);
			return null;
		}

		Response_type7 response = notificationsResponse.getResponse();
		if (response == null) {
			getLogger().warning("Unable to get " + Response_type7.class +
					" by user: " + user);
			return null;
		}

		return response.getNotification();
	}

	protected UserId getUserId(User user) {
		if (user == null) {
			return null;
		}

		UserId userID = getInstantiatedObject(UserId.class);
		if (userID == null) {
			return null;
		}

		userID.setUserId(user.getPersonalID());
		return userID;
	}

	protected Issue getIssue(String issueText) {
		if (StringUtil.isEmpty(issueText)) {
			return null;
		}

		Issue issue = getInstantiatedObject(Issue.class);
		if (issue == null) {
			return null;
		}

		issue.setIssue(issueText);
		return issue;
	}

	protected Service getService(String serviceName) {
		if (StringUtil.isEmpty(serviceName)) {
			return null;
		}

		Service service = getInstantiatedObject(Service.class);
		if (service == null) {
			return null;
		}

		service.setService(serviceName);
		return service;
	}

	protected Consumer getConsumer() {
		Consumer consumer = getInstantiatedObject(Consumer.class);
		if (consumer == null) {
			return null;
		}

		consumer.setConsumer(XRoadClientConstants.SERVICE_CONSUMER);
		return consumer;
	}

	protected Id getServiceID(String serviceID) {
		if (StringUtil.isEmpty(serviceID)) {
			return null;
		}

		Id id = getInstantiatedObject(Id.class);
		if (id == null) {
			return null;
		}

		id.setId(XRoadClientConstants.SERVICE_PRODUCER +
				CoreConstants.DOT + serviceID);
		return id;
	}

	protected Producer getProducer() {
		Producer producer = getInstantiatedObject(Producer.class);
		if (producer == null) {
			return null;
		}

		producer.setProducer(XRoadClientConstants.SERVICE_PRODUCER);
		return producer;
	}

	protected XRoadDAO getXRoadDAO() {
		if (xroadDAO == null)
			ELUtil.getInstance().autowire(this);
		return xroadDAO;
	}

	protected static <T> T getInstantiatedObject(
			java.lang.Class<T> type) {
		if (type == null) {
			return null;
		}

		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			getLogger(XRoadServices.class).log(Level.WARNING,
					"Unable to instantiate " + type + " cause of: ", e);
		} catch (IllegalAccessException e) {
			getLogger(XRoadServices.class).log(Level.WARNING,
					"Can't access constructor of " + type + " cause of: ", e);
		}

		return null;
	}

	protected EhubserviceServiceStub getEhubserviceServiceStub() {
		if (this.stub == null) {
			try {
				this.stub = new EhubserviceServiceStub();
			} catch (AxisFault e) {
				getLogger().log(Level.WARNING, "Unable to create axis service stub:", e);
			}
		}

		return this.stub;
	}

	protected User getUser(String personalId) {
		if (StringUtil.isEmpty(personalId)) {
			return null;
		}

		try {
			return getUserBusiness().getUser(personalId);
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to connect database: ", e);
		} catch (FinderException e) {
			getLogger().log(Level.WARNING, "Unable to find " + User.class +
					" by personal id: " + personalId);
		}

		try {
			return getUserBusiness().getUser(Integer.valueOf(personalId));
		} catch (NumberFormatException e) {
			getLogger().log(Level.WARNING,
					"Unable to convert id: " + personalId + " to numeric.");
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to connect data source: ", e);
		}

		return null;
	}

	protected UserBusiness getUserBusiness() {
		if (this.userBusiness != null) {
			return this.userBusiness;
		}

		try {
			this.userBusiness = IBOLookup.getServiceInstance(
					CoreUtil.getIWContext(), UserBusiness.class);
		} catch (IBOLookupException e) {
			getLogger().log(Level.WARNING,
					"Unable to get: " + UserBusiness.class + ": ", e);
		}

		return this.userBusiness;
	}
}
