package edu.ship.engr.display.entity;

import edu.ship.engr.display.Board;
import edu.ship.engr.enums.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the player class
 */
public class PlayerTest {

    /**
     * Tests that we can give a player coords to move to and they move to them
     */
    @Test
    public void testMove() {
        Board board = Board.getInstance();
        Player player = board.getMyPlayer();
        player.setPosition(3, 3);
        player.setDirection(Direction.Down);

        player.move(4, 3, Direction.Right);
        assertEquals(4, player.getX());
        assertEquals(3, player.getY());
        assertEquals(Direction.Right, player.getDirection());
    }

    public void testPlaceBox() {
        Board board = Board.getInstance();
    }
}
