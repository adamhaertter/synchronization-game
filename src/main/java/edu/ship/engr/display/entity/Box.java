package edu.ship.engr.display.entity;

public class Box extends Item {

    public Box(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public void interact(Player player) {
        //TODO
        player.pickupBox(this);
        this.setState(true);
    }
}
