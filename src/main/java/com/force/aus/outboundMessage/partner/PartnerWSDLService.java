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

public class PartnerWSDLService {

	private Logger logger;
	
	public PartnerWSDLService() {
		logger = LoggerFactory.getLogger(PartnerWSDLService.class);
		logger.info("Have initialised a {}", PartnerWSDLService.class);
	}
	
	public GetUserInfoResult getUserInfo(ReceivedMessage message) throws ConnectionException {
		
		logger.info("Getting user info");
		PartnerConnection conn = getPartnerConnection(message);
		return conn.getUserInfo();
		
	}
	
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
