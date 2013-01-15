package com.force.aus.outboundMessage.actions;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.entity.UserInfo;
import com.force.aus.outboundMessage.exceptions.PartnerAPIException;
import com.force.aus.outboundMessage.partner.PartnerWSDLService;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.ws.ConnectionException;


public class ViewOBMAction extends BaseOBMAction {

	private Logger logger;
	private ReceivedMessage message;
	private String messageId;
	private GetUserInfoResult userInfo;
	private String errorMessage;

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
		try {
			PartnerWSDLService service = new PartnerWSDLService();
			userInfo = service.getUserInfo(message);
		} catch (ConnectionException ce) {
			errorMessage = "The Partner API interface failed to retrieve the necessary details.\n"+ce.getMessage();
			logger.warn("The partner API failed to respond correctly {}", ce.getMessage());
			ce.printStackTrace();
		}
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
	
	
}
