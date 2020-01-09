package agents;

/**
 * An action to be applied to a {@link agents.SystemMessage}. 
 * @author Aidan
 */
public enum SystemAction {

    /**
     * No action to take; the message may contain information but no action 
     * should be taken.
     */
    NONE,
    /**
     * An agent is to be registered; the new agent's name should be within the
     * data of this message.
     */
    REGISTER_AGENT,
    /**
     * An agent is to be removed; this should be handled by the agent who is to
     * be removed by removing the reference to its parent and its parent should
     * remove any/all references to it, allowing for GC cleanup.
     */
    REMOVE_AGENT,
    /**
     * This agent should be disconnected from the network; and its future 
     * messages should be dropped; however it should not be removed as it may
     * be connected later in time.
     */
    DISCONNECT_AGENT;
    
}
