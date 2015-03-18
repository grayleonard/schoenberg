# schoenberg

##Requirements
===
java 1.6
maven

##How to
===
compile
`mvn package appassembler:assemble`

build corpus
`./target/appassembler/bin/schoenberg build midi/folder output.corpus`

generate song
`./target/appassembler/bin/schoenberg generate example.corpus 20`

20 is the time between notes

song is outputted to the generated folder, with the timestamp as its name

playback midi
`./target/appassembler/bin/schoenberg play generated/file.mid`
