package edu.ship.engr.display.entity;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.Board;
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

    public void placeBox() throws InvalidTargetException {
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
            currentBox.setPos(targetPosition[0], targetPosition[1]);
            currentBox.setState(false);
            currentBox = null;
        }
    }

    public int[] getTargetPosition() {
        int targetX = xPos;
        int targetY = yPos;
        switch (direction) {
            case Up -> targetY--;
            case Down -> targetY++;
            case Right -> targetX++;
            case Left -> targetX--;
            default -> {
            }
            //shouldn't reach
        }

        // ensure target is steppable
        Board board = Board.getInstance();
        return board.canPlaceBox(targetX, targetY) ? new int[]{targetX, targetY} : null;
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

    public Box getBox() {
        return this.currentBox;
    }

    public char getDisplayChar() {
        return 'P';
    }
}
