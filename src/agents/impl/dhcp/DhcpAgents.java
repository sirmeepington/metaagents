package agents.impl.dhcp;

import agents.Portal;
import agents.main.Showcase;

/**
 * A showcase class to demonstrate the DHCP protocol implementation via 
 * MetaAgents.
 * @author Aidan
 */
public class DhcpAgents implements Showcase {

    /**
     * Begins the DHCP demonstration
     * @see agents.main.MainClass
     */
    @Override
    public void run() {
        // Initialise the portal to route messages through.
        Portal router = new Portal(100, "192.168.1.1");
        // Create client agents.
        DhcpClientAgent a1 = new DhcpClientAgent(10, router);
        DhcpClientAgent a2 = new DhcpClientAgent(10, router);
        DhcpClientAgent a3 = new DhcpClientAgent(10, router);
        DhcpClientAgent a4 = new DhcpClientAgent(10, router);
        DhcpClientAgent a5 = new DhcpClientAgent(10, router);
        // Create a server agent.
        DhcpServerAgent s1 = new DhcpServerAgent(10, router);
        // Add them as direct children to the router.
        router.addChild(a1);
        router.addChild(s1);
        router.addChild(a2);
        router.addChild(a3);
        router.addChild(a4);
        router.addChild(a5);
        // Begin DHCP via Discover. 
        a1.dhcpDiscover();
        a2.dhcpDiscover();
        a3.dhcpDiscover();
        a4.dhcpDiscover();
        a5.dhcpDiscover();
    }
    
    public static void main(String[] args) {
        Showcase instance = new DhcpAgents();
        instance.run();
    }
    
}
