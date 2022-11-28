package edu.ship.engr.display.entity;

public class Lever extends Item {

    public Lever(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public void interact(Player player) {
        if (this.state) {
            this.state = false;
        } else {
            this.state = true;
        }
    }
}
