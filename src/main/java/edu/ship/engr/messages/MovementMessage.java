package edu.ship.engr.messages;

import edu.ship.engr.enums.Direction;

import java.util.LinkedHashMap;

public class MovementMessage {
    private final int newX;
    private final int newY;
    private final int oldX;
    private final int oldY;
    private final Direction direction;
    private final int timestamp;

    /**
     * @param x the new x coordinate of the player
     * @param y the new y coordinate of the player
     */
    public MovementMessage(int x, int y, int oldX, int oldY, Direction direction, int timestamp) {
        this.newX = x;
        this.newY = y;
        this.oldX = oldX;
        this.oldY = oldY;
        this.direction = direction;
        this.timestamp = timestamp + 1;
    }

    /**
     * @param p      the hashmap that contains the data we need
     */
    public MovementMessage (LinkedHashMap<String, Object> p) {
        this.newX = (int) p.get("newX");
        this.newY = (int) p.get("newY");
        this.oldX = (int) p.get("oldX");
        this.oldY = (int) p.get("oldY");
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

    public int getOldX() {
        return this.oldX;
    }

    public int getOldY() {
        return this.oldY;
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
