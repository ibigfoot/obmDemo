package com.force.aus.outboundMessage.exceptions;

/**
 * Checked Exception for any error that occurs within the Partner API services.
 * This should be recoverable within the application code itself.
 * 
 * @author tsellers
 *
 */
public class PartnerAPIException extends Exception {

	private boolean expiredSessionId;

	public boolean isExpiredSessionId() {
		return expiredSessionId;
	}

	public void setExpiredSessionId(boolean expiredSessionId) {
		this.expiredSessionId = expiredSessionId;
	}
	
	public PartnerAPIException(String s) {
		super(s);
	}
	public PartnerAPIException(Throwable t) {
		super(t);
	}
	
	
}
