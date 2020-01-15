package agents.impl.sockets;

import agents.Flags;
import agents.Message;
import agents.Portal;
import agents.Protocol;
import agents.impl.misc.LogMetaAgent;
import agents.util.EncodingUtil;
import java.util.EnumSet;
import agents.main.Showcase;

/**
 * Main method to show feature of sockets on localhost.
 * This class is a showcase class for the Socket implementation.
 * @author Aidan
 */
public class SocketMain implements Showcase {
    
    @Override
    public void run() {
        // Setup the port
        final int port = 25565;
        // Initialise the server
        final SocketAgent server = new SocketServer(20, "Server", port);
        
        // Create a mini network called network A.
        Portal portalA = new Portal(20, "Portal A"); 
        SocketAgent clientA = new SocketClient(20, "Client A", portalA, "localhost", port);
        LogMetaAgent agentA = new LogMetaAgent(10, "Agent A", portalA);
        portalA.addChild(agentA);
        portalA.addChild(clientA);


        // Create a mini network called network B.
        Portal portalB = new Portal(20, "Portal B"); 
        SocketAgent clientB = new SocketClient(20, "Client B", portalB, "localhost", port);
        LogMetaAgent agentB = new LogMetaAgent(10, "Agent B", portalB);
        portalB.addChild(agentB);
        portalB.addChild(clientB);

        // Let the server know of our two socket clients.
        Message clientAIdent = new Message("Server", null, "Client A", Protocol.IDENT);
        Message clientBIdent = new Message("Server", null, "Client B", Protocol.IDENT);
        clientA.addMessage(clientAIdent);
        clientB.addMessage(clientBIdent);
        
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
        portalA.addMessage(agentToClient);
        
    }
    
    public static void main(String[] args) {
        Showcase instance = new SocketMain();
        instance.run();
    }
    
    
}
