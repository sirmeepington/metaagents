/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import agents.impl.dhcp.DhcpClientAgent;
import agents.impl.dhcp.DhcpServerAgent;

/**
 * Main class for demonstrating and testing the behaviour of the MAS.
 * @author v8076743
 */
public class Agents {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Portal router = new Portal(100, "192.168.1.1");
        
        DhcpClientAgent a1 = new DhcpClientAgent(5, router);
        DhcpClientAgent a2 = new DhcpClientAgent(5, router);
        DhcpClientAgent a3 = new DhcpClientAgent(5, router);
        DhcpClientAgent a4 = new DhcpClientAgent(5, router);
        DhcpClientAgent a5 = new DhcpClientAgent(5, router);
        DhcpClientAgent a6 = new DhcpClientAgent(5, router);
        DhcpClientAgent a7 = new DhcpClientAgent(5, router);
        DhcpClientAgent a8 = new DhcpClientAgent(5, router);
        DhcpClientAgent a9 = new DhcpClientAgent(5, router);
        DhcpServerAgent s1 = new DhcpServerAgent(5, router);

        router.addAgent(a1);
        router.addAgent(s1);
        router.addAgent(a2);
        router.addAgent(a3);
        router.addAgent(a4);
        router.addAgent(a5);
        router.addAgent(a6);
        router.addAgent(a7);
        router.addAgent(a8);
        router.addAgent(a9);
        
        a1.dhcpDiscover();
        a2.dhcpDiscover();
        a3.dhcpDiscover();
        a4.dhcpDiscover();
        a5.dhcpDiscover();
        a6.dhcpDiscover();
        a7.dhcpDiscover();
        a8.dhcpDiscover();
        a9.dhcpDiscover();
  
        // Ending a portal ends all of its sub-agents.
        router.end();
    }
    
}
