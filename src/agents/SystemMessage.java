/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 *
 * @author Aidan
 */
public class SystemMessage extends Message {
    
    private SystemAction action;
    
    protected SystemMessage(String receiptant, byte[] data, String sender) {
        super(receiptant, data, sender);
        this.action = SystemAction.NONE;
    }
    
    protected SystemMessage(String recipient, byte[] data, SystemAction action, String sender){
        super(recipient, data, sender);
        this.action = action;
    }
    
    protected SystemMessage(Wildcard recipient, byte[] data, SystemAction action, String sender){
        super(recipient,data,sender);
        this.action = action;
    }
    
    public SystemAction getAction(){
        return this.action;
    }
    
    protected void setAction(SystemAction action){
        this.action = action;
    }

    
    
}
