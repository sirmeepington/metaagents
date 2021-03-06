package agents;

/**
 * The message protocol for specialised implementations of the Meta-Agent system.
 * An example would be the {@code DHCP} protocol that is defined in this class
 * as it tells the {@link agents.impl.dhcp.DhcpClient} and 
 * {@link agents.impl.dhcp.DhcpServer} that the message is directed towards them
 * explicitly without having a change of content.
 * @author Aidan
 */
public enum Protocol {
    
    /**
     * No specific protocol was specified; this message is a generic user
     * message.
     */
    NONE,
    
    /**
     * The message is to be used in the DHCP process to assign an IP to a 
     * {@link agents.impl.dhcp.DhcpClient}.
     */
    DHCP,
    
    /**
     * This message is a system message and should NOT be able to be created 
     * from the user.
     * System messages should be interpreted as from other agent within the
     * system and are not a result of direct user input.
     */
    SYS,
    
    /**
     * This message is to be used for networking protocols over sockets.
     * For example a message with this protocol can be used to send
     * messages to a Socket Server.
     */
    NET,
    
    /**
     * This message is used for identification over sockets.
     */
    IDENT;
    
}
