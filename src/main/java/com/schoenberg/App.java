package com.schoenberg;

import org.jfugue.*;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.util.concurrent.ConcurrentSkipListMap;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;

class App {
	public static void main(String[] args) {
		Corpus corpus = new Corpus();
		String command = args[0];
		if(command.equalsIgnoreCase("build")) {
			String midiFolder = args[1];
			String corpusFile = args[2];
			corpus.build(new File(midiFolder), new File(corpusFile));
			System.out.println("Made corpus " + corpusFile);
		}

		if(command.equalsIgnoreCase("generate")) {
			String corpusFile = args[1];
			String toFile = "generated/" + System.currentTimeMillis() / 1000L + ".mid";
			corpus.load(new File(corpusFile));
			Generator generator = new Generator(corpus);
			Song generated = generator.generate();
			Player player = new Player();
			try {
				player.saveMidi(new Pattern(generated.toString()), new File(toFile));
			} catch(Exception e) {
				System.out.println("Error saving midi");
				e.printStackTrace();
			}
			player.play(generated.toString());
		}

		if(command.equalsIgnoreCase("play")) {
			Player player = new Player();
			Pattern toPlay = null;
			try {
				toPlay = player.loadMidi(new File(args[1]));
			} catch(Exception e) {
				System.out.println("Error loading file " + args[1]);
			}
			player.play(toPlay);
		}
		System.exit(0);
	}
}
