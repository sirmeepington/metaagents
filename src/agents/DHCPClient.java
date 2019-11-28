/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 *
 * @author v8076743
 */
public interface DHCPClient {
    
    public void discover();
    
    public void request(String sender);
    
    public void handleDhcp(Message message);
    
}
