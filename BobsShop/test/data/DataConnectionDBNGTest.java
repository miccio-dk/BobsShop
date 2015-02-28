/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data;

import elements.Customer;
import elements.Product;
import elements.Purchase;
import java.util.ArrayList;
import java.util.Date;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jiachen Yin
 */
public class DataConnectionDBNGTest {
    
    public DataConnectionDBNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of create method, of class DataConnectionDB.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        String url = "jdbc:mysql://sql4.freesqldatabase.com:3306/sql419771";
        String username = "sql419771";
        String password = "gH7*nI5*";
        DataConnectionDB expResult = null;
        DataConnectionDB result = DataConnectionDB.create(url, username, password);
      
        // TODO review the generated test code and remove the default call to fail.
         assertThat(result, instanceOf(data.DataConnectionDB.class));
         fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomers method, of class DataConnectionDB.
     */
    @Test
    public void testGetCustomers() {
        System.out.println("getCustomers");
        DataConnectionDB instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getCustomers();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProducts method, of class DataConnectionDB.
     */
    @Test
    public void testGetProducts() {
        System.out.println("getProducts");
        DataConnectionDB instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getProducts();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBalance method, of class DataConnectionDB.
     */
    @Test
    public void testGetBalance() {
        System.out.println("getBalance");
        Customer customer = null;
        DataConnectionDB instance = null;
        double expResult = 0.0;
        double result = instance.getBalance(customer);
        assertEquals(result, expResult, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProductQuantity method, of class DataConnectionDB.
     */
    @Test
    public void testGetProductQuantity() {
        System.out.println("getProductQuantity");
        Product product = null;
        DataConnectionDB instance = null;
        int expResult = 0;
        int result = instance.getProductQuantity(product);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCustomer method, of class DataConnectionDB.
     */
    @Test
    public void testAddCustomer() {
        System.out.println("addCustomer");
        String userPin = "";
        String userName = "";
        String userAddress = "";
        Boolean userActive = false;
        DataConnectionDB instance = null;
        Customer expResult = null;
        Customer result = instance.addCustomer(userPin, userName, userAddress,userActive);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delCustomer method, of class DataConnectionDB.
     */
    @Test
    public void testDelCustomer() {
        System.out.println("delCustomer");
        Customer customer = null;
        DataConnectionDB instance = null;
        boolean expResult = false;
        boolean result = instance.delCustomer(customer);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addProduct method, of class DataConnectionDB.
     */
    @Test
    public void testAddProduct() {
        System.out.println("addProduct");
        String prodName = "";
        double prodCost = 0.0;
        double prodPrice = 0.0;
        int prodThreshold = 0;
        DataConnectionDB instance = null;
        Product expResult = null;
        Product result = instance.addProduct(prodName, prodCost, prodPrice, prodThreshold);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delProduct method, of class DataConnectionDB.
     */
    @Test
    public void testDelProduct() {
        System.out.println("delProduct");
        Product product = null;
        DataConnectionDB instance = null;
        boolean expResult = false;
        boolean result = instance.delProduct(product);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addReceipt method, of class DataConnectionDB.
     */
    @Test
    public void testAddReceipt() {
        System.out.println("addReceipt");
        Customer customer = null;
        ArrayList<Purchase> purchaseArrayList = null;
        Date receDate = null;
        DataConnectionDB instance = null;
        boolean expResult = false;
        boolean result = instance.addReceipt(customer, purchaseArrayList, receDate);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProductTrend method, of class DataConnectionDB.
     */
    @Test
    public void testGetProductTrend() {
        System.out.println("getProductTrend");
        Product product = null;
        DataConnectionDB instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getProductTrend(product);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editProduct method, of class DataConnectionDB.
     */
    @Test
    public void testEditProduct() {
        System.out.println("editProduct");
        Product product = null;
        DataConnectionDB instance = null;
        boolean expResult = false;
        boolean result = instance.editProduct(product);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editCustomer method, of class DataConnectionDB.
     */
    @Test
    public void testEditCustomer() {
        System.out.println("editCustomer");
        Customer customer = null;
        DataConnectionDB instance = null;
        boolean expResult = false;
        boolean result = instance.editCustomer(customer);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
