package com.schoenberg;

import org.jfugue.*;

import java.util.ArrayList;
import java.util.Random;
import java.io.File;

import java.lang.Long;

class Generator {

	Corpus corpus = null;
	int prefix_len = 1;
	Time curr = new Time(0);

	Random r = new Random();
	int max_notes = 50 + r.nextInt(80);

	public Generator(Corpus corpus) {
		this.corpus = corpus;
	}

	public Song generate() {
		ArrayList<ArrayList<Pattern>> songs = corpus.getCorpus();
		ArrayList<Pattern> tokens = songs.get(r.nextInt(songs.size()));

		Pattern init_note = tokens.get(r.nextInt(tokens.size()));

		ArrayList<Pattern> generated = new ArrayList<Pattern>();
		Pattern current_note = init_note;
		generated.add(new Pattern(curr.getMusicString()));
		generated.add(current_note);
		for(int i = 0; i < max_notes; i++) {
			current_note = getRandomNextNote(current_note);
			generated.add(current_note);
			generated.add(getRandomTimeStep());
		}
		generated.add(getRandomNextNote(current_note));
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

	public Pattern getRandomNextNote(Pattern prefix) {
		ArrayList<Pattern> suffixes = new ArrayList<Pattern>();
		for(ArrayList<Pattern> entry : corpus.getCorpus()) {
			for(int i = 0; i < entry.size(); i++) {
				Pattern p = entry.get(i);
				if(p.getMusicString().split("/")[0].equals(prefix.getMusicString().split("/")[0])) {
					try {
						suffixes.add(entry.get(i+1));
					} catch(Exception e) {
					}
				}
			}
		}
		if(suffixes.size() == 0) {
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
