package com.force.aus.outboundMessage.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class StrutsAction extends ActionSupport{

	private Logger logger;
	
	private String hello = "Hello from Action!";
	
	public String execute() throws Exception {
		logger = LoggerFactory.getLogger(StrutsAction.class);
		logger.info("We have a struts action!");
		
		return SUCCESS;
	}
	
	public String getHello() {
		return hello;
	}

	
	public void setHello(String hello) {
		this.hello = hello;
	}
}
