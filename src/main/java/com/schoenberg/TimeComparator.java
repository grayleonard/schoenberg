package com.schoenberg;

import org.jfugue.*;

import java.util.Comparator;

class TimeComparator implements Comparator<Time> {
	public int compare(Time t1, Time t2) {
		if(t1.getMusicString().equals(t2.getMusicString()))
			return 0;
		else if(t1.getTime() > t2.getTime())
			return 1;
		else if(t1.getTime() < t2.getTime())
			return -1;
		return 1;
	}
}
