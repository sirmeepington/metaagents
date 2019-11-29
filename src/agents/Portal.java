/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import java.util.ArrayList;

/**
 *
 * @author v8076743
 */
public class Portal extends MetaAgent {
    
    /**
     * The sub-agents of this portal.
     */
    private final ArrayList<MetaAgent> agents;
    
    public Portal(int capacity, String name) {
        super(capacity, name, null);
        agents = new ArrayList<>();
    }
    
    public Portal(int capacity, String name, Portal parent){
        super(capacity, name, parent);
        agents = new ArrayList<>();
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
        
        if (message.getRecipient().equals(Wildcard.ALL.getChar())){
            agents.forEach(a -> a.parse(message));
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
    
    protected MetaAgent getSubAgent(String name){
        for(MetaAgent agent : agents){
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

    public void addAgent(MetaAgent a){
        if (!agents.contains(a))
            agents.add(a);
    }
    
    @Override
    public void end(){
        agents.forEach((a) -> a.end());
        super.end();
    }
    
}
