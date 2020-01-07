/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.impl.dhcp;

import agents.Portal;
import agents.main.Showcase;

/**
 * Main class for demonstrating and testing the behaviour of the MAS.
 * @author v8076743
 */
public class DhcpAgents implements Showcase {

    @Override
    public void run() {
        
        Portal router = new Portal(100, "192.168.1.1");
        
        DhcpClientAgent a1 = new DhcpClientAgent(10, router);
        DhcpClientAgent a2 = new DhcpClientAgent(10, router);
        DhcpClientAgent a3 = new DhcpClientAgent(10, router);
        DhcpClientAgent a4 = new DhcpClientAgent(10, router);
        DhcpClientAgent a5 = new DhcpClientAgent(10, router);
        DhcpClientAgent a6 = new DhcpClientAgent(10, router);
        DhcpClientAgent a7 = new DhcpClientAgent(10, router);
        DhcpClientAgent a8 = new DhcpClientAgent(10, router);
        DhcpClientAgent a9 = new DhcpClientAgent(10, router);
        DhcpServerAgent s1 = new DhcpServerAgent(10, router);

        router.addChild(a1);
        router.addChild(s1);
        router.addChild(a2);
        router.addChild(a3);
        router.addChild(a4);
        router.addChild(a5);
        router.addChild(a6);
        router.addChild(a7);
        router.addChild(a8);
        router.addChild(a9);
        
        a1.dhcpDiscover();
        a2.dhcpDiscover();
        a3.dhcpDiscover();
        a4.dhcpDiscover();
        a5.dhcpDiscover();
        a6.dhcpDiscover();
        a7.dhcpDiscover();
        a8.dhcpDiscover();
        a9.dhcpDiscover();
  
        
    }
    
    public static void main(String[] args) {
        Showcase instance = new DhcpAgents();
        instance.run();
    }
    
}
