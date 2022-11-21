package display.entity;

import enums.Direction;

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
    }

    public void move() {
        xPos = xPosNext;
        yPos = yPosNext;
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
