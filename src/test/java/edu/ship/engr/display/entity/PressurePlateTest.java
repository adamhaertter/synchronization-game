package edu.ship.engr.display.entity;

import edu.ship.engr.display.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PressurePlateTest {

    /**
     * Tests that interacting with the pressure plate
     * changes its state from false to true
     */
    @Test
    public void testChangeState() {
        Board board = Board.getInstance();
        PressurePlate pressurePlate = (PressurePlate) board.getItemAt(5, 18);

        //Pressure plate should start as false.
        assertFalse(pressurePlate.getState());

        //No player is needed since anything placed on a pressure plate
        //activates the pressure plate
        pressurePlate.interact(null);

        //Pressure plate should activate after interact
        assertTrue(pressurePlate.getState());
    }

    /**
     * Tests that interacting with two pressure plates
     * linked to the same door will open the door
     */
    @Test
    public void testDoorOpens() {
        Board board = Board.getInstance();
        PressurePlate pp1 = (PressurePlate) board.getItemAt(35, 18);
        PressurePlate pp2 = (PressurePlate) board.getItemAt(47, 18);
        Door door = (Door) board.getItemAt(24, 20);

        //Door should be locked
        assertFalse(door.isUnlocked());

        //Door should open
        pp1.interact(null);
        pp2.interact(null);
        assertTrue(door.isUnlocked());
    }
}
