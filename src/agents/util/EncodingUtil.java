/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.util;

import java.nio.charset.StandardCharsets;

/**
 * String encoding utility class to help with the conversion to/from a byte
 * array within the Messages.
 * @author Aidan
 * @see agents.Message#data
 */
public class EncodingUtil {
    
    /**
     * Converts the String given by the {@code in} parameter and converts to a
     * byte array using the {@code StandardCharsets.UTF_8} char-set.
     * @see StandardCharsets#UTF_8
     * @param in The string to convert to a byte array.
     * @return The UTF-8 formatted byte array representation of the string 
     * {@code in}.
     */
    public static byte[] StringToBytes(String in){
        return in.getBytes(StandardCharsets.UTF_8);
    }
    
    /**
     * Converts the byte array given by the {@code in} parameter and converts to
     * a String using the {@code StandardCharsets.UTF_8} char-set.
     * This is a functional opposite of {@link #StringToBytes(java.lang.String)}
     * @see StandardCharsets#UTF_8
     * @param in The byte array to convert to a string.
     * @return The String representation of the UTF-8 byte array {@code in}.
     */
    public static String BytesToString(byte[] in){
         return new String(in,StandardCharsets.UTF_8);
    }
    
}
