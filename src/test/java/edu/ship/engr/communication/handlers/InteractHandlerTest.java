package edu.ship.engr.communication.handlers;

import edu.ship.engr.communication.TestUtilities;
import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.Board;
import edu.ship.engr.display.entity.Box;
import edu.ship.engr.display.entity.Item;
import edu.ship.engr.display.entity.Player;
import edu.ship.engr.enums.Direction;
import edu.ship.engr.messages.BadInteractReply;
import edu.ship.engr.messages.BadMovementReply;
import edu.ship.engr.messages.InteractMessage;
import edu.ship.engr.messages.Message;
import edu.ship.engr.peertopeer.PlayRunner;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InteractHandlerTest {

    @Test
    public void testSameBoxPickup() throws IOException {
        // set up the message and then convert it to simulate what happens when
        // we sent it
        Board board = Board.getInstance();
        Player host = board.getMyPlayer();
        Timestamp.getInstance().setTimestamp(4);
        host.setPosition(10, 21);
        host.setDirection(Direction.Right);

        // simulate box pickup on box at 11, 21
        Item targetBox = board.getItemAtIfBox(11, 21);
        host.setBox((Box) targetBox);
        InteractMessage pickupToProcess = new InteractMessage(11, 21, 2);
        Message<InteractMessage> msgToSend = new Message<>(pickupToProcess);
        Message<?> msgAfterSending = TestUtilities.convertToUnpackedJSon(msgToSend);

        InteractHandler handler = new InteractHandler();
        handler.processMessage(msgAfterSending);

        // A BadInteract should have been queued
        ArrayList<Message<?>> queuedMessages =
                PlayRunner.messageAccumulator.getPendingMsgs();
        assertEquals(1,queuedMessages.size());
        assertEquals(BadInteractReply.class,queuedMessages.get(0).getObjectType() );
    }

    @Test
    public void testRemoveClonedBox() throws IOException {
        // this is from peer perspective
        PlayRunner.IS_HOST = false;
        Board board = Board.getInstance();

        // simulate box pickup on box at 11, 21
        Item targetBox = board.getItemAtIfBox(11, 21);
        board.getMyPlayer().setBox((Box) targetBox);
        board.getOtherPlayer().setBox((Box) targetBox);

        BadInteractReply badInteract = new BadInteractReply(4);
        BadInteractHandler handler = new BadInteractHandler();
        Message<BadInteractReply> msgToSend = new Message<>(badInteract);
        Message<?> msgAfterSending = TestUtilities.convertToUnpackedJSon(msgToSend);
        handler.processMessage(msgAfterSending);

        // verify that the other player does not have a box
        assertNull(board.getMyPlayer().getBox());
        assertNotNull(board.getOtherPlayer().getBox());
    }
}
