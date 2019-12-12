/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import java.util.ArrayList;
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
     * The sub-agents of this portal.
     */
    private final TreeMap<String,MetaAgent> agents;
    
    public Portal(int capacity, String name) {
        super(capacity, name, null);
        agents = new TreeMap<>();
    }
    
    public Portal(int capacity, String name, Portal parent){
        super(capacity, name, parent);
        agents = new TreeMap<>();
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
    public void execute(Message message){
        
        if (message.getRecipient().equals(Wildcard.ALL.toString())){
            agents.forEach((s,a) -> a.parse(message));
            return;
        }
        
        MetaAgent receive = getSubAgent(message.getRecipient());
        if (receive == null)
        {
            System.err.println("Invalid receiptant: "+message.getRecipient());
            return;
        }
        receive.parse(message);
    }
    
    /**
     * Gets the child MetaAgent of the Portal from the input name {@code name}.
     * @param name The name of the MetaAgent.
     * @return The MetaAgent to retrieve from its name.
     */
    protected MetaAgent getSubAgent(String name){
        for(MetaAgent agent : agents.values()){
            if (agent instanceof Portal){
                MetaAgent subagent = ((Portal) agent).getSubAgent(name);
                if (subagent != null)
                    return subagent;
            }
            else if (agent.getName().equals(name)){
                    return agent;
            }
        }
        return null;
    }

    /**
     * Adds a Meta-Agent to the tree of children on this Portal.
     * If a Meta-Agent of the same name already is contained in this portal
     * then it is not added.
     * @param a The MetaAgent to add.
     */
    public void addAgent(MetaAgent a){
        if (!agents.containsKey(a.getName())){
            agents.put(a.getName(), a);
        }
    }
    
    /**
     * Sends the command to end for all children of the portal and then ends
     * this portal.
     * @see MetaAgent#end() 
     */
    @Override
    public void end(){
        agents.forEach((s,a) -> a.end());
        super.end();
    }
    
}
