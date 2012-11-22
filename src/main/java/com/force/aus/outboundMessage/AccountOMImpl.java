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

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.ENCODED)
public class AccountOMImpl {

	private Logger logger;
	
	public AccountOMImpl() {
		logger = LoggerFactory.getLogger(AccountOMImpl.class);
	}
	
	@WebMethod
	public NotificationsResponse handleOutboundMessage(Notifications notifications) {
		
		logger.info("Have received the notfication");
		logger.info("OrgID ["+notifications.getOrganizationId()+"]");
		logger.info("SessionID ["+notifications.getSessionId()+"]");
		logger.info("PartnerURL ["+notifications.getPartnerUrl()+"]");
		logger.info("ActionID ["+notifications.getActionId()+"]");
		
		NotificationsResponse response = new NotificationsResponse();
		response.setAck(Boolean.TRUE);
		
		return response;
		
	}
	
}
