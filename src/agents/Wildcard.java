package agents;

/**
 * A Wildcard operator is used to easily distinguish unique recipients for 
 * Messages in the case where it is required.
 * @see Message
 * @author Aidan
 */
public enum Wildcard {
    
    /**
     * The message should be received by all agents in the current system.
     * When inter-networking is implemented; this should be renamed to 
     * {@code LOCAL_ALL} and have another wildcard be implemented that allows
     * for inter-network broadcasting.
     */
    ALL("*");
    
    /**
     * The string representation of the wildcard to use in the recipient 
     * variable within message.
     */
    private final String wildcard;

    Wildcard(String wildcard){
        this.wildcard = wildcard;
    }
    
    /**
     * Returns the string wildcard for the current wildcard; e.g. {@code *}.
     * @return The string representation of the wildcard.
     */
    @Override
    public String toString(){
        return wildcard;
    }
    
    
}
