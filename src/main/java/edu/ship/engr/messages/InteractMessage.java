package edu.ship.engr.messages;

import edu.ship.engr.display.entity.Item;

import java.util.LinkedHashMap;

public class InteractMessage {
    private final Item EventObject;
    private final int timestamp;

    /**
     * @param timestamp
     */
    public InteractMessage(Item EventObject, int timestamp) {
        this.EventObject = EventObject;
        this.timestamp = timestamp;
    }

    /**
     * @param p         the hashmap that contains the data we need
     */
    public InteractMessage (LinkedHashMap<String, Object> p) {
        this.EventObject = (Item) p.get("item");
        this.timestamp = (int) p.get("timestamp");
    }

    public Item getEventObject() {
        return EventObject;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return "This interact is for object: " + this.EventObject;
    }
}
