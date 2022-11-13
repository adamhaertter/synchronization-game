package edu.ship.engr.communication.handlers;

import edu.ship.engr.messages.Message;

/**
 * Handlers process incoming messages
 */
public interface Handler
{
    /**
     * Process the message.  The message that we are given is the result of
     * building an object from the JSon string that was sent.  While the class
     * name it contains will be correct, the object it contains will be a
     * hashmap holding the fields of the object that was being sent.  See the
     * FirstMessageHandler for an example of how to rebuild the original object
     * that was sent.
     * @param msg the message we are to process
     */
    void processMessage(Message<?> msg);
}
