package com.force.aus.outboundMessage.actions;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteOBMAction extends BaseOBMAction {

	private Logger logger;
	private String messageId;
	
	public String execute() {
		
		logger = LoggerFactory.getLogger(DeleteOBMAction.class);
		logger.info("Handling action with class {}", DeleteOBMAction.class);
		
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("delete ReceivedMessage where id ="+messageId);
		q.executeUpdate();
		em.getTransaction().commit();
		em.close();
		
		return SUCCESS;
		
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	
}
