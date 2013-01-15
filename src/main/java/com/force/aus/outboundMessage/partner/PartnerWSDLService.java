package com.force.aus.outboundMessage.partner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.force.aus.outboundMessage.entity.ReceivedMessage;
import com.force.aus.outboundMessage.entity.UserInfo;
import com.force.aus.soap.partner.DescribeGlobal;
import com.force.aus.soap.partner.DescribeGlobalResponse;
import com.force.aus.soap.partner.DescribeGlobalResultType;
import com.force.aus.soap.partner.DescribeGlobalSObjectResultType;
import com.force.aus.soap.partner.SessionHeader;
import com.force.aus.soap.partner.SforceService;
import com.force.aus.soap.partner.Soap;
import com.force.aus.soap.partner.UnexpectedErrorFault_Exception;
import com.sun.xml.bind.api.JAXBRIContext;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;

public class PartnerWSDLService {

	private Logger logger;
	private static String WSDL_NAME = "/partner.wsdl";
	private static String QNAME_NAMESPACE = "urn:partner.soap.sforce.com";
	private static String QNAME_LOCAL_PART = "SforceService";
	
	public UserInfo getUserInfo(ReceivedMessage message) {
		
		logger = LoggerFactory.getLogger(PartnerWSDLService.class);
		logger.info("Attempting to conect to org {} using URL {}", message.getOrgId(), message.getPartnerURL());
		UserInfo info = new UserInfo();
		WSBindingProvider wsBindingProvider = null;
		
		try { // we shouldn't need login because SessionID should be valid.
			
			URL url = this.getClass().getResource(WSDL_NAME);
			if (url == null) {
				throw new RuntimeException("Unable to find WSDL ["+WSDL_NAME+"] on classpath");
			} else {
				logger.info("URL {}", url.getPath());
			}
			SforceService service = new SforceService(url, new QName(QNAME_NAMESPACE, QNAME_LOCAL_PART));
			Soap port = service.getSoap();
			
			JAXBContext jaxbContext = JAXBContext.newInstance("com.force.aus.soap.partner");

//String sessionId = "00D90000000hOVK!AQYAQE9lU_VlAV.73Yrj33ku2LQ5BK3VCngVwPxW9QC0MvOcXvt45x8Dif7_k1.vr4Q0td.JoHRTRZbb10DmJyxJp7Op.hdd";
//String partnerId = "https://ap1-api.salesforce.com/services/Soap/u/26.0/00D90000000hOVK";
			
			wsBindingProvider = (WSBindingProvider)port;
			Map<String, Object> requestContext = wsBindingProvider.getRequestContext();
			requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, message.getPartnerURL() /*partnerId*/);
			Map<String, List<String>> httpHeaders = new HashMap<String, List<String>>();
			httpHeaders.put("Content-Encoding", Collections.singletonList("gzip"));
			httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
			requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
			
			List<Header> headers = new ArrayList<Header>();
			
			SessionHeader sessionHeader = new SessionHeader();
			sessionHeader.setSessionId(message.getSessionId() /*sessionId*/);

			headers.add(Headers.create((JAXBRIContext)jaxbContext, sessionHeader));
			
			wsBindingProvider.setOutboundHeaders(headers);
			
			DescribeGlobalResponse describeGlobalResponse = port.describeGlobal(new DescribeGlobal());
			
			DescribeGlobalResultType describeGlobalResultType = describeGlobalResponse.getResult();
			for(DescribeGlobalSObjectResultType type : describeGlobalResultType.getSobjects()) {
				logger.info("We have got a describe global back {}", type.getName());
			}
		} catch(UnexpectedErrorFault_Exception uxe) {
			logger.error("Caught EnexpectedErrorFault_Exception trying to connect {}", uxe.getMessage());
			uxe.printStackTrace();
		} catch (JAXBException jbe) {
			logger.error("Caught a JAXBException trying to connect {}", jbe.getMessage());
			jbe.printStackTrace();
		} finally {
			try {
				if(wsBindingProvider != null) 
					wsBindingProvider.close();
			} catch (IOException ioe) {
				logger.error("IOException while trying to close the wsBindingProvider {}", ioe.getMessage());
				ioe.printStackTrace();
			}
		}
		
		return info;
	}
	


}
