package edu.ship.engr.display.entity;

import java.util.ArrayList;

public class Door extends Item {

    private ArrayList<Item> items;

    public Door(ArrayList<Item> items, int xPos, int yPos) {
        super(xPos, yPos);
        this.items = items;
    }
    public boolean isUnlocked() {
        int numOn = 0;
        for (Item item : items) {
            if (item.getState()) {
                numOn++;
            }
        }
        if (numOn == items.size()) {
            return true;
        }
        return false;
    }

    public void interact(Player player) {
        this.state = isUnlocked();
    }
}
