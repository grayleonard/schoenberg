package com.schoenberg;

import org.jfugue.*;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

class Corpus {

	public ArrayList<ArrayList<Pattern>> corpus = new ArrayList<ArrayList<Pattern>>();
	
	public Corpus() {
	}

	public void build(File dir, File save) {
		Gson gson = new Gson();
		Player player = new Player();
		ArrayList<ArrayList<Pattern>> toIns = new ArrayList<ArrayList<Pattern>>();
		FileWriter corpusFile = null;
		if(save.exists()){return;}
		try {corpusFile = new FileWriter(save);}catch(Exception e){}
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
			Type type = new TypeToken<ArrayList<ArrayList<Pattern>>>(){}.getType();
			corpus=(ArrayList<ArrayList<Pattern>>) gson.fromJson(fileText, type);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ArrayList<Pattern>> getCorpus() {
		return corpus;
	}

	public Pattern getPattern(int song, int index) {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Pattern>>(){}.getType();
		Pattern pattern = new Pattern(corpus.get(song).get(index).getMusicString());
		return pattern;
	}
}
