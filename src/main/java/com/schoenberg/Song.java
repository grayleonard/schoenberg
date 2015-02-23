package com.schoenberg;

import org.jfugue.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

class Song {

	public Song() {
	}

	private ArrayList<Object> tokens = new ArrayList<Object>();
	public ArrayList<Pattern> orderedTokens = new ArrayList<Pattern>();

	public void add(Object o) {
		if(o instanceof Note) {
			Note n = (Note)o;
			tokens.add(n);
		}
		if(o instanceof Time) {
			Time t = (Time)o;
			tokens.add(t);
		}
	}


	/*
	 * Removes unecessary Times and repositions Notes in tokens array 
	 */
	public void clean() {
		for(int i = 0; i < tokens.size(); i++) {
			Object o = tokens.get(i);
			if(o instanceof Time) {
				Time t = (Time)o;
				Note n = new Note();
				for(int j = 0; j < tokens.indexOf(o); j++) {
					if(tokens.get(j) instanceof Time) {
						Time y = (Time)tokens.get(j);
						if(y.getTime() == t.getTime()) {
							if(tokens.get(i+1) instanceof Note) 
								n = (Note)tokens.get(i+1);
							tokens.remove(t);
							tokens.remove(n);
							tokens.add(j + 1, n);
						}
					}
				}
			}
		}
	}

	/*
	 * Sorts array by Times sequentially 
	 */
	public void sort() {
		TreeMap<Time, ArrayList<Note>> ordered_tokens = new TreeMap<Time, ArrayList<Note>>(new TimeComparator());
		Time ct = new Time(-1);
		for(int i = 0; i < tokens.size(); i++) {
			Object o = tokens.get(i);
			if(o instanceof Time) {
				ct = (Time)o;
				ArrayList<Note> notes = new ArrayList<Note>();
				for(int j = i+1; j < tokens.size(); j++) {
					Object p = tokens.get(j);
					if(p instanceof Note)
						notes.add((Note)p);
					else
						break;
				}
				ordered_tokens.put(ct, notes);
			}
		}
		ArrayList<Pattern> tmp = new ArrayList<Pattern>();
		for(ArrayList<Note> a : ordered_tokens.values()) {
			String s = "";
			for(int k = 0; k < a.size(); k++) {
				if(k < a.size()-1 && a.size()>1)
					s+=a.get(k).getMusicString()+"+";
				else
					s+=a.get(k).getMusicString();
			}
			tmp.add(new Pattern(s));
		}
		orderedTokens = tmp;
	}

	public String toString() {
		String listString = "";
		for (Pattern o : orderedTokens) {
			listString += o.getMusicString() + " ";
		}
		/*	if(o instanceof Note) {
				Note n = (Note)o;
				listString += n.getMusicString() + " ";
			}
			if(o instanceof Time) {
				Time t = (Time)o;
				listString += t.getMusicString() + " ";
			}

		}
		*/
		return listString;
	}

	public ArrayList<Object> getTokens() {
		return tokens;
	}

	public ArrayList<Pattern> getOrderedTokens() {
		return orderedTokens;
	}

}
