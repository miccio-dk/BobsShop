/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 *
 * @author Rudolf Fortes
 * @deprecated
 */
public class JUNIT_CustomerTest {

    /**
     *
     */
    public JUNIT_CustomerTest() {
    }

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Customer Test: Begin");
        int userID = 0;
        String userPin = "1234";
        String userName = "My Name";
        String userAddress = "Not here, 1234 neverland";
        Customer result = Customer.create(userID, userPin, userName, userAddress);
        //assertNull("Customer is null", result);

        //return result;

    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Customer Test: End");

    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class Customer.
     */
    @org.junit.Test
    public void testCreate_false() {
        System.out.println("create");
        int userID = 0;
        String userPin = "";
        String userName = "";
        String userAddress = "";
        Customer expResult = null;
        Customer result = Customer.create(userID, userPin, userName, userAddress);
        //assertEquals(,instanceOf(elements.Customer));
        assertNull("Customer is null", result);
        //assertThat(result, instanceOf(elements.Customer.class));
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class Customer.
     */
    @org.junit.Test
    public void testCreate() {
        System.out.println("create");
        int userID = 0;
        String userPin = "";
        String userName = "";
        String userAddress = "";
        Customer expResult = null;
        Customer customer = Customer.create(12,"1234","My Name","Not here, 1234 neverland");
        //Customer result = Customer.create(userID, userPin, userName, userAddress);
        //assertEquals(,instanceOf(elements.Customer));
        //assertNull("Customer is null", result);
        assertThat(customer, instanceOf(elements.Customer.class));
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of getUserID method, of class Customer.
     */
    @org.junit.Test
    public void testGetUserID() {
        System.out.println("getUserID");
        Customer customer = Customer.create(12,"1234","My Name","Not here, 1234 neverland");
        //Customer instance = null;
        int expResult = 12;
        int result = customer.getUserID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkUserPin method, of class Customer.
     */
    @org.junit.Test
    public void testCheckUserPin() {
        System.out.println("checkUserPin");
        Customer customer = Customer.create(12,"1234","My Name","Not here, 1234 neverland");
        String pin = "1234";
        //Customer instance = null;
        boolean expResult = true;
        boolean result = customer.checkUserPin(pin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getUserPin method, of class Customer.
     */
    @org.junit.Test
    public void testGetUserPin() {
        System.out.println("getUserPin");
        Customer customer = Customer.create(12,"1234","My Name","Not here, 1234 neverland");
        String expResult = "1234";
        String result = customer.getUserPin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getUserName method, of class Customer.
     */
    @org.junit.Test
    public void testGetUserName() {
        System.out.println("getUserName");
        Customer customer = Customer.create(12,"1234","My Name","Not here, 1234 neverland");
        String expResult = "My Name";
        String result = customer.getUserName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getUserAddress method, of class Customer.
     */
    @org.junit.Test
    public void testGetUserAddress() {
        System.out.println("getUserAddress");
        Customer customer = Customer.create(12,"1234","My Name","Not here, 1234 neverland");
        String expResult = "Not here, 1234 neverland";
        String result = customer.getUserAddress();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setUserPin method, of class Customer.
     */
    @org.junit.Test
    public void testSetUserPin() {
        System.out.println("setUserPin");
        Customer customer = Customer.create(12,"1234","My Name","Not here, 1234 neverland");
        String userPin = "5678";
        //Customer instance = null;
        boolean expResult = true;
        boolean result = customer.setUserPin(userPin);
        assertEquals(expResult, result);
        String expResult2 = "5678";
        String result2 = customer.getUserPin();
        assertEquals(expResult2, result2);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setUserName method, of class Customer.
     */
    @org.junit.Test
    public void testSetUserName() {
        System.out.println("setUserName");
        Customer customer = Customer.create(12,"1234","My Name","Not here, 1234 neverland");
        String userName = "Not My Name";
        Customer instance = null;
        boolean expResult = true;
        boolean result = customer.setUserName(userName);
        assertEquals(expResult, result);
        
        String expResult2 = "Not My Name";
        String result2 = customer.getUserName();
        assertEquals(expResult2, result2);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setUserAddress method, of class Customer.
     */
    @org.junit.Test
    public void testSetUserAddress() {
        System.out.println("setUserAddress");
        Customer customer = Customer.create(12,"1234","My Name","Not here, 1234 neverland");

        String userAddress = "Here, 5678 Wonderland";
        boolean expResult = true;
        boolean result = customer.setUserAddress(userAddress);
        assertEquals(expResult, result);
        String expResult2 = userAddress;
        String result2 = customer.getUserAddress();
        assertEquals(expResult2, result2);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Customer.
     */
    @org.junit.Test
    public void testToString() {
        System.out.println("toString");
        Customer customer = Customer.create(12,"1234","My Name","Not here, 1234 neverland");

        //Customer instance = null;
        String expResult =  customer.getUserID() + "\t" 
                            + customer.getUserPin() + "\t" 
                            + customer.getUserName() + "\t" 
                            + customer.getUserAddress() + "\n";
        String result = customer.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}