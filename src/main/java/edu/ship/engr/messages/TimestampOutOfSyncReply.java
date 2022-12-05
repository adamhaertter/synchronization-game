package edu.ship.engr.messages;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class TimestampOutOfSyncReply implements Serializable {

    private final int timestamp;

    public TimestampOutOfSyncReply(int timestamp) {
        this.timestamp = timestamp;
    }

    public TimestampOutOfSyncReply(LinkedHashMap<String, Object> p) {
        this.timestamp = (int) p.get("timestamp");
    }

    public int getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "The timestamps are out of sync";
    }
}
