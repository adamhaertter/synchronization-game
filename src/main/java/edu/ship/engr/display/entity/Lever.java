package edu.ship.engr.display.entity;

public class Lever extends Item {

    public Lever(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public void interact(Player player) {
        System.out.println("state changed in lever");
        this.state = !this.state;
    }
}
