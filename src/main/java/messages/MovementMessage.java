package messages;

import enums.Direction;

import java.util.LinkedHashMap;

public class MovementMessage {
    private final Direction direction;

    /**
     * @param direction the direction that the movement corresponds to
     */
    public MovementMessage(Direction direction) {
        this.direction = direction;
    }

    /**
     * @param p the hashmap that contains the data we need
     */
    public MovementMessage (LinkedHashMap<String, Object> p) {
        this.direction = (Direction) p.get("direction");
    }

    /**
     * @return the direction of the message
     */
    public Direction getDirection() {
        return direction;
    }
}
