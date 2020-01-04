/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.sockets;

import agents.Message;
import agents.Protocol;
import agents.util.EncodingUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.in;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Paul
 */
public class SocketHandler extends Thread {

    private final SocketConnection connection;
    private final SocketServer server;

    private final ArrayBlockingQueue<Message> messagesToSend;
    private final ArrayBlockingQueue<Message> parseQueue;

    public SocketHandler(SocketServer server, SocketConnection socket) {
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
     * Takes from the socket.
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

    private void parse() {
        if (parseQueue.isEmpty()) {
            return;
        }

        Message m = parseQueue.remove();
        Message message = (Message) EncodingUtil.BytesToObj(m.getData());
        if (message == null) {
            return;
        }

        if (message.getProtocol() == Protocol.IDENT) {
            server.identify(connection, message.getSender());
        }

        if (!message.getRecipient().equals(server.getName())) {
            messagesToSend.add(message);
        }
    }

    /**
     * Gives to the current client (or other connections).
     */
    private void give() {
        if (messagesToSend.isEmpty()) {
            return;
        }

        // Do parsing.
        Message message = messagesToSend.remove();
        System.out.println("["+getName()+"] Found message to send: " + message);

        if (!message.getRecipient().equals(connection.getClientName())) {
            SocketHandler handler = server.getHandler(message.getRecipient());
            if (handler == null) {
                System.out.println("["+getName()+"] I can't find the recipient. Have they identified?");
                return;
            }
            System.out.println("["+getName()+"] Found SocketConnection for " + message.getRecipient() + ": " + handler.getName());
            handler.add(message);
        } else {

            try {
                ObjectOutputStream out = new ObjectOutputStream(connection.getConnection().getOutputStream());
                out.writeObject(message);
                System.out.println("["+getName()+"] Wrote message " + message + " to my client.");
            } catch (IOException ex) {
                System.err.println("["+getName()+"] Exception writing to socket: "+ex.getMessage());
            }
        }
    }
    
    protected void add(Message message){
        parseQueue.add(message);
    }

}
