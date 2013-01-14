package com.force.aus.outboundMessage.actions;

import javax.persistence.EntityManager;

import com.force.aus.outboundMessage.listeners.EMFListener;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseOBMAction extends ActionSupport {

	protected EntityManager getEntityManager() {
		return EMFListener.createEntityManager();
	}
}
