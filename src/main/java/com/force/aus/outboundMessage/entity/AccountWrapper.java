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
package com.force.aus.outboundMessage.entity;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.sobject.SObject;

/**
 * Populate Object ID and List<FieldType> 
 * Then populate query
 * @author tsellers
 *
 */
public class AccountWrapper {

	private String objectId;
	private Field[] fields;
	private Map<Field, Object> values;
	private Logger logger;
	private String accountName;
	
	public AccountWrapper() {
		logger = LoggerFactory.getLogger(AccountWrapper.class);
	}
	
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public Field[] getFields() {
		return fields;
	}
	public void setFields(Field[] fields) {
		this.fields = fields;
	}
	public Map<Field, Object> getValues() {
		return values;
	}
	public void setValues(Map<Field, Object> values) {
		this.values = values;
	}
	public String getAccountName() {
		return accountName;
	}
	public void processSObject(SObject object) {
		values = new HashMap<Field, Object>();
		
		if(fields == null) {
			throw new IllegalStateException("Require a list of field names to process an SObject");
		}
		if(object == null) {
			throw new IllegalStateException("This method doesn't tollerate NULL sobject parameter");
		}
		for(Field f : fields) {
			if(!f.isAutoNumber() && !f.isCalculated() && !f.isDeprecatedAndHidden() && f.isUpdateable()) {
				logger.info("Field {} - value {}", f.getName(), (object.getField(f.getName()) != null ? object.getField(f.getName()) : ""));
				values.put(f, (object.getField(f.getName()) != null ? object.getField(f.getName()) : ""));
			}
			//set account name
			if(f.getName().equalsIgnoreCase("name")) {
				accountName = object.getField(f.getName()).toString();
			}
		}
	}
	public String getQuery() {
		if(objectId == null || fields == null) {
			throw new IllegalStateException("Unable to build query without Object ID and / or Field[]");
		}
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ");
		for(int i=0 ; i<fields.length ; i++) {
			sb.append(fields[i].getName());
			if(i < fields.length-1) {
				sb.append(",");
			} else {
				sb.append(" ");
			}
		}

		sb.append("from account where id ='");
		sb.append(objectId);
		sb.append("'");
		
		return sb.toString();
	}
	
}
