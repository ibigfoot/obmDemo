package com.force.aus.outboundMessage.actions;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class OAuthCallbackAction extends BaseOBMAction {

	private String code;
	private String error;
	private String error_description;
	
	@Override
	public String doExecute() {
		
		addActionMessage("We have received a call for the OAuth Callback - need to figure out how to access stuff");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Enumeration<String> e = request.getAttributeNames();
		while(e.hasMoreElements()) {
			String element = e.nextElement();
			logger.info("Element {} value {}", element, request.getAttribute(element));
		}
		
		logger.info("do we have code {}", code != null ? code : "null");
		
		if(code != null && code.length() > 0) {
			addActionMessage("Code ["+code+"]");
		}
		if(error != null && error.length() > 0) {
			addActionError("Error ["+error+"]");
		}
		if(error_description != null && error_description.length() > 0) {
			addActionError("Error Desc ["+error_description+"]");
		}
		
		return SUCCESS;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}
	
	
	
}
