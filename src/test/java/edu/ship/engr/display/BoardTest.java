package edu.ship.engr.display;

import edu.ship.engr.display.entity.*;
import edu.ship.engr.enums.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    /**
     * Test that the correct amount of players and items are created when the board is created
     */
    @Test
    public void TestBoardCreate() {
        Board board = Board.getInstance();

        assertNotNull(board.getMyPlayer());
        assertNotNull(board.getOtherPlayer());

        assertEquals(14, board.getItems().size());
        assertEquals(2, board.getItemsOfType(Lever.class).size());
        assertEquals(4, board.getItemsOfType(PressurePlate.class).size());
        assertEquals(5, board.getItemsOfType(Door.class).size());
        assertEquals(3, board.getItemsOfType(Box.class).size());
    }

    /**
     * Test that the isSteppable method works correctly
     */
    @Test
    public void TestIsSteppable() {
        Board board = Board.getInstance();

        // Test that the player can step on a space
        assertTrue(board.isSteppable(3, 4));

        // Test that the player can't step on a wall
        assertFalse(board.isSteppable(0, 0));

        // Test that the player can't step on a box
        assertFalse(board.isSteppable(11, 21));

        // Test that the player can't step on a door
        assertFalse(board.isSteppable(11, 11));

        // Test that the player can't step on a lever
        assertFalse(board.isSteppable(24, 6));

        // Test that the player can step on a pressure plate
        assertTrue(board.isSteppable(5, 18));
    }

    /**
     * Test that the canPlaceBox method works correctly
     */
    @Test
    public void TestCanPlaceBox() {
        Board board = Board.getInstance();

        // Test that the player can place a box on a space
        assertTrue(board.canPlaceBox(3, 4));

        // Test that the player can't place a box on a wall
        assertFalse(board.canPlaceBox(0, 0));

        // Test that the player can't place a box on a door
        assertFalse(board.canPlaceBox(11, 11));

        // Test that the player can't place a box on a lever
        assertFalse(board.canPlaceBox(24, 6));

        // Test that the player can place a box on a pressure plate
        assertTrue(board.canPlaceBox(5, 18));
    }

    /**
     * Test that updating an item updates the board
     */
    @Test
    public void TestUpdateItem() {
        Board board = Board.getInstance();

        // Test that the board can update a lever
        Lever lever = (Lever) board.getItemAt(24, 6);
        assertEquals('*', board.boardArray[24][6]);
        lever.setState(true);
        board.refreshItems();
        assertEquals('#', board.boardArray[24][6]);

        // Test that the board can update a pressure plate
        PressurePlate plate = (PressurePlate) board.getItemAt(5, 18);
        assertEquals('O', board.boardArray[5][18]);
        plate.setState(true);
        board.refreshItems();
        // in a typical application a box or player would show and overwrite the plate since the pressure plate still
        // exists and we don't want to overwrite our newly placed box or player, therefore, it should still be O
        assertEquals('O', board.boardArray[5][18]);

        // Test that the board can update a door
        Door door = (Door) board.getItemAt(11, 11);
        Lever leverForDoor = (Lever) board.getItemAt(30, 6);
        assertEquals('D', board.boardArray[11][11]);
        leverForDoor.setState(true);
        board.refreshItems();
        assertEquals(' ', board.boardArray[11][11]);

        // Test that the board can update a box
        Box box = (Box) board.getItemAt(11, 21);
        assertEquals('B', board.boardArray[11][21]);
        box.setState(true);
        board.refreshItems();
        assertEquals(' ', board.boardArray[11][21]);
    }

    /**
     * Test getItemAt method
     */
    @Test
    public void TestGetItemAt() {
        Board board = Board.getInstance();

        // Test that the board can get a lever
        assertNotNull(board.getItemAt(24, 6));

        // Test that the board can get a pressure plate
        assertNotNull(board.getItemAt(5, 18));

        // Test that the board can get a door
        assertNotNull(board.getItemAt(11, 11));

        // Test that the board can get a box
        assertNotNull(board.getItemAt(11, 21));
    }


}
