/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import agents.util.EncodingUtil;
import java.util.TreeMap;

/**
 * A Portal MetaAgent whose main purpose is to redirect incoming messages to
 * its children.
 * The portal is required to receive messages and redirect them to the 
 * applicable children via the {@link MetaAgent#canReceive(agents.Message)}
 * method that more concrete implementations of the class have overridden.
 * @author v8076743
 */
public class Portal extends MetaAgent {
    
    /**
     * The routing table for all known agents to this Portal.
     */
    private final TreeMap<String,String> agents;
    
    /**
     * All known immediate children to this Portal.
     */
    private final TreeMap<String,MetaAgent> immediateChildren;
    
    public Portal(int capacity, String name) {
        super(capacity, name, null);
        agents = new TreeMap<>();
        immediateChildren = new TreeMap<>();
    }
    
    public Portal(int capacity, String name, Portal parent){
        super(capacity, name, parent);
        agents = new TreeMap<>();
        immediateChildren = new TreeMap<>();
    }
    
    /**
     * Portals should be able to parse all message for the purposes of reading
     * the recipient and passing it onto its sub-agents.
     * @param message The message received.
     * @return True.
     */
    @Override
    protected boolean canReceive(Message message){
        return true;
    }
    
    /**
     * Passes the message onto the respective sub-agent, finding it recursively
     * by running this method on any sub-portals.
     * Messages that cannot be sent will be dropped.
     * @param message The message to pass on.
     */
    @Override
    protected void execute(Message message){
        
        if (!message.bounce())
            return;
        
        if (message.getRecipient().equals(Wildcard.ALL.toString())){
            getChildren().forEach((s,agent) -> executeOnSubAgent(s,message));
            return;
        }
        
        // Find recipient.
        handleMessage(message);
        executeOnSubAgent(message.getRecipient(), message);
    }
    
    /**
     * Handles the message; running any specific code for the message, e.g.
     * handling system messages.
     * @param message The message to parse and handle the specifics of.
     */
    protected void handleMessage(Message message){
        if (message instanceof SystemMessage){
            // System message.
            SystemMessage msg = (SystemMessage) message;
            if (msg.getAction() == SystemAction.REGISTER_AGENT){
                String newAgentName = EncodingUtil.BytesToString(msg.getData());
                if (!agents.containsKey(newAgentName)
                        && !getChildren().containsKey(newAgentName) 
                        && (getParent() == null ? true : !getParent().getName().equals(newAgentName))
                        && !newAgentName.equals(message.getSender())){
//                    System.out.println("["+getName()+"] Received REGISTER AGENT message for new agent "
//                           + newAgentName+" to be registered as "+message.getSender());
                        agents.put(newAgentName, message.getSender());

                    msg.setSender(getName());
                }
                if (getParent() != null && !getParent().agents.containsKey(newAgentName)){
                    getParent().addMessage(msg);
                }
            }
        }
    }
    
    /**
     * Runs the message on the sub-agent to this portal with the specified name;
     * this is done by choosing the next portal or sub-agent via the routing
     * table {@link #agents}.
     * @param name The name of the target sub-agent to send the message to.
     * @param message The message to send to the sub-agent.
     */
    protected void executeOnSubAgent(String name, Message message){
        if (name.equals(getName()))
            return;
        MetaAgent receive = getSubAgent(name);
        if (receive == null)
        {
            if (getParent() != null && name.equals(getParent().getName())){
                receive = getParent();
            } else {
                //System.err.println("["+getName()+"] Received message for unknown recipient: "+name+": "+message);
                return;
            }
        }
        //System.out.println("["+getName()+"] Forwarded message ("+message+") to "+receive);
        receive.addMessage(message);
    }
    
    /**
     * Retrieves a sub-agent via the routing table and the list of immediate 
     * children to find the client which best suits the name given.
     * @param name The name of the agent to find.
     * @return The MetaAgent or next portal which leads to the target.
     */
    protected MetaAgent getSubAgent(String name){
        if (immediateChildren.containsKey(name))
            return immediateChildren.get(name);
        
        if (agents.containsKey(name))
            if (immediateChildren.containsKey(agents.get(name)))
                return immediateChildren.get(agents.get(name));
        
        return null;
    }

    /**
     * Adds a Meta Agent as a direct child to the portal. 
     * This is added into a list of direct children. If a new child is added and
     * there is a portal above this one; a System message is broadcasted outward
     * to update the routing tables to apply the addition of this agent.
     * @param agent The MetaAgent to add as a child of this portal.
     */
    public void addChild(MetaAgent agent){
        if (!immediateChildren.containsKey(agent.getName())){
            immediateChildren.put(agent.getName(), agent);
            
            if (getParent() != null){
                addMessage(
                        new SystemMessage(getParent().getName(),
                                EncodingUtil.StringToBytes(agent.getName()),
                                SystemAction.REGISTER_AGENT,
                                getName()
                        )
                );
            }
        }
    }
    
    protected TreeMap<String,MetaAgent> getChildren(){
        return immediateChildren;
    }
    
    /**
     * Sends the command to end this portal.
     * @see MetaAgent#end() 
     */
    @Override
    public void end(){
        super.end();
    }
    
}
