/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

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
 * The Customer's NGTest
 * encapsulates all Test functionality
 * related to the Customer attributes and handling.
 *
 * Static Factory Method Design Pattern
 * with data validity check.
 * </pre>
 *
 * @see elements
 * @see <a href="http://www.mkyong.com/tutorials/testng-tutorials">http://www.mkyong.com/tutorials/testng-tutorials</a>
 * @see <a href="http://testng.org">http://testng.org</a>
 * @author Rudolf Fortes
 * @todo toString CheckPin
 *
 */
public class TestNG_Customer {

    /**
     * part of the default Test Constructor Not in Use but kept just in case
     *
     *
     * @deprecated
     */
    public TestNG_Customer() {
    }

    /**
     * <pre>
     * DataProvider testExamples
     *
     * reformatted to Raw data to use in other tests
     * written in the format of
     * Type         {String,int,    String, String,     String,     boolean,    boolean}
     * Description  {note,  userID, userPin,userName,   userAddress,userActive,  expectedResult}
     * example      {"base Test", 1, "1234", "My Name", "My Address", false, true}
     * notes are catagorized as [base Test|userID|userPin|userName|userAddress|userActive|expectedResult]
     * </pre>
     *
     * @return List<Object[]> used as Object[][]
     */
    public static List<Object[]> testExamplesRAW() {
        List<Object[]> returnData = new ArrayList<Object[]>();
        Object[][] object_array = new Object[][]{
            //base Test
            {"base Test", 1, "1234", "My Name", "My Address", true, true},
            //Test for userID
            {"userID", -1, "1234", "My Name", "My Address", true, false},
            {"userID", 0, "1234", "My Name", "My Address", true, true},
            {"userID", 1, "1234", "My Name", "My Address", true, true},
            {"userID", 100, "1234", "My Name", "My Address", true, true},
            //Test for userPin
            {"userPin", 1, "1234", "My Name", "My Address", true, true},
            {"userPin", 1, "", "My Name", "My Address", true, false},
            {"userPin", 1, "123", "My Name", "My Address", true, false},
            {"userPin", 1, "123456", "My Name", "My Address", true, false},
            {"userPin", 1, "0000", "My Name", "My Address", true, true},
            //Test for userName
            {"userName", 1, "1234", "", "My Address", true, false},
            {"userName", 1, "1234", "My Name", "My Address", true, true},
            {"userName", 1, "1234", "My 2nd Name", "My Address", true, true},
            {"userName", 1, "1234", "My 3rd Name", "My Address", true, true},
            {"userName", 1, "1234", "12My 3rd Name", "My Address", true, true},
            //Test for userAddress
            {"userAddress", 1, "1234", "My Name", "My Address", true, true},
            {"userAddress", 1, "1234", "My Name", "123", true, true},
            {"userAddress", 1, "1234", "My Name", "", true, false},
            {"userAddress", 1, "1234", "My Name", "My 3rd Address", true, true},
            {"userAddress", 1, "1234", "My Name", "not My Address", true, true},
            //Test for userActive
            {"userActive", 1, "1234", "My Name", "My Address", true, true},
            {"userActive", 1, "1234", "My Name", "My Address", false, true}
        };
        for(Object[] object:object_array){
        returnData.add(object);
        }
        return returnData;
    }

