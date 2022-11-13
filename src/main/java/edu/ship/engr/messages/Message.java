package edu.ship.engr.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"rel", "href", "method"})
public class Message<T>
{
    @JsonProperty("type")
    private Class<T> objectType;
    @JsonProperty("object")
    private Object object;

    public Message(Object object)
    {
        this.objectType = (Class<T>) object.getClass();
        this.object = object;
    }

    /**
     * We need a default constructor for the comm layer to be able to
     * unserialize
     * these objects - no one else should use this!
     */
    public Message()
    {

    }


    /**
     * We setters for the comm layer to be able to unserialize
     * these objects - no one else should use this!
     */
    public void setObjectType(Class<T> objectType)
    {
        this.objectType = objectType;
    }

    /**
     * We setters for the comm layer to be able to unserialize
     * these objects - no one else should use this!
     */
    public void setObject(Object object)
    {
        this.object = object;
    }

    public String toString()
    {
        return "messages.Message: type = " + objectType + " object = " + object;
    }

    public Object getObject()
    {
        return object;
    }

    public Class<T> getObjectType()
    {
        return objectType;
    }
}
