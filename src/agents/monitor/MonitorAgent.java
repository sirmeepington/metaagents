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
 *
 * @author Aidan
 */
public class MonitorAgent extends Portal {

    public MonitorAgent(int capacity, String name, Portal parent) {
        super(capacity, name, parent);
    }
    
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
    
    protected void log(MetaAgent target, Message message){
        System.out.println("["+getName()+"] Intercepted message for " 
                + target.getName()+": \n\t"+message);
    }
    
    
}
