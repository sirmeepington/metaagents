package agents;

/**
 * An abstract system-level agent that is used to discern a difference in 
 * heading for its child classes.
 * SystemAgents are more based towards the behaviour of a system instead of the
 * behaviour of a process within a system; and as such are to be used and 
 * extended for such behaviour.
 * @author Aidan
 */
public abstract class SystemAgent extends MetaAgent {
    
    public SystemAgent(int capacity, String name, Portal parent) {
        super(capacity, name, parent);
    }

}
