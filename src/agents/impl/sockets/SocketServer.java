package agents.impl.sockets;

import agents.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A server-sided socket implementation that allows for transmitting 
 * {@link Message}s across a network. 
 * The SocketServer is used as a relay in this case for directing messages 
 * across the network to many different clients.
 * When a client connects to this SocketServer an instance of 
 * {@link SocketHandler} is spawned and ran to handle the inbound/outbound
 * connections and freeing up the server thread to accept more connections 
 * if needed.
 * @author Aidan
 */
public class SocketServer extends SocketAgent {
    
    /**
     * The underlying server socket to accept connections through.
     */
    private ServerSocket socket;
    
    /**
     * A thread-safe HashMap implementation to manage the current connections.
     */
    private ConcurrentHashMap<SocketConnection, SocketHandler> connections;

    public SocketServer(int capacity, String name, int port) {
        super(capacity, name, null, port);
    }
    
    @Override
    protected void threadRun(){
        try {
            connections = new ConcurrentHashMap<>();
            socket = new ServerSocket(getPort());
        } catch (IOException ex){
            return;
        }
        while (isRunning()){
            try {
                Socket s = socket.accept();
                if (containsSocket(s)){
                    continue;
                }
                SocketConnection conn = new SocketConnection(s);
                SocketHandler thread = new SocketHandler(this,conn);
                thread.start();
                connections.put(conn, thread);
            } catch (IOException ex) { }
        }
    }
    
    /**
     * Checks if the connection map contains the socket given.
     * @param s The socket to check existence of.
     * @return True if the socket exists; false otherwise.
     */
    private boolean containsSocket(Socket s){
        for(SocketConnection conn : connections.keySet()){
            if (conn.getConnection().equals(s))
                return true;
        }
        return false;
    }

    /**
     * Prevents SocketServers from executing messages.
     * @param message The message to execute (does nothing).
     */
    @Override
    protected void execute(Message message) {}
    
    /**
     * Gets the underlying {@link ServerSocket} from this server object.
     * @return The underlying ServerSocket
     */
    protected ServerSocket getSocket(){
        return this.socket;
    }
    
    /**
     * Returns a {@link SocketHandler} based off of the {@code clientName}
     * specified. 
     * This checks the connected {@link SocketConnection}s for a client name
     * that matches the one given and returns the handler.
     * @param clientName The client name to search for.
     * @return A valid handler which is for the user {@code clientName} or null
     * if one does not exist.
     */
    protected final SocketHandler getHandler(String clientName){
        for(SocketConnection conn : connections.keySet()){
            if (conn.getClientName() != null 
                    && conn.getClientName().equals(clientName))
                return connections.get(conn);
        }
        return null;
    }
    
    /**
     * Registers the identity of a given {@link SocketConnection}, overwriting
     * its {@link SocketConnection#clientName} with the one given and saving it.
     * Ideally this is ran only once; future checks will be put in place to
     * ensure the single-use of this method for assigning identities to a
     * connection.
     * @param sc The {@link SocketConnection} to change the client name of.
     * @param clientName The new client's name to register as the socket's
     * identity.
     */
    protected final void identify(SocketConnection sc, String clientName){
        System.out.println("["+getName()+"] Running identify protocol on "
                +sc.getConnection().getInetAddress()+":"
                +sc.getConnection().getPort()+" to be "+clientName);
        for(SocketConnection conn : connections.keySet()){
            if (sc.equals(conn)){
                sc.setClientName(clientName);
                connections.get(sc).setName("SocketHandler for "+clientName);
                break;
            }
        }
    }
}
