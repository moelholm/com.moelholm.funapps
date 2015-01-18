# About

This application contains a Web service that can receive uploaded WAV files (sound clips) and play them. 

It runs as a fat JAR file - meaning that you don't have to run any application server or the like. Just run the JAR using the usual syntax: `java -jar whaaat-*.jar`. Then invoke the RESTfule Web service operations using a RESTful client tool of your choice. Examples below uses the`curl` tool for that.

### BUILD and RUN  ( development mode )

1) BUILD+RUN     : `mvn spring-boot:run`

2) SHOW HELP     : `curl localhost:8080/`

3) PLAY SOUND    : `curl localhost:8080/playsound/whaaat.wav`

### PACKAGE and DISTRIBUTE ( for users )

1) PACKAGE       : `mvn package`

0) DISTRIBUTE    : `target\whaat-*.jar` 
   ( fat jar )

### USE DISTRIBUTION ( for users )

1) START APP : `java -jar whaaat-*.jar`   
	( Java 8 )

2) UPLOAD    : `curl localhost:8080/uploadfile --form file=@/path/to/sound.wav`

3) PLAY      : `curl localhost:8080/playsound/sound.wav`

4) LIST      : `curl localhost:8080/listsounds`
