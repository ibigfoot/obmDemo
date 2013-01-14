package com.force.aus.outboundMessage.listeners;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class EMFListener implements ServletContextListener {

	private static EntityManagerFactory emf;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory("com.force.aus.outboundMessage.obmpu");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if(emf.isOpen()) {
			emf.close();
		}
	}
	
	public static EntityManager createEntityManager() {
		if(emf == null) {
			throw new IllegalStateException("Context is not initialised yet");
		}
		return emf.createEntityManager();
	}
	
}
