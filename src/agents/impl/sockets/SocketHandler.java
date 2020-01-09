/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.impl.sockets;

import agents.Flags;
import agents.Message;
import agents.Protocol;
import agents.util.EncodingUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * The handler thread for a {@link SocketConnection} to a {@link SocketServer}.
 * This thread is spawned on connection from a client to a server where 
 * the client's socket is not already registered to a handler.
 * @author Aidan
 */
public class SocketHandler extends Thread {

    /**
     * The {@link SocketConnection} that is associated with this handler.
     */
    private final SocketConnection connection;
    
    /**
     * The {@link SocketServer} that the handler is from.
     */
    private final SocketServer server;

    private final ArrayBlockingQueue<Message> messagesToSend;
    private final ArrayBlockingQueue<Message> parseQueue;

    public SocketHandler(SocketServer server, SocketConnection socket) {
        setName("SocketHandler");
        this.server = server;
        this.connection = socket;
        messagesToSend = new ArrayBlockingQueue<>(25);
        parseQueue = new ArrayBlockingQueue<>(25);
    }

    @Override
    public void run() {
        while (!connection.getConnection().isClosed()) {
            give();
            take();
            parse();
        }
    }

    /**
     * Takes available data from the socket and adds it to the 
     * {@link #parseQueue} queue if it matches certain criteria.
     */
    private void take() {
        try {
            InputStream str = connection.getConnection().getInputStream();
            if (str.available() <= 0)
                return;
            ObjectInputStream in = new ObjectInputStream(str);
            Message m = (Message) in.readObject();
            if (m.getProtocol() != Protocol.NET) {
                return;
            }
            if (m.getRecipient().equals(server.getName())) {
                parseQueue.add(m);
            }

        } catch (IOException | ClassNotFoundException ex) {
        }
    }

    /**
     * Parses a message from the queue and adds it to the 
     * {@link #messagesToSend} queue if appropriate.
     */
    private void parse() {
        if (parseQueue.isEmpty()) {
            return;
        }

        Message message = parseQueue.remove();
        if (message.getFlags().contains(Flags.WRAPPED)){
            message = (Message) EncodingUtil.BytesToObj(message.getData());
        }

        if (message.getProtocol() == Protocol.IDENT) {
            server.identify(connection, message.getSender());
        }
        

        if (!message.getRecipient().equals(server.getName())) {
            messagesToSend.add(message);
        }
    }

    /**
     * Takes a message from the {@link #messagesToSend} and finds an appropriate
     * client and give it to the them or passes through the socket for the 
     * client.
     */
    private void give() {
        if (messagesToSend.isEmpty()) {
            return;
        }
        Message message = messagesToSend.remove();
        if (message.getFlags().contains(Flags.WRAPPED)){
            message = (Message) EncodingUtil.BytesToObj(message.getData());
        }
        if (!message.getRecipient().equals(connection.getClientName()) 
                && !message.getFlags().contains(Flags.INTERNAL)) {
            SocketHandler handler = server.getHandler(message.getRecipient());
            if (handler == null) {
                return;
            }
            handler.add(message);
        } else {
            try {
                ObjectOutputStream out = new ObjectOutputStream(
                        connection.getConnection().getOutputStream());
                out.writeObject(message);
            } catch (IOException ex) { }
        }
    }
    
    /**
     * Adds a message to the parse queue.
     * This can be called from other areas to add a message to parse for this
     * client on the socket server.
     * @param message The message to add to the parse queue.
     */
    protected void add(Message message){
        parseQueue.add(message);
    }

}
