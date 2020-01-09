package agents;

import java.util.EnumSet;

/**
 * A flaggable enumeration example for Java where the {@link Message} flags are
 * specified using bit-shifted values and stored in the message via an integer.
 * Behaviour in this class is similar to how the {@code System.Flags} attribute
 * works on enumeration types in C# as well as the implementation of their 
 * {@code HasFlag()} extension method.
 * @author Aidan
 */
public enum Flags {
    
    /**
     * No special behaviour to implement.
     */
    NONE(0),
    /**
     * A reply should not be sent when this message is received (if one is
     * usually sent).
     */
    NO_REPLY(1<<1),
    /**
     * This message is internal to the current network and should not be 
     * broadcasted through the SocketServer.
     */
    INTERNAL(1<<2),
    /**
     * This message's data is a another message and this current message is to
     * serve as a wrapper.
     */
    WRAPPED(1<<3);
    
    /**
     * The integer value of the bit-shifted enumeration value.
     */
    private final int flagValue;
    
    private Flags(int value){
        flagValue = value;
    }
    
    /**
     * Returns the integer value of the flag.
     * @return The integer value.
     */
    public int getValue(){
        return flagValue;
    }
    
    /**
     * Creates an {@link EnumSet} from an integer value of Flags.
     * @param val The integer value.
     * @return An EnumSet of valid Flags values.
     */
    public static EnumSet<Flags> fromInt(int val){
        EnumSet flags = EnumSet.noneOf(Flags.class);
        for(Flags flag : Flags.values()){
            int fVal = flag.flagValue;
            if ((val & fVal) == fVal){
                flags.add(flag);
            }
        }
        return flags;
    }
    
    /**
     * Converts an {@link EnumSet} of Flags to their integer form.
     * @param flags The EnumSet of flags.
     * @return Their integer form.
     */
    public static int toInt(EnumSet<Flags> flags){
        int val = 0;
        for(Flags flag : flags){
            val |= flag.flagValue;
        }
        return val;
    }

}
