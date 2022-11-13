package edu.ship.engr.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ship.engr.messages.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Responsible for sending outgoing messages
 *
 * @author Merlin
 */
public class ConnectionOutgoing implements Runnable
{

    private ObjectOutputStream ostream;
    /**
     *
     */
    private final MessageAccumulator stateAccumulator;


    /**
     * @param socket           Socket being used - will be null for JUnit tests
     * @param stateAccumulator the accumulator that is gathering events that
     *                         should be sent to the other side
     * @throws IOException Exception thrown for invalid input or output
     */
    public ConnectionOutgoing(Socket socket,
                              MessageAccumulator stateAccumulator)
            throws IOException
    {
        if (socket != null)
        {
            this.ostream = new ObjectOutputStream(socket.getOutputStream());
        }
        this.stateAccumulator = stateAccumulator;
    }

    /**
     * @see Runnable#run()
     */
    public void run()
    {
        try
        {
            boolean done = false;
            while (!Thread.currentThread().isInterrupted() && !done)
            {
                ArrayList<Message<?>> msgs = stateAccumulator.getPendingMsgs();
                if (msgs.size() == 0)
                {
                    Thread.sleep(100);
                    continue;
                }

                if (ostream != null)
                {
                    for (Message<?> msg : msgs)
                    {
                        try
                        {
                            System.out.println("Sending " + msg);
                            String json =
                                    (new ObjectMapper()).writer().withDefaultPrettyPrinter()
                                            .writeValueAsString(msg);
                            this.ostream.writeObject(json);
                        }
                        catch (SocketException e)
                        {
                            cleanUpAndExit();
                            done = true;
                        }
                    }
                }

            }
        }
        catch (InterruptedException E)
        {
            cleanUpAndExit();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void cleanUpAndExit()
    {

    }

    /**
     * @return the state accumulator associated with this outgoing connecting
     */
    public MessageAccumulator getStateAccumulator()
    {
        return stateAccumulator;
    }

    /**
     * @return the output stream we are writing to
     */
    public ObjectOutputStream getStream()
    {
        return this.ostream;
    }
}
