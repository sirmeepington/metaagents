/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.monitor;

import agents.Message;
import agents.MetaAgent;
import agents.Portal;

/**
 * A portal wrapper that intercepts and logs / copies / exports messages
 * received.
 * Messages intercepted via this MonitorAgent can be exported to other classes
 * outside of the agent system.
 * @author Aidan
 */
public class MonitorAgent extends Portal {

    public MonitorAgent(int capacity, String name, Portal parent) {
        super(capacity, name, parent);
    }
    
    /**
     * Overrides the execution of a message on a sub-agent.
     * Implements same behaviour as parent except the interception of messages
     * meant for direct children of this agent.
     * @param agent The name of the sub-agent.
     * @param message The message intended for it this agent.
     */
    @Override
    protected void executeOnSubAgent(String agent, Message message){
        MetaAgent receive = getSubAgent(agent);
        if (receive == null)
        {
            return;
        }
        
        // Intercept and log messages for to direct children.
        if (getChildren().containsValue(receive))
            log(receive,message);
        receive.addMessage(message);
    }
    
    /**
     * Logs that the given message has been intercepted heading towards the 
     * given target.
     * @param target The target that the message is intended to be for.
     * @param message The message that has been intercepted.
     */
    protected void log(MetaAgent target, Message message){
        System.out.println("["+getName()+"] Intercepted message for " 
                + target.getName()+": \n\t"+message);
    }
    
    
}
