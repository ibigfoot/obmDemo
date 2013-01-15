package com.force.aus.outboundMessage.actions;

import org.slf4j.Logger;

import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.partner.PartnerWSDLService;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.ws.ConnectionException;


public class ViewOBMAction extends BaseOBMAction {

	private ReceivedMessage message;
	private String messageId;
	private GetUserInfoResult userInfo;
	private String errorMessage;

	public String execute() {
	
		initialise(ViewObjectAction.class.getName());

		message = (ReceivedMessage)doSingleQuery("from ReceivedMessage where id="+messageId);

		try {
			PartnerWSDLService service = new PartnerWSDLService();
			userInfo = service.getUserInfo(message);
		} catch (ConnectionException ce) {
			errorMessage = "The Partner API interface failed to retrieve the necessary details.\n"+ce.getMessage();
			ce.printStackTrace();
		}
		
		cleanUp();
		return SUCCESS;		
	}
	
	public ReceivedMessage getMessage() {
		return message;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	public GetUserInfoResult getUserInfo() {
		return userInfo;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
}
