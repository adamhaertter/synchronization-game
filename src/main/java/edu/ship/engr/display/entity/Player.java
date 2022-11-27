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
    private int xPosNext;
    private int yPosNext;
    private Direction direction;
    private ArrayList<Item> inventory = new ArrayList<Item>();

    public Player(int startingX, int startingY) {
        yPos = startingY;
        xPos = startingX;
        remainInPlace();
    }

    public void move() {
        xPos = xPosNext;
        yPos = yPosNext;
        Timestamp ts = Timestamp.getInstance();
        MovementMessage movement = new MovementMessage(xPos, yPos, ts.getTimestamp());
        PlayRunner.messageAccumulator.queueMessage(new Message<>(movement));
    }

    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public void interact(Item x) {
        x.interact(this);
    }

    public void calculateUpcomingMove(Direction direction) {
        this.direction = direction;
        if (direction == Direction.Up) {
            yPosNext = yPos - 1;
        } else if(direction == Direction.Down) {
            yPosNext = yPos + 1;
        } else if (direction == Direction.Right) {
            xPosNext = xPos + 1;
        } else if (direction == Direction.Left) {
            xPosNext = xPos - 1;
        }
    }

    public void remainInPlace() {
        xPosNext = xPos;
        yPosNext = yPos;
    }

    public void addToInventory(Item x) {
        inventory.add(x);
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

    public int getNextX() {
        return xPosNext;
    }

    public int getNextY() {
        return yPosNext;
    }

    public char getDisplayChar() {
        return 'P';
    }
}
