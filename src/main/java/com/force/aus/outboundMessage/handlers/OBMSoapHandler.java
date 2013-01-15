package com.force.aus.outboundMessage.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class OBMSoapHandler implements SOAPHandler<SOAPMessageContext>{

	private Logger logger;

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		logger = LoggerFactory.getLogger(OBMSoapHandler.class);
		
		try {
			SOAPMessage message = context.getMessage();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);			
			HttpServletRequest request = (HttpServletRequest)context.get(SOAPMessageContext.SERVLET_REQUEST);
			request.setAttribute("RAW_XML", out.toString());
			
		} catch (SOAPException se) {
			logger.error("Have caught a SOAP Excpetion while storing XML payload");
			se.printStackTrace();
		} catch (IOException ioe) {
			logger.error("Have caught an IOException while writing XML payload");
			ioe.printStackTrace();
		} 
		return true;
	}


	@Override
	public boolean handleFault(SOAPMessageContext context) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub
		
	}




}
