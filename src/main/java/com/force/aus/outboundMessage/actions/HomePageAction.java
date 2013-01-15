package com.force.aus.outboundMessage.actions;

import java.util.List;

import com.force.aus.outboundMessage.entity.ReceivedMessage;

public class HomePageAction extends BaseOBMAction{

	/**
	 * serialVersionUID = -3644741437299668608L;
	 */
	private static final long serialVersionUID = -3644741437299668608L;
	private List<ReceivedMessage> messages;
	
	public String execute() {

		initialise(HomePageAction.class.getName());
		
		messages = (List<ReceivedMessage>)doListQuery("from ReceivedMessage");

		cleanUp();
		return SUCCESS;
	}
	
	public List<ReceivedMessage> getMessages() {
		return messages;
	}

	
}
