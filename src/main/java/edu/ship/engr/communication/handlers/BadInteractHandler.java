package edu.ship.engr.communication.handlers;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.Board;
import edu.ship.engr.messages.BadInteractReply;
import edu.ship.engr.messages.Message;

import java.util.LinkedHashMap;

/**
 * Message to be sent when two players try to pick up a box at the same time. Host gets priority
 */
public class BadInteractHandler implements Handler {

    @Override
    public void processMessage(Message<?> msg) {
        BadInteractReply badInteractReply = new BadInteractReply((LinkedHashMap<String, Object>) msg.getObject());
        Board board = Board.getInstance();
        // since we got a bad interact back, we can't pick up the box
        board.getMyPlayer().setBox(null);
        Timestamp.getInstance().setTimestamp(badInteractReply.getTimestamp());
    }
}
