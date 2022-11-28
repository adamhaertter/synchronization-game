package edu.ship.engr.display.entity;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.messages.Message;
import edu.ship.engr.peertopeer.PlayRunner;
import edu.ship.engr.enums.Direction;
import edu.ship.engr.messages.MovementMessage;

import java.util.ArrayList;

public class Player {
    private int xPos;
    private int yPos;
    private Direction direction = Direction.Right;
    private Box currentBox = null;

    public Player(int startingX, int startingY) {
        yPos = startingY;
        xPos = startingX;
    }

    public void move(int x, int y) {
        xPos = x;
        yPos = y;
        Timestamp ts = Timestamp.getInstance();
        MovementMessage movement = new MovementMessage(xPos, yPos, ts.getTimestamp());
        PlayRunner.messageAccumulator.queueMessage(new Message<>(movement));
    }

    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public void interact(Item x) {
        if (!(x instanceof Door)) {
            x.interact(this);
        }
    }

    public void pickupBox(Box x) {
        currentBox = x;
    }

    public void setDirection(Direction dir){
        direction = dir;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public Direction getDirection() {
        return direction;
    }
    public char getDisplayChar() {
        return 'P';
    }
}
