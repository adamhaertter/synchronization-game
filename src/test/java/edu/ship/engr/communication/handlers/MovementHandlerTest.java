package edu.ship.engr.communication.handlers;

import edu.ship.engr.communication.TestUtilities;
import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.Board;
import edu.ship.engr.display.entity.Player;
import edu.ship.engr.enums.Direction;
import edu.ship.engr.messages.BadMovementReply;
import edu.ship.engr.messages.Message;
import edu.ship.engr.messages.MovementMessage;
import edu.ship.engr.messages.ReplyObject;
import edu.ship.engr.peertopeer.PlayRunner;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementHandlerTest {

    @Test
    public void testSameBlockMove() throws IOException {
        // set up the message and then convert it to simulate what happens when
        // we sent it
        Board board = Board.getInstance();
        Player host = board.getMyPlayer();
        Timestamp.getInstance().setTimestamp(4);
        host.setPosition(3, 3);
        // simulate a movement coming in for 3, 3
        MovementMessage movementToProcess = new MovementMessage(3, 3, 3, 2, Direction.Down, 2);
        Message<MovementMessage> msgToSend = new Message<>(movementToProcess);
        Message<?> msgAfterSending = TestUtilities.convertToUnpackedJSon(msgToSend);

        MovementHandler handler = new MovementHandler();
        handler.processMessage(msgAfterSending);

        // A BadMovement should have been queued
        ArrayList<Message<?>> queuedMessages =
                PlayRunner.messageAccumulator.getPendingMsgs();
        assertEquals(1,queuedMessages.size());
        assertEquals(BadMovementReply.class,queuedMessages.get(0).getObjectType() );
    }

    @Test
    public void testRevertPickup() throws IOException {
        // this is from peer perspective
        PlayRunner.IS_HOST = false;
        Board board = Board.getInstance();
        BadMovementReply badMovement = new BadMovementReply(3, 2, 4);
        BadMovementHandler handler = new BadMovementHandler();
        Message<BadMovementReply> msgToSend = new Message<>(badMovement);
        Message<?> msgAfterSending = TestUtilities.convertToUnpackedJSon(msgToSend);
        handler.processMessage(msgAfterSending);

        // verify that the other player got moved back
        assertEquals(3, board.getMyPlayer().getX());
        assertEquals(2, board.getMyPlayer().getY());
    }
}
