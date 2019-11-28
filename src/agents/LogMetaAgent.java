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
public class LogMetaAgent extends MetaAgent {

    public LogMetaAgent(int capacity, String name, Portal parent) {
        super(capacity, name, parent);
    }

    @Override
    protected void execute(Message message) {
        System.out.println(message+" received by "+getName());
    }
    
}
