package edu.ship.engr.display.entity;

public abstract class Item {
    private int xPos;
    private int yPos;

    protected boolean state;

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean newState) {
        state = newState;
    }

    public abstract void interact(Player player);

    public Item(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        state = false;
    }

}
