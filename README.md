# NgenicTrackStats
NgenicTrackStats is a program that creates graphs and visualisations for the track data, that can be requested from Ngenic.
 
## Run release version
To run using a release version, first download the latest release version from the release tab, unzip the files and start the .jar file. To run the .jar file you may need to download or update Java and the JDK.

Download java here:
https://www.java.com/download/ie_manual.jsp

And make sure that the JDK version is 13.0+:
https://www.oracle.com/java/technologies/downloads/

Before running make sure to put the .csv files from Ngenic in the "Data" folder, if there exists no "Data" folder either create it and put it in the "target" folder or start the program and the folder should be automatically created.

## Set-up project
Start by cloning the repository, and running the following maven command to download the dependencies.

```
mvn clean install
```

The project can then be started using the following command:

```
java -jar target/ngenictrackstats-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Build .jar file
To build the jar file, run the following command in the terminal:

```
mvn clean compile assembly:single
```
