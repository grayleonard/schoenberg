# schoenberg

![Schoenberg](http://www.emmanuelmusic.org/images/cal_tix/schoenberg_schiele.jpg)

##Description

Schoenberg is a music generator using Markov Models.  It works in two stages: first, existing MIDI files are compiled into a designated corpus, which contains all the notes in sequential order.  Second, a Markov-based random song is generated using the corpus.

##Requirements

* Java 1.6

* Maven (all other dependencies are downloaded through Maven's pom.xml file)

##Usage

Compile:

`mvn package appassembler:assemble`

Build corpus:

`./target/appassembler/bin/schoenberg build midi/folder output.corpus`

Generate song:

`./target/appassembler/bin/schoenberg generate example.corpus 20`

where 20 is the time in millseconds between notes.

The generated song is both played by schoenberg after generation and outputted to the folder 'generated/'.  It's name is its creation timestamp.

Play MIDI file:

`./target/appassembler/bin/schoenberg play generated/file.mid`
