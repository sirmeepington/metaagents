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
 *
 * @author Aidan
 */
public class MonitorFrame extends JFrame {
    
    public JPanel backgroundPanel;
    public StructurePanel structurePanel;
    protected static final Dimension SIZE = new Dimension(400,600);

    private boolean initialized = false;
    
    public void init(){
        if (initialized)
            return;
        
        this.setTitle("Monitor Frame");
        this.setSize(SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        structurePanel = new StructurePanel(this);
        structurePanel.setPreferredSize(SIZE);
        backgroundPanel = new JPanel();
        
        backgroundPanel.add(structurePanel);
        backgroundPanel.setPreferredSize(SIZE);

        this.add(backgroundPanel);
        this.setVisible(true);
        initialized = true;
    }
    
    public void addPath(ArrayList<String> path){
        Stack s = new Stack();
        s.addAll(path);
        structurePanel.addPath(s);
    }
}
