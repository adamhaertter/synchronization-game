package edu.ship.engr.display.entity;

import edu.ship.engr.display.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeverTest {

    @Test
    public void testChangeState() {
        Board board = Board.getInstance();
        Lever lever = (Lever) board.getItemAt(24, 6);

        // Lever should start as false.
        assertFalse(lever.getState());

        // No player is technically needed on Lever's interact, so this tests the functionality
        // and also the lack of player necessary for its flipping.
        lever.interact(null);

        // Lever should flip after being interacted.
        assertTrue(lever.getState());
    }

    @Test
    public void testToggleDoor() {
        Board board = Board.getInstance();
        Lever lever = (Lever) board.getItemAt(24, 6);
        Door door = (Door) board.getItemAt(42, 11);

        // The linked door must start locked
        assertFalse(door.isUnlocked());

        // Open the door
        lever.interact(null);
        assertTrue(door.isUnlocked());

        // Close the door again
        lever.interact(null);
        assertFalse(door.isUnlocked());
    }
}
