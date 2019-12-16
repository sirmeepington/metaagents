/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author s6345788
 */
public class MessageTest {
    
    public MessageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSender method, of class Message.
     */
    @Test
    public void testGetSender() {
        System.out.println("getSender");
        Message instance = null;
        String expResult = "";
        String result = instance.getSender();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSender method, of class Message.
     */
    @Test
    public void testSetSender() {
        System.out.println("setSender");
        String sender = "";
        Message instance = null;
        instance.setSender(sender);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecipient method, of class Message.
     */
    @Test
    public void testGetRecipient() {
        System.out.println("getRecipient");
        Message instance = null;
        String expResult = "";
        String result = instance.getRecipient();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRecipient method, of class Message.
     */
    @Test
    public void testSetRecipient_String() {
        System.out.println("setRecipient");
        String recipient = "";
        Message instance = null;
        instance.setRecipient(recipient);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRecipient method, of class Message.
     */
    @Test
    public void testSetRecipient_Wildcard() {
        System.out.println("setRecipient");
        Wildcard recipient = null;
        Message instance = null;
        instance.setRecipient(recipient);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class Message.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        Message instance = null;
        byte[] expResult = null;
        byte[] result = instance.getData();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setData method, of class Message.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        byte[] data = null;
        Message instance = null;
        instance.setData(data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Message.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Message instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProtocol method, of class Message.
     */
    @Test
    public void testGetProtocol() {
        System.out.println("getProtocol");
        Message instance = null;
        Protocol expResult = null;
        Protocol result = instance.getProtocol();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFlags method, of class Message.
     */
    @Test
    public void testSetFlags() {
        System.out.println("setFlags");
        byte flags = 0;
        Message instance = null;
        instance.setFlags(flags);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFlags method, of class Message.
     */
    @Test
    public void testGetFlags() {
        System.out.println("getFlags");
        Message instance = null;
        byte expResult = 0;
        byte result = instance.getFlags();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
