/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import elements.Purchase.FlowDirection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.*;
import org.junit.Before;
import static org.testng.Assert.*;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * <pre>
 * The Cart's NGTest
 * encapsulates all Test functionality
 * related to the Cart attributes and handling.
 *
 * Static Factory Method Design Pattern
 * with data validity check.
 * </pre>
 *
 * @see elements
 * @see <a
 * href="http://www.mkyong.com/tutorials/testng-tutorials">http://www.mkyong.com/tutorials/testng-tutorials</a>
 * @see <a href="http://testng.org">http://testng.org</a>
 * @author Rudolf Anton Fortes
 */
public class TestNG_Cart {

    /**
     *
     *
     * @deprecated
     */
    public TestNG_Cart() {
    }

    /**
     * <pre>
     * DataProvider testExamples
     *
     * Uses Purchase.testExample in the format
     * Type         (String,Product,Object,     int,    FlowDirection,  boolean}
     * Description  (note,  prod,   purcCost,   purcQty,flow,           expectedResult}
     *
     * Uses Customer.testExample in the format
     * Type         {String,int,    String, String,     String,     boolean,    boolean}
     * Description  {note,  userID, userPin,userName,   userAddress,userActive,  expectedResult}
     *
     * written in the format of
     * Type         {String,Customer, ArrayList    ,  boolean      }
     * Description  {note,  customer, purchasesList, expectedResult}
     * example      {"base Test", customer, purchasesList, true}
     * notes are catagorized as [base Test|customer|purchasesList|expectedResult]
     * </pre>
     *
     * @return Object[][]
     */
    @DataProvider(name = "testExamples")
    public static Iterator<Object[]> testExamples() {
        List<Object[]> returnData = TestNG_Cart.testExamplesRAW();
        return returnData.iterator();
        /*
         List<Object[]> returnData = new ArrayList<Object[]>();
         ArrayList<Purchase> purchasesList = new ArrayList<Purchase>();
         List<Object[]> PurchaseArray = new ArrayList<Object[]>();

         for (Object[] purchaseExample : TestNG_Purchase.testExamplesRAW()) {
         String note = (String) purchaseExample[0]; // not used
         Product prod = (Product) purchaseExample[1];
         Object purcCost = purchaseExample[2];
         int purcQty = (int) purchaseExample[3];
         Purchase.FlowDirection flow = (Purchase.FlowDirection) purchaseExample[4];
         boolean expectedResult = (boolean) purchaseExample[5];

         if (purcCost != null && purcQty != 0 && expectedResult) {
         Purchase purchase = Purchase.create(prod, (double) purcCost, purcQty, flow);
         if (purchase instanceof Purchase) {
         purchasesList.add(purchase);
         }
         } //4 arguments
         else {
         Purchase purchase = Purchase.create(prod, purcQty, flow);
         if (expectedResult && purchase instanceof Purchase) {
         purchasesList.add(purchase);
         }
         }
         }
         for (Object[] customerExample : TestNG_Customer.testExamplesRAW()) {
         //Object[] customerExample = it.next();
         String note = (String) customerExample[0];
         int userID = (int) customerExample[1];
         String userPin = (String) customerExample[2];
         String userName = (String) customerExample[3];
         String userAddress = (String) customerExample[4];
         boolean userActive = (boolean) customerExample[5];
         boolean expectedResult = (boolean) customerExample[6];
         Customer customer = Customer.create(userID, userPin, userName, userAddress, userActive);
         if (expectedResult && customer instanceof Customer) {
         returnData.add(new Object[]{"base Test", customer, purchasesList, true});
         returnData.add(new Object[]{"Purchase", customer, null, false});
         } else {
         returnData.add(new Object[]{"Customer", null, purchasesList, false});
         }
         }
         return returnData.iterator();*/
    }

