package com.force.aus.outboundMessage.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.opensymphony.xwork2.ActionSupport;

public class HomePageAction extends ActionSupport{

	private Logger logger;
	private List<ReceivedMessage> messages;
	private EntityManager em;
	
	public String execute() {
	
		logger = LoggerFactory.getLogger(HomePageAction.class);
		
		logger.debug("Handling action with %1", HomePageAction.class);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.force.aus.outboundMessage.obmpu");
		em = emf.createEntityManager();
		
		return SUCCESS;
	}
	
	public List<ReceivedMessage> getMessages() {
		
		Query q = em.createQuery("from ReceivedMessage");
		messages = (List<ReceivedMessage>)q.getResultList();
		
		return messages;
	}
	
}
