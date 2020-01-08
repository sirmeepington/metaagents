/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Abstract MetaAgent class that implements a receiving and parsing behaviour 
 * for incoming message.
 * 
 * In the terms of a message's lifetime within a MetaAgent; it should be added
 * via {@link #addMessage(agents.Message)} which adds it to the queue; where it
 * is taken via the thread and {@link #parse(agents.Message)}d. If parsing 
 * passes the message is {@link #execute(agents.Message)}d.
 * 
 * @see Message
 * @author v8076743
 */
public abstract class MetaAgent {
    
    /**
     * The Thread that the MetaAgent is currently running on.
     * A thread will be running per-MetaAgent; and as such may use up all of the
     * allotted threads for the process when the MetaAgent count is too high.
     */
    private Thread thread;
    
    /**
     * The name of the MetaAgent.
     */
    protected String name;
    
    /**
     * The parent Portal that messages will be redirected to if this agent
     * cannot handle them.
     * In the case that the parent is null; the message will be simply dropped.
     */
    private final Portal parent;
    
    /**
     * The Queue that will be used to block the worker thread and await any 
     * inbound messages.
     * This queue blocks the thread if a message cannot be found and will notify
     * the thread after a message is found to allow it to process.
     */
    private final ArrayBlockingQueue<Message> queue;
    
    /**
     * Whether or not the thread should be running.
     * This is relatively unused due to the fact that the queue is blocking;
     * the best way to end the thread is via {@link Thread#interrupt()} as it
     * ensures the death of the thread. However; setting this to false and
     * sending another message will give this thread a soft death compared to 
     * the hard death of an interrupt signal.
     */
    private volatile boolean running = true;
    

    public MetaAgent(int capacity, String name, Portal parent) {
        this.queue = new ArrayBlockingQueue<>(capacity);
        this.parent = parent;
        this.name = name;
        initThread();
    }
    
    /**
     * Initialises the worker thread for this MetaAgent.
     * Peeks data and polls if not null.
     * Parses non-null data that is polled.
     * @see #parse(agents.Message) 
     */
    private void initThread(){
        thread = new Thread(){
            @Override
            public void run() {
                threadRun();
            }
        };
        thread.setName("Thread for "+getClass().getSimpleName()+" "+name);
        thread.start();
    }
    
    /**
     * Runs the logic that would usually happen within the running of the 
     * anonymous backing thread.
     * This is to allow for overriding within child classes should the need
     * arise for changing the running operation of the agent.
     */
    protected void threadRun(){
        while (isRunning()){
            Message m;
            try {
                m = queue.take();
                if (m != null)
                    parse(m);
            } catch (InterruptedException ex) { }
        }
    }
    
    /**
     * Parses a message, checking if the message is null and if the MetaAgent
     * can receive it.
     * @param message The message to parse.
     */
    protected void parse(Message message){
        if (message == null)
            return;
        if (!message.bounce())
            return;

        if (canReceive(message)){
            execute(message);
        } else {
            getParent().parse(message);
        }
    }
    
    /**
     * Adds a message to this agent's queue. 
     * Use this over {@link #parse(agents.Message) } to respect the MetaAgents
     * queue.
     * @param message The message to add to the queue.
     */
    public void addMessage(Message message){
        getQueue().add(message);
    }
    
    /**
     * Executes the behaviour of this agent for the respective message.
     * @param message The message to execute the behaviour of.
     */
    protected abstract void execute(Message message);
    
    /**
     * Whether or not the MetaAgent can receive and parse the message passed.
     * @param message The message passed.
     * @return Whether the message can be received and parsed.
     */
    protected boolean canReceive(Message message){
        if (message.getRecipient() == null)
            return false;
        return message.getRecipient().equals(getName()) || message.getRecipient().equals(Wildcard.ALL.toString());
    }

    /**
     * Returns the name of the MetaAgent.
     * @return The meta agent's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the parent agent of this agent.
     * @return The parent agent.
     */
    public Portal getParent(){
        return parent;
    }
    
    /**
     * End the worker thread for this MetaAgent, setting Running to false.
     * This will also interrupt the thread (due to blocking behaviour on the
     * Message Queue) and cause a 'hard' death for the thread.
     */
    public void end(){
        running = false;
        thread.interrupt();
    }
    
    /**
     * Returns the string interpretation of the MetaAgent.
     * @return The meta agent's name preceded by a string that shows that this
     * is a meta agent.
     */
    @Override
    public String toString() {
        return "Meta Agent: "+name;
    }
    
    /**
     * Is the backing thread to this MetaAgent currently running?
     * @return True if the current thread is running; false otherwise.
     */
    public boolean isRunning(){
        return running;
    }
    
    /**
     * Returns the Queue for this MetaAgent.
     * @return The Meta-Agent's queue.
     */
    protected ArrayBlockingQueue<Message> getQueue(){
        return queue;
    }
}
