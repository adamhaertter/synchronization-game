package edu.ship.engr.display.entity;

public class PressurePlate extends Item {

    public PressurePlate(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public void interact(Player player) {
        state = !state;
    }
}
