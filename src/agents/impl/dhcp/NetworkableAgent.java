package agents.impl.dhcp;

import agents.Message;
import agents.Portal;
import agents.SystemAgent;
import java.util.Random;

/**
 * An extension of MetaAgent that provides methods and variables for IP and MAC
 * addresses as well as the getters/setters and behaviour to assign a random MAC
 * address on creation of the agent. 
 * This agent replaces the {@code name} variable with a MAC address on creation
 * and overrides {@link agents.MetaAgent#getName()} by returning the most
 * qualified address for the NetworkableAgent. 
 * @see #getQualifiedAddress() 
 * @see #getName() 
 * @author Aidan
 */
public abstract class NetworkableAgent extends SystemAgent {
    
    /**
     * A string denoting the IPv4 address of this agent.
     */
    protected String ipAddress;
    /**
     * A string denoting the 6-byte MAC address of this agent.
     */
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
     * the circumstances for itself by overriding this method. 
     * By default this method chooses the IP address over the MAC address for
     * then NetworkableAgent, (If IP is null then MAC else IP).
     * @return The most qualified address for the networked agent.
     */
    public String getQualifiedAddress(){
        return getIpAddress() == null ? getMacAddress() : getIpAddress();
    }
  
   /**
    * Assigns a random MAC address for the NetworkableAgent.
    * Creates a 6-byte array for MAC address and creates a String identifier
    * from it in the format of XX:XX:XX:XX:XX:XX.
    * MAC addresses created via this method are not completely guaranteed to be
    * unique; however, unique occurrences of the same MAC address is unlikely as
    * the seed for the random creation is based from the system's nano-time.
    */
    protected final void assignMac(){
        Random rand = new Random(System.nanoTime());
        byte[] mac = new byte[6];
        rand.nextBytes(mac);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++){
             sb.append(String.format("%02X", (0xff & mac[i])));
             if (i != mac.length-1)
                 sb.append(":");
        }
        setMacAddress(sb.toString());
    }
    
    /**
     * Calls the super version of this method to check if this agent can receive
     * or checks if the qualified address matches the message's recipient.
     * @param message The message that we to check if we can receive it.
     * @return True if we can receive the message; false otherwise.
     */
    @Override
    protected boolean canReceive(Message message) {
        return super.canReceive(message) || message.getRecipient().equals(getQualifiedAddress());
    }    
    
    /**
     * Returns the qualified address of the NetworkableAgent as the name of the
     * agent is unnecessary in the case of MAC and IP based networking.
     * @return The Qualified address of the NetworkableAgent.
     * @see #getQualifiedAddress() 
     */
    @Override
    public String getName(){
        return getQualifiedAddress();
    }

    
}
