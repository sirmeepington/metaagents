package agents;

/**
 * A specialised message whose construction is restricted to the current program.
 * Messages of this type are used to update agents with system-specific 
 * information such as that addition or removal of agents to a portal, etc.
 * @see SystemAction
 * @author Aidan
 */
public class SystemMessage extends Message {
    
    /**
     * The action to take with this message.
     * @see SystemAction
     */
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
    
    /**
     * Returns the action that this System Message is providing to the agent.
     * @return The SystemAction.
     * @see SystemAction
     */
    public SystemAction getAction(){
        return this.action;
    }
    
    /**
     * Allows the program to internally update the action of the message.
     * @param action The new action to be performed with this message.
     */
    protected void setAction(SystemAction action){
        this.action = action;
    }

    /**
     * Shows that the message is a system message as well as the action that it
     * includes; then appends the results from a call to
     * {@link Message#toString()}.
     * @return The string representation of this SystemMessage.
     */
    @Override
    public String toString() {
        return "System Message: [ACTION: "+getAction()+"] "+super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
