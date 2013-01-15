package com.force.aus.outboundMessage.actions;


public class DeleteOBMAction extends BaseOBMAction {

	private String messageId;
	
	public String execute() {
		
		initialise(DeleteOBMAction.class.getName());
		
		doExecuteQuery("delete from ModifiedObject where receivedmessage_id ="+messageId);
		doExecuteQuery("delete ReceivedMessage where id ="+messageId);
		
		cleanUp();
		return SUCCESS;
		
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	
}
