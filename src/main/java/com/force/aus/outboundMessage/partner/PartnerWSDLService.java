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
package com.force.aus.outboundMessage.partner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.entity.AccountWrapper;
import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.exceptions.PartnerAPIException;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
/**
 * Helper to wrap around Salesforce Partner API
 * 
 * @author tsellers@salesforce.com
 *
 */
public class PartnerWSDLService {

	private Logger logger;
	
	/**
	 * Constructor. Initialises logger. 
	 */
	public PartnerWSDLService() {
		logger = LoggerFactory.getLogger(PartnerWSDLService.class);
		logger.info("Have initialised a {}", PartnerWSDLService.class);
	}
	/**
	 * Gets the UserInfo object from a Partner Connection. 
	 * Looks for SessionID and PartnerURL values in the ReceivedMessage param
	 * 
	 * @param ReceivedMessage
	 * @return GetUserInfoResult
	 * @throws ConnectionException
	 */
	public GetUserInfoResult getUserInfo(ReceivedMessage message) throws ConnectionException {
		
		logger.info("Getting user info");
		PartnerConnection conn = getPartnerConnection(message);
		return conn.getUserInfo();
		
	}
	
	/*
	 * Helper for getting the Connection.
	 *
	 * @param message
	 * @return
	 * @throws ConnectionException
	 */
	//TODO - move configuration to config params file or env vars for heroku deployment.
	private PartnerConnection getPartnerConnection(ReceivedMessage message) throws ConnectionException {
		logger = LoggerFactory.getLogger(PartnerWSDLService.class);
		logger.info("Attempting to conect to org {} using URL {}", message.getOrgId(), message.getPartnerURL());
		
//		message.setPartnerURL("https://ap1-api.salesforce.com/services/Soap/u/26.0/00D90000000itXM");
//		message.setSessionId("00D90000000itXM!AQ8AQGebSnUTzh16iJz8Iq6VlimH7q92NFkRkogKKtlq8YNXRLSJgU7lOrArlD62PQDQj3fZqFL8NUfZdF7TolCVTAqL2Ih7");
		
		
		ConnectorConfig config = new ConnectorConfig();
		config.setServiceEndpoint(message.getPartnerURL());
		config.setSessionId(message.getSessionId());
		config.setCompression(true);
		config.setPrettyPrintXml(true);
		config.setTraceMessage(true);
		
		PartnerConnection conn = new PartnerConnection(config);
		return conn;
	}
	
	/**
	 * Builds a query on all fields of an Account object. Uses a describe call to get the fields
	 * available for that particular object, then builds the query. 
	 * i.e. This method uses 2 API calls 
	 *  
	 * @param String - objectId
	 * @param ReceivedMessage - message
	 * @return AccountWrapper
	 * @throws ConnectionException
	 * @throws PartnerAPIException
	 */
	public AccountWrapper getAccount(String objectId, ReceivedMessage message) throws ConnectionException, PartnerAPIException {
		
//		objectId = "0019000000KJQLj";
		
		logger.info("Query account object {}", objectId);
		AccountWrapper wrapper = new AccountWrapper();
		
		PartnerConnection conn = getPartnerConnection(message);
		DescribeSObjectResult result = conn.describeSObject("Account");
		Field[] fields = result.getFields();
		wrapper.setObjectId(objectId);
		wrapper.setFields(fields);
		
		QueryResult qr = conn.queryAll(wrapper.getQuery());
		if(qr.getRecords() == null || qr.getRecords().length != 1) {
			throw new PartnerAPIException("The single object query has returned more than one result");
		}
		wrapper.processSObject(qr.getRecords()[0]);
		return wrapper;
	}

	
}
