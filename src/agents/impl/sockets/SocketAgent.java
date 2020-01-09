package agents.impl.sockets;

import agents.MetaAgent;
import agents.Portal;

/**
 * A Meta-Agent that is used to route messages through a socket.
 * @author Aidan
 */
public abstract class SocketAgent extends MetaAgent {
    
    /**
     * The server port to connect to / host on.
     */
    private final int port;
    
    public SocketAgent(int capacity, String name, Portal parent, int port) {
        super(capacity, name, parent);
        this.port = port;
    }   
    
    /**
     * Returns the port that the SocketAgent is using to connect or host on.
     * @return The port.
     */
    public int getPort(){
        return this.port;
    }
    
}
