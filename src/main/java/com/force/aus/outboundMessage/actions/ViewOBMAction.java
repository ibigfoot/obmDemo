package com.force.aus.outboundMessage.actions;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.entity.UserInfo;
import com.force.aus.outboundMessage.partner.PartnerWSDLService;


public class ViewOBMAction extends BaseOBMAction {

	private Logger logger;
	private ReceivedMessage message;
	private String messageId;
	private UserInfo userInfo;
	
	public String execute() {
	
		logger = LoggerFactory.getLogger(ViewOBMAction.class);
		logger.info("Entered action {} for message {}", ViewOBMAction.class, messageId);
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		Query q = em.createQuery("from ReceivedMessage where id="+messageId);
		message = (ReceivedMessage)q.getSingleResult();
		em.getTransaction().commit();
		em.close();
		
		populateUserInfo();
		
		return SUCCESS;		
	}
	/*
	 * Uses the SF Partner API to find out user information
	 */
	private void populateUserInfo() {
		PartnerWSDLService service = new PartnerWSDLService();
		userInfo = service.getUserInfo(message);
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
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	
	
}
