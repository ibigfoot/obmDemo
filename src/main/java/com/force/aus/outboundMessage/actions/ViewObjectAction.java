package com.force.aus.outboundMessage.actions;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.entity.AccountWrapper;
import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.exceptions.PartnerAPIException;
import com.force.aus.outboundMessage.listeners.EMFListener;
import com.force.aus.outboundMessage.partner.PartnerWSDLService;
import com.sforce.ws.ConnectionException;

public class ViewObjectAction extends BaseOBMAction{

	private Logger logger;
	private EntityManager em;
	private String objectId;
	private String messageId;
	private String errorMessage;
	private AccountWrapper account;
	private String accountName;
	
	public String execute() {
		
		logger = LoggerFactory.getLogger(ViewObjectAction.class);
		logger.info("Processing Action {}",ViewObjectAction.class);
		
		PartnerWSDLService service = new PartnerWSDLService();
		try {
			
			em = EMFListener.createEntityManager();
			Query q = em.createQuery("from ReceivedMessage where id="+messageId);
			ReceivedMessage message = (ReceivedMessage)q.getSingleResult();
			
			account = service.getAccount(objectId, message);
		
		} catch (PartnerAPIException pae) {
			errorMessage = pae.getMessage();
			pae.printStackTrace();
		} catch (ConnectionException ce) {
			errorMessage = ce.getMessage();
			ce.printStackTrace();
		}
		
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
