package com.force.aus.outboundMessage.actions;

public class OAuthCallbackAction extends BaseOBMAction {

	@Override
	public String doExecute() {
		
		addActionMessage("We have received a call for the OAuth Callback - need to figure out how to access stuff");
		
		return SUCCESS;
	}
	
}
