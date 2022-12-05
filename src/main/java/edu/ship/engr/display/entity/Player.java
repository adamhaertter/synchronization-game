package edu.ship.engr.display.entity;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.Board;
import edu.ship.engr.display.GUI;
import edu.ship.engr.exceptions.InvalidTargetException;
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

    public void move(int x, int y, Direction direction) {
        Board board = Board.getInstance();
        Item itemAtNewPosition = board.getItemAt(x, y);
        if (itemAtNewPosition != null) {
            itemAtNewPosition.interact(this);
        }

        Item atPreviousPosition = board.getItemAt(xPos, yPos);
        if (atPreviousPosition != null) {
            atPreviousPosition.interact(this);
        }

        this.xPos = x;
        this.yPos = y;
        this.direction = direction;

        if (Board.isPlayerOnWinningSpot(this)) {
            GUI.showWinMSG();
        }
    }

    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public void placeBox() throws InvalidTargetException {
        System.out.println("Placed box");
        int[] targetPosition = getTargetPosition();

        if (targetPosition == null) {
            throw new InvalidTargetException("Invalid target position");
        }

        if (currentBox != null) {
            Board board = Board.getInstance();
            Item item = board.getItemAtIfPressurePlate(targetPosition[0], targetPosition[1]);

            if (item instanceof PressurePlate) {
                item.setState(true);
                System.out.println("Placed box on pressure plate");
            }

            // We have a box to place
            // print out boxes location before placing
            System.out.println("Box is at " + currentBox.getXPos() + ", " + currentBox.getYPos());
            currentBox.setPos(targetPosition[0], targetPosition[1]);
            System.out.println("Placing box at " + targetPosition[0] + ", " + targetPosition[1]);
            System.out.println("Box has a state of " + currentBox.getState());
            currentBox.setState(false);
            System.out.println("Box now has a state of " + currentBox.getState());
            currentBox = null;
        }
    }

    public int[] getTargetPosition() {
        int targetX = xPos;
        int targetY = yPos;
        switch (direction) {
            case Up:
                targetY--;
                break;
            case Down:
                targetY++;
                break;
            case Right:
                targetX++;
                break;
            case Left:
                targetX--;
                break;
            default:
                //shouldn't reach
        }

        // ensure target is steppable
        Board board = Board.getInstance();
        return board.canPlaceBox(targetX, targetY) ? new int[]{targetX, targetY} : null;
    }

    public void pickupBox(Box x) {
        System.out.println("Picked up box");
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

    public Box getBox() {
        return this.currentBox;
    }

    public void setBox(Box box) {
        this.currentBox = box;
    }

    public char getDisplayChar() {
        return 'P';
    }
}
