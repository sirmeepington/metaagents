/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.sockets;

import agents.Message;
import agents.Portal;
import agents.Protocol;
import agents.util.EncodingUtil;

/**
 *
 * @author Paul
 */
public class SocketMain {
    
    
    public static void main(String[] args) {
    
        
        SocketAgent server = new SocketServer(20, "Server", 25565);
        
        
        Portal rootA = new Portal(20, "Root");
        SocketAgent clientA = new SocketClient(20, "Client A", rootA, 25565);

        Portal rootB = new Portal(20, "Root");
        SocketAgent clientB = new SocketClient(20, "Client B", rootB, 25565);
        
        Message clientAIdent = new Message("Server", null, "Client A", Protocol.IDENT);
        Message clientBIdent = new Message("Server", null, "Client B", Protocol.IDENT);
        System.out.println("[MAIN] Client A sending Message "+clientAIdent);
        clientA.execute(clientAIdent);
        System.out.println("[MAIN] Client Bs sending Message "+clientBIdent);
        clientB.execute(clientBIdent);
        
        Message message = new Message("Client B", EncodingUtil.StringToBytes("Test"), "Client A");
        System.out.println("[MAIN] Client Bs sending Message "+message);
        clientA.execute(message);
    }
    
    
}
