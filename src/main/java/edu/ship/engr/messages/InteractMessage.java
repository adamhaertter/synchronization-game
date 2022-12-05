package edu.ship.engr.messages;

import java.util.LinkedHashMap;

public class InteractMessage {
    private int targetX;
    private int targetY;
    private final int timestamp;

    /**
     * @param timestamp
     */
    public InteractMessage(int x, int y, int timestamp) {
        this.targetX = x;
        this.targetY = y;
        this.timestamp = timestamp;
    }

    /**
     * @param p         the hashmap that contains the data we need
     */
    public InteractMessage (LinkedHashMap<String, Object> p) {
        this.targetX = (int) p.get("targetX");
        this.targetY = (int) p.get("targetY");
        this.timestamp = (int) p.get("timestamp");
    }

    public int getTargetX() {
        return this.targetX;
    }

    public int getTargetY()  {
        return this.targetY;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return "This interact is the object at: " + this.targetX + ", " + this.targetY;
    }
}
