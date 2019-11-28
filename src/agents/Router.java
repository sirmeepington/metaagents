/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author v8076743
 */
public class Router extends Portal implements DHCPServer, NetworkedAgent {
    
    private final String ipAddress = "192.168.1.1";
    
    private final ArrayBlockingQueue<String> ips = new ArrayBlockingQueue<>(255);
    
    public Router(int capacity, String name) {
        super(capacity, name);
        for (int i = 2; i < 255; i++){
            ips.add("192.168.1."+i);
        }
    }

    @Override
    protected void execute(Message message) {
        if (message == null)
            return;
        if (message.getProtocol().equals("DHCP")){
            switch (message.getMessage().toLowerCase()){
                case "discover":
                    dhcpOffer(message);
                    break;
                case "request":
                    dhcpAcknowledge(message);
                    break;
                default:
                    super.execute(message);
                    break;
            }
        } else {
            super.execute(message);
        }
    }

    @Override
    public void dhcpOffer(Message message) {
        // Say we exist. 
        execute(new Message(message.getSender(), "Offer", getQualifiedAddress(),Protocol.DHCP));
    }

    @Override
    public void dhcpAcknowledge(Message message) {
        String ip = ips.poll();
        if (ip == null)
            return;
        
        String sender = message.getSender(); // MAC address
        execute(new Message(sender, "Acknowledge|"+ip,getQualifiedAddress(),Protocol.DHCP));
    }

    @Override
    public String getIpAddress() {
        return this.ipAddress;
    }

    @Override
    public void setIpAddress(String ipAddress) { }

    @Override
    public String getMacAddress() {
        return this.getName();
    }

    @Override
    public void setMacAddress(String macAddress) { }

    @Override
    public String getQualifiedAddress() {
        return getIpAddress() == null ? getMacAddress() : getIpAddress();
    }
    
}
