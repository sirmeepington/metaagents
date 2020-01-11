/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.monitor.ui;

import java.util.Enumeration;
import java.util.Stack;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Aidan
 */
public class StructurePanel extends JPanel{

    private final MonitorFrame rootFrame;
    private DefaultMutableTreeNode rootNode;
    private final JTree tree;
    private final DefaultTreeModel treeModel;
    
    private final int agentWidth = 50;
    private final int agentHeight = 50;
    
    private Object lock;
    
    public StructurePanel(MonitorFrame frame){
        this.rootFrame = frame;
        this.treeModel = new DefaultTreeModel(null);
        this.tree = new JTree(treeModel);
        addTree();
    }
    
    private void addTree(){
        this.tree.setPreferredSize(MonitorFrame.SIZE);
        this.add(tree);
    }
    
    public void addPath(Stack<String> path){
        String root = path.pop();
        if (rootNode == null){
            rootNode = new DefaultMutableTreeNode(root);
            treeModel.setRoot(rootNode);
        } else {
            if (!rootNode.getUserObject().equals(root)) // Different root node.
                return;
        }
        DefaultMutableTreeNode curNode = rootNode;
        while(!path.isEmpty()) {
            String stackValue = path.pop();
            if (canAdd(stackValue,curNode)){
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(stackValue);
                curNode.add(node);
                curNode = node;
            } else {
                curNode = getExistingChild(curNode,stackValue);
            }
        }
        this.repaint();
    }
    
    private DefaultMutableTreeNode getExistingChild(DefaultMutableTreeNode curNode, String value){
        for (int i = 0; i < curNode.getChildCount(); i++){
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) curNode.getChildAt(i);
            if (child != null && ((String)child.getUserObject()).equals(value))
                return child;
        }
        return curNode;
    }
    
    private boolean canAdd(String data, DefaultMutableTreeNode curNode){
        for(int i = 0; i < curNode.getChildCount(); i++){
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) curNode.getChildAt(i);
            String str = (String) child.getUserObject();
            if (str.equals(data)){
                return false;
            }
        }
        return true;
    }
}
