package edu.ship.engr.communication;

public class Timestamp {
    // Singleton class for keeping track of synchronization through a message counter
    private static Timestamp instance = null;
    private int timestamp = 0;

    private Timestamp() {

    }

    public static Timestamp getInstance() {
        if (instance == null) {
            instance = new Timestamp();
        }
        return instance;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void increment() {
        timestamp++;
    }

    public int incrementAndGet() {
        return ++timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
