package edu.ship.engr.communication.handlers;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.Board;
import edu.ship.engr.messages.BadMovementReply;
import edu.ship.engr.messages.Message;

import java.util.LinkedHashMap;

/**
 * Message sent if two players tried to move onto the same spot at the same time.
 * Host will take priority in these situations
 */
public class BadMovementHandler implements Handler {

    @Override
    public void processMessage(Message<?> msg) {
        BadMovementReply badMovementReply = new BadMovementReply((LinkedHashMap<String, Object>) msg.getObject());
        // the fact that we get a BadMovementReply is enough to signal that we can't move to the spot we want to

        Board board = Board.getInstance();
        board.getMyPlayer().setPosition(badMovementReply.getOldX(), badMovementReply.getOldY());
        Timestamp.getInstance().setTimestamp(badMovementReply.getTimestamp());
    }
}
