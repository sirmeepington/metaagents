/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 * An abstract system-level agent that is used to discern a different in heading
 * for its child classes.
 * SystemAgents are more based towards the behaviour of a system instead of the
 * behaviour of a process within a system.
 * @author v8076743
 */
public abstract class SystemAgent extends MetaAgent {
    
    public SystemAgent(int capacity, String name, Portal parent) {
        super(capacity, name, parent);
    }

}
