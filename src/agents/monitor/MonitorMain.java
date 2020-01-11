package agents.monitor;

import agents.Message;
import agents.MetaAgent;
import agents.Portal;
import agents.impl.misc.LogMetaAgent;
import agents.main.Showcase;
import agents.monitor.ui.MonitorFrame;
import agents.util.EncodingUtil;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/**
 *
 * @author Aidan
 */
public class MonitorMain implements Showcase {
    
    public MonitorFrame frame;
    
    @Override
    public void run(){
        
        try {
            SwingUtilities.invokeAndWait(() -> {
                frame = new MonitorFrame();
                frame.init();
            });
        } catch (InterruptedException | InvocationTargetException ex) {
        }

        Portal portalA = new MonitorAgent(10, "Portal A", null,frame);
        Portal portalB = new Portal(10, "Portal B", portalA);
        MetaAgent agentB = new LogMetaAgent(10, "Agent B", portalB);
        Portal portalC = new Portal(10, "Portal C", portalB);
        MetaAgent agentA = new LogMetaAgent(10, "Agent A", portalC);
        
        portalA.addChild(portalB);
        portalB.addChild(portalC);
        portalB.addChild(agentB);
        portalC.addChild(agentA);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex){}
        
        System.out.println("Sending message:");
        portalA.addMessage(new Message
            ("Agent A", EncodingUtil.StringToBytes("Hello World!"), "Portal A"));
    }
    
    public static void main(String[] args) {
        Showcase instance = new MonitorMain();
        instance.run();
    }
    
}
