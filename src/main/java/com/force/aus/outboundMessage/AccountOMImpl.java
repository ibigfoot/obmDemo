package com.force.aus.outboundMessage;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.wsdl.Notifications;
import com.force.aus.wsdl.NotificationsResponse;

@WebService(targetNamespace="http://soap.sforce.com/2005/09/outbound")
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)

public class AccountOMImpl {

	private Logger logger;
	
	public AccountOMImpl() {
		logger = LoggerFactory.getLogger(AccountOMImpl.class);
	}
	
	public NotificationsResponse notifications(Notifications notifications) {
		
		logger.info("Have received the notfication");
		logger.info("Notifications not null ["+(notifications != null ? true : false)+"]");

		NotificationsResponse response = new NotificationsResponse();
		response.setAck(Boolean.TRUE);
		
		return response;
		
	}
	
	
}
