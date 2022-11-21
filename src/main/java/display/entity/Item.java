package display.entity;

public abstract class Item {
    private int xPos;
    private int yPos;

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public abstract void interact(Player player);


}
