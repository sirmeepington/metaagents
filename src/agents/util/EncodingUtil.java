/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents.util;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author Aidan
 */
public class EncodingUtil {
    
    public static byte[] StringToBytes(String in){
        return in.getBytes(StandardCharsets.UTF_8);
    }
    
    public static String BytesToString(byte[] in){
         return new String(in,StandardCharsets.UTF_8);
    }
    
}
