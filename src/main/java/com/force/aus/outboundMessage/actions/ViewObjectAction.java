package com.force.aus.outboundMessage.actions;

import com.force.aus.outboundMessage.entity.AccountWrapper;
import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.exceptions.PartnerAPIException;
import com.force.aus.outboundMessage.partner.PartnerWSDLService;
import com.sforce.ws.ConnectionException;

public class ViewObjectAction extends BaseOBMAction{

	/**
	 * serialVersionUID = 3458656659518181880L;
	 */
	private static final long serialVersionUID = 3458656659518181880L;
	private String objectId;
	private String messageId;
	private String errorMessage;
	private AccountWrapper account;
	
	public String execute() {
		
		initialise(ViewObjectAction.class.getName());
		
		try {
			ReceivedMessage message = (ReceivedMessage)doSingleQuery("from ReceivedMessage where id="+messageId);
			
			PartnerWSDLService service = new PartnerWSDLService();
			account = service.getAccount(objectId, message);
		
		} catch (PartnerAPIException pae) {
			errorMessage = pae.getMessage();
			pae.printStackTrace();
		} catch (ConnectionException ce) {
			errorMessage = ce.getMessage();
			ce.printStackTrace();
		}
		
		cleanUp();
		return SUCCESS;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public AccountWrapper getAccount() {
		return account;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getMessage() {
		return errorMessage;
	}
	
	
}