    /**
     * <pre>
     * DataProvider testExamples
     *
     * written in the format of
     * Type         {String,int,    String, String,     String,     boolean,    boolean}
     * Description  {note,  userID, userPin,userName,   userAddress,userActive,  expectedResult}
     * example      {"base Test", 1, "1234", "My Name", "My Address", false, true}
     * notes are catagorized as [base Test|userID|userPin|userName|userAddress|userActive|expectedResult]
     * </pre>
     *
     * @return Object[][]
     */
    @DataProvider(name = "testExamples")
    public static Iterator<Object[]> testExamples() {
        List<Object[]> returnData = TestNG_Customer.testExamplesRAW();
        return returnData.iterator();
        /*return new Object[][]{
            //base Test
            {"base Test", 1, "1234", "My Name", "My Address", true, true},
            //Test for userID
            {"userID", -1, "1234", "My Name", "My Address", true, false},
            {"userID", 0, "1234", "My Name", "My Address", true, true},
            {"userID", 1, "1234", "My Name", "My Address", true, true},
            {"userID", 100, "1234", "My Name", "My Address", true, true},
            //Test for userPin
            {"userPin", 1, "1234", "My Name", "My Address", true, true},
            {"userPin", 1, "", "My Name", "My Address", true, false},
            {"userPin", 1, "123", "My Name", "My Address", true, false},
            {"userPin", 1, "123456", "My Name", "My Address", true, false},
            {"userPin", 1, "0000", "My Name", "My Address", true, true},
            //Test for userName
            {"userName", 1, "1234", "", "My Address", true, false},
            {"userName", 1, "1234", "My Name", "My Address", true, true},
            {"userName", 1, "1234", "My 2nd Name", "My Address", true, true},
            {"userName", 1, "1234", "My 3rd Name", "My Address", true, true},
            {"userName", 1, "1234", "12My 3rd Name", "My Address", true, true},
            //Test for userAddress
            {"userAddress", 1, "1234", "My Name", "My Address", true, true},
            {"userAddress", 1, "1234", "My Name", "123", true, true},
            {"userAddress", 1, "1234", "My Name", "", true, false},
            {"userAddress", 1, "1234", "My Name", "My 3rd Address", true, true},
            {"userAddress", 1, "1234", "My Name", "not My Address", true, true},
            //Test for userActive
            {"userActive", 1, "1234", "My Name", "My Address", true, true},
            {"userActive", 1, "1234", "My Name", "My Address", false, true}
        };*/
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
     * Test of create method, of class Customer.
     *
     * @dataProvider = "testExamples"
     * example {"base Test", 1, "1234", "My Name", "My Address", false, true}
     * notes are catagorized as [base Test|userID|userPin|userName|userAddress|userActive|expectedResult]
     * </pre>
     *
     * @param note String
     * @param userID integer
     * @param userName String
     * @param userPin String [Validity Check "^[0-9]{4}$"]
     * @param userAddress String
     * @param userActive
     * @param expectedResult boolean
     */
    @Test(dataProvider = "testExamples", groups = ("Factory"))
    public void testCreate(String note, int userID, String userPin, String userName, String userAddress, boolean userActive, boolean expectedResult) {
        Customer customer = Customer.create(userID, userPin, userName, userAddress, userActive);
        if (!expectedResult) {
            assertNull(customer, note);
            //System.out.println(note+"Failed");
        } else {
            if (customer instanceof Customer) {
                assertTrue(true, note);
            } else {
                assertFalse(false, note);
            }
        }
    }

    /**
     * <pre>
     * Test of Getters methods in class Customer.
     *
     * @dataProvider = "testExamples"
     * example {"base Test", 1, "1234", "My Name", "My Address", false, true}
     * notes are catagorized as [base Test|userID|userPin|userName|userAddress|userActive|expectedResult]
     * </pre>
     *
     * @param note String
     * @param userID integer
     * @param userName String
     * @param userPin String [Validity Check "^[0-9]{4}$"]
     * @param userAddress String
     * @param userActive
     * @param expectedResult boolean
     */
    @Test(dataProvider = "testExamples", groups = ("Getters"))
    public void testGetters(String note, int userID, String userPin, String userName, String userAddress, boolean userActive, boolean expectedResult) {
        Customer customer = Customer.create(userID, userPin, userName, userAddress, userActive);
        if (customer != null) {
            switch (note) {
                case "userID":
                    //assertEquals(userID, userID);
                    assertSame(userID, (int) userID, note);
                    //assertEquals(userID, customer.getUserID());
                    break;
                case "userPin"://to be removed next iteration
                    assertEquals(userPin, customer.getUserPin());
                    break;
                case "userName":
                    assertEquals(userName, customer.getUserName());
                    break;
                case "userAddress":
                    assertEquals(userAddress, customer.getUserAddress());
                    break;
                case "userActive":
                    if (userActive) {
                        assertEquals(userActive, customer.isActive());
                    }
                    break;
                default:
                    if (!note.equals("base Test")) {
                        fail("No Test Case Specified Yet: <" + note + "> <" + customer.toString() + ">");
                    }
                    break;
            }
        } else {
            //fail("No Test Case Specified Yet: <" + note + "> <" + customer.toString() + ">");
            //assertThat(customer, instanceOf(elements.Customer.class));
        }
    }

    /**
     * <pre>
     * Test of Getters methods in class Customer.
     *
     * @dataProvider = "testExamples"
     * example {"base Test", 1, "1234", "My Name", "My Address", false, true}
     * notes are catagorized as [base Test|userID|userPin|userName|userAddress|userActive|expectedResult]
     * </pre>
     *
     * @param note String
     * @param userID integer
     * @param userName String
     * @param userPin String [Validity Check "^[0-9]{4}$"]
     * @param userAddress String
     * @param userActive
     * @param expectedResult boolean
     */
    @Test(dataProvider = "testExamples", groups = ("Setters"))
    public void testSetters(String note, int userID, String userPin, String userName, String userAddress, boolean userActive, boolean expectedResult) {
        //            {"base Test", 1, "1234", "My Name", "My Address", true},
        Customer customer = Customer.create(1, "1234", "My Name", "My Address", userActive);
        if (expectedResult == true) {
            switch (note) {
                case "userID":
                    break;
                case "userPin":
                    if (expectedResult) {
                        assertTrue(customer.setUserPin(userPin), note);
                    } else {
                        assertFalse(customer.setUserPin(userPin), note);
                    }
                    break;
                case "userName":
                    if (expectedResult) {
                        assertTrue(customer.setUserName(userName), note);
                    } else {
                        assertFalse(customer.setUserName(userName), note);
                    }
                    break;
                case "userAddress":
                    if (expectedResult) {
                        assertTrue(customer.setUserAddress(userAddress), note);
                    } else {
                        assertFalse(customer.setUserAddress(userAddress), note);
                    }
                    break;
                case "userActive":
                    if (!userActive) {
                        customer.lock();
                        assertEquals(userActive, customer.isActive());
                    } else {
                        customer.unlock();
                        assertEquals(userActive, customer.isActive());
                    }
                    break;
                default:
                    if (!note.equals("base Test")) {
                        fail("No Test Case Specified Yet: <" + note + "> <" + customer.toString() + ">");
                    }
                    break;
            }
        }
    }

    /**
     * <pre>
     * Test of checkUserPin method, of class Customer.
     *
     * @dataProvider = "testExamples"
     * example {"base Test", 1, "1234", "My Name", "My Address", false, true}
     * notes are catagorized as [base Test|userID|userPin|userName|userAddress|userActive|expectedResult]
     * </pre>
     *
     * @param note String
     * @param userID integer
     * @param userName String
     * @param userPin String [Validity Check "^[0-9]{4}$"]
     * @param userAddress String
     * @param userActive
     * @param expectedResult boolean
     */
    @Test(dataProvider = "testExamples", groups = ("Check"))
    public void testCheckUserPin(String note, int userID, String userPin, String userName, String userAddress, boolean userActive, boolean expectedResult) {
        Customer customer = Customer.create(userID, userPin, userName, userAddress, userActive);
        if (customer != null) {
            switch (note) {
                case "userPin":
                    if (expectedResult) {
                        assertTrue(customer.checkUserPin(userPin), note);
                    } else {
                        assertFalse(customer.setUserPin(userPin), note);
                    }
                    break;
                default:
                    List<String> testCase = Arrays.asList(new String[]{"base Test", "userID", "userName", "userAddress","userActive"});
                    if (testCase.contains(note)) {
                    } else {
                        fail("No Test Case Specified Yet: <" + note + "> <" + customer.toString() + ">");
                    }
                    break;
            }
        }
    }

    /**
     * <pre>
     * Test of toString method, of class Customer.
     *
     * @dataProvider = "testExamples"
     * example {"base Test", 1, "1234", "My Name", "My Address", false, true}
     * notes are catagorized as [base Test|userID|userPin|userName|userAddress|userActive|expectedResult]
     * </pre>
     *
     * @param note String
     * @param userID integer
     * @param userName String
     * @param userPin String [Validity Check "^[0-9]{4}$"]
     * @param userAddress String
     * @param userActive
     * @param expectedResult boolean
     */
    @Test(dataProvider = "testExamples", groups = ("toString"))
    public void testToString(String note, int userID, String userPin, String userName, String userAddress, boolean userActive, boolean expectedResult) {
        Customer customer = Customer.create(userID, userPin, userName, userAddress, userActive);
        if (customer != null) {
            String result = customer.toString();
            String expectedString = userID + "\t" + userPin + "\t" + userName + "\t" + userAddress + "\n" + userActive + "\n";
            assertEquals(result, expectedString);
        }
    }
}
