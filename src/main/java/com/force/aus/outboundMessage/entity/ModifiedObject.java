package com.force.aus.outboundMessage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.sun.istack.NotNull;


@Entity
public class ModifiedObject {

	@Id
	@GeneratedValue(generator="modified_object_seq")
	@SequenceGenerator(name="modified_object_seq", sequenceName="modified_object_seq", allocationSize=1)
	private Long id;
	@NotNull
	private String objectId;
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


}
