package agents.monitor;

import agents.Message;
import agents.MetaAgent;
import agents.Portal;
import agents.monitor.ui.MonitorFrame;
import agents.util.EncodingUtil;
import java.util.ArrayList;

/**
 * A portal wrapper that intercepts and logs / copies / exports messages
 * received.
 * Messages intercepted via this MonitorAgent can be exported to other classes
 * outside of the agent system.
 * @author Aidan
 */
public class MonitorAgent extends Portal {

    /**
     * The UI frame for the Monitor.
     */
    private final MonitorFrame frame;
    
    public MonitorAgent(int capacity, String name, Portal parent, MonitorFrame monitor) {
        super(capacity, name, parent);
        this.frame = monitor;
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
        MetaAgent receive = getSubAgentOrParent(agent);
        log(message);
        if (receive == null)
        {
            return;
        }
        // Intercept and log messages for to direct children.
        receive.addMessage(message);
    }
    
    /**
     * Logs that the given message has been intercepted heading towards the 
     * given target.
     * @param message The message that has been intercepted.
     */
    protected void log(Message message){
        if (frame == null)
            return;
        ArrayList<String> path = (ArrayList<String>)
                EncodingUtil.BytesToObj(message.getData());
        if (path == null)
            return;
        frame.addPath(path);
        
    }
    
    
}
