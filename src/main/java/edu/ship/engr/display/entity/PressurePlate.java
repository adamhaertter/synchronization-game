package edu.ship.engr.display.entity;

public class PressurePlate extends Item {

    public PressurePlate(int xPos, int yPos) {
        super(xPos, yPos);
    }

    private boolean isActivated = false;

    public boolean isActivated() {
        return isActivated;
    }

    public void interact(Player player) {
        // TODO this is not correct
//        if(player.getX() == getXPos() && player.getY() == getYPos())
//            isActivated = true;
//        else
//            isActivated = false;
    }
}
