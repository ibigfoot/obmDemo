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
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import com.force.aus.outboundMessage.comparators.ModifiedObjectComparator;
import com.force.aus.outboundMessage.entity.AccountWrapper;
import com.force.aus.outboundMessage.entity.ModifiedObject;
import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.exceptions.PartnerAPIException;
import com.force.aus.outboundMessage.partner.PartnerWSDLService;
import com.sforce.ws.ConnectionException;

public class ViewObjectAction extends BaseOBMAction{

	/**
	 * serialVersionUID = 3458656659518181880L;
	 */
	private static final long serialVersionUID = 3458656659518181880L;
	private String objectId;
	private String messageId;
	private AccountWrapper account;
	
	public String doExecute() {
		
		ReceivedMessage message = null;
		try {
			message = (ReceivedMessage)doSingleQuery("from ReceivedMessage where id="+messageId);
			PartnerWSDLService service = new PartnerWSDLService();
			account = service.getAccount(objectId, message);
		    addActionMessage("Have retrieved details of Account "+account.getAccountName()+" from the Partner API");
		    
		} catch (PartnerAPIException pae) {
			pae.printStackTrace();
			addActionError("There has been a problem accessing the Salesforce Partner API\n"+pae.getMessage());
		} catch (ConnectionException ce) {
			ce.printStackTrace();
			addActionError("There has been a problem accessing the Salesforce Partner API\nThe OutboundMessage that is stored needs to be removed from the database becuase it had an Invalid Session ID");
		} catch (NoResultException nre) {
			logger.info("Query on messageId {} returned no results");
			addActionError("Unable to find local copy of Outbound Message : ID "+ messageId);
			nre.printStackTrace();
		}
		return SUCCESS;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public AccountWrapper getAccount() {
		return account;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

}
