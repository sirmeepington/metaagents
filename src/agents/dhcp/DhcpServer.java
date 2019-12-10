/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.dhcp;

import agents.Message;

/**
 *
 * @author v8076743
 */
public interface DhcpServer {
    
    public void dhcpOffer(Message message);
    
    public void dhcpAcknowledge(Message message);
    
}