    /**
     * <pre>
     * DataProvider testExamples return raw data not as iterator
     *
     * Uses Purchase.testExample in the format
     * Type         (String,Product,Object,     int,    FlowDirection,  boolean}
     * Description  (note,  prod,   purcCost,   purcQty,flow,           expectedResult}
     *
     * Uses Customer.testExample in the format
     * Type         {String,int,    String, String,     String,     boolean,    boolean}
     * Description  {note,  userID, userPin,userName,   userAddress,userActive,  expectedResult}
     *
     * written in the format of
     * Type         {String,Customer, ArrayList    ,  boolean      }
     * Description  {note,  customer, purchasesList, expectedResult}
     * example      {"base Test", customer, purchasesList, true}
     * notes are catagorized as [base Test|customer|purchasesList|expectedResult]
     * </pre>
     *
     * @return List<Object[]>
     */
    public static List<Object[]> testExamplesRAW() {
        List<Object[]> returnData = new ArrayList<Object[]>();
        ArrayList<Purchase> purchasesList = new ArrayList<Purchase>();
        ArrayList<Purchase> purchasesListEmpty = new ArrayList<Purchase>();
        List<Object[]> PurchaseArray = new ArrayList<Object[]>();

        for (Object[] purchaseExample : TestNG_Purchase.testExamplesRAW()) {
            String note = (String) purchaseExample[0]; // not used
            Product prod = (Product) purchaseExample[1];
            Object purcCost = purchaseExample[2];
            int purcQty = (int) purchaseExample[3];
            Purchase.FlowDirection flow = (Purchase.FlowDirection) purchaseExample[4];
            boolean expectedResult = (boolean) purchaseExample[5];

            if (purcCost != null && purcQty != 0 && expectedResult) {
                Purchase purchase = Purchase.create(prod, (double) purcCost, purcQty, flow);
                if (purchase instanceof Purchase) {
                    purchasesList.add(purchase);
                }
            } //4 arguments
            else {
                Purchase purchase = Purchase.create(prod, purcQty, flow);
                if (expectedResult && purchase instanceof Purchase) {
                    purchasesList.add(purchase);
                }
            }
        }
        for (Object[] customerExample : TestNG_Customer.testExamplesRAW()) {
            //Object[] customerExample = it.next();
            String note = (String) customerExample[0];
            int userID = (int) customerExample[1];
            String userPin = (String) customerExample[2];
            String userName = (String) customerExample[3];
            String userAddress = (String) customerExample[4];
            boolean userActive = (boolean) customerExample[5];
            boolean expectedResult = (boolean) customerExample[6];
            Customer customer = Customer.create(userID, userPin, userName, userAddress, userActive);
            if (expectedResult && customer instanceof Customer) {
                returnData.add(new Object[]{"base Test", customer, purchasesList, true});
                returnData.add(new Object[]{"PurchaseEmpty", customer, purchasesListEmpty, true});
                //returnData.add(new Object[]{"Purchase", customer, null, false});
            } else {
                returnData.add(new Object[]{"Customer", null, purchasesList, false});
            }
        }
        return returnData;
    }

    /**
     * <pre>
     * DataProvider testExamples
     *
     * written in the format of
     * Type         (String,Product,Object,     int,    FlowDirection,  boolean}
     * Description  (note,  prod,   purcCost,   purcQty,flow,           expectedResult}
     * example      {"base Test", prod, 0.0, 1, FlowDirection.RESTOCK, true});
     * notes are catagorized as [base Test|prod|purcCost|purcQty|Flow]
     * </pre>
     *
     * @return List<Object[]> used as Object[][]
     */
    @DataProvider(name = "PurchaseTestExamples")
    public static Iterator<Object[]> PurchaseTestExamples() {
        List<Object[]> returnData = TestNG_Purchase.testExamplesRAW();
        return returnData.iterator();
        /*
         List<Object[]> returnData = new ArrayList<Object[]>();
         for (Object[] productExample : TestNG_Product.testExamples()) {
         Product prod = Product.create((int) productExample[1], (String) productExample[2], (double) productExample[3], (double) productExample[4], (int) productExample[5]);
      
         returnData.add(new Object[]{"base Test", prod, 0.0, 1, FlowDirection.RESTOCK, true});

         returnData.add(new Object[]{"prod", prod, 0.0, 1, FlowDirection.RESTOCK, true});

         returnData.add(new Object[]{"purcCost", prod, null, 1, FlowDirection.RESTOCK, true});
         returnData.add(new Object[]{"purcCost", prod, 0.0, 1, FlowDirection.SELL, true});
         returnData.add(new Object[]{"purcCost", prod, 11.0, 1, FlowDirection.TOPUP, true});
         returnData.add(new Object[]{"purcCost", prod, 12.0, 1, FlowDirection.TOPUP, true});
         returnData.add(new Object[]{"purcCost", prod, 14.0, 1, FlowDirection.TOPUP, true});
         returnData.add(new Object[]{"purcCost", prod, 15.0, 1, FlowDirection.TOPUP, true});
         returnData.add(new Object[]{"purcCost", prod, 500.0, 1, FlowDirection.TOPUP, true});

         returnData.add(new Object[]{"purcQty", prod, 0.0, -5, FlowDirection.TOPUP, false});
         returnData.add(new Object[]{"purcQty", prod, 0.0, 0, FlowDirection.TOPUP, false});
         returnData.add(new Object[]{"purcQty", prod, 0.0, 1, FlowDirection.TOPUP, true});
         returnData.add(new Object[]{"purcQty", prod, 0.0, 10, FlowDirection.TOPUP, true});

         returnData.add(new Object[]{"Flow", prod, 0.0, 1, FlowDirection.RESTOCK, true});
         returnData.add(new Object[]{"Flow", prod, 0.0, 1, FlowDirection.SELL, true});
         returnData.add(new Object[]{"Flow", prod, 0.0, 1, FlowDirection.TOPUP, true});
         }*/
        //return returnData.iterator();
    }

