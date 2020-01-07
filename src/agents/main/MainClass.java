/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.main;

import agents.impl.dhcp.DhcpAgents;
import agents.impl.misc.LogAgents;
import agents.impl.sockets.SocketMain;

/**
 * Welcome to Meta Agents.
 * This application shows different implementations of Meta Agents.
 * Please see the following classes and run them for demos.
 * {@link agents.impl.sockets.SocketMain}
 * {@link agents.impl.misc.LogAgents}
 * {@link agents.impl.dhcp.DhcpAgents}
 * @author Aidan
 */
public class MainClass {
    
    
    public static void main(String[] args) {
        
        if (args.length != 1){        
            System.out.println("This application shows different implementations"
                    + " of Meta Agents.");
            System.out.println("To run a specific showcase use the argument "
                    + "specified in the squre brackes ([example]).");
            System.out.println("To run in NetBeans IDE; run any of the classes "
                    + "referenced below.");
            System.out.println("DHCP Protocol: DhcpAgents [dhcp]");
            System.out.println("Generic Logging: LogAgents [generic]");
            System.out.println("Sockets: SocketMain [sockets]");
            return;
        }
        
        Showcase showcase = null;
        switch (args[0]){
            case "dhcp":
                showcase = new DhcpAgents();
                break;
                
            case "generic":
                showcase = new LogAgents();
                break;
                
            case "sockets":
                showcase = new SocketMain();
                break;
                
            default:
                return;
        }
        System.out.println("Running... use CTRL+C to exit if it doesn't "
                + "automatically!");
        
        if (showcase != null)
            showcase.run();
        
        
    }
}
