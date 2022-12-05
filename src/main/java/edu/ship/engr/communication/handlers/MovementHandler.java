package edu.ship.engr.communication.handlers;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.Board;
import edu.ship.engr.display.entity.Player;
import edu.ship.engr.messages.BadMovementReply;
import edu.ship.engr.messages.Message;
import edu.ship.engr.messages.MovementMessage;
import edu.ship.engr.messages.TimestampOutOfSyncReply;
import edu.ship.engr.peertopeer.PlayRunner;

import java.util.LinkedHashMap;

public class MovementHandler implements Handler {

    @Override
    public void processMessage(Message<?> msgFromJSon) {
        MovementMessage movement = new MovementMessage((LinkedHashMap<String, Object>) msgFromJSon.getObject());
        System.out.println("Received movement for the other player with coordinates x" + movement.getNewX() + " y" + movement.getNewY());

        Board board = Board.getInstance();
        Timestamp ts = Timestamp.getInstance();

        // Time stamp we receive should be greater than our current one
        if (PlayRunner.IS_HOST && ts.getTimestamp() >= movement.getTimestamp()) {
            // uh oh stinky
            // logic to decide what we do here
            Player ourPlayer = board.getMyPlayer();
            // check to see if the movement of the other player conflicts with our own
            if (ourPlayer.getX() == movement.getNewX() && ourPlayer.getY() == movement.getNewY()) {
                // the other player is trying to move into our space
                PlayRunner.messageAccumulator.queueMessage(new Message<>(new BadMovementReply(movement.getOldX(), movement.getOldY(), ts.getTimestamp())));
                return;
            }
            PlayRunner.messageAccumulator.queueMessage(new Message<>(new TimestampOutOfSyncReply(ts.getTimestamp())));
        }

        ts.setTimestamp(movement.getTimestamp());

        // Move the opposite player
        board.getOtherPlayer().move(movement.getNewX(), movement.getNewY(), movement.getDirection());
    }
}
