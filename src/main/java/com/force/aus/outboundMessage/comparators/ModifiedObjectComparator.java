package com.force.aus.outboundMessage.comparators;

import java.util.Comparator;

import com.force.aus.outboundMessage.entity.ModifiedObject;

public class ModifiedObjectComparator implements Comparator<ModifiedObject>{

	@Override
	public int compare(ModifiedObject o1, ModifiedObject o2) {
		
		if(o1.getObjectName() != null && o2.getObjectName() != null)	
			return o1.getObjectName().toLowerCase().compareTo(o2.getObjectName().toLowerCase());
		if(o1.getObjectName() == null && o2.getObjectName() != null) 
			return -1;
		if(o1.getObjectName() != null && o2.getObjectName() == null) 
			return 1;
		
		return 0;
	}

}
