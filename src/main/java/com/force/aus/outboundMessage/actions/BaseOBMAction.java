package com.force.aus.outboundMessage.actions;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.listeners.EMFListener;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseOBMAction extends ActionSupport {

	protected EntityManager em;
	protected Logger logger;
	
	protected void initialise(String className) {
		if(em == null) {
			em = EMFListener.createEntityManager();
		}	
		logger = LoggerFactory.getLogger(className);
		logger.info("~~Initialising for ActionHandler {}", className);
	}
	
	protected void cleanUp() {
		if(em != null && em.isOpen()) {
			em.close();
		}
	}
	protected EntityManager getEntityManager() {
		return EMFListener.createEntityManager();
	}
	
	protected List doListQuery(String query) {
		
		em.getTransaction().begin();
		List retVal = em.createQuery(query).getResultList();
		em.getTransaction().commit();
		
		return retVal;
		
	}
	
	protected Object doSingleQuery(String query) {
		
		em.getTransaction().begin();
		Object obj = em.createQuery(query).getSingleResult();
		em.getTransaction().commit();
		
		return obj;
	}
	
	protected int doExecuteQuery(String query) {
	
		em.getTransaction().begin();
		int retVal = em.createQuery(query).executeUpdate();
		em.getTransaction().commit();
		
		return retVal;
		
	}
	
	
}
