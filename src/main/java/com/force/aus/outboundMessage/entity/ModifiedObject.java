package com.force.aus.outboundMessage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.NotNull;


@Entity
@Table(name="modifiedobject")
public class ModifiedObject {

	@Id
	@GeneratedValue(generator="modified_object_seq")
	@SequenceGenerator(name="modified_object_seq", sequenceName="modified_object_seq", allocationSize=1)
	private Long id;
	
	@NotNull
	private String objectId;
	
	@ManyToOne
	@JoinColumn(name="receivedmessage_id")
	private ReceivedMessage receivedMessage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public ReceivedMessage getReceivedMessage() {
		return receivedMessage;
	}
	public void setReceivedMessage(ReceivedMessage receivedMessage) {
		this.receivedMessage = receivedMessage;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((objectId == null) ? 0 : objectId.hashCode());
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
		ModifiedObject other = (ModifiedObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		return true;
	}


}
