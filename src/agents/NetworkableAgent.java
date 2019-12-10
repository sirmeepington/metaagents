/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import java.util.Random;

/**
 * A networked agent interface which provides the signature for getters and
 * setters for IP and MAC address as well as a method for getting a more
 * qualified address out of the two.
 * @author v8076743
 */
public abstract class NetworkableAgent extends SystemAgent {
    
    protected String ipAddress;
    protected String macAddress;

    public NetworkableAgent(int capacity, Portal parent) {
        super(capacity, "00:00:00:00:00:00", parent);
        assignMac();
    }
    
    /**
     * Gets the IP address of this networked agent.
     * @return The IP address of the networked agent,
     */
    public String getIpAddress(){
        return this.ipAddress;
    }
    
    /**
     * Sets the IP address of the networked agent.
     * @param ipAddress The IP address to set for this networked agent.
     */
    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }
    
    /**
     * Gets the MAC address of the networked agent.
     * @return The networked agent's MAC address.
     */
    public String getMacAddress(){
        return this.macAddress;
    }
    
    /**
     * Sets the MAC address for the networked agent.
     * @param macAddress The MAC address for the networked agent.
     */
    public void setMacAddress(String macAddress){
        this.macAddress = macAddress;
    }
    
    /**
     * Returns the most qualified address for the networked agent.
     * Implementing classes can choose which address is more qualifying in
     * the circumstances for itself.
     * @return The most qualified address for the networked agent.
     */
    public String getQualifiedAddress(){
        return getIpAddress() == null ? getMacAddress() : getIpAddress();
    }
  
   
    protected final void assignMac(){
        Random rand = new Random(System.nanoTime());
        byte[] mac = new byte[6];
        rand.nextBytes(mac);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++){
             sb.append(Integer.toHexString(0xff & mac[i]));
             if (i != mac.length-1)
                 sb.append(":");
        }
        setMacAddress(sb.toString());
    }
    
    @Override
    protected boolean canReceive(Message message) {
        return super.canReceive(message) || message.getRecipient().equals(getQualifiedAddress());
    }    
    
    @Override
    public String getName(){
        return getQualifiedAddress();
    }

    
}
