package com.force.aus.outboundMessage.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="receivedmessage")
public class ReceivedMessage implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	
	private String orgId;
	private String actionId;
	private String sessionId;
	private String enterpriseURL;
	private String partnerURL;
	private Date dateReceived;
	@Lob
	private String xmlMessage;
	
	@OneToMany(mappedBy="receivedMessage", fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ModifiedObject> modifiedObjects;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getEnterpriseURL() {
		return enterpriseURL;
	}
	public void setEnterpriseURL(String enterpriseURL) {
		this.enterpriseURL = enterpriseURL;
	}
	public String getPartnerURL() {
		return partnerURL;
	}
	public void setPartnerURL(String partnerURL) {
		this.partnerURL = partnerURL;
	}
	public Date getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	public String getXmlMessage() {
		return xmlMessage;
	}
	public void setXmlMessage(String xmlMessage) {
		this.xmlMessage = xmlMessage;
	}
	public Set<ModifiedObject> getModifiedObjects() {
		return modifiedObjects;
	}
	public void setModifiedObjects(Set<ModifiedObject> modifiedObjects) {
		this.modifiedObjects = modifiedObjects;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actionId == null) ? 0 : actionId.hashCode());
		result = prime * result
				+ ((dateReceived == null) ? 0 : dateReceived.hashCode());
		result = prime * result
				+ ((enterpriseURL == null) ? 0 : enterpriseURL.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((modifiedObjects == null) ? 0 : modifiedObjects.hashCode());
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result
				+ ((partnerURL == null) ? 0 : partnerURL.hashCode());
		result = prime * result
				+ ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result
				+ ((xmlMessage == null) ? 0 : xmlMessage.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReceivedMessage other = (ReceivedMessage) obj;
		if (actionId == null) {
			if (other.actionId != null)
				return false;
		} else if (!actionId.equals(other.actionId))
			return false;
		if (dateReceived == null) {
			if (other.dateReceived != null)
				return false;
		} else if (!dateReceived.equals(other.dateReceived))
			return false;
		if (enterpriseURL == null) {
			if (other.enterpriseURL != null)
				return false;
		} else if (!enterpriseURL.equals(other.enterpriseURL))
			return false;
		if (id != other.id)
			return false;
		if (modifiedObjects == null) {
			if (other.modifiedObjects != null)
				return false;
		} else if (!modifiedObjects.equals(other.modifiedObjects))
			return false;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		if (partnerURL == null) {
			if (other.partnerURL != null)
				return false;
		} else if (!partnerURL.equals(other.partnerURL))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (xmlMessage == null) {
			if (other.xmlMessage != null)
				return false;
		} else if (!xmlMessage.equals(other.xmlMessage))
			return false;
		return true;
	}

	
	
}