    /**
     * part of the default Test Constructor Not in Use but kept just in case
     *
     * @throws Exception
     * @deprecated
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     * part of the default Test Constructor Not in Use but kept just in case
     *
     * @throws Exception
     * @deprecated
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * part of the default Test Constructor Not in Use but kept just in case
     *
     * @throws Exception
     * @deprecated
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * part of the default Test Constructor Not in Use but kept just in case
     *
     * @throws Exception
     * @deprecated
     */
    @After
    public void tearDown() throws Exception {
    }

    //@Assumption(prod instanceof Product)
//    @Test(dataProvider = "testExamples", groups = ("Factory"))
    /**
     * <pre>
     * Test of create method, of class Cart.
     *
     * @dataProvider = "testExamples"
     *
     * Uses Purchase.testExample in the format
     * Type         (String,Product,Object,     int,    FlowDirection,  boolean}
     * Description  (note,  prod,   purcCost,   purcQty,flow,           expectedResult}
     * Uses Customer.testExample in the format
     * Type         {String,int,    String, String,     String,     boolean,    boolean}
     * Description  {note,  userID, userPin,userName,   userAddress,userActive,  expectedResult}
     *
     * TestExamples written in the format of
     * Type         {String,Customer, ArrayList    ,  boolean      }
     * Description  {note,  customer, purchasesList, expectedResult}
     * example      {"base Test", customer, purchasesList, true}
     * notes are catagorized as [base Test|customer|purchasesList|expectedResult]
     * </pre>
     *
     * @param note String
     * @param customer Customer
     * @param purchasesList ArrayList<Purchase>
     * @param expectedResult boolean
     */
    @Test(dataProvider = "testExamples", groups = ("Factory"))
//   @Test(dataProvider = "testExamples")
    public void testCreate(String note, Customer customer, ArrayList<Purchase> purchasesList, boolean expectedResult) {
        Cart cart = Cart.create(customer, purchasesList);
        //if(customer!=null){System.out.println(customer.toString() + " " + purchasesList.toString());}System.out.println("");

        if (expectedResult) {
            assertTrue(cart instanceof Cart);
        } else {
            assertFalse(cart instanceof Cart);
        }

        if (!expectedResult) {
            assertNull(cart, note);
            //System.out.println(note + "Failed");
        } else {
            assertTrue(cart instanceof Cart, note);
        }

    }

