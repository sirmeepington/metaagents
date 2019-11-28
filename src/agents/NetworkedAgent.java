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
public interface NetworkedAgent {
    
    public String getIpAddress();
    
    public void setIpAddress(String ipAddress);
    
    public String getMacAddress();
    
    public void setMacAddress(String macAddress);
    
    public String getSendAddress();
    
}
