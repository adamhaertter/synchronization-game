package edu.ship.engr.messages;

import edu.ship.engr.display.entity.Player;
import edu.ship.engr.enums.Direction;

import java.util.LinkedHashMap;

public class MovementMessage {
    private int newX;
    private int newY;
    private Direction direction;
    private final int timestamp;

    /**
     * @param x the new x coordinate of the player
     * @param y the new y coordinate of the player
     */
    public MovementMessage(int x, int y, Direction direction, int timestamp) {
        this.newX = x;
        this.newY = y;
        this.direction = direction;
        this.timestamp = timestamp;
    }

    /**
     * @param p      the hashmap that contains the data we need
     */
    public MovementMessage (LinkedHashMap<String, Object> p) {
        this.newX = (int) p.get("newX");
        this.newY = (int) p.get("newY");
        this.direction = Direction.valueOf((String) p.get("direction"));
        this.timestamp = (int) p.get("timestamp");
    }

    /**
     * @return the direction of the message
     */
    public int getNewX() {
        return this.newX;
    }

    public int getNewY()  {
        return this.newY;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return "This movement is for x: " + this.newX + " and y: " + this.newY;
    }
}
