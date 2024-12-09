package com.example;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.time.Hour;
import org.jfree.data.time.Second;

public class TrackObject {
    String name;
    boolean active = false;
    List<Node> nodes = new ArrayList<>();
    float lastTime = 0;
    float startTime = 0;
    float maxValue;
    float minValue;

    static public Map<String, String> nameMap = new HashMap<>();
    static {
        nameMap.put(".1.8.0", "Aktiv Energi Uttag");
        nameMap.put(".2.8.0", "Aktiv Energi Inmatning");
        nameMap.put(".3.8.0", "Reaktiv Energi Uttag");
        nameMap.put(".4.8.0", "Reaktiv Energi Inmatning");
        nameMap.put(".1.7.0", "Aktiv Effekt Uttag");
        nameMap.put(".2.7.0", "Aktiv Effekt Inmatning");
        nameMap.put(".3.7.0", "Reaktiv Effekt Uttag");
        nameMap.put(".4.7.0", "Reaktiv Effekt Inmatning");
        nameMap.put(".21.7.0", "Aktiv Effekt L1 Uttag");
        nameMap.put(".22.7.0", "Aktiv Effekt L1 Inmatning");
        nameMap.put(".41.7.0", "Aktiv Effekt L2 Uttag");
        nameMap.put(".42.7.0", "Aktiv Effekt L2 Inmatning");
        nameMap.put(".61.7.0", "Aktiv Effekt L3 Uttag");
        nameMap.put(".62.7.0", "Aktiv Effekt L3 Inmatning");
        nameMap.put(".23.7.0", "Reaktiv Effekt L1 Uttag");
        nameMap.put(".24.7.0", "Reaktiv Effekt L1 Inmatning");
        nameMap.put(".43.7.0", "Reaktiv Effekt L2 Uttag");
        nameMap.put(".44.7.0", "Reaktiv Effekt L2 Inmatning");
        nameMap.put(".63.7.0", "Reaktiv Effekt L3 Uttag");
        nameMap.put(".64.7.0", "Reaktiv Effekt L3 Inmatning");
        nameMap.put(".32.7.0", "Fasspänning L1");
        nameMap.put(".52.7.0", "Fasspänning L2");
        nameMap.put(".72.7.0", "Fasspänning L3");
        nameMap.put(".31.7.0", "Fasström L1");
        nameMap.put(".51.7.0", "Fasström L2");
        nameMap.put(".71.7.0", "Fasström L3");
        nameMap.put("Outdoor", "Temp Ute");
        nameMap.put("Kök", "Temp Inne");
    }
    

    public TrackObject(String name) {
        this.name = name;

        for (Map.Entry<String, String> entry : nameMap.entrySet()) {
            if (name.contains(entry.getKey())) {
                this.name = entry.getValue();
                break;
            }
        }
    }

    public Node getNode(int index) {
        return nodes.get(index);
    }

    public int size() {
        return nodes.size();
    }

    public void AddNode(float value, String timeString) {
        String[] fullString = timeString.split("T");
        String[] dates = fullString[0].split("-");
        String[] times = fullString[1].split(":");
        Second tSecond = new Second(Integer.parseInt(times[2]), Integer.parseInt(times[1]), Integer.parseInt(times[0]), Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0]));
        int time = Integer.parseInt(times[0]) * 3600 + Integer.parseInt(times[1]) * 60 + Integer.parseInt(times[2]);

        if (lastTime != 0)
            nodes.add(new Node((time - lastTime) % 86400, value, timeString, tSecond));
        else {
            nodes.add(new Node(0, value, timeString, tSecond));
            startTime = time;
        }

        if (maxValue < value)
            maxValue = value;
        if (minValue > value)
            minValue = value;

        lastTime = time;
    }

    public float totalDelta() {
        return lastTime - startTime;
    }
}
