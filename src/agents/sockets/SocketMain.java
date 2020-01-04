/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.sockets;

import agents.Flags;
import agents.Message;
import agents.Portal;
import agents.Protocol;
import agents.impl.misc.LogMetaAgent;
import agents.util.EncodingUtil;
import java.util.EnumSet;

/**
 *
 * @author Paul
 */
public class SocketMain {
    
    
    public static void main(String[] args) {
    
        // Setup the port
        int port = 25565;
        
        // Initialise the server
        SocketAgent server = new SocketServer(20, "Server", port);
        
        // Create a mini network called network A.
        Portal portalA = new Portal(20, "Portal A"); 
        SocketAgent clientA = new SocketClient(20, "Client A", portalA, port);
        LogMetaAgent agentA = new LogMetaAgent(10, "Agent A", portalA);
        portalA.addAgent(agentA);
        portalA.addAgent(clientA);


        // Create a mini network called network B.
        Portal portalB = new Portal(20, "Portal B"); 
        SocketAgent clientB = new SocketClient(20, "Client B", portalB, port);
        LogMetaAgent agentB = new LogMetaAgent(10, "Agent B", portalB);
        portalB.addAgent(agentB);
        portalB.addAgent(clientB);
        

        // Let the server know of our two socket clients.
        Message clientAIdent = new Message("Server", null, "Client A", Protocol.IDENT);
        Message clientBIdent = new Message("Server", null, "Client B", Protocol.IDENT);
        clientA.execute(clientAIdent);
        clientB.execute(clientBIdent);
        
        // Tell Client A to send a mesage to client B from Agent A.
        Message message = new Message("Agent B",
                EncodingUtil.StringToBytes("Hello!"), "Agent A");
        message.setFlags(EnumSet.of(Flags.INTERNAL));

        Message clientToClient = new Message("Client B",
                EncodingUtil.ObjToBytes(message), "Client A");
        clientToClient.setFlags(EnumSet.of(Flags.WRAPPED));

        
        Message agentToClient = new Message("Client A",
            EncodingUtil.ObjToBytes(clientToClient), "Agent A");
        agentToClient.setFlags(EnumSet.of(Flags.INTERNAL, Flags.WRAPPED));
        
        // Send the message via Portal A
        portalA.execute(agentToClient);
        
    }
    
    
}
