package com.schoenberg;

import org.jfugue.*;

import java.util.concurrent.ConcurrentSkipListMap;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;

class App {
	public static void main(String[] args) {
		Corpus corpus = new Corpus();
		corpus.build(new File("./midis/"));
		//System.out.println(pattern.getMusicString());
		System.exit(0);
	}
}
