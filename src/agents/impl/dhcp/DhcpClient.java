/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.impl.dhcp;

import agents.Message;

/**
 * Interface implementation of the DHCP protocol's methods for a client.
 * The client is responsible for starting the DHCP process by broadcasting
 * their discovery intent.
 * @author v8076743
 */
public interface DhcpClient {
    
    /**
     * Broadcasts a DHCP (Discover) message to attempt to find a DHCP server to
     * handle future requests.
     * Sends a message via the parent portal to broadcast DHCP discovery intent.
     * DHCP servers will receive and handle this by initiating the DHCP process
     * and offering their service(s).
     * @see DhcpServer#dhcpOffer(agents.Message)
     */
    public void dhcpDiscover();
    
    /**
     * Sends a request to the DHCP server that has replied via their 'Offer'
     * message.
     * @see DhcpServer#dhcpOffer(agents.Message)
     * @param sender The qualifying address of the DHCP server.
     * @see DhcpServerAgent#dhcpOffer(agents.Message) 
     */
    public void dhcpRequest(String sender);
    
    /**
     * An entry point for the DHCP methodology to where messages that are 
     * executed run this method. This method should implement the behaviour
     * that distinguishes the messages from the server into the respective
     * DHCP methods. 
     * @param message The message that is passed via 
     * {@link agents.MetaAgent#execute(agents.Message)}.
     */
    public void handleDhcp(Message message);
    
}
