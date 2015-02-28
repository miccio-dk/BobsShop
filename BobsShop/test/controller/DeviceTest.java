package controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *<pre>
 * The Device's NGTest
 * encapsulates all Test functionality
 * related to the Device attributes and handling.
 *
 * Factory Method Design Pattern
 * </pre>
 * @author Jiachen Yin
 * @see controller
 */

public class DeviceTest {
    
    /**
     *@deprecated
     */
    public DeviceTest() {
    }
    
    /**
     * @deprecated
     */
    @BeforeClass
    public static void setUpClass() {
    }
    
    /**
     * @deprecated
     */
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * @deprecated
     */
    @Before
    public void setUp() {
    }
    
    /**
     * @deprecated
     */
    @After
    public void tearDown() {
    }
 /**
     * <pre>
     * DataProvider testExamples
     *
     * written in the format of
     * Type         {String,String,    String ,     boolean }
     * Description  {note,  portNumber, id,  expectedResult}
     * example      {"base Test","COM5", "CA", true}
     * notes are catagorized as [base Test|portNumber|id]
     * </pre>
     *
     * @return Object[][]
     */
     @DataProvider(name = "testExamples")
    public static Object[][] testExamples() {
        return new Object[][]{
            //base Test
            {"base Test", "COM5", "CA", true},
            //Test for portNumber
            {"portNumber", ""    , "CA",  false},
            {"portNumber", " "   , "CA",  false},
            {"portNumber", "COM5", "CA",  true},
             //Test for id
            {"id", "COM5", "",  false},
            {"id", "COM5", " ",  false},
            {"id", "COM5", "CA",  true}
        };        
}
    
     /**
     * <pre>
     * Test of Getters methods in class Device.
     *
     * @dataProvider = "testExamples"
     * example {"base Test","COM5", "CA", true}
     * notes are catagorized as [base Test|portNumber|id]
     * </pre>
     * @param note String
     * @param portNumber String
     * @param id String
     * @param expectedResult Boolean
     */
     @org.testng.annotations.Test(dataProvider = "testExamples", groups = ("Getters"))
    public void testGetters(String note, String portNumber, String id, boolean expectedResult) {
        Device device = new Device(portNumber,id);
        if (device instanceof Device) {
            switch (note) {
                case "portNumber":
                    assertEquals(device.getPortNumber(), portNumber);
                    break;
                case "id":
                    assertEquals(device.getId(), id);
                    break;
                default:
                    break;
            }
        } else {
            assertNull(device);
        }
    }
    
     /**
     * <pre>
     * Test of Setters methods in class Device.
     *
     * @dataProvider = "testExamples"
     * example {"base Test","COM5", "CA", true}
     * notes are catagorized as [base Test|portNumber|id]
     * </pre>
     * @param note String
     * @param portNumber String
     * @param id String
     * @param expectedResult Boolean
     */
    
      @Test(dataProvider = "testExamples", groups = ("Setters"))
    public void testSetters(String note, String portNumber, String id, boolean expectedResult) {
        Device device = new Device("COM5","CA");
        if(device instanceof Device){
           device.setPortNumber(portNumber);
           device.setSource(id);
          
        }
        else{
            
        }
    }
}
