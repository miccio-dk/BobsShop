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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author Jiachen Yin
 */
public class DataConnectionNGTest {
    
    public DataConnectionNGTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

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
     * Test of getCustomers method, of class DataConnection.
     */
    @Test
    public void testGetCustomers() {
        System.out.println("getCustomers");
        DataConnection instance = new DataConnectionImpl();
        ArrayList<Customer> expResult = null;
        ArrayList<Customer> result = instance.getCustomers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProducts method, of class DataConnection.
     */
    @Test
    public void testGetProducts() {
        System.out.println("getProducts");
        DataConnection instance = new DataConnectionImpl();
        ArrayList<Product> expResult = null;
        ArrayList<Product> result = instance.getProducts();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPurchase method, of class DataConnection.
     */
    @Test
    public void testGetPurchase() {
        System.out.println("getPurchase");
        DataConnection instance = new DataConnectionImpl();
        ArrayList<Purchase> expResult = null;
        ArrayList<Purchase> result = instance.getPurchase();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBalance method, of class DataConnection.
     */
    @Test
    public void testGetBalance() {
        System.out.println("getBalance");
        Customer customer = null;
        DataConnection instance = new DataConnectionImpl();
        double expResult = 0.0;
        double result = instance.getBalance(customer);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProductQuantity method, of class DataConnection.
     */
    @Test
    public void testGetProductQuantity() {
        System.out.println("getProductQuantity");
        Product product = null;
        DataConnection instance = new DataConnectionImpl();
        int expResult = 0;
        int result = instance.getProductQuantity(product);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProductTrend method, of class DataConnection.
     */
    @Test
    public void testGetProductTrend() {
        System.out.println("getProductTrend");
        Product product = null;
        DataConnection instance = new DataConnectionImpl();
        ArrayList<Integer> expResult = null;
        ArrayList<Integer> result = instance.getProductTrend(product);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCustomer method, of class DataConnection.
     */
    @Test
    public void testAddCustomer() {
        System.out.println("addCustomer");
        String userPin = "";
        String userName = "";
        String userAddress = "";
        boolean userActive = false;
        DataConnection instance = new DataConnectionImpl();
        Customer expResult = null;
        Customer result = instance.addCustomer(userPin, userName, userAddress, userActive);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delCustomer method, of class DataConnection.
     */
    @Test
    public void testDelCustomer() {
        System.out.println("delCustomer");
        Customer customer = null;
        DataConnection instance = new DataConnectionImpl();
        boolean expResult = false;
        boolean result = instance.delCustomer(customer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editCustomer method, of class DataConnection.
     */
    @Test
    public void testEditCustomer() {
        System.out.println("editCustomer");
        Customer customer = null;
        DataConnection instance = new DataConnectionImpl();
        boolean expResult = false;
        boolean result = instance.editCustomer(customer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addProduct method, of class DataConnection.
     */
    @Test
    public void testAddProduct() {
        System.out.println("addProduct");
        String prodName = "";
        double prodCost = 0.0;
        double prodPrice = 0.0;
        int prodThreshold = 0;
        DataConnection instance = new DataConnectionImpl();
        Product expResult = null;
        Product result = instance.addProduct(prodName, prodCost, prodPrice, prodThreshold);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delProduct method, of class DataConnection.
     */
    @Test
    public void testDelProduct() {
        System.out.println("delProduct");
        Product product = null;
        DataConnection instance = new DataConnectionImpl();
        boolean expResult = false;
        boolean result = instance.delProduct(product);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editProduct method, of class DataConnection.
     */
    @Test
    public void testEditProduct() {
        System.out.println("editProduct");
        Product product = null;
        DataConnection instance = new DataConnectionImpl();
        boolean expResult = false;
        boolean result = instance.editProduct(product);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addReceipt method, of class DataConnection.
     */
    @Test
    public void testAddReceipt() {
        System.out.println("addReceipt");
        Customer customer = null;
        ArrayList<Purchase> purchaseArrayList = null;
        Date recentDate = null;
        DataConnection instance = new DataConnectionImpl();
        boolean expResult = false;
        boolean result = instance.addReceipt(customer, purchaseArrayList, recentDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomer method, of class DataConnection.
     */
    @Test
    public void testGetCustomer_String() {
        System.out.println("getCustomer");
        String userRFID = "";
        DataConnection instance = new DataConnectionImpl();
        Customer expResult = null;
        Customer result = instance.getCustomer(userRFID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomer method, of class DataConnection.
     */
    @Test
    public void testGetCustomer_int() {
        System.out.println("getCustomer");
        int userID = 0;
        DataConnection instance = new DataConnectionImpl();
        Customer expResult = null;
        Customer result = instance.getCustomer(userID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProduct method, of class DataConnection.
     */
    @Test
    public void testGetProduct_String() {
        System.out.println("getProduct");
        String prodRFID = "";
        DataConnection instance = new DataConnectionImpl();
        Product expResult = null;
        Product result = instance.getProduct(prodRFID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProduct method, of class DataConnection.
     */
    @Test
    public void testGetProduct_int() {
        System.out.println("getProduct");
        int prodID = 0;
        DataConnection instance = new DataConnectionImpl();
        Product expResult = null;
        Product result = instance.getProduct(prodID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class DataConnectionImpl extends DataConnection {

        public ArrayList<Customer> getCustomers() {
            return null;
        }

        public ArrayList<Product> getProducts() {
            return null;
        }

        public ArrayList<Purchase> getPurchase() {
            return null;
        }

        public double getBalance(Customer customer) {
            return 0.0;
        }

        public int getProductQuantity(Product product) {
            return 0;
        }

        public ArrayList<Integer> getProductTrend(Product product) {
            return null;
        }

        public Customer addCustomer(String userPin, String userName, String userAddress, boolean userActive) {
            return null;
        }

        public boolean delCustomer(Customer customer) {
            return false;
        }

        public boolean editCustomer(Customer customer) {
            return false;
        }

        public Product addProduct(String prodName, double prodCost, double prodPrice, int prodThreshold) {
            return null;
        }

        public boolean delProduct(Product product) {
            return false;
        }

        public boolean editProduct(Product product) {
            return false;
        }

        public boolean addReceipt(Customer customer, ArrayList<Purchase> purchaseArrayList, Date recentDate) {
            return false;
        }

        public Customer getCustomer(String userRFID) {
            return null;
        }

        public Customer getCustomer(int userID) {
            return null;
        }

        public Product getProduct(String prodRFID) {
            return null;
        }

        public Product getProduct(int prodID) {
            return null;
        }

        @Override
        public boolean editCustomerRFID(Customer customer, String rfid) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean editProductRFID(Product product, String rfid) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
    