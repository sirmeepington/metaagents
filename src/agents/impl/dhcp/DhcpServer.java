package agents.impl.dhcp;

import agents.Message;

/**
 * Interface implementation for the server-side portion of the DHCP process.
 * The DHCP server is responsible for handling the IP addresses that are given
 * to clients that request them. 
 * The DHCP server offers its service to clients that have broadcasted its
 * discovery intent; it also acknowledges a request for an IP address via its
 * {@link #dhcpAcknowledge(agents.Message)} method, which sends the IP address
 * within the message.
 * @author Aidan
 */
public interface DhcpServer {
    
    /**
     * Offers the server's DHCP services to the client that has broadcasted
     * discovery intent.
     * A check can be used in the implementing agent which verifies the lack of
     * an existing IP address as the sender of the message to reduce the work
     * of the server in the case of discovery requests by an agent who has 
     * already been given an IP address.
     * @param message The discovery message from the DHCP client.
     */
    public void dhcpOffer(Message message);
    
    /**
     * Acknowledges the client's request for an IP address by sending them
     * a message containing the acknowledgement as well as the IP address that
     * has been assigned to them.
     * This method should also tell the server agent to remember which client
     * (its MAC address) has sent this message so it is able to map the IP
     * to the client in the case of a reconnection / retry.
     * @param message The request message from the DHCP client.
     */
    public void dhcpAcknowledge(Message message);
    
}
