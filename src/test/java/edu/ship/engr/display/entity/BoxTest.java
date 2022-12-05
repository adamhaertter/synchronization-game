package edu.ship.engr.display.entity;

import edu.ship.engr.display.Board;
import edu.ship.engr.enums.Direction;
import edu.ship.engr.exceptions.InvalidTargetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoxTest {

    @Test
    public void testPickUpBox() {
        Board board = Board.getInstance();
        Box box = (Box) board.getItemAt(11, 21);
        Player player = board.getMyPlayer();

        // Box should start as false.
        assertFalse(box.getState());

        box.interact(player);

        // Player should own the same box and box should be true
        assertTrue(box.getState());
        assertNotNull(player.getBox());
        assertEquals(box, player.getBox());
    }

    @Test
    public void testPlaceBox() {
        Board board = Board.getInstance();
        Box box = (Box) board.getItemAt(11, 21);
        Player player = board.getMyPlayer();
        box.interact(player);

        player.setPosition(10, 21);
        player.setDirection(Direction.Right);
        try {
            player.placeBox();
        } catch (InvalidTargetException i) {

        }

        // Player should not hold a box, box should be false, and should be in the new position
        assertFalse(box.getState());
        assertNull(player.getBox());
        assertEquals(box.getXPos(), 11);
        assertEquals(box.getYPos(), 21);
    }
}
