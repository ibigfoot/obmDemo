package com.force.aus.outboundMessage.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.entity.ReceivedMessage;

public class HomePageAction extends BaseOBMAction{

	private List<ReceivedMessage> messages;
	private Logger logger;
	
	public String execute() {

		logger = LoggerFactory.getLogger(HomePageAction.class);
		logger.info("~~Handling action with {}", HomePageAction.class);
		
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("from ReceivedMessage");
		messages = (List<ReceivedMessage>)q.getResultList();
		em.getTransaction().commit();
		em.close();
		return SUCCESS;
	}
	
	public List<ReceivedMessage> getMessages() {
		return messages;
	}

	
}
