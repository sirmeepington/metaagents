/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.sockets;

import agents.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Paul
 */
public class SocketServer extends SocketAgent {
    
    private ServerSocket socket;
    
    private ConcurrentHashMap<SocketConnection, SocketHandler> connections;
    
    private final Object lock;

    public SocketServer(int capacity, String name, int port) {
        super(capacity, name, null, port, true);
        lock = new Object();
    }
    
    @Override
    protected void threadRun(){
        try {
            connections = new ConcurrentHashMap<>();
            socket = new ServerSocket(getPort());
            System.out.println("["+getName()+"] Bound and created socket on port "
                    + getPort());
        } catch (IOException ex){
            System.err.println("["+getName()+"] Cannot bind Server Socket to port "
                    + getPort());
            return;
        }
        while (isRunning()){
            try {
                Socket s = socket.accept();
                SocketConnection conn = new SocketConnection(s);
                SocketHandler thread = new SocketHandler(this,conn);
                thread.setName("SocketHandler");
                thread.start();
                connections.put(conn, thread);
                System.out.println("["+getName()+"] Created handler thread for "
                        + s.getInetAddress()+":"+s.getPort());
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public void execute(Message message) {}
    
    protected ServerSocket getSocket(){
        return this.socket;
    }
    
    protected SocketHandler getHandler(String clientName){
        for(SocketConnection conn : connections.keySet()){
            if (conn.getClientName() != null && conn.getClientName().equals(clientName))
                return connections.get(conn);
        }
        return null;
    }
    
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
