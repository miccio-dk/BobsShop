/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lenovo
 */
public class PacketTest {
    
    public PacketTest() {
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
     * Test of getBytes method, of class Packet.
     */
    @Test
    public void testGetBytes() {
        System.out.println("getBytes");
        Packet instance = new PacketImpl();
        byte[] expResult = null;
        byte[] result = instance.getBytes();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSource method, of class Packet.
     */
    @Test
    public void testGetSource() {
        System.out.println("getSource");
        Packet instance = new PacketImpl();
        String expResult = "";
        String result = instance.getSource();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSource method, of class Packet.
     */
    @Test
    public void testSetSource() {
        System.out.println("setSource");
        String source = "";
        Packet instance = new PacketImpl();
        instance.setSource(source);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDestination method, of class Packet.
     */
    @Test
    public void testGetDestination() {
        System.out.println("getDestination");
        Packet instance = new PacketImpl();
        String expResult = "";
        String result = instance.getDestination();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDestination method, of class Packet.
     */
    @Test
    public void testSetDestination() {
        System.out.println("setDestination");
        String destination = "";
        Packet instance = new PacketImpl();
        instance.setDestination(destination);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCommandStatus method, of class Packet.
     */
    @Test
    public void testGetCommandStatus() {
        System.out.println("getCommandStatus");
        Packet instance = new PacketImpl();
        CommandStatus expResult = null;
        CommandStatus result = instance.getCommandStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCommandStatus method, of class Packet.
     */
    @Test
    public void testSetCommandStatus() {
        System.out.println("setCommandStatus");
        CommandStatus commandStatus = null;
        Packet instance = new PacketImpl();
        instance.setCommandStatus(commandStatus);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataLength method, of class Packet.
     */
    @Test
    public void testGetDataLength() {
        System.out.println("getDataLength");
        Packet instance = new PacketImpl();
        int expResult = 0;
        int result = instance.getDataLength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDataLength method, of class Packet.
     */
    @Test
    public void testSetDataLength() {
        System.out.println("setDataLength");
        int dataLength = 0;
        Packet instance = new PacketImpl();
        instance.setDataLength(dataLength);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class Packet.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        Packet instance = new PacketImpl();
        String expResult = "";
        String result = instance.getData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setData method, of class Packet.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        String data = "";
        Packet instance = new PacketImpl();
        instance.setData(data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateChecksum method, of class Packet.
     */
    @Test
    public void testGenerateChecksum() {
        System.out.println("generateChecksum");
        Packet instance = new PacketImpl();
        instance.generateChecksum();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChecksum method, of class Packet.
     */
    @Test
    public void testGetChecksum() {
        System.out.println("getChecksum");
        Packet instance = new PacketImpl();
        int expResult = 0;
        int result = instance.getChecksum();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setChecksum method, of class Packet.
     */
    @Test
    public void testSetChecksum() {
        System.out.println("setChecksum");
        int checkSum = 0;
        Packet instance = new PacketImpl();
        instance.setChecksum(checkSum);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateEndofPacket method, of class Packet.
     */
    @Test
    public void testGenerateEndofPacket() {
        System.out.println("generateEndofPacket");
        Packet instance = new PacketImpl();
        instance.generateEndofPacket();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndofPacket method, of class Packet.
     */
    @Test
    public void testGetEndofPacket() {
        System.out.println("getEndofPacket");
        Packet instance = new PacketImpl();
        String expResult = "";
        String result = instance.getEndofPacket();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEndofPacket method, of class Packet.
     */
    @Test
    public void testSetEndofPacket() {
        System.out.println("setEndofPacket");
        String endofPacket = "";
        Packet instance = new PacketImpl();
        instance.setEndofPacket(endofPacket);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPacketComplete method, of class Packet.
     */
    @Test
    public void testIsPacketComplete() {
        System.out.println("isPacketComplete");
        byte[] byteFrame = null;
        Packet instance = new PacketImpl();
        boolean expResult = false;
        boolean result = instance.isPacketComplete(byteFrame);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPacketSize method, of class Packet.
     */
    @Test
    public void testGetPacketSize() {
        System.out.println("getPacketSize");
        byte[] byteFrame = null;
        Packet instance = new PacketImpl();
        int expResult = 0;
        int result = instance.getPacketSize(byteFrame);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class PacketImpl extends Packet {

        public byte[] getBytes() {
            return null;
        }

        public void generateChecksum() {
        }

        public void generateEndofPacket() {
        }

        public boolean isPacketComplete(byte[] byteFrame) {
            return false;
        }

        public int getPacketSize(byte[] byteFrame) {
            return 0;
        }
    }
    
}
