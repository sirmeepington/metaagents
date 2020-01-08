/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.impl.sockets;

import java.net.Socket;
import java.util.Objects;

/**
 * A connection to the SocketServer which contains the identifiable information
 * for the socket and the underlying connection itself.
 * @author Aidan
 */
public class SocketConnection {
    
    /**
     * The underlying socket connection to the server.
     */
    private final Socket connection;
    
    /**
     * The identifiable name for this connection.
     */
    private String clientName;

    public SocketConnection(Socket connection){
        this.connection = connection;
    }

    /**
     * Returns the socket object from the connection object.
     * @return The underlying socket for this connection.
     */
    public Socket getConnection() {
        return connection;
    }

    /**
     * Gets the client name from this client.
     * @return The client name.
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets the client's name.
     * This is updated during identification within 
     * {@link SocketServer#identify(agents.impl.sockets.SocketConnection,
     * java.lang.String)}.
     * Ideally this is only ran ONCE when the connection has been set up.
     * @param clientName The new client name for this connection.
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public int hashCode() {
        // Does not calculate clientName as we want the only discerning factor
        // to be the underlying connection itself.
        return super.hashCode() ^ connection.hashCode(); 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SocketConnection other = (SocketConnection) obj;
        if (!Objects.equals(this.clientName, other.clientName)) {
            return false;
        }
        if (!Objects.equals(this.connection, other.connection)) {
            return false;
        }
        return true;
    }
}
