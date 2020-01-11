/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.util.structures;

/**
 *
 * @author Paul
 */
public class AgentTree<T> {
    
    private AgentNode<T> root;
    
    public AgentTree(T data){
        this.root = new AgentNode<>(data);
    }
    
    public void addChild(AgentNode<T> node){
        if (root == null){
            root = node;
            return;
        }
    }
    
    public int getDepth(){
        return getDepth(root);
    }
    
        
    private int getDepth(AgentNode<T> child){
        int depth = 1;
        for(AgentNode<T> c : child.getChildren()){
            depth = Math.max(depth,1+getDepth(c));
        }
        return depth;
    }
}
