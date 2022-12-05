package edu.ship.engr.messages;

import java.util.LinkedHashMap;

public class BadInteractReply {
    private final int timestamp;

    public BadInteractReply(int timestamp) {
        this.timestamp = timestamp;
    }

    public BadInteractReply(LinkedHashMap<String, Object> p) {
        this.timestamp = (int) p.get("timestamp");
    }

    public int getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "The interaction was not able to be done";
    }
}
