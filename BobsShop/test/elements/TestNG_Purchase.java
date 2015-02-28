/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import elements.Purchase.FlowDirection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * <pre>
 * The Purchase's NGTest
 * encapsulates all Test functionality
 * related to the Purchase attributes and handling.
 *
 * Factory Method Design Pattern
 * with data validity check.
 * </pre>
 *
 * @author Rudolf Fortes
 * @see "http://www.mkyong.com/tutorials/testng-tutorials/"
 * @see "http://testng.org"
 * @see elements.Purchase
 */
public class TestNG_Purchase {

    /**
     * <pre>
     * DataProvider testExamples return raw data not as iterator
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
    public static List<Object[]> testExamplesRAW() {
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
    @DataProvider(name = "testExamples")
    public static Iterator<Object[]> testExamples() {
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
     *
     * @deprecated
     */
    public TestNG_Purchase() {
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
    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    /**
     * part of the default Test Constructor Not in Use but kept just in case
     *
     * @throws Exception
     * @deprecated
     */
    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of create method, of class Purchase.
     *
     * @param note String
     * @param prod Product
     * @param purcCost double
     * @param purcQty integer
     * @param flow FlowDirection [enumerated]
     * @param expectedResult boolean
     */
    @Test(dataProvider = "testExamples", groups = ("Factory"))
    //@Assumption(prod instanceof Product)
    public void testCreate(String note, Product prod, Object purcCost, int purcQty, FlowDirection flow, boolean expectedResult) {
        if (purcCost != null && purcQty != 0) {
            Purchase purchase = Purchase.create(prod, (double) purcCost, purcQty, flow);
            if (prod instanceof Product && expectedResult) {
                assertTrue(purchase instanceof Purchase);
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
                        assertTrue(purchase instanceof Purchase);
                    } else {
                        assertFalse(purchase instanceof Purchase);
                    }
                }
            } else {
                assertNull(purchase);
            }

        }
    }

    /**
     * Test of getProd method, of class Purchase.
     *
     * prod test : none data pre-verifies these prod purcCost :
     *
     * @param note
     * @param purcQty
     * @param prod
     * @param purcCost
     * @param flow
     * @param expectedResult
     */
    @Test(dataProvider = "testExamples", groups = ("Getters"))
    public void testGetters(String note, Product prod, Object purcCost, int purcQty, FlowDirection flow, boolean expectedResult) {
        Purchase purchase;
        if (purcCost != null) {
            purchase = Purchase.create(prod, (double) purcCost, purcQty, flow);
        } else {
            purchase = Purchase.create(prod, purcQty, flow);
        }
        switch (note) {
            case "prod":
                if (prod instanceof Product) {
                    assertTrue(purchase.getProd().equals(prod));
                }
                break;
            case "purcCost":
                if (prod instanceof Product) {
                    if (purcCost == null) {
                        assertEquals(purchase.getPurcCost(), purchase.getProd().getProdCost());
                    } else if (purchase instanceof Purchase) {
                        if (purchase.getProd().getProdCost() < (double) purcCost) {
                            assertEquals(purchase.getPurcCost(), purcCost);
                        } else {
                            assertEquals(purchase.getPurcCost(), purcCost);
                            //assertEquals(purcCost, purchase.getProd().getProdCost());
                        }
                    }
                }
                break;
            case "purcQty":
                if (purchase instanceof Purchase) {
                    if (expectedResult) {
                        assertEquals(purchase.getPurcQty(), purcQty);
                    } else {
                        assertFalse(purchase instanceof Purchase);
                    }
                }
                break;
            case "Flow":
                if (purchase instanceof Purchase) {
                    if (expectedResult) {
                        assertEquals(purchase.getFlowDirection(), flow);
                    } else {
                        assertFalse(purchase instanceof Purchase);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * Test of getProd method, of class Purchase.
     *
     * @param note
     * @param purcQty
     * @param prod
     * @param purcCost
     * @param flow
     * @param expectedResult
     */
    @Test(dataProvider = "testExamples", groups = ("Setters"))
    public void testSetters(String note, Product prod, Object purcCost, int purcQty, FlowDirection flow, boolean expectedResult) {
        Purchase purchase = Purchase.create(prod, 0.0, 1, FlowDirection.RESTOCK);
        switch (note) {
            case "prod":
                if (prod instanceof Product) {
                    assertTrue(purchase.setProd(prod));
                }
                break;
            case "purcCost":
                if (prod instanceof Product) {
                    if (purcCost != null) {
                        if (expectedResult) {
                            if ((double) purcCost < purchase.getProd().getProdCost()) {
                                assertTrue(purchase.setPurcCost((double) purcCost));
                            } else {
                                assertFalse(purchase.setPurcCost((double) purcCost));
                            }
                        } else {
                            assertFalse(purchase.setPurcCost((double) purcCost));
                        }

                    }
                }
                break;
            case "purcQty":
                if (purchase instanceof Purchase) {
                    if (expectedResult) {
                        assertTrue(purchase.setPurcQty(purcQty));
                        //assertEquals(purchase.getPurcQty(), purcQty);
                    } else {
                        assertFalse(purchase.setPurcQty(purcQty));
                    }
                }
                break;
            default:
                List<String> testCase = Arrays.asList(new String[]{"base Test", "Flow"});
                if (testCase.contains(note)) {
                } else {
                    fail("No Test Case Specified Yet: <" + note + ">");
                }
                break;
        }
    }

    /**
     * Test of resetPurcCost method, of class Purchase.
     */
    @Test(dataProvider = "testExamples", groups = ("Other"))
    public void testResetPurcCost(String note, Product prod, Object purcCost, int purcQty, FlowDirection flow, boolean expectedResult) {
        Purchase purchase;
        if (purcCost != null) {
            purchase = Purchase.create(prod, (double) purcCost, purcQty, flow);
        } else {
            purchase = Purchase.create(prod, purcQty, flow);
        }
        if(purchase instanceof Purchase){
            assertTrue(expectedResult);purchase.resetPurcCost();
        }
        else{
            
        }
    }
}
