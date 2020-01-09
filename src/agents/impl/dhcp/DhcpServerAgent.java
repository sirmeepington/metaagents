package agents.impl.dhcp;

import agents.util.EncodingUtil;
import agents.Message;
import agents.Portal;
import agents.Protocol;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * MetaAgent implementation of the server-side behaviour of the DHCP process.
 * @see DhcpServer
 * @author Aidan
 */
public class DhcpServerAgent extends NetworkableAgent implements DhcpServer {

    /**
     * A queue of IP addresses that manage the in-order issuing of IP addresses.
     */
    private final ArrayBlockingQueue<String> ips = new ArrayBlockingQueue<>(255);
    
    public DhcpServerAgent(int capacity, Portal parent) {
        super(capacity, parent);
        for (int i = 3; i < 255; i++){
            ips.add("192.168.1."+i);
        }
        ipAddress = "192.168.1.2";
    }
    
    @Override
    public boolean canReceive(Message message){
        return super.canReceive(message) && message.getProtocol() == Protocol.DHCP;
    }

    /**
     * Checks if the message can be received and if the message contains the
     * appropriate message for DHCP is is transferred tho the respective method.
     * @param message The message to execute on.
     */
    @Override
    protected void execute(Message message) {
        if (message == null) return;
        if (!canReceive(message)) {
            getParent().addMessage(message);
            return;
        }
        
        String m = EncodingUtil.BytesToString(message.getData()).toLowerCase();
        switch (m){
            case "discover":
                dhcpOffer(message);
                break;
            case "request":
                dhcpAcknowledge(message);
                break;
            default:
                getParent().addMessage(message);
                break;
        }
    }

    /**
     * Concrete implementation of the DhcpServer interfaces method.
     * @see DhcpServer#dhcpOffer(agents.Message) 
     * @param message The message to offer.
     */
    @Override
    public void dhcpOffer(Message message) {
            // Say we exist.
        System.out.println("[DCHP Server "+getName()+"] Received client discovery message from "+message.getSender()+" Offering...");
        addMessage(new Message(message.getSender(), EncodingUtil.StringToBytes("Offer"), getQualifiedAddress(),Protocol.DHCP));
    }

    /**
     * Concrete implementation of the DhcpServer interfaces method.
     * @see DhcpServer#dhcpAcknowledge(agents.Message) 
     * @param message The message to acknowledge.
     */
    @Override
    public void dhcpAcknowledge(Message message) {
        String ip = ips.poll();
        if (ip == null)
            return;
        
        System.out.println("[DCHP Server "+getName()+"] Acknowledging client request from "+message.getSender()+" sending IP "+ip);
        String sender = message.getSender(); // MAC address
        addMessage(new Message(sender, EncodingUtil.StringToBytes("Acknowledge|"+ip),getQualifiedAddress(),Protocol.DHCP));
    }

    /**
     * Overridden setter for IP address which does not change the IP of the
     * DHCP server; as its IP must be set statically.
     * @param ipAddress Incoming IP address.
     */
    @Override
    public void setIpAddress(String ipAddress) { }
    
}
