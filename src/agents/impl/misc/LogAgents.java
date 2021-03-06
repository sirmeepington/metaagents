package agents.impl.misc;

import agents.Message;
import agents.MetaAgent;
import agents.Portal;
import agents.main.Showcase;
import agents.util.EncodingUtil;

/**
 * A demonstration of the generic portal to client transfer with intermediary 
 * portals.
 * @author Aidan
 */
public class LogAgents implements Showcase {
    
    @Override
    public void run() {
        
        
        Portal portalA = new Portal(10, "Portal A", null);
        Portal portalB = new Portal(10, "Portal B", portalA);
        Portal portalC = new Portal(10, "Portal C", portalB);
        MetaAgent agentA = new LogMetaAgent(10, "Agent A", portalC);
        
        portalA.addChild(portalB);
        portalB.addChild(portalC);
        portalC.addChild(agentA);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex){}
        
        System.out.println("Sending message:");
        portalA.addMessage(new Message
            ("Agent A", EncodingUtil.StringToBytes("Hello World!"), "Portal A"));
        
    }
    
    public static void main(String[] args) {
        Showcase instance = new LogAgents();
        instance.run();
    }

    
    
}
