package agents.impl.dhcp;

import agents.util.EncodingUtil;
import agents.Message;
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
    protected void execute(Message message){
        if (canReceive(message))
            handleDhcp(message);
    }
    
    /**
     * Checks whether the message is using the DHCP protocol and can be received
     * via the traditional way.
     * @param message The message to check.
     * @return True if the message is using the DHCP protocol; false otherwise.
     */
    @Override
    public boolean canReceive(Message message){
        return super.canReceive(message) && message.getProtocol() == Protocol.DHCP;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void dhcpDiscover() {
        System.out.println("\n"+"[DCHP Client "+getName()+"] System "+getQualifiedAddress()+" broadcasting discovery inent for DHCP.");
        getParent().addMessage(new Message(Wildcard.ALL,EncodingUtil.StringToBytes("Discover"),getQualifiedAddress(),Protocol.DHCP));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void dhcpRequest(String sender) {
        System.out.println("[DCHP Client "+getName()+"] System "+getQualifiedAddress()+" asking "+sender+" for IP address.");
        getParent().addMessage(new Message(sender, EncodingUtil.StringToBytes("Request"), getQualifiedAddress(), Protocol.DHCP));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleDhcp(Message message) {
        if (message == null || message.getProtocol() != Protocol.DHCP)
            return;
        
        if (getIpAddress() != null && !getIpAddress().equals(""))
            return; // IP has already been set. DHCP process is unnecessary.
        
        String msg = EncodingUtil.BytesToString(message.getData());
        String[] in = msg.split("\\|");
        if (msg.contains("|")){
            msg = in[0];
        }
        switch (msg.toLowerCase()){
            case "offer":
                System.out.println("[DCHP Client "+getName()+"] Receieved server offer from "+message.getSender());
                dhcpRequest(message.getSender());
                break;
            case "acknowledge":
                String ip = in[1];
                setIpAddress(ip);
                System.out.println("[DCHP Client "+getName()+"] System "+getQualifiedAddress()+" aknowledged IP "+ip+" from "+message.getSender());
        }
    }
}
