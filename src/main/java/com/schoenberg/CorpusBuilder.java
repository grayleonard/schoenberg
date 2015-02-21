package com.schoenberg;

import org.jfugue.*;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import com.google.gson.Gson;

import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;

class Corpus {

	public HashMap<String, Object> corpus = new HashMap<String, Object>();
	
	public Corpus() {
	}

	public void build(File dir) {
		Gson gson = new Gson();
		Player player = new Player();
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
			try {
				corpusFile.write(gson.toJson(spl.getSong().getOrderedTokens()));
			} catch(Exception e) {}
		}
		corpusFile.close();
	}
	public void load(File dir) {
		Gson gson = new Gson();
		try {
			String fileText = Files.toString(dir, Charsets.UTF_8);
			corpus=(HashMap<String,Object>) gson.fromJson(fileText, corpus.getClass());
			System.out.println(gson.toJson(corpus));
			System.out.println(corpus);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
