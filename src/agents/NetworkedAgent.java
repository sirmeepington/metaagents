/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 * A networked agent interface which provides the signature for getters and
 * setters for IP and MAC address as well as a method for getting a more
 * qualified address out of the two.
 * @author v8076743
 */
public interface NetworkedAgent {
    
    /**
     * Gets the IP address of this networked agent.
     * @return The IP address of the networked agent,
     */
    public String getIpAddress();
    
    /**
     * Sets the IP address of the networked agent.
     * @param ipAddress The IP address to set for this networked agent.
     */
    public void setIpAddress(String ipAddress);
    
    /**
     * Gets the MAC address of the networked agent.
     * @return The networked agent's MAC address.
     */
    public String getMacAddress();
    
    /**
     * Sets the MAC address for the networked agent.
     * @param macAddress The MAC address for the networked agent.
     */
    public void setMacAddress(String macAddress);
    
    /**
     * Returns the most qualified address for the networked agent.
     * Implementing classes can choose which address is more qualifying in
     * the circumstances for itself.
     * @return The most qualified address for the networked agent.
     */
    public String getQualifiedAddress();
    
}
