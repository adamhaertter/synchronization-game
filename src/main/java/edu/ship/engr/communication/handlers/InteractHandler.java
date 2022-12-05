package edu.ship.engr.communication.handlers;

import edu.ship.engr.communication.Timestamp;
import edu.ship.engr.display.Board;
import edu.ship.engr.display.GUI;
import edu.ship.engr.display.entity.Box;
import edu.ship.engr.display.entity.Item;
import edu.ship.engr.display.entity.Player;
import edu.ship.engr.messages.BadInteractReply;
import edu.ship.engr.messages.InteractMessage;
import edu.ship.engr.messages.Message;
import edu.ship.engr.messages.TimestampOutOfSyncReply;
import edu.ship.engr.peertopeer.PlayRunner;

import java.util.LinkedHashMap;

public class InteractHandler implements Handler {
    @Override
    public void processMessage(Message<?> msgFromJSon) {
        System.out.println("Message as follows: " + msgFromJSon);
        InteractMessage interact = new InteractMessage((LinkedHashMap<String, Object>) msgFromJSon.getObject());
        System.out.println("Received interact for the other player with object: " + interact.getTargetX() + ", " + interact.getTargetY());

        Board board = Board.getInstance();
        Timestamp ts = Timestamp.getInstance();
        Player otherPlayer = board.getOtherPlayer();

        Item targetItem = board.getItemAtIfBox(interact.getTargetX(), interact.getTargetY());

        // The only interactable that can be hit by both players at a time is the box
        if (PlayRunner.IS_HOST && targetItem != null) {
            Box ourBox = board.getMyPlayer().getBox();
            if (ourBox!= null && ourBox.equals(targetItem)) {

                PlayRunner.messageAccumulator.queueMessage(new Message<>(new BadInteractReply(ts.getTimestamp())));
                return;
            }
            PlayRunner.messageAccumulator.queueMessage(new Message<>(new TimestampOutOfSyncReply(ts.getTimestamp())));
        }

        GUI.processInteraction(otherPlayer, interact.getTargetX(), interact.getTargetY());
    }
}
