/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.monitor.ui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A Javax Swing JFrame to display the System structure on.
 * The structure itself is displayed via a {@link StructurePanel}.
 * @author Aidan
 */
public class MonitorFrame extends JFrame {
    
    /**
     * The base background panel.
     */
    private JPanel backgroundPanel;
    /**
     * The panel to which the structure is stored on.
     */
    private StructurePanel structurePanel;
    
    /**
     * The size of the window. 
     */
    protected static final Dimension SIZE = new Dimension(400,600);

    /**
     * Has the frame already started initialising?
     */
    private boolean initialized = false;
    
    /**
     * Initialises the frame.
     */
    public void init(){
        if (initialized)
            return;
        
        this.setTitle("Monitor Frame");
        this.setSize(SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        structurePanel = new StructurePanel(this);
        backgroundPanel = new JPanel();
        
        structurePanel.setPreferredSize(SIZE);
        backgroundPanel.setPreferredSize(SIZE);

        backgroundPanel.add(structurePanel);
        this.add(backgroundPanel);
        this.setVisible(true);
        initialized = true;
    }
    
    /**
     * Adds a list from the {@link agents.monitor.MonitorAgent} to the list.
     * @param path The ArrayList that has been passed into the frame from the 
     * monitor
     */
    public void addPath(ArrayList<String> path){
        Stack s = new Stack();
        s.addAll(path);
        structurePanel.addPath(s);
    }
}
