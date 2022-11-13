package edu.ship.engr.communication;

import java.io.IOException;
import java.net.Socket;

/**
 * Manages all the pieces necessary to manage the connection between the two
 * peers.  There are three threads that we need to set up and manage: one for
 * processing incoming messages (ConnectionIncoming), one for sending
 * outgoing messages (ConnectionOutgoing and one that
 * listens to the socket and reacts if the other side disconnect
 * (ConnectionListener)
 *
 * @author merlin
 */
public class ConnectionManager
{

    private final Thread outgoingThread;
    private final Thread incomingThread;
    private final Socket socket;

    /**
     * Create everything necessary for building messages to send to the other
     * side and handling messages that come from the other side.
     *
     * @param sock                the socket connection we are managing
     * @param outgoingAccumulator the accumulator connecting us to the rest
     *                            of the system
     * @throws IOException caused by socket issues
     */
    public ConnectionManager(Socket sock,
                             MessageAccumulator outgoingAccumulator)
            throws IOException, ClassNotFoundException
    {
        this.socket = sock;

        ConnectionOutgoing outgoing = new ConnectionOutgoing(sock,
                outgoingAccumulator);
        outgoingThread = new Thread(outgoing);
        outgoingThread.start();

        ConnectionIncoming incoming = new ConnectionIncoming(sock);
        incomingThread = new Thread(incoming);
        incomingThread.start();

        ConnectionListener cl =
                new ConnectionListener(outgoing.getStream(), 5000);
        cl.setDisconnectionAction(this::disconnect);
        Thread watcherThread = new Thread(cl);
        watcherThread.start();

    }

    /**
     * Kill the threads and let go of the socket
     */
    public void disconnect()
    {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        incomingThread.interrupt();
        outgoingThread.interrupt();
    }
}
