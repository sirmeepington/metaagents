package agents.impl.sockets;

import java.net.Socket;
import java.util.Objects;

/**
 * A connection to the SocketServer which contains the identifiable information
 * for the socket and the underlying connection itself.
 * @see SocketServer
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

    /**
     * Calculates the hashCode for this object.
     * Only the connection itself is used to calculate the hashCode.
     * @return The objects hashCode.
     */
    @Override
    public int hashCode() {
        // Does not calculate clientName as we want the only discerning factor
        // to be the underlying connection itself.
        return Objects.hash(connection);
    }

    /**
     * Checks the equality of two SocketConnections.
     * @param obj The other connection to check equality of.
     * @return True if the Connections are equal; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SocketConnection other = (SocketConnection) obj;
        return Objects.equals(this.clientName, other.clientName) 
                && Objects.equals(this.connection, other.connection);
    }
}
