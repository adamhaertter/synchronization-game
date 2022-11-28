package edu.ship.engr.display.entity;

import edu.ship.engr.display.Board;

public class Box extends Item {

    public Box(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public void interact(Player player) {
        //TODO
        if (player.getBox() == null) {
            player.pickupBox(this);
            this.state = true;

            Board board = Board.getInstance();

            Item pressurePlate = board.getItemAtIfPressurePlate(xPos, yPos);

            if (pressurePlate != null) {
                pressurePlate.setState(false);
            }

//            this.setPos(5, 5);
        }
    }
}
