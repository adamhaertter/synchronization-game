package edu.ship.engr.messages;

import edu.ship.engr.display.entity.Item;

import java.util.LinkedHashMap;

public class InteractMessage {
    private final Item EventObject;

    public InteractMessage(Item EventObject) {
        this.EventObject = EventObject;
    }

    /**
     * @param p the hashmap that contains the data we need
     */
    public InteractMessage (LinkedHashMap<String, Object> p) {
        this.EventObject = (Item) p.get("item");
    }

    public Item getEventObject() {
        return EventObject;
    }
}
