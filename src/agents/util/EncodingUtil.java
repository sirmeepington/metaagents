/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
    
    /**
     * Serialises an object to a byte array and returns it.
     * If the object does not implement {@link Serializable} or if serialisation
     * fails then {@code null} is returned.
     * @param in The object to serialise to bytes.
     * @return The serialised byte array of the object.
     */
    public static byte[] ObjToBytes(Object in){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(baos);
            o.writeObject(in);
            o.flush();
            return baos.toByteArray();
        } catch (IOException ex){
            return null;
        }
    }
    
    /**
     * De-serialises an object from a byte array to an Object.
     * This method will return {@code null} if de-serialisation fails.
     * @param in The byte array to de-serialise.
     * @return The resulting object; or {@code null} if the objects class cannot
     * be found or if reading fails.
     */
    public static Object BytesToObj(byte[] in){
        try {
            ByteArrayInputStream baos = new ByteArrayInputStream(in);
            ObjectInputStream o = new ObjectInputStream(baos);
            return o.readObject();
        } catch (IOException | ClassNotFoundException ex){
            return null;
        }
    }
}
