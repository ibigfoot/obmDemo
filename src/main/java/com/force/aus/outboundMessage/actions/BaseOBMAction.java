package com.force.aus.outboundMessage.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseOBMAction extends ActionSupport {
	
	private String ENTITY_MANAGER = "entity_manager";

	protected EntityManager getEntityManager() {
		
		EntityManager em = (EntityManager)ActionContext.getContext().getSession().get(ENTITY_MANAGER);
		if (em == null) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.force.aus.outboundMessage.obmpu");
			em = emf.createEntityManager();
			if(em == null) 
				throw new RuntimeException("Unable to create an Entity Manager.");
			ActionContext.getContext().getSession().put(ENTITY_MANAGER, em);
		}
		return em;
	}
}
