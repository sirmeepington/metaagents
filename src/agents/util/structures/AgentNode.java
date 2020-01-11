/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.util.structures;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Aidan
 * @param <T> Type
 */
public class AgentNode<T> {
    
    private T data;
    private final ArrayList<AgentNode<T>> children;
    private AgentNode<T> parent;
    
    public AgentNode(T data){
        this.children = new ArrayList<>();
        this.data = data;
        this.parent = null;
    }
    
    public AgentNode<T> getDirectChild(T data){
        for(AgentNode<T> agent : children){
            if (agent.data.equals(data))
                return agent;
        }
        return null;
    }
    
    public boolean containsChild(T data){
        for(AgentNode<T> agent : children){
            if (agent.data.equals(data))
                return true;
        }
        return false;
    }
    
    public ArrayList<T> getParentPath(){
        ArrayList<T> path = new ArrayList<>();
        AgentNode<T> n = this;
        path.add(n.getData());
        while (n.getParent() != null){
            n = n.getParent();
            path.add(n.getData());
        }
        return path;
    }
    
    public AgentNode<T> getChild(T data, AgentNode<T> root){
        LinkedBlockingQueue<AgentNode<T>> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()){
            AgentNode<T> qv = queue.remove();
            if (qv.getData().equals(data))
                return qv;
            queue.addAll(qv.getChildren());
        }
        return null;
    }
    
    public boolean removeChild(T data){
        AgentNode<T> node = getDirectChild(data);
        children.remove(node);
        return true;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ArrayList<AgentNode<T>> getChildren() {
        return children;
    }

    public AgentNode<T> getParent() {
        return parent;
    }
    
    protected void setParent(AgentNode<T> parent){
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "AgentNode: "+data;
    }
    
    
    
}
