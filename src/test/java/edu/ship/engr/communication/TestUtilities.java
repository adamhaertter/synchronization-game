package edu.ship.engr.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ship.engr.messages.Message;

import java.io.IOException;

public class TestUtilities
{

    /**
     * Convert a Message to JSon and back to a Message. The result will be a
     * Message where the object it holds is a HashMap of the fields of the
     * original message
     * @param msg the message we want to convert
     * @return the converted message
     * @throws IOException shouldn't
     */
    public static Message<?> convertToUnpackedJSon(Message<?> msg)
            throws IOException
    {
        String json =
                (new ObjectMapper()).writer().withDefaultPrettyPrinter()
                        .writeValueAsString(msg);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json,
                Message.class);
    }
}