    /**
     * <pre>
     * Test of Getters methods in class Customer.
     *
     * @dataProvider = "testExamples"
     * Type         {String,Customer, ArrayList    ,  boolean      }
     * Description  {note,  customer, purchasesList, expectedResult}
     * </pre>
     *
     * @param note
     * @param customer
     * @param purchasesList
     * @param expectedResult
     */
    @org.testng.annotations.Test(dataProvider = "testExamples", groups = ("Getters"))
    public void testGetters(String note, Customer customer, ArrayList<Purchase> purchasesList, boolean expectedResult
    ) {
        Cart cart = Cart.create(customer, purchasesList);
        if (cart instanceof Cart) {
            switch (note) {
                case "customer":
                    if (customer instanceof Customer) {
                        assertTrue(cart.getCustomer().equals(customer));
                    }
                    break;
                case "purchaseList":
                    assertTrue(cart.getPurchasesList().equals(purchasesList));
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * <pre>
     * Test of Getters methods in class Customer.
     *
     * @dataProvider = "testExamples"
     * Type         {String,Customer, ArrayList    ,  boolean      }
     * Description  {note,  customer, purchasesList, expectedResult}
     * </pre>
     *
     * @param note
     * @param customer
     * @param purchasesList
     * @param expectedResult
     */
    @org.testng.annotations.Test(dataProvider = "testExamples", groups = ("Getters"))
    public void testSetters(String note, Customer customer, ArrayList<Purchase> purchasesList, boolean expectedResult
    ) {
        Cart cart = Cart.create(customer, purchasesList);
        if (cart instanceof Cart) {
            switch (note) {
                case "customer":
                    if (customer instanceof Customer) {
                        assertTrue(cart.getCustomer().equals(customer));
                    }
                    break;
                case "purchaseList":
                    assertTrue(cart.getPurchasesList().equals(purchasesList));
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * <pre>
     * test Add Purchase to Cart
     * DataProvider Purchase.TestExamples
     *
     * written in the format of
     * Type         (String,Product,Object,     int,    FlowDirection,  boolean}
     * Description  (note,  prod,   purcCost,   purcQty,flow,           expectedResult}
     * example      {"base Test", prod, 0.0, 1, FlowDirection.RESTOCK, true});
     * notes are catagorized as [base Test|prod|purcCost|purcQty|Flow]
     * </pre>
     *
     * @param note String
     * @param prod Product
     * @param purcCost double
     * @param purcQty integer
     * @param flow FlowDirection [enumerated]
     * @param expectedResult boolean
     */
    @org.testng.annotations.Test(dataProvider = "PurchaseTestExamples", groups = ("Setters"))
    public void testAddPurchase(String note, Product prod, Object purcCost, int purcQty, FlowDirection flow, boolean expectedResult) {
        Object[] CartRaw = TestNG_Cart.testExamplesRAW().get(0);

        Cart cart = Cart.create((Customer) CartRaw[1], (ArrayList) CartRaw[2]);

        if (purcCost != null && purcQty != 0) {
            Purchase purchase = Purchase.create(prod, (double) purcCost, purcQty, flow);
            if (prod instanceof Product && expectedResult) {
                assertTrue(cart.addPurchase(purchase));
            } else {
                assertNull(purchase);
            }
        } //4 arguments
        else {
            Purchase purchase = Purchase.create(prod, purcQty, flow);
            if (expectedResult) {
                if (prod instanceof Product) {
                    //assertTrue(purchase instanceof Purchase);
                    if (expectedResult) {
                        assertTrue(cart.addPurchase(purchase));
                    } else {
                        assertFalse(purchase instanceof Purchase);
                    }
                }
            } else {
                //assertNull(purchase);
            }
        }
    }

    /**
     * Test of remPurchase method, of class Cart.
     */
    @Test
    public void testRemPurchase_Purchase() {
        /*Purchase purchase = null;
        Cart instance = new Cart();
        boolean expResult = false;
        boolean result = instance.remPurchase(purchase);*/
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //skip()
        //fail("The test case is a prototype.");
        System.out.println("remPurchase");
        //throw new SkipException("Skip");
        throw new SkipException("Skipping - This is not ready for testing ");


    }

    /**
     * Test of remPurchase method, of class Cart.
     */
    @Test
    public void testRemPurchase_int() {
        System.out.println("remPurchase");
        //throw new SkipException("Skip");
        throw new SkipException("Skipping - This is not ready for testing ");
        /*int prodID = 0;
        Cart instance = new Cart();
        boolean expResult = false;
        boolean result = instance.remPurchase(prodID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of editPurchase method, of class Cart.
     */
    @Test
    public void testEditPurchase() {
        System.out.println("editPurchase");
        throw new SkipException("Skipping - This is not ready for testing ");
        /*Purchase oldPurchase = null;
        Purchase newPurchase = null;
        Cart instance = new Cart();
        boolean expResult = false;
        boolean result = instance.editPurchase(oldPurchase, newPurchase);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of getPurchase method, of class Cart.
     */
    @Test
    public void testGetPurchase_Product() {
        System.out.println("getPurchase");
        throw new SkipException("Skipping - This is not ready for testing ");
        /*
        Product product = null;
        Cart instance = new Cart();
        Purchase expResult = null;
        Purchase result = instance.getPurchase(product);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of getPurchase method, of class Cart.
     */
    @Test
    public void testGetPurchase_int() {
        System.out.println("getPurchase");
        throw new SkipException("Skipping - This is not ready for testing ");
        /*
        int prodID = 0;
        Cart instance = new Cart();
        Purchase expResult = null;
        Purchase result = instance.getPurchase(prodID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of containsProduct method, of class Cart.
     */
    @Test
    public void testContainsProduct() {
        System.out.println("containsProduct");
        throw new SkipException("Skipping - This is not ready for testing ");
        /*
        Product product = null;
        Cart instance = new Cart();
        boolean expResult = false;
        boolean result = instance.containsProduct(product);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of equals method, of class Cart.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        throw new SkipException("Skipping - This is not ready for testing ");
        /*
        Object obj = null;
        Cart instance = new Cart();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

}
