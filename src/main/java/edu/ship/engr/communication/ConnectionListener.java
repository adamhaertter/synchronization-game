package edu.ship.engr.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Watches to make sure once a socket is created, we touch it periodically to
 * make sure the socket is alive.  If the other side has disconnected, we
 * run the disconnect action specified.
 *
 * @author merlin
 */
public class ConnectionListener implements Runnable
{

    private final ObjectOutputStream stream;
    private final int rate;
    private Runnable disconnectAction;

    /**
     * Creates a new listener
     *
     * @param stream    The socket to listen to
     * @param frequency The rate at which we should poll the socket to check the
     *                  availability of the connection
     */
    public ConnectionListener(ObjectOutputStream stream, int frequency)
    {
        this.rate = frequency;
        this.stream = stream;
        disconnectAction = () ->
        {
            // by default, we do nothing on disconnect
        };
    }

    /**
     * @see Runnable#run()
     */
    @Override
    public void run()
    {
        try
        {
            // try writing to the connection at a regular interval
            // if it takes too long, force close the connection
            // as we can assume the connection has been lost
            while (!Thread.currentThread().isInterrupted())
            {
                if (stream != null)
                {
                    // write a single byte to poll if the socket is still
                    // available
                    stream.writeObject((byte) 0);
                }
                // poll every few seconds
                try
                {
                    Thread.sleep(rate);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            disconnectAction.run();
        }
    }

    /**
     * Use this to specify what we should do if the other side disconnects from
     * this connection.
     *
     * @param action the thing we should do when this connection disconnects
     */
    public void setDisconnectionAction(Runnable action)
    {
        this.disconnectAction = action;
    }
}
