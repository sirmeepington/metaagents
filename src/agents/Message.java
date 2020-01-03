/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * A message object that is instantiated to send message back and forth
 * between MetaAgents.
 * @author v8076743
 */
public class Message implements Serializable {
    
    /**
     * The protocol that the message is using. 
     * This is used in the MetaAgent's parsing of the message to decide
     * what behaviour to use.
     */
    private Protocol protocol;
    /**
     * The name of the recipient of the Message.
     * This MAY need changing to use a structure to identify inter-network level
     * communication as well as local network, for example wrapping the sender
     * in layers or using a complex object.
     */
    private String recipient;
    /**
     * The data to be sent via the message.
     * The data is a byte array to allow for a range of data to be sent; as
     * sending just a string may be limiting when it comes to different uses
     * of the system.
     */
    private byte[] data;
    /**
     * The name of the sender of the message.
     * This may require the same alterations as the recipient variable to
     * discern whether the sender is from the same network or from an inter-
     * network level of agents.
     */
    private String sender;
    /**
     * The flags for the message.
     * This will need developing on to provide additional information about the
     * message; such as do-not-reply, etc.
     */
    private byte flags;
    
    /**
     * The amount of bounces the messages can have before it dies.
     */
    private int bounces = 100;

    public Message(String receiptant, byte[] data, String sender) {
        this.recipient = receiptant;
        this.data = data;
        this.sender = sender;
        this.protocol = Protocol.NONE;
    }

    public Message(String receiptant, byte[] data, String sender, Protocol protocol) {
        this.recipient = receiptant;
        this.data = data;
        this.sender = sender;
        this.protocol = protocol;
    }

    public Message(Wildcard reciptant, byte[] data, String sender){
        this.recipient = reciptant.toString();
        this.data = data;
        this.sender = sender;
        this.protocol = Protocol.NONE;
    }
    
    public Message(Wildcard reciptant, byte[] data, String sender, Protocol protocol){
        this.recipient = reciptant.toString();
        this.data = data;
        this.sender = sender;
        this.protocol = protocol;
    }
    
    public Message(MetaAgent recipient, byte[] data, String sender, Protocol protocol){
        this.recipient = recipient.getName();
        this.data = data;
        this.sender = sender;
        this.protocol = protocol;
    }

    /**
     * Returns the sender of the message.
     * @return The sender of the message.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Changes the sender of the message to the name given.
     * @param sender The name of the new message sender.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    /**
     * Returns the name of the message recipient.
     * @return The name of the message recipient.
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Changes the message recipient to the name given in {@code recipient}.
     * @param recipient The new name of the message recipient.
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    /**
     * Changes the name of the message recipient.
     * @param recipient The new name of the message recipient.
     */
    public void setRecipient(Wildcard recipient){
        this.recipient = recipient.toString();
    }

    /**
     * Returns the data in the message as a byte array.
     * @return The byte array data.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the data of the message to the byte array.
     * @param data The byte array of data.
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Attempts to decode the information in {@code data} to a string and return
     * it.
     * This also includes the name of the recipient.
     * @return The string representation of the object.
     */
    @Override
    public String toString() {
        return "\""+new String(data,StandardCharsets.UTF_8)+"\" for "+recipient;
    }
    
    /**
     * Returns the protocol that the message is using.
     * Defaults to {@link Protocol#NONE}.
     * @see Protocol
     * @return The message protocol.
     */
    public Protocol getProtocol(){
        return this.protocol;
    }
    
    /**
     * Sets the flags to the byte given.
     * @param flags The flag byte.
     */
    public void setFlags(byte flags){
        this.flags = flags;
    }
    
    /**
     * Returns the flag byte.
     * @return The flag byte.
     */
    public byte getFlags(){
        return flags;
    }
    
    public boolean ping(){
        if (bounces <= 0)
            return false;
        bounces--;
        return true;
    }
    
}
