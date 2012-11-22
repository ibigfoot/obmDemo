package com.force.aus.outboundMessage;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.wsdl.AccountNotification;
import com.force.aus.wsdl.NotificationPort;

@WebService(targetNamespace="http://soap.sforce.com/2005/09/outbound")
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)

public class AccountOMImpl implements NotificationPort{

	private Logger logger;
	
	public AccountOMImpl() {
		logger = LoggerFactory.getLogger(AccountOMImpl.class);
	}

	@WebMethod
	@WebResult(name = "Ack", targetNamespace = "http://soap.sforce.com/2005/09/outbound")
	@RequestWrapper(localName = "notifications", targetNamespace = "http://soap.sforce.com/2005/09/outbound", className = "com.force.aus.wsdl.Notifications")
	@ResponseWrapper(localName = "notificationsResponse", targetNamespace = "http://soap.sforce.com/2005/09/outbound", className = "com.force.aus.wsdl.NotificationsResponse")
	public boolean notifications(
			@WebParam(name = "OrganizationId", targetNamespace = "http://soap.sforce.com/2005/09/outbound") String arg0,
			@WebParam(name = "ActionId", targetNamespace = "http://soap.sforce.com/2005/09/outbound") String arg1,
			@WebParam(name = "SessionId", targetNamespace = "http://soap.sforce.com/2005/09/outbound") String arg2,
			@WebParam(name = "EnterpriseUrl", targetNamespace = "http://soap.sforce.com/2005/09/outbound") String arg3,
			@WebParam(name = "PartnerUrl", targetNamespace = "http://soap.sforce.com/2005/09/outbound") String arg4,
			@WebParam(name = "Notification", targetNamespace = "http://soap.sforce.com/2005/09/outbound") List<AccountNotification> arg5) {
		// TODO Auto-generated method stub
		
		logger.info("Have received a bunch of stuff this time");
		logger.info("Have account notifications ["+(arg5 != null ? true : false) +"]");
		return true;
	}

	
	
	
}
