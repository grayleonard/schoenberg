package com.schoenberg;

import org.jfugue.*;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

class SchoenbergParserListener extends ParserListenerAdapter {

/*
 * Iterates through every token that is parsed in order
 * to generate a TreeMap<Time, ArrayList<Note>> (contained
 * within the Song object).  This is because events in
 * midi are not ordered sequential in time, but which is
 * needed to build an ordered Markov model.
 *
 * Logic:
 * 	for every Time token:
 * 		ArrayList<Note> = all Note tokens
 * 		append Song.TreeMap with resultant <Time, ArrayList<Note>>
 */

	//CorpusBuilder cbuilder = new CorpusBuilder(new File("./corpus.json"));
	private Song song = new Song();

	public void noteEvent(Note n) {
		song.add(n);
	}

	public void parallelNoteEvent(Note note) {
		System.out.println("parallel"+note.getMusicString());
	}

	public void timeEvent(Time t) {
		song.add(t);
	}

	public Song getSong() {
		return song;
	}
}
