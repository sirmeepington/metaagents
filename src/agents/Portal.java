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
     * The sub-agents of this portal.
     */
    private final TreeMap<String,String> agents;
    
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
    public void execute(Message message){
        
        if (!message.bounce())
            return;
        
        handleMessage(message);
        
        if (message.getRecipient().equals(Wildcard.ALL.toString())){
            immediateChildren.forEach((s,agent) -> agent.addMessage(message));
            return;
        }
        
        // Find recipient.
        executeOnSubAgent(message.getRecipient(), message);
    }
    
    private void handleMessage(Message message){
        if (message instanceof SystemMessage){
            // System message.
            SystemMessage msg = (SystemMessage) message;
            if (msg.getAction() == SystemAction.REGISTER_AGENT){
                String newAgentName = EncodingUtil.BytesToString(msg.getData());
                if (!agents.containsKey(newAgentName) && !immediateChildren.containsKey(newAgentName)){
                System.out.println("["+getName()+"] Received REGISTER AGENT message for new agent "
                        + newAgentName+" to be registered as "+message.getSender());
                    agents.put(newAgentName, message.getSender());
                }
                msg.setSender(getName());
                if (getParent() != null)
                    getParent().addMessage(msg);
            }
        }
    }
    
    protected void executeOnSubAgent(String name, Message message){
        MetaAgent receive = getSubAgent(name);
        if (receive == null)
        {
//            System.err.println("["+getName()+"] Invalid recipient: "+name+" ("+message+")");
            return;
        }
        receive.addMessage(message);
    }
    
    /**
     * Gets the child MetaAgent of the Portal from the input name {@code name}.
     * @param name The name of the MetaAgent.
     * @return The MetaAgent to retrieve from its name.
     */
//    protected MetaAgent getSubAgent(String name){
//        for(MetaAgent agent : agents.values()){
//            if (agent instanceof Portal){
//                MetaAgent subagent = ((Portal) agent).getSubAgent(name);
//                if (subagent != null)
//                    return subagent;
//            }
//            else if (agent.getName().equals(name)){
//                    return agent;
//            }
//        }
//        return null;
//    }
    
    protected MetaAgent getSubAgent(String name){
        
        if (immediateChildren.containsKey(name))
            return immediateChildren.get(name);
        
        if (agents.containsKey(name))
            if (immediateChildren.containsKey(agents.get(name)))
                return immediateChildren.get(agents.get(name));
        
        return null;
    }

    /**
     * Adds a Meta-Agent to the tree of children on this Portal.
     * If a Meta-Agent of the same name already is contained in this portal
     * then it is not added.
     * @param agent The MetaAgent to add.
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
    
    /**
     * Sends the command to end for all children of the portal and then ends
     * this portal.
     * @see MetaAgent#end() 
     */
    @Override
    public void end(){
//        agents.forEach((s,a) -> a.end());
        super.end();
    }
    
}
