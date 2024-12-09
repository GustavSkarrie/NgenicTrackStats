package com.example;

import java.util.ArrayList;
import java.util.List;

public class TrackMonitor {
    List<TrackObject> tracks = new ArrayList<>();

    public synchronized void AddTrack(TrackObject track) {
        tracks.add(track);
    }
}
