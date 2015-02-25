package com.schoenberg;

import org.jfugue.*;

import java.util.ArrayList;
import java.util.Random;
import java.io.File;

import java.lang.Long;

class Generator {

	Corpus corpus = null;
	int prefix_len = 3;
	Time curr = new Time(0);

	Random r = new Random();
	int max_notes = 50 + r.nextInt(80);

	public Generator(Corpus corpus) {
		this.corpus = corpus;
	}


	/* Generator.generate() implements a markov model, which creates
	 * a pseudo-random sequential list based on a sequence of
	 * determined states.
	 */
	public Song generate() {
		ArrayList<ArrayList<Pattern>> songs = corpus.getCorpus();
		ArrayList<Pattern> tokens = songs.get(r.nextInt(songs.size()));

		ArrayList<Pattern> curr_notes = new ArrayList<Pattern>();

		ArrayList<Pattern> generated = new ArrayList<Pattern>();

		//Initialize the curr_notes array
		for(int i = 0; i < prefix_len; i++) {
			curr_notes.add(getRandomNextNote(curr_notes));
			generated.add(curr_notes.get(i));
		}
		System.out.println(curr_notes.toString());

		for(int j = 0; j < max_notes; j++) {
			// Push onto the array a markov-determined note
			Pattern new_note = getRandomNextNote(curr_notes);
			System.out.println("Adding new note" + new_note);
			curr_notes.add(new_note);

			// Pop the first element from the array
			curr_notes.remove(0);
			generated.add(new_note);
			generated.add(getRandomTimeStep());
		}
		Player player = new Player();
		Song gen_song = new Song();
		gen_song.orderedTokens = generated;
		return gen_song;
	}

	public Pattern getRandomTimeStep() {
		long high = 100L;
		long rand = (long)(r.nextDouble()*high);
		// Basic weighted random
		if(rand >= 50) {
			rand = (long)Math.floor(rand / 4);
		}
		rand = 25;
		curr.setTime(curr.getTime() + rand);
		return new Pattern(curr.getMusicString());
	}

	public Pattern getRandomNextNote(ArrayList<Pattern> prefix) {
		ArrayList<Pattern> suffixes = new ArrayList<Pattern>();
		for(ArrayList<Pattern> entry : corpus.getCorpus()) {
			for(int i = 0 + prefix.size(); i < entry.size(); i++) {
				boolean shouldAdd = true;
				Pattern p = entry.get(i);
				for(int j = 1; j < prefix.size(); j++) {
					try {
						String ent = entry.get(i-j).getMusicString().split("/")[0];
						String pre = prefix.get(prefix.size()-j-1).getMusicString().split("/")[0];
						if(!ent.equalsIgnoreCase(pre)) {
							shouldAdd = false;
							break;
						}
					} catch(Exception e) {
						e.printStackTrace();
						shouldAdd = false;
						break;
					}
				}
				if(shouldAdd)
					suffixes.add(p);
			}
		}
		if(suffixes.size() == 0) {
			System.out.println("Returning random note");
			return getRandomNote();
		}
		Pattern toReturn = suffixes.get(r.nextInt(suffixes.size()));
		while(toReturn.getMusicString().equals(""))
			toReturn = getRandomNote();
		return toReturn;
	}

	public Pattern getRandomNote() {
		ArrayList<Pattern> t = corpus.getCorpus().get(r.nextInt(corpus.getCorpus().size()));
		Pattern p = t.get(r.nextInt(t.size()));
		return p; 
	}

}
