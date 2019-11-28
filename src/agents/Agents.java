/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 *
 * @author v8076743
 */
public class Agents {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Router router = new Router(100, "192.168.1.1");
        
        SystemAgent a1 = new SystemAgent(5, router);
        SystemAgent a2 = new SystemAgent(5, router);
        SystemAgent a3 = new SystemAgent(5, router);
        SystemAgent a4 = new SystemAgent(5, router);
        SystemAgent a5 = new SystemAgent(5, router);
        SystemAgent a6 = new SystemAgent(5, router);
        SystemAgent a7 = new SystemAgent(5, router);
        SystemAgent a8 = new SystemAgent(5, router);
        SystemAgent a9 = new SystemAgent(5, router);
       
        router.addAgent(a1);
        router.addAgent(a2);
        router.addAgent(a3);
        router.addAgent(a4);
        router.addAgent(a5);
        router.addAgent(a6);
        router.addAgent(a7);
        router.addAgent(a8);
        router.addAgent(a9);
        
        a1.discover();
        a2.discover();
        a3.discover();
        a4.discover();
        a5.discover();
        a6.discover();
        a7.discover();
        a8.discover();
        a9.discover();
        
        
        // Ending a portal ends all of its sub-agents.
        router.end();
    }
    
}