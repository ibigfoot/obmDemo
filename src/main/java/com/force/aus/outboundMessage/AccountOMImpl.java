/*
 * Copyright (c) 2013, salesforce.com, inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice, this list of conditions and the
 *    following disclaimer.
 *
 *    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 *    the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 *    Neither the name of salesforce.com, inc. nor the names of its contributors may be used to endorse or
 *    promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.force.aus.outboundMessage;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.comparators.ModifiedObjectComparator;
import com.force.aus.outboundMessage.entity.ModifiedObject;
import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.listeners.EMFListener;
import com.force.aus.outboundMessage.partner.PartnerWSDLService;
import com.force.aus.wsdl.AccountNotification;
import com.force.aus.wsdl.NotificationPort;
import com.sforce.ws.ConnectionException;

@WebService(targetNamespace="http://soap.sforce.com/2005/09/outbound")
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
@HandlerChain(file="handler-chain.xml")
public class AccountOMImpl implements NotificationPort{

	@Resource
	private WebServiceContext context;
	
	private Logger logger;
	
	public AccountOMImpl() {
		logger = LoggerFactory.getLogger(AccountOMImpl.class);
	}

	@WebMethod
	@WebResult(name = "Ack", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
	@RequestWrapper(localName = "notifications", targetNamespace = "http://soap.sforce.com/2005/09/outbound", className = "com.force.aus.wsdl.Notifications")
	@ResponseWrapper(localName = "notificationsResponse", targetNamespace = "http://soap.sforce.com/2005/09/outbound", className = "com.force.aus.wsdl.NotificationsResponse")
	public boolean notifications(
			@WebParam(name = "OrganizationId", targetNamespace = "http://soap.sforce.com/2005/09/outbound") String orgId,
			@WebParam(name = "ActionId", targetNamespace = "http://soap.sforce.com/2005/09/outbound") String actionId,
			@WebParam(name = "SessionId", targetNamespace = "http://soap.sforce.com/2005/09/outbound") String sessionId,
			@WebParam(name = "EnterpriseUrl", targetNamespace = "http://soap.sforce.com/2005/09/outbound") String enterpriseURL,
			@WebParam(name = "PartnerUrl", targetNamespace = "http://soap.sforce.com/2005/09/outbound") String partnerURL,
			@WebParam(name = "Notification", targetNamespace = "http://soap.sforce.com/2005/09/outbound") List<AccountNotification> notificationList) {
		
		
		EntityManager entityManager = EMFListener.createEntityManager();
		logger.info("Webservice handling of Outbound Message using class {}", AccountOMImpl.class);
		logger.info("Processing {} changed objects for Org {}. Have SessionId {}", notificationList.size(), orgId, sessionId);
		
		HttpServletRequest request = (HttpServletRequest)context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
		
		entityManager.getTransaction().begin();
		ReceivedMessage message = new ReceivedMessage();
		message.setActionId(actionId);
		message.setDateReceived(Calendar.getInstance().getTime());
		message.setEnterpriseURL(enterpriseURL);
		message.setOrgId(orgId);
		message.setPartnerURL(partnerURL);
		message.setSessionId(sessionId);
		message.setXmlMessage(((String)request.getAttribute("RAW_XML")).trim());
		
		Set<ModifiedObject> modifiedObjects = new HashSet<>();
		for(AccountNotification an : notificationList) {
			ModifiedObject mo= new ModifiedObject();
			mo.setObjectId(an.getSObject().getId());
			mo.setReceivedMessage(message);
			modifiedObjects.add(mo);
		}
		message.setModifiedObjects(modifiedObjects);

		PartnerWSDLService service = new PartnerWSDLService();
		try {
			service.populateObjectNames(message);
		} catch (ConnectionException ce) {
			logger.error("Unable to populate object names for received message {}", ce.getMessage());
			ce.printStackTrace();
		}
		entityManager.persist(message);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		logger.info("Persisted {} objects",notificationList.size());

		return (notificationList != null ? true : false);
	}
}
