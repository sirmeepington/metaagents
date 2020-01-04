/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import java.util.EnumSet;

/**
 *
 * @author Aidan
 */
public enum Flags {
    
    NONE(0),
    NO_REPLY(1<<1),
    INTERNAL(1<<2),
    WRAPPED(1<<3);
    
    
    private final int flagValue;
    
    Flags(int value){
        flagValue = value;
    }
    
    public int getValue(){
        return flagValue;
    }
    
    public static EnumSet<Flags> fromInt(int val){
        EnumSet flags = EnumSet.noneOf(Flags.class);
        for(Flags flag : Flags.values()){
            int fVal = flag.flagValue;
            if ((fVal & val) == fVal){
                flags.add(flag);
            }
        }
        return flags;
    }
    
    public static int toInt(EnumSet<Flags> flags){
        int val = 0;
        for(Flags flag : flags){
            val |= flag.flagValue;
        }
        return val;
    }

}
