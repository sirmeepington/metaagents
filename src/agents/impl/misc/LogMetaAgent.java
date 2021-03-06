package agents.impl.misc;

import agents.Message;
import agents.MetaAgent;
import agents.Portal;

/**
 * A basic implementation of a MetaAgent which logs to the screen messages
 * received.
 * The {@code execute} method of this implementation only outputs the string
 * representation of the method to standard output.
 * @author Aidan
 */
public class LogMetaAgent extends MetaAgent {

    public LogMetaAgent(int capacity, String name, Portal parent) {
        super(capacity, name, parent);
    }

    /**
     * Logs the received message into the standard system output.
     * @param message The message to output.
     */
    @Override
    protected void execute(Message message) {
        System.out.println(message+" received by "+getName());
    }
    
}
