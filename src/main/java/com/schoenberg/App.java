package com.schoenberg;

import org.jfugue.*;
import java.io.File;

class App {
	public static void main(String[] args) {
		Player player = new Player();
		Pattern pattern = new Pattern("C D E F G A B");
		try {
			pattern = player.loadMidi(new File("schoenberg.mid"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(pattern.getMusicString());
		player.play(pattern);
	}
}
