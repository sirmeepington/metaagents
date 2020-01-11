/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.monitor.ui;

import agents.util.structures.AgentNode;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;
import javax.swing.JPanel;

/**
 *
 * @author Aidan
 */
public class StructurePanel extends JPanel{

    private final MonitorFrame rootFrame;
    private AgentNode<String> rootNode;
    
    private final int agentWidth = 50;
    private final int agentHeight = 50;
    
    private Object lock;
    
    public StructurePanel(MonitorFrame frame){
        this.rootFrame = frame;
    }
    
    public void addPath(Stack<String> path){
        String root = path.pop();
        if (rootNode == null){
            rootNode = new AgentNode<>(root);
        } else {
            if (!rootNode.getData().equals(root)) // Different root node.
                return;
        }
        AgentNode<String> curNode = rootNode;
        while(!path.isEmpty()) {
            String stackValue = path.pop();
            AgentNode<String> node = new AgentNode<>(stackValue);
            curNode.addChild(node);
            curNode = node;
        }
        
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (rootNode == null || lock != null)
            return;
        
        lock = new Object();
        int depth = rootNode.getDepth();
        AgentNode<String> node = rootNode;
        System.out.println(rootNode.getChildren().toString());
        for (int i = 1; i <= depth; i++){
            int childCount = node.getChildren() == null ? 0 : node.getChildren().size();
            for(int j = 1; j <= childCount; j++){
                System.out.println("Child count: "+childCount+"/"+j+" - DepthIter: "+i+"/"+depth);
                paintNode(g, node, j, depth, i);
            }
        }
        
        lock = null;
    }
    
    private void paintNode(Graphics g, AgentNode<String> node, int childNo, int depthTotal, int depthIter){
        int x = MonitorFrame.SIZE.width / 2 * childNo;
        int y = (-100)+MonitorFrame.SIZE.height / depthTotal * depthIter;

        x = x - (agentWidth/2);
        y = y - (agentHeight/2);
        g.setColor(Color.black);
        g.fillOval(x, y, agentWidth, agentHeight);
        g.setColor(Color.white);
        g.drawString(node.getData(), x - (agentWidth/2), y - (agentHeight/2));
    }
    
}
