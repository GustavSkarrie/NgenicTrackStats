package com.example;

import org.jfree.data.time.Second;

public class Node {
    float timeDelta;
    float value;
    String timeString;
    Second secTime;

    public Node(float timeDelta, float value, String timeString, Second secTime) {
        this.timeDelta = timeDelta;

        if (timeDelta < 0) {
            this.timeDelta = 86400 - timeDelta; 
        }

        this.value = value;
        this.timeString = timeString;
        this.secTime = secTime;
    }

    public float getDelta() {
        return timeDelta;
    }

    public float getValue() {
        return value;
    }
}
