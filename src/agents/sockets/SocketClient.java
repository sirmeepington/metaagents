/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.sockets;

import agents.Flags;
import agents.Message;
import agents.Portal;
import agents.Protocol;
import agents.util.EncodingUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.EnumSet;

/**
 *
 * @author Paul
 */
public class SocketClient extends SocketAgent {

    private Socket client;
    
    private volatile boolean running;
    
    public SocketClient(int capacity, String name, Portal parent, int port) {
        super(capacity, name, parent, port, false);
    }
    
    @Override
    protected void threadRun(){
        try {
            running = true;
            client = new Socket("localhost",getPort());
            System.out.println("["+getName()+"] Created connection to " +
                    client.getInetAddress());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        while (running){
            
            take();
            give();
            
        }
    }
    
    private void take(){
        try {
            InputStream str = client.getInputStream();
            if (str.available() <= 0)
                return;
            ObjectInputStream in = new ObjectInputStream(str);
            Message m = (Message)in.readObject();
            System.out.println("["+getName()+"] Received message "+m);
            getParent().execute(m);
        } catch (IOException | ClassNotFoundException ex){
            System.err.println(ex.getMessage());
        } 
    }
    
    private void give(){
        if (getQueue().isEmpty())
            return;
        if (client == null)
            return;
        Message message = getQueue().remove();
        try {
            if (message.getFlags().contains(Flags.WRAPPED) && message.getSender().equals(getName())){
                message = (Message)EncodingUtil.BytesToObj(message.getData());
            }
            System.out.println("["+getName()+"] Sending message (" +
                    message+") across socket...");
            Message socketMessage = new Message("Server",
                    EncodingUtil.ObjToBytes(message),getName(), Protocol.NET);
            socketMessage.setFlags(EnumSet.of(Flags.WRAPPED));
            ObjectOutputStream stream = new ObjectOutputStream(
                    client.getOutputStream());
            stream.writeObject(socketMessage);
            System.out.println("["+getName()+"] Wrote message across socket.");
        } catch (IOException ex)
        {
            System.err.println("["+getName()+"] Failed to write message " +
                    message+": "+ex.getMessage());
        }
    }

    @Override
    public void execute(Message message) {
        getQueue().add(message);
    }
    
}
