package edu.ship.engr.display.entity;

public abstract class Item {
    protected int xPos;
    protected int yPos;

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

    public void setPos(int x, int y) {
        this.xPos = x;
        this.yPos = y;
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
