package edu.ship.engr.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ship.engr.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Responsible for managing the incoming side of the socket connection
 *
 * @author Merlin
 */
public class ConnectionIncoming implements Runnable
{

    private final MessageHandlerSet messageHandler;
    private ObjectInputStream istream;
    private final Socket socket;

    /**
     * @param socket Socket being used
     * @throws ClassNotFoundException if you see this, you have not added your
     *                                message type to the array in
     *                                MessageHandlerSet
     */
    public ConnectionIncoming(Socket socket) throws ClassNotFoundException
    {
        this.socket = socket;
        this.messageHandler = new MessageHandlerSet();
    }

    /**
     * This is the thread that will listen for and handle incoming messages
     *
     * @see Runnable#run()
     */
    public void run()
    {
        try
        {
            if (socket != null)
            {
                this.istream = new ObjectInputStream(socket.getInputStream());
            }
            while (!Thread.currentThread().isInterrupted())
            {
                assert socket != null;
                if (socket.getInputStream().available() == 0)
                {
                    Thread.sleep(100);
                    continue;
                }

                Object incomingJSONString = this.istream.readObject();
                if (String.class.isAssignableFrom(incomingJSONString.getClass()))
                {
                    Message<?> msg =
                            convertJSONStringToMessage((String) incomingJSONString);
                    messageHandler.process(msg);
                }
            }
        }
        catch (InterruptedException E)
        {
            // ok - we just need to quit
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static Message<?> convertJSONStringToMessage(String incomingMsg)
            throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(incomingMsg,
                Message.class);
    }
}
