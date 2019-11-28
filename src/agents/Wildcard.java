/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 *
 * @author v8076743
 */
public enum Wildcard {
    
    ALL("*");
    
    
    private final String wildcard;

    Wildcard(String wildcard){
        this.wildcard = wildcard;
    }
    
    public String getChar(){
        return wildcard;
    }
    
    
}
