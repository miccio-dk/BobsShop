/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * <pre>
 * The Product's NGTest
 * encapsulates all Test functionality
 * related to the Product attributes and handling.
 *
 * Factory Method Design Pattern
 * with data validity check.
 * </pre>
 *
 * @author rudolffortes
 * @todo to string
 * @see "http://www.mkyong.com/tutorials/testng-tutorials/"
 * @see "http://testng.org"
 * @see elements.Product
 */
@Test
public class TestNG_Product {

    /**
     * part of the default Test Constructor Not in Use but kept just in case     *
     * @deprecated
     */
    public TestNG_Product() {
    }

    /**
     * <pre>
     * DataProvider testExamples
     *
     * written in the format of
     * Type         {String,int,    String ,    double ,    double ,    int ,           boolean }
     * Description  {note,  prodID, prodName,   prodCost,   prodPrice,  prodThreshold,  expectedResult}
     * example      {"base Test", 1, "Shovel", 12.0, 14.0, 5, true}
     * notes are catagorized as [base Test|prodID|prodName|prodCost|prodPrice|prodThreshold]
     * </pre>
     *
     * @return Object[][]
     */
    @DataProvider(name = "testExamples")
    public static Object[][] testExamples() {
        return new Object[][]{
            //base Test
            {"base Test", 1, "Shovel", 12.0, 14.0, 5, true},
            //Test for prodID
            {"prodID", 5, "Shovel", 12.0, 14.0, 5, true},
            {"prodID", 0, "Shovel", 12.0, 14.0, 5, true},
            {"prodID", -1, "Shovel", 12.0, 14.0, 5, false},
            {"prodID", -3, "Shovel", 12.0, 14.0, 5, false},
            //Test for prodName
            {"prodName", 1, "", 12.0, 14.0, 5, false},
            {"prodName", 1, " ", 12.0, 14.0, 5, false},
            {"prodName", 1, "Shovel", 12.0, 14.0, 5, true},
            //Test for prodCost
            {"prodCost", 1, "Shovel", -1.0, 14.0, 5, false},
            {"prodCost", 1, "Shovel", 0.0, 14.0, 5, true},
            {"prodCost", 1, "Shovel", 12.0, 14.0, 5, true},
            {"prodCost", 1, "Shovel", 14.0, 14.0, 5, false},
            {"prodCost", 1, "Shovel", 16.0, 14.0, 5, false},
            //test for prodPrice
            {"prodPrice", 1, "Shovel", 12.0, -1.0, 5, false},
            {"prodPrice", 1, "Shovel", 12.0, 12.0, 5, false},
            {"prodPrice", 1, "Shovel", 12.0, 14.0, 5, true},
            //Test for prodThreshold
            {"prodThreshold", 1, "Shovel", 12.0, 14.0, -1, false},
            {"prodThreshold", 1, "Shovel", 12.0, 14.0, 0, true},
            {"prodThreshold", 1, "Shovel", 12.0, 14.0, 5, true}
        };
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
     * <pre>
     * Test of create method, for the Product class.
     *
     * @dataProvider = "testExamples"
     * example:{"base Test", 1, "Shovel", 12.0, 14.0, 5, true}
     * notes are catagorized as [base Test|prodID|prodName|prodCost|prodPrice|prodThreshold]
     * </pre>
     *
     * @param note String
     * @param prodID integer
     * @param prodName String
     * @param prodCost double
     * @param prodPrice double
     * @param prodThreshold double
     * @param expectedResult boolean
     */
    @Test(dataProvider = "testExamples", groups = ("Factory"))
    public void testCreate(String note, int prodID, String prodName, double prodCost, double prodPrice, int prodThreshold, boolean expectedResult) {
        Product product = Product.create(prodID, prodName, prodCost, prodPrice, prodThreshold);
        if (!expectedResult) {
            assertNull(note, product);
        } else if (expectedResult) {
            assertThat(product, instanceOf(elements.Product.class));
        }
    }

    /**
     * <pre>
     * Test of the getter method's, for the Product class.
     *
     * @dataProvider = "getters"
     * example: {"base Test", Product.create(0, "Shovel", 12.0, 14.0, 5), "base", true}
     * notes are catagorized as [base Test|prodID|prodName|prodCost|prodPrice|prodThreshold]
     * </pre>
     *
     * @param prodPrice
     * @param prodID
     * @param prodThreshold
     * @param prodName
     * @param prodCost
     * @param note String
     * @param expectedResult boolean
     */
    @Test(dataProvider = "testExamples", groups = ("Getters"))
    public void testGetters(String note, int prodID, String prodName, double prodCost, double prodPrice, int prodThreshold, boolean expectedResult) {
        Product product = Product.create(prodID, prodName, prodCost, prodPrice, prodThreshold);
        if (product instanceof Product) {
            switch (note) {
                case "prodID":
                    assertEquals(product.getProdID(), prodID);
                    break;
                case "prodName":
                    assertEquals(product.getProdName(), prodName);
                    break;
                case "prodCost":
                    assertEquals(product.getProdCost(), prodCost);
                    break;
                case "prodPrice":
                    assertEquals(product.getProdPrice(), prodPrice);
                    break;
                case "prodThreshold":
                    assertEquals(product.getProdThreshold(), prodThreshold);
                    break;
                default:
                    if (!"base Test".equals(note)) {
                        fail("No Test Case Specified Yet: <" + note + "> <" + product.toString() + ">");
                    }
                    break;
            }
        } else {
            assertNull(product);
        }
    }

    /**
     * <pre>
     * Test of the setter method's, for the Product class.
     * 
     * @dataProvider = "setters"
     * example: {"base Test", Product.create(0, "Shovel", 12.0, 14.0, 5), "base", true}
     * notes are catagorized as [base Test|prodID|prodName|prodCost|prodPrice|prodThreshold]
     * </pre>
     *
     * @param prodID
     * @param prodName
     * @param prodCost
     * @param prodPrice
     * @param prodThreshold
     * @param note String
     * @param expectedResult boolean
     */
    @Test(dataProvider = "testExamples", groups = ("Setters"))
    public void testSetters(String note, int prodID, String prodName, double prodCost, double prodPrice, int prodThreshold, boolean expectedResult) {
        Product product = Product.create(0, "Shovel", 12.0, 14.0, 5);

        switch (note) {
            case "prodName":
                assertEquals(product.setProdName(prodName), expectedResult);
                break;
            case "prodCost":
                assertEquals(product.setProdCost(prodCost), expectedResult);
                break;
            case "prodPrice":
                assertEquals(product.setProdPrice(prodPrice), expectedResult);
                break;
            case "prodThreshold":
                assertEquals(product.setProdThreshold(prodThreshold), expectedResult);
                break;
            default:
                List<String> testCase = Arrays.asList(new String[]{"base Test", "prodID"});
                if (testCase.contains(note)) {
                } else {
                    fail("No Test Case Specified Yet: <" + note + ">");
                }
                break;
        }
    }
}
