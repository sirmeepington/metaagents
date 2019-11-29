/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import java.util.Random;

/**
 *
 * @author v8076743
 */
public class SystemAgent extends MetaAgent implements DHCPClient, NetworkedAgent {

    private String ipAddress;
    
    public SystemAgent(int capacity, Portal parent) {
        super(capacity, "00:00:00:00:00:00", parent);
        assignMac();
    }
    
    private void assignMac(){
        Random rand = new Random(System.nanoTime());
        byte[] mac = new byte[6];
        rand.nextBytes(mac);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++){
             sb.append(Integer.toHexString(0xff & mac[i]));
             if (i != mac.length-1)
                 sb.append(":");
        }
        this.name = sb.toString();
        rand = null;
    }

    @Override
    protected void execute(Message message) {
        if (message != null && canReceive(message)){
            if (message.getProtocol().equals("DHCP")){
                handleDhcp(message);
            }
        }
    }

    @Override
    public void dhcpDiscover() {
        System.out.println("\nSystem "+getQualifiedAddress()+" broadcasting discovery inent for DHCP.");
        getParent().execute(new Message(Wildcard.ALL,"Discover",getQualifiedAddress(),Protocol.DHCP));
    }

    @Override
    public void dhcpRequest(String sender) {
        System.out.println("System "+getQualifiedAddress()+" asking "+sender+" for IP address.");
        getParent().execute(new Message(sender, "Request", getQualifiedAddress(), Protocol.DHCP));
    }

    @Override
    public void handleDhcp(Message message) {
        if (message == null || !message.getProtocol().equals("DHCP"))
            return;
        
        String msg = message.getMessage();
        String[] in = msg.split("\\|");
        if (message.getMessage().contains("|")){
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

    @Override
    protected boolean canReceive(Message message) {
        return super.canReceive(message) || message.getRecipient().equals(getQualifiedAddress());
    }

    @Override
    public String getIpAddress() {
        return this.ipAddress;
    }

    @Override
    public void setIpAddress(String ipAddress) {
        System.out.println("IP address of "+getQualifiedAddress()+" set to "+ipAddress);
        this.ipAddress = ipAddress;
    }

    @Override
    public String getMacAddress() {
        return this.getName();
    }

    @Override
    public void setMacAddress(String macAddress) {
        this.name = macAddress;
    }
    
    @Override
    public String getQualifiedAddress() {
        return getIpAddress() == null ? getMacAddress() : getIpAddress();
    }

    
}
