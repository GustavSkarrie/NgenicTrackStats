package com.example;

import java.io.File;
import java.util.Scanner;

public class App {
    public static TrackMonitor monitor = new TrackMonitor();

    public void LoadTracks(final File folder) throws InterruptedException {
        for (final File fileEntry : folder.listFiles()) {
            System.out.println(fileEntry.getName());
            try (Scanner scanner = new Scanner(fileEntry)) {
                TrackObject object = new TrackObject(fileEntry.getName());
                while (scanner.hasNextLine()) {
                    String[] values = scanner.nextLine().split(",");
                    object.AddNode(Float.parseFloat(values[1]), values[0]);
                }
                monitor.AddTrack(object);
            } catch (Exception e) {
                System.err.println(e);
            }
        }   
        
        new AppUI(monitor.tracks);
    }
}
