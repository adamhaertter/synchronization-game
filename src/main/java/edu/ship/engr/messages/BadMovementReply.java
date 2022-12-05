package edu.ship.engr.messages;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class BadMovementReply implements Serializable {

    private final int oldX;
    private final int oldY;
    private final int timestamp;

    public BadMovementReply(int oldX, int oldY, int timestamp) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.timestamp = timestamp;
    }

    public BadMovementReply(LinkedHashMap<String, Object> p) {
        this.oldX = (int) p.get("oldX");
        this.oldY = (int) p.get("oldY");
        this.timestamp = (int) p.get("timestamp");
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "The movement is not able to be done";
    }
}
