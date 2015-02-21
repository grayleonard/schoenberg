package com.schoenberg;

import org.jfugue.*;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import com.google.gson.Gson;

import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

class Corpus {

	public ArrayList<ArrayList<Pattern>> corpus = new ArrayList<ArrayList<Pattern>>();
	
	public Corpus() {
	}

	public void build(File dir) {
		Gson gson = new Gson();
		Player player = new Player();
		ArrayList<ArrayList<Pattern>> toIns = new ArrayList<ArrayList<Pattern>>();
		FileWriter corpusFile = null;
		try {corpusFile = new FileWriter("corpus/schoenberg.corpus", true);}catch(Exception e){}
		for(File f : dir.listFiles()) {
			Pattern p = new Pattern();
			SchoenbergParserListener spl = new SchoenbergParserListener();
			try {p = player.loadMidi(f);}catch(Exception e){}
			MusicStringParser parser = new MusicStringParser();
			parser.addParserListener(spl);
			parser.parse(p);	
			spl.getSong().clean();
			spl.getSong().sort();
			toIns.add(spl.getSong().getOrderedTokens());
		}
		try {
			corpusFile.write(gson.toJson(toIns));
			corpusFile.close();
		} catch(Exception e) {}
	}
	public void load(File file) {
		Gson gson = new Gson();
		try {
			String fileText = Files.toString(file, Charsets.UTF_8);
			corpus=(ArrayList<ArrayList<Pattern>>) gson.fromJson(fileText, corpus.getClass());
			System.out.println(gson.toJson(corpus));
			System.out.println(corpus);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
