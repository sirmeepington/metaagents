/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.sockets;

import agents.Message;
import agents.MetaAgent;
import agents.Portal;

/**
 * A Meta-Agent that is used to route messages through a socket.
 * @author v8076743
 */
public abstract class SocketAgent extends MetaAgent {
    
    private final int port;
    private final boolean isServer;
    
    public SocketAgent(int capacity, String name, Portal parent, int port, boolean isServer) {
        super(capacity, name, parent);
        this.port = port;
        this.isServer = isServer;
    }   
    
    public int getPort(){
        return this.port;
    }
    
    public boolean isServer(){
        return isServer;
    }
    
}
