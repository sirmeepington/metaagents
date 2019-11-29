/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 *
 * @author v8076743
 */
public class Message {
    
    private Protocol protocol;
    private String recipient;
    private String message;
    private String sender;

    public Message(String receiptant, String message, String sender) {
        this.recipient = receiptant;
        this.message = message;
        this.sender = sender;
    }

    public Message(String receiptant, String message, String sender, Protocol protocol) {
        this.recipient = receiptant;
        this.message = message;
        this.sender = sender;
        this.protocol = protocol;
    }

    public Message(Wildcard reciptant, String message, String sender){
        this.recipient = reciptant.getChar();
        this.message = message;
        this.sender = sender;
    }
    
    public Message(Wildcard reciptant, String message, String sender, Protocol protocol){
        this.recipient = reciptant.getChar();
        this.message = message;
        this.sender = sender;
        this.protocol = protocol;
    }
    
    public Message(MetaAgent recipient, String message, String sender, Protocol protocol){
        this.recipient = recipient.getName();
        this.message = message;
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

    public void setReceiptant(String receiptant) {
        this.recipient = receiptant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "\""+message+"\" for "+recipient;
    }
    
    public Protocol getProtocol(){
        return this.protocol;
    }
    
}
