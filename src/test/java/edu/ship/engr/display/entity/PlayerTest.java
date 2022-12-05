package edu.ship.engr.display.entity;

import edu.ship.engr.display.Board;
import edu.ship.engr.enums.Direction;
import edu.ship.engr.exceptions.InvalidTargetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    @Test
    public void testTargetPosition() {
        Board board = Board.getInstance();
        Player p = board.getMyPlayer();
        p.setPosition(3, 3);

        p.setDirection(Direction.Down);
        int[] targetPos = p.getTargetPosition();
        assertEquals(3, targetPos[0]);
        assertEquals(4, targetPos[1]);

        p.setDirection(Direction.Up);
        targetPos = p.getTargetPosition();
        assertEquals(3, targetPos[0]);
        assertEquals(2, targetPos[1]);

        p.setDirection(Direction.Right);
        targetPos = p.getTargetPosition();
        assertEquals(4, targetPos[0]);
        assertEquals(3, targetPos[1]);

        p.setDirection(Direction.Left);
        targetPos = p.getTargetPosition();
        assertEquals(2, targetPos[0]);
        assertEquals(3, targetPos[1]);
    }

    @Test
    public void testPlaceBox() throws InvalidTargetException {
        Board board = Board.getInstance();
        Player p = board.getMyPlayer();
        p.setPosition(3, 3);
        p.setDirection(Direction.Down);
        // doesn't matter about coords as long as they are different than where we are placing
        Box b = new Box(2, 2);
        p.pickupBox(b);

        p.placeBox();
        assertFalse(b.getState());
        assertEquals(3, b.getXPos());
        assertEquals(4, b.getYPos());
    }
}
