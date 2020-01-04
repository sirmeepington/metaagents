/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.sockets;

import java.net.Socket;
import java.util.Objects;

/**
 *
 * @author Paul
 */
public class SocketConnection {
    
    
    private Socket connection;
    
    private String clientName;

    public SocketConnection(Socket connection){
        this.connection = connection;
    }

    
    public Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String ClientName) {
        this.clientName = ClientName;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ connection.hashCode(); //To change body of generated methods, choose Tools | Templates.
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
