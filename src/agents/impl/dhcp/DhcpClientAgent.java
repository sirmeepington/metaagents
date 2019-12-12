/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.impl.dhcp;

import agents.util.EncodingUtil;
import agents.Message;
import agents.NetworkableAgent;
import agents.Portal;
import agents.Protocol;
import agents.Wildcard;

/**
 * MetaAgent implementation of the client-side version of the DHCP protocol.
 * @see DhcpClient
 * @author Aidan
 */
public class DhcpClientAgent extends NetworkableAgent implements DhcpClient {

    public DhcpClientAgent(int capacity, Portal parent) {
        super(capacity, parent);
    }
    
    /**
     * Executes the {@link #handleDhcp(agents.Message)} method if the message
     * can be received and is using the DHCP protocol.
     * @param message The incoming message.
     */
    @Override
    public void execute(Message message){
        if (canReceive(message) && message.getProtocol() == Protocol.DHCP)
            handleDhcp(message);
    }

    @Override
    public void dhcpDiscover() {
        System.out.println("\nSystem "+getQualifiedAddress()+" broadcasting discovery inent for DHCP.");
        getParent().execute(new Message(Wildcard.ALL,EncodingUtil.StringToBytes("Discover"),getQualifiedAddress(),Protocol.DHCP));
    }

    @Override
    public void dhcpRequest(String sender) {
        System.out.println("System "+getQualifiedAddress()+" asking "+sender+" for IP address.");
        getParent().execute(new Message(sender, EncodingUtil.StringToBytes("Request"), getQualifiedAddress(), Protocol.DHCP));
    }

    @Override
    public void handleDhcp(Message message) {
        if (message == null || message.getProtocol() != Protocol.DHCP)
            return;
        
        if (getIpAddress() != null && !getIpAddress().trim().equals(""))
            return; // IP has already been set. DHCP process is unnecessary.
        
        String msg = EncodingUtil.BytesToString(message.getData());
        String[] in = msg.split("\\|");
        if (msg.contains("|")){
            msg = in[0];
        }
        switch (msg.toLowerCase()){
            case "offer":
                System.out.println("Receieved server offer from "+message.getSender());
                dhcpRequest(message.getSender());
                break;
            case "acknowledge":
                String ip = in[1];
                System.out.println("System "+getQualifiedAddress()+" aknowledged IP "+ip+" from "+message.getSender());
                setIpAddress(ip);
        }
    }
}
