package edu.ship.engr.display.entity;

import edu.ship.engr.display.Board;
import edu.ship.engr.enums.Direction;
import edu.ship.engr.exceptions.InvalidTargetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorTest {

    /**
     * Tests that the door opens properly upon interacting with the lever
     * in the first room
     */
    @Test
    public void testOpenDoorWithLever() {
        Board board = Board.getInstance();
        Lever lever = (Lever) board.getItemAt(24, 6);
        Door door = (Door) board.getItemAt(42, 11);
        Player player = board.getMyPlayer();

        // door and lever should be false at first
        assertFalse(lever.getState());
        assertFalse(door.getState());

        // player interacts with lever
        lever.interact(player);

        // both lever and (more importantly) door should be true now
        assertTrue(lever.getState());
        assertTrue(door.getState());
    }

    /**
     * Tests that the door opens properly upon interacting with the
     * pressure plates in the second room
     */
    @Test
    public void testOpenDoorWithPressurePlate() throws InvalidTargetException {
        Board board = Board.getInstance();
        PressurePlate pressure1 = (PressurePlate) board.getItemAt(35, 18);
        PressurePlate pressure2 = (PressurePlate) board.getItemAt(47, 18);
        Door door = (Door) board.getItemAt(24, 20);
        Box box = (Box) board.getItemAt(41, 21);
        Player player = board.getMyPlayer();

        player.setPosition(40, 21);
        box.interact(player);

        // door should start as false
        assertFalse(door.getState());

        // placing down box
        player.setPosition(46,18);
        player.setDirection(Direction.Right);
        try{
            player.placeBox();
        } catch (InvalidTargetException i) {

        }

        // placing player on pressure plate
        player.setPosition(34, 18);
        player.move(35,18, Direction.Right);

        // both pressure plates should be true, and the door should be open
        assertTrue(pressure1.getState());
        assertTrue(pressure2.getState());
        assertTrue(door.isUnlocked());
    }
}

