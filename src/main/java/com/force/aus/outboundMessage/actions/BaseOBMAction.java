/*
 * Copyright (c) 2013, salesforce.com, inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice, this list of conditions and the
 *    following disclaimer.
 *
 *    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 *    the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 *    Neither the name of salesforce.com, inc. nor the names of its contributors may be used to endorse or
 *    promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.force.aus.outboundMessage.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.listeners.EMFListener;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * BaseOBMAction handles common action tasks.
 * Manages transactions and creation and closing of EntityManager.
 * Also provides method wrappers for JPA queries.
 * Instantiates and handles most of the logging.
 * 
 * @author tsellers@salesforce.com
 *
 */
public abstract class BaseOBMAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8515642582004576369L;
	protected EntityManager em;
	protected Logger logger;
	
	private static String ST = "sessionTest";
	
	private Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;
		if(session == null) {
			logger.info("Creating a new session");
			session = new HashMap<String,Object>();
			session.put(ST, new Integer(0));
		}
		Integer count = (Integer)session.get(ST);
		count++;
		session.put(ST, count);
	}
	
	public Map<String,Object> getSession() {
		return session;
	}

	public String execute() {
	
		
		
		logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Executing Action {}", this.getClass());
		
		logger.info("WE have session count {}", session.get(ST));
		
		em = EMFListener.createEntityManager();
		
		em.getTransaction().begin();
		// call method provided concrete by implementation classes
		String action = doExecute();
		em.getTransaction().commit();
		em.close();
		
		logger.info("Action {} complete, returning {}", this.getClass(), action);
		
		return action;
	}
	
	/**
	 * Method for extending action classes to implement
	 * @return
	 */
	public abstract String doExecute();
	
	/**
	 * Get an EntityManager from the EMFListener class.
	 * 
	 * @return
	 */
	protected EntityManager getEntityManager() {
		return EMFListener.createEntityManager();
	}
	
	/**
	 * Return an un-typed list from executing the JPA getResultList() query. 
	 * 
	 * @param query
	 * @return
	 */
	protected List doListQuery(String query) {
		
		logger.info("About to do ListQuery {}",query);
		List retVal = em.createQuery(query).getResultList();
		
		return retVal;

	}
	/**
	 * Returns an Object from executing the JPA getSingleResult() query.
	 * @param query
	 * @return
	 */
	protected Object doSingleQuery(String query) {
		
		logger.info("About to do SingleQuery {}", query);			
		Object obj = em.createQuery(query).getSingleResult();


		return obj;
	}
	
	/**
	 * Returns int that is the result of a JPA executeUpdate() query.
	 * 
	 * @param query
	 * @return
	 */
	protected int doExecuteQuery(String query) {
	
		logger.info("About to execute query {}", query);		
		int retVal = em.createQuery(query).executeUpdate();
		
		return retVal;
		
	}	
}
