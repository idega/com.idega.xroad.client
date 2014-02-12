/**
 * @(#)XRoadServices.java    1.0.0 3:55:31 PM
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
package com.idega.xroad.client.business;

import java.io.InputStream;
import java.util.Locale;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.w3c.dom.Document;

import com.idega.block.form.data.XForm;
import com.idega.notifier.data.NotificationReceiverEntity;
import com.idega.user.data.User;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Case;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.CaseStep;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.LabelPair;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Message;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.Notification;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.ServiceEntry;

/**
 * <p>Interface for communication with X-Road server</p>
 * <p>You can report about problems to:
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Apr 17, 2013
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public interface XRoadServices {

	/**
	 *
	 * <p>Queries X-Road for data about services.</p>
	 * @param serviceProviderID
	 * @param personalID - {@link User#getPersonalID()} of {@link User}
	 * who has access to it, not <code>null</code>;
	 * @return services by given criteria, or <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public ServiceEntry[] getServiceEntries(String serviceProviderID, String personalID);

	/**
	 *
	 * <p>Queries X-Road for data about services</p>
	 * @param serviceProviderID
	 * @param user which can access service, not <code>null</code>;
	 * @return  services by given criteria, or <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public ServiceEntry[] getServiceEntries(String serviceProviderID, User user);

	/**
	 *
	 * <p>Queries X-Road for data about {@link Case}s</p>
	 * @param serviceProviderID
	 * @param personalID - {@link User#getPersonalID()} of {@link User}
	 * who can view {@link Case}s and is registered on required service,
	 * not <code>null</code>;
	 * @return data about {@link Case}s or <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Case[] getCasesEntries(String serviceProviderID, String personalID);

	/**
	 *
	 * <p>Queries X-Road for data about {@link Case}s</p>
	 * @param serviceProviderID
	 * @param user is {@link User}
	 * who can view {@link Case}s and is registered on required service,
	 * not <code>null</code>;
	 * @return data about {@link Case}s or <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Case[] getCasesEntries(String serviceProviderID, User user);

	/**
	 *
	 * <p>Queries X-Road for data about concrete {@link Case} and its
	 * {@link Task}s.</p>
	 * @param serviceProviderID
	 * @param caseIdentifier - {@link Case#getCaseIdentifier()}, not
	 * <code>null</code>;
	 * @param user is {@link User}
	 * who can view {@link Case}s and is registered on required service,
	 * not <code>null</code>;
	 * @return data about {@link Case} and its {@link Task}s,
	 * defined by criteria, or <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public CaseStep[] getCaseEntries(String serviceProviderID,
			String caseIdentifier, User user);

	/**
	 *
	 * <p>Queries X-Road for data about concrete {@link Case} and its
	 * {@link Task}s.</p>
	 * @param serviceProviderID
	 * @param caseIdentifier - {@link Case#getCaseIdentifier()}, not
	 * <code>null</code>;
	 * @param personalID - {@link User#getPersonalID()} of {@link User}
	 * who can view {@link Case}s and is registered on required service,
	 * not <code>null</code>;
	 * @return data about {@link Case} and its {@link Task}s,
	 * defined by criteria, or <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public CaseStep[] getCaseEntries(String serviceProviderID,
			String caseIdentifier, String personalID);

	/**
	 *
	 * <p>Queries X-Road for data about labels of XForm</p>
	 * @param serviceProviderID
	 * @param xFormID id of XForm, not <code>null</code>;
	 * @param language - {@link Locale#getLanguage()},
	 * not <code>null</code>;
	 * @return map of selected XForm keys + labels by given language or
	 * <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public LabelPair[] getXFormsLabels(String serviceProviderID,
			String xFormID, String language);

	/**
	 *
	 * <p>Queries X-Road for data about {@link Message}s of given
	 * {@link User}.</p>
	 * @param serviceProviderID
	 * @param personalID of {@link User} to get messages of, not <code>null</code>;
	 * @return data about {@link Message}s or {@link User} or
	 * <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Message[] getMessageEntries(String serviceProviderID,
			String personalID);

	/**
	 *
	 * <p>Queries X-Road for data about {@link Message}s of given
	 * {@link User}.</p>
	 * @param serviceProviderID
	 * @param user to get messages of, not <code>null</code>;
	 * @param caseId TODO
	 * @return data about {@link Message}s or {@link User} or
	 * <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Message[] getMessageEntries(String serviceProviderID, User user, String caseId);

	/**
	 *
	 * <p>Queries X-Road for processed {@link Document} of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.</p>
	 * @param serviceProviderID
	 * @param documentID - {@link TaskInstance#getId()},
	 * not <code>null</code>;
	 * @param user who can access {@link Document}, not null;
	 * @return processed {@link Document} of {@link XForm} or
	 * <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public InputStream getProcessedDocument(
			String serviceProviderID,
			String documentID,
			User user);

	/**
	 *
	 * <p>Queries X-Road for processed {@link Document} of {@link XForm}
	 * by given {@link User} and {@link XForm#getFormId()}.</p>
	 * @param serviceProviderID
	 * @param documentID - {@link XForm#getFormId()},
	 * not <code>null</code>;
	 * @param userId is {@link User#getPersonalID()} of {@link User}
	 * who can access {@link Document}, not <code>null</code>;
	 * @return processed {@link Document} of {@link XForm} or
	 * <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public InputStream getProcessedDocument(
			String serviceProviderID,
			String documentID,
			String userId);

	/**
	 *
	 * <p>Queries X-Road for processed {@link Document} of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.</p>
	 * @param serviceProviderID
	 * @param documentID - {@link TaskInstance#getId()},
	 * not <code>null</code>;
	 * @param user who can access {@link Document}, not null;
	 * @return processed {@link Document} of {@link XForm} or
	 * <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Document getProcessedDocumentInXML(String serviceProviderID,
			String documentID, User user);

	/**
	 *
	 * <p>Queries X-Road for processed {@link Document} of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.</p>
	 * @param serviceProviderID
	 * @param documentID - {@link TaskInstance#getId()},
	 * not <code>null</code>;
	 * @param userId is {@link User#getPersonalID()} of {@link User}
	 * who can access {@link Document}, not <code>null</code>;
	 * @return processed {@link Document} of {@link XForm} or
	 * <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Document getProcessedDocumentInXML(String serviceProviderID,
			String documentID, String userID);

	/**
	 *
	 * <p>Queries X-Road for {@link Document} template of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.</p>
	 * @param serviceProviderID
	 * @param documentID - {@link TaskInstance#getId()},
	 * not <code>null</code>;
	 * @param user who can access {@link Document}, not null;
	 * @return stream of {@link Document} template or <code>null</code>
	 * on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public InputStream getXFormsDocumentTemplate(String serviceProviderID,
			String documentID, User user);

	/**
	 *
	 * <p>Queries X-Road for {@link Document} template of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.</p>
	 * @param serviceProviderID
	 * @param documentID - {@link TaskInstance#getId()},
	 * not <code>null</code>;
	 * @param userID is {@link User#getPersonalID()} of {@link User}
	 * who can access {@link Document}, not <code>null</code>;
	 * @return stream of {@link Document} template or <code>null</code>
	 * on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public InputStream getXFormsDocumentTemplate(String serviceProviderID,
			String documentID, String userID);

	/**
	 *
	 * <p>Queries X-Road for {@link Document} template of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.</p>
	 * @param serviceProviderID
	 * @param documentID - {@link TaskInstance#getId()},
	 * not <code>null</code>;
	 * @param user who can access {@link Document}, not null;
	 * @return converted stream of {@link Document} template or
	 * <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Document getXFormsDocumentTemplateInXML(String serviceProviderID,
			String documentID, User user);

	/**
	 *
	 * <p>Queries X-Road for {@link Document} template of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.</p>
	 * @param serviceProviderID
	 * @param documentID - {@link TaskInstance#getId()},
	 * not <code>null</code>;
	 * @param userID is {@link User#getPersonalID()} of {@link User}
	 * who can access {@link Document}, not <code>null</code>;
	 * @return converted stream of {@link Document} template or
	 * <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Document getXFormsDocumentTemplateInXML(String serviceProviderID,
			String documentID, String userID);

	/**
	 *
	 * <p>Queries X-Road for {@link Document} template of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.
	 * {@link Document} will be filled by initial data by given
	 * {@link User#getPersonalID()}.</p>
	 * @param applicationID is id of
	 * is.idega.idegaweb.egov.application.data.Application, not <code>null</code>;
	 * @param taskID is {@link TaskInstance#getId()}, which {@link Document}
	 * is required, when <code>null</code>, then first task of new
	 * {@link ProcessInstance} will be provided;
	 * @param userId is {@link User#getPersonalID()} of user, who can submit
	 * {@link Document}, not <code>null</code>;
	 * @param language of form to be translated to,
	 * language of required service will be used when <code>null</code>;
	 * @return stream of {@link Document} filled with starting values and ready for
	 * XSLT transformation or <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public InputStream getPreffiledDocument(
			String applicationID,
			String taskID,
			String userId,
			String language);

	/**
	 *
	 * <p>Queries X-Road for {@link Document} template of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.
	 * {@link Document} will be filled by initial data by given
	 * {@link User#getPersonalID()}.</p>
	 * @param applicationID is id of
	 * is.idega.idegaweb.egov.application.data.Application, not <code>null</code>;
	 * @param taskID is {@link TaskInstance#getId()}, which {@link Document}
	 * is required, when <code>null</code>, then first task of new
	 * {@link ProcessInstance} will be provided;
	 * @param user is {@link User}, who can submit
	 * {@link Document}, not <code>null</code>;
	 * @param language of form to be translated to,
	 * language of required service will be used when <code>null</code>;
	 * @return stream of {@link Document} filled with starting values and ready for
	 * XSLT transformation or <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public InputStream getPreffiledDocument(
			String applicationID,
			String taskID,
			User user,
			String language);

	/**
	 *
	 * <p>Queries X-Road for {@link Document} template of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.
	 * {@link Document} will be filled by initial data by given
	 * {@link User#getPersonalID()}.</p>
	 * @param applicationID is id of
	 * is.idega.idegaweb.egov.application.data.Application, not <code>null</code>;
	 * @param taskID is {@link TaskInstance#getId()}, which {@link Document}
	 * is required, when <code>null</code>, then first task of new
	 * {@link ProcessInstance} will be provided;
	 * @param userId is {@link User#getPersonalID()} of user, who can submit
	 * {@link Document}, not <code>null</code>;
	 * @param language of form to be translated to,
	 * language of required service will be used when <code>null</code>;
	 * @return {@link Document} filled with starting values and ready for
	 * XSLT transformation or <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Document getPreffiledDocumentInXML(
			String applicationID,
			String taskID,
			String userId,
			String language);

	/**
	 *
	 * <p>Queries X-Road for {@link Document} template of {@link XForm}
	 * by given {@link User} and {@link TaskInstance#getId()}.
	 * {@link Document} will be filled by initial data by given
	 * {@link User#getPersonalID()}.</p>
	 * @param applicationID is id of
	 * is.idega.idegaweb.egov.application.data.Application, not <code>null</code>;
	 * @param taskID is {@link TaskInstance#getId()}, which {@link Document}
	 * is required, when <code>null</code>, then first task of new
	 * {@link ProcessInstance} will be provided;
	 * @param user is {@link User}, who can submit
	 * {@link Document}, not <code>null</code>;
	 * @param language of form to be translated to,
	 * language of required service will be used when <code>null</code>;
	 * @return {@link Document} filled with starting values and ready for
	 * XSLT transformation or <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Document getPreffiledDocumentInXML(
			String applicationID,
			String taskID,
			User user,
			String language);

	/**
	 *
	 * <p>Queries X-Road for notifications for given user</p>
	 * @param user to get notifications for, not <code>null</code>;
	 * @return notifications for {@link User} or <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Notification[] getNotifications(User user, String serviceProviderId);

	/**
	 *
	 * <p>Marks {@link Notification} as read in remote server.</p>
	 * @param user who's {@link NotificationReceiverEntity}s should be searched, not <code>null</code>;
	 * @param serviceProviderId
	 * @return <code>true</code> on sucess, <code>false</code> otherwise;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public boolean doMarkNotificationAsRead(
			User user,
			String serviceProviderId,
			String notificationId,
			boolean markAsRead);


	/**
	 *
	 * <p>Marks {@link com.idega.block.process.data.Case} as read on remote system.</p>
	 * @param user who's {@link com.idega.block.process.data.Case}s should be searche, not <code>null</code>;
	 * @param serviceProviderId
	 * @param caseId is {@link com.idega.block.process.data.Case#getPrimaryKey()} in remote system, not <code>null</code>;
	 * @param markAsRead <code>true</code> if mark as read required,
	 * <code>false</code> if mark as unread required;
	 * @return <code>true</code> if marked, <code>false</code> otherwise;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public boolean doMarkCaseAsRead(User user, String serviceProviderId,
			String caseId, boolean markAsRead);

	/**
	 * 
	 * @param user to get methods for, not <code>null</code>;
	 * @param serviceProviderId to get methods for, not <code>null</code>;
	 * @return names of accessible methods in service or <code>null</code> 
	 * on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	String[] getMethodsList(User user, String serviceProviderId);
}
