/*
 * $ID: RegularExprssionUtilityTest.java Version1.0 2/10/2015 11:30pm
 * $Tyler Jones
 */
package edu.usd.csci.utility;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A JUnit Test Class to test the RegularExpresssionUtility Methods
 *
 * @author Tyler
 */
public class RegularExpressionUtilityTest {

    static public RegularExpressionUtility regexUtility = new RegularExpressionUtility();

    public RegularExpressionUtilityTest() {
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
     * Test of isValidZipCode method, of class RegularExpressionUtility.
     */
    @Test
    public void testIsValidZipCode() {
        try {
            assertTrue("Zip code should have been Valid", regexUtility.isValidZipCode("12345"));
            assertTrue("Zip code should have been Valid", regexUtility.isValidZipCode("12345-1234"));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            fail("Unexpected Exception was Thrown");
        }
    }

    /**
     * Test of isValidZipCode method, of class RegularExpressionUtility. Tests
     * passing an Invalid Zip code.
     */
    @Test
    public void testInvalidZipCode() {
        try {
            assertFalse("Zip Code Should Have Been Invalid", regexUtility.isValidZipCode("12345-788"));
            assertFalse("Zip Code Should Have Been Invalid", regexUtility.isValidZipCode("1234"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("Unexpected Exception was Thrown");
        }
    }

    /**
     * Test of isValidPhoneNumber method, of class RegularExpressionUtility.
     */
    @Test
    public void testIsValidPhoneNumber() {
        try {
            assertTrue("Phone Number Should Have Been Valid", regexUtility.isValidPhoneNumber("2225551212"));
            assertTrue("Phone Number Should Have Been Valid", regexUtility.isValidPhoneNumber("222 555 1212"));
            assertTrue("Phone Number Should Have Been Valid", regexUtility.isValidPhoneNumber("222-555-1212"));
            assertTrue("Phone Number Should Have Been Valid", regexUtility.isValidPhoneNumber("(222) 555 1212"));
            assertTrue("Phone Number Should Have Been Valid", regexUtility.isValidPhoneNumber("(222) 555-1212"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("Unexpected Exception was Thrown");
        }
    }

    /**
     * Test of isValidPhoneNumber method, of class RegularExpressionUtility.
     * Tests an Invalid Phone Number
     */
    @Test
    public void testInvalidPhoneNumber() {
        try {
            assertFalse("Phone number should have been Invalid", regexUtility.isValidPhoneNumber("222-456-789890"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("Unexpected Exception was Thrown");
        }
    }

    /**
     * Test of isValidUserAgent method, of class RegularExpressionUtility.
     */
    @Test
    public void testIsValidUserAgent() {
        try {
            assertTrue("Test", regexUtility.isValidUserAgent("PHP v5.5.18 - URS-SSO v1.0"));
            assertTrue("Test", regexUtility.isValidUserAgent("PHP v1.2.12 - URS-SSO v4.0"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("Unexpected Exception was Thrown");
        }
    }

    /**
     * Test of isValidUserAgent method, of class RegularExpressionUtility. Tests
     * an Invalid User Agent
     */
    @Test
    public void testInvalidUserAgent() {
        try {
            assertFalse("Test", regexUtility.isValidUserAgent("HTML - URS-SSO v1.0"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("Unexpected Exception was Thrown");
        }
    }

    /**
     * Test of isValidCreditCardNumber method, of class
     * RegularExpressionUtility.
     */
    @Test()
    public void testIsValidCreditCardNumber() {
        try {
            assertTrue("Credit card number should be valid.", regexUtility.isValidCreditCardNumber("4222-1234-1234-1234"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail("Unexpected Exception was Thrown");
        }
    }

    /**
     * Test of isValidCreditCardNumber method, of class
     * RegularExpressionUtility. Tests an Invalid Credit Card Number
     */
    @Test
    public void testInvalidCreditCardNumber() {
        try {
            assertFalse("Credit Card should have been Invalid.", regexUtility.isValidCreditCardNumber("4222.1234.1234"));
        } catch (InvalidStringException e) {
            System.out.println("Exception: " + e.getMessage());
            assertTrue("Wrong exception was thrown", e instanceof InvalidStringException);
        }
    }

    /**
     * Test of isValidCreditCardNumber method, of class
     * RegularExpressionUtility. Tests a credit card number that is too long.
     */
    @Test
    public void testLongCreditCardNumber() {
        try {
            regexUtility.isValidCreditCardNumber("4222-1234-1234-1234-1234");
            fail("InvalidStringException Should Have been Thrown");
        } catch (InvalidStringException e) {
            System.out.println("Exception: " + e.getMessage());
            assertTrue("Wrong exception was thrown", e instanceof InvalidStringException);
        }
    }

    /**
     * Test of isValidCreditCardNumber method, of class
     * RegularExpressionUtility. Tests a Credit Card number that is Too Short
     */
    @Test
    public void testShortCreditCardNumber() {
        try {
            regexUtility.isValidCreditCardNumber("4222-1234-1234");
            fail("InvalidStringException Should Have been Thrown");
        } catch (InvalidStringException e) {
            System.out.println("Exception: " + e.getMessage());
            assertTrue("Wrong exception was thrown", e instanceof InvalidStringException);
        }
    }

    /*
     * Tests if InvalidStringException is thrown when TargetString is Empty.
     * NullPointer exception thrown if null.
     */
    @Test
    public void testEmptyString() {
        try {
            regexUtility.isValidCreditCardNumber("");
            fail("Invalid String Exception was Not Thrown");
        } catch (InvalidStringException e) {
            assertTrue("Wrong Exception Thrown.", e instanceof InvalidStringException);
        }
    }

    /*
     * Tests that an InvalidString exception is thrown if a null target string is passed.
     */
    @Test()
    public void testNullString() {
        try {
            regexUtility.isValidZipCode(null);
            fail("Exception Should Have Been Thrown");
        } catch (InvalidStringException e) {
            assertTrue("Wrong Exception Thrown", e instanceof InvalidStringException);
        }
    }
}
