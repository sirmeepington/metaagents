/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.monitor.ui;

import java.util.Stack;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * A JPanel outlining the structure of the meta-agent system.
 * Uses a {@link JTree} to visualise the structure and 
 * {@link DefaultMutableTreeNode} from Swing to piece together the structure.
 * @author Aidan
 */
public class StructurePanel extends JPanel{

    /**
     * The root node of the tree.
     */
    private DefaultMutableTreeNode rootNode;
    /**
     * The tree itself.
     */
    private final JTree tree;
    /**
     * A data model for the Tree.
     */
    private final DefaultTreeModel treeModel;
    
    public StructurePanel(MonitorFrame frame){
        this.treeModel = new DefaultTreeModel(null);
        this.tree = new JTree(treeModel);
        addTree();
    }
    
    /**
     * Creates the tree.
     */
    private void addTree(){
        this.tree.setPreferredSize(MonitorFrame.SIZE);
        this.add(tree);
    }
    
    /**
     * Takes a stack of Strings from the frame and 
     * @param path 
     */
    public void addPath(Stack<String> path){
        String root = path.pop();
        if (rootNode == null){
            // Create new root
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
    
    /**
     * Gets an existing child node underneath the current node with the value 
     * specified.
     * @param curNode The current node to check the children of.
     * @param value The string to check the value for.
     * @return The node if a child is found, the current node otherwise.
     */
    private DefaultMutableTreeNode getExistingChild(DefaultMutableTreeNode curNode, String value){
        for (int i = 0; i < curNode.getChildCount(); i++){
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) curNode.getChildAt(i);
            if (child != null && ((String)child.getUserObject()).equals(value))
                return child;
        }
        return curNode;
    }
    
    /**
     * Can the data be inserted into the current node.
     * The data cannot be inserted under the current node if the current node
     * already contains an entry with the same data.
     * @param data The data to check to insert.
     * @param curNode The current node to check the children of.
     * @return True if the data can be inserted underneath the current node.
     */
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
