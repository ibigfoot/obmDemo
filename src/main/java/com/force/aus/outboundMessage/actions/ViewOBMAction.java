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

import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.partner.PartnerWSDLService;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.ws.ConnectionException;


public class ViewOBMAction extends BaseOBMAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8648555807094867691L;
	private ReceivedMessage message;
	private String messageId;
	private GetUserInfoResult userInfo;
	private String errorMessage;

	public String execute() {
	
		initialise(ViewObjectAction.class.getName());

		message = (ReceivedMessage)doSingleQuery("from ReceivedMessage where id="+messageId);

		try {
			PartnerWSDLService service = new PartnerWSDLService();
			userInfo = service.getUserInfo(message);
		} catch (ConnectionException ce) {
			errorMessage = "The Partner API interface failed to retrieve the necessary details.\n"+ce.getMessage();
			ce.printStackTrace();
		}
		
		cleanUp();
		return SUCCESS;		
	}
	
	public ReceivedMessage getMessage() {
		return message;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	public GetUserInfoResult getUserInfo() {
		return userInfo;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
}
