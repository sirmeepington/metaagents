/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.dhcp;

import agents.util.EncodingUtil;
import agents.Message;
import agents.NetworkableAgent;
import agents.Portal;
import agents.Protocol;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Aidan
 */
public class DhcpServerAgent extends NetworkableAgent implements DhcpServer {

    private final ArrayBlockingQueue<String> ips = new ArrayBlockingQueue<>(255);
    
    public DhcpServerAgent(int capacity, Portal parent) {
        super(capacity, parent);
        for (int i = 3; i < 255; i++){
            ips.add("192.168.1."+i);
        }
        ipAddress = "192.168.1.2";
    }

    @Override
    public void execute(Message message) {
        if (message == null)
            return;
        if (message.getProtocol() == Protocol.DHCP){
            String m = EncodingUtil.BytesToString(message.getData()).toLowerCase();
            switch (m){
                case "discover":
                    dhcpOffer(message);
                    break;
                case "request":
                    dhcpAcknowledge(message);
                    break;
                default:
                    getParent().execute(message);
                    break;
            }
        } else {
            getParent().execute(message);
        }
    }

    @Override
    public void dhcpOffer(Message message) {
            // Say we exist.
        System.out.println("Received client discovery message from "+message.getSender()+" Offering...");
        execute(new Message(message.getSender(), EncodingUtil.StringToBytes("Offer"), getQualifiedAddress(),Protocol.DHCP));
    }

    @Override
    public void dhcpAcknowledge(Message message) {
        String ip = ips.poll();
        if (ip == null)
            return;
        
        System.out.println("Acknowledging client request from "+message.getSender()+" sending IP "+ip);
        String sender = message.getSender(); // MAC address
        execute(new Message(sender, EncodingUtil.StringToBytes("Acknowledge|"+ip),getQualifiedAddress(),Protocol.DHCP));
    }

    @Override
    public void setIpAddress(String ipAddress) { }

    @Override
    public String getQualifiedAddress() {
        return getIpAddress() == null ? getMacAddress() : getIpAddress();
    }
    
}
