package com.force.aus.outboundMessage.actions;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.entity.ReceivedMessage;


public class EditOBMAction extends BaseOBMAction {

	private Logger logger;
	private ReceivedMessage message;
	private String messageId;
	
	
	public String execute() {
	
		logger = LoggerFactory.getLogger(EditOBMAction.class);
		logger.info("Entered action {} for message {}", EditOBMAction.class, messageId);
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		Query q = em.createQuery("from ReceivedMessage where id="+messageId);
		message = (ReceivedMessage)q.getSingleResult();
		em.getTransaction().commit();
		em.close();
		
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
	
	
	
}
