package com.force.aus.outboundMessage.actions;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class OAuthCallbackAction extends BaseOBMAction {

	@Override
	public String doExecute() {
		
		addActionMessage("We have received a call for the OAuth Callback - need to figure out how to access stuff");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Enumeration<String> e = request.getAttributeNames();
		while(e.hasMoreElements()) {
			String element = e.nextElement();
			logger.info("Element {} value {}", element, request.getAttribute(element));
		}
		return SUCCESS;
	}
	
}
