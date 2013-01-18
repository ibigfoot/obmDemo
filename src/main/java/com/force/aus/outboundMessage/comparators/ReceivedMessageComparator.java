package com.force.aus.outboundMessage.comparators;

import java.util.Comparator;

import com.force.aus.outboundMessage.entity.ReceivedMessage;

public class ReceivedMessageComparator implements Comparator<ReceivedMessage>{

	@Override
	public int compare(ReceivedMessage o1, ReceivedMessage o2) {
			
		return o1.getDateReceived().getTime() > o2.getDateReceived().getTime() ? -1 : (o1.getDateReceived().getTime() == o2.getDateReceived().getTime() ? 0 : 1);
	}

	
}
