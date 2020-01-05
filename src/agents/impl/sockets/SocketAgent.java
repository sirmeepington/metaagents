/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.impl.sockets;

import agents.MetaAgent;
import agents.Portal;

/**
 * A Meta-Agent that is used to route messages through a socket.
 * @author Aidan
 */
public abstract class SocketAgent extends MetaAgent {
    
    /**
     * The server port.
     */
    private final int port;
    
    public SocketAgent(int capacity, String name, Portal parent, int port) {
        super(capacity, name, parent);
        this.port = port;
    }   
    
    /**
     * Returns the port that the SocketAgent is using.
     * @return The port.
     */
    public int getPort(){
        return this.port;
    }
    
}