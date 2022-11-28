package edu.ship.engr.display.entity;

import java.util.ArrayList;

public class Door extends Item {

    private ArrayList<Item> items;

    public Door(ArrayList<Item> items, int xPos, int yPos) {
        super(xPos, yPos);
        this.items = items;
    }
    public void isUnlocked() {
        int numOn = 0;
        for (Item item : items) {
            if (item.getState()) {
                numOn++;
            }
        }
        if (numOn == items.size()) {
            this.setState(true);
        }
    }

    public void interact(Player player) {
        if (this.state) {
            this.state = false;
        } else {
            this.state = true;
        }
    }
}
