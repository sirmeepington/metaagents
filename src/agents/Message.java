/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author v8076743
 */
public class Message {
    
    private MessageType typeId;
    private Protocol protocol;
    private String recipient;
    private byte[] data;
    private String sender;
    private byte flags;

    public Message(String receiptant, byte[] data, String sender) {
        this.recipient = receiptant;
        this.data = data;
        this.sender = sender;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public void setRecipient(Wildcard recipient){
        this.recipient = recipient.toString();
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        try {
            return "\""+new String(data,"UTF-8")+"\" for "+recipient;
        } catch (UnsupportedEncodingException ex) {
            return "\"[Malformed String]\" for "+recipient;
        }
    }
    
    public Protocol getProtocol(){
        return this.protocol;
    }
    
    public MessageType getMessageType(){
        return typeId;
    }

    public void setMessageType(MessageType type){
        this.typeId = type;
    }
    
    public void setFlags(byte flags){
        this.flags = flags;
    }
    
    public byte getFlags(){
        return flags;
    }
    
}
