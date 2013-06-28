/**
 * @(#)RemoteApplicationsViewer.java    1.0.0 1:22:10 PM
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
package com.idega.xroad.client.presentation;

import java.util.logging.Level;

import javax.xml.transform.TransformerException;

import org.chiba.xml.xslt.TransformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import com.idega.idegaweb.IWMainApplication;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.SubmitButton;
import com.idega.util.ArrayUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;
import com.idega.xformsmanager.generator.ComponentsGenerator;
import com.idega.xroad.client.business.XRoadServices;
import com.idega.xroad.client.wsdl.EhubserviceServiceStub.ServiceEntry_type0;

/**
 * <p>Component for listing available Applications in remote server.</p>
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 May 23, 2013
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public class RemoteApplicationsViewer extends Block {

	public static final String PARAMETER_ACTION = "prm_action";
	public static final int ACTION_NOTHING = 0;
	public static final int ACTION_START = 1;
	
	public static final String PARAMETER_APPLICATION = "prm_application";

	private TransformerService transformerService = null;
	
	@Autowired
	private XRoadServices xRoadServices;
	
	protected XRoadServices getXRoadServices() {
		if (this.xRoadServices == null) {
			ELUtil.getInstance().autowire(this);
		}
		
		return this.xRoadServices;
	}
	
	@Autowired
	private ComponentsGenerator componentGenerator;
	
	protected ComponentsGenerator getComponentsGenerator(IWContext iwc) {
		if (this.componentGenerator == null) {
			ELUtil.getInstance().autowire(this);
		}
		
		if (!this.componentGenerator.isInstantiated()) {
			com.idega.idegaweb.IWMainApplication iwma = null;
			if(iwc != null)
				iwma = iwc.getIWMainApplication();
			else
				iwma = IWMainApplication.getDefaultIWMainApplication();
			
			TransformerService transformerService = (TransformerService) iwma.getAttribute(
							TransformerService.class.getName());
			this.componentGenerator.init(iwma, transformerService);
		}
		
		return this.componentGenerator;
	}
	
	@Override
	public void main(IWContext iwc) throws Exception {
		Layer container = new Layer();
		add(container);
		
		Form form = new Form();
		container.add(form);
		
		switch (parseAction(iwc)) {
			case ACTION_NOTHING:
				form.add(getApplicationsList(iwc, "0"));
				form.add(getSubmitButton(iwc));
				break;

			case ACTION_START:
				form.add(getRemoteFormViewer(iwc, 
						iwc.getParameter(PARAMETER_APPLICATION)));
				break;
		}
	}
	
	protected RemoteFormViewer getRemoteFormViewer(
			IWContext iwc, String applicationId) {
		if (StringUtil.isEmpty(applicationId)) {
			return null;
		}
		
		Document xformDocument = getXRoadServices().getPreffiledDocumentInXML(
				applicationId, 
				null, 
				iwc.getCurrentUser(), 
				iwc.getCurrentLocale().getLanguage());
		if (xformDocument == null) {
			return null;
		}
		
//		return xformDocument;
		
//		Document representation = getComponentsGenerator(iwc)
//				.generateHtmlRepresentation(xformDocument);
//		if (representation == null) {
//			return null;
//		}
//		
		RemoteFormViewer rfv = new RemoteFormViewer();
		rfv.setXFormsDocument(xformDocument);
		return rfv;
	}
	
	protected SubmitButton getSubmitButton(IWContext iwc) {
		return new SubmitButton(getLocalizedString("submit", "Submit", iwc), 
				PARAMETER_ACTION, String.valueOf(ACTION_START));
	}
	
	protected DropdownMenu getApplicationsList(IWContext iwc, 
			String serviceProviderId) {
		DropdownMenu menu = new DropdownMenu(PARAMETER_APPLICATION);
		menu.addMenuElementFirst(
				"-1", 
				getLocalizedString("select_application", "Select application", iwc));
		
		ServiceEntry_type0[] entries = getXRoadServices().getServiceEntries(
				serviceProviderId, iwc.getCurrentUser());
		if (ArrayUtil.isEmpty(entries)) {
			return menu;
		}
		
		for (ServiceEntry_type0 entry: entries) {
			menu.addMenuElement(entry.getId(), entry.getNameLabel().getText());
		}
		
		return menu;
	}
	
	public javax.xml.transform.Transformer getTransformer() {
		try {
			return getTransformerService().getTransformer();
		} catch (TransformerException e) {
			getLogger().log(Level.WARNING, "Unable to get transformer: ", e);
		}
		
		return null;
	}
	
	public TransformerService getTransformerService() {
		return transformerService;
	}
	
	protected int parseAction(IWContext iwc) {
		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_NOTHING;
	}
}
