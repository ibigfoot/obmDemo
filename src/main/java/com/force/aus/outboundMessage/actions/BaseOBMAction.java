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

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.listeners.EMFListener;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseOBMAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8515642582004576369L;
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
