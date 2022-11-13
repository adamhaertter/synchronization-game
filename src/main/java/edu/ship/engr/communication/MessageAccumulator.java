package edu.ship.engr.communication;

import edu.ship.engr.messages.Message;

import java.util.ArrayList;

/**
 * Manages a queue of messages that have not been sent to
 * the other side of our connection
 *
 * @author merlin
 */
public class MessageAccumulator
{

    /**
     * need this to be visible to the tests
     */
    protected ArrayList<Message<?>> pendingMsgs;

    /**
     *
     */
    public MessageAccumulator()
    {
        pendingMsgs = new ArrayList<>();

    }

    /**
     * Gives a list of all of the messages that are pending and empties this
     * accumulator
     *
     * @return the current pending messages
     */
    public synchronized ArrayList<Message<?>> getPendingMsgs()
    {
        ArrayList<Message<?>> temp = pendingMsgs;
        pendingMsgs = new ArrayList<>();
        return temp;
    }

    /**
     * Force a specific message to be put into the queue
     *
     * @param msg the msg we want to send
     */
    public void queueMessage(Message<?> msg)
    {
        synchronized (pendingMsgs)
        {
            pendingMsgs.add(msg);
        }
    }

    /**
     * Throw away any messages that are waiting to be sent out
     */
    public void purgePendingMessages()
    {
        pendingMsgs = new ArrayList<>();
    }

}
