package com.force.aus.outboundMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.force.aus.outboundMessage.entity.ModifiedObject;
import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.listeners.EMFListener;
import com.force.aus.wsdl.AccountNotification;
import com.force.aus.wsdl.NotificationPort;

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
		message.setXmlMessage((String)request.getAttribute("RAW_XML"));
		
		Set<ModifiedObject> modifiedObjects = new HashSet<ModifiedObject>();
		for(AccountNotification an : notificationList) {
			ModifiedObject mo= new ModifiedObject();
			mo.setObjectId(an.getSObject().getId());
			mo.setReceivedMessage(message);
			modifiedObjects.add(mo);
		}
		message.setModifiedObjects(modifiedObjects);
	
		entityManager.persist(message);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		logger.info("Persisted {} objects",notificationList.size());

		return (notificationList != null ? true : false);
	}
}
