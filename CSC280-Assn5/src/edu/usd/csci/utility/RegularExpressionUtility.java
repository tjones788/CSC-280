/*
 * RegularExpressionUtility.java Version 1.0 2/10/2015 11:30pm
 * #Name: Tyler Jones
 */
package edu.usd.csci.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class containing methods to test if target string matches Regular expression
 * Pattern.
 *
 * @author Tyler Jones
 */
public class RegularExpressionUtility {

    public RegularExpressionUtility() {

    }
    /*
     * Test for a valid zip code using regex Pattern and Matcher
     *
     * @Return True if a Valid, accepted String.  False if invalid.
     * @Throws InvalidString Exception  Thrown if TargetString is NULL or EMPTY   
     */

    public Boolean isValidZipCode(String targetString) throws InvalidStringException {
        System.out.println("===Testing isValidZipCode()===");
        try {
            if (targetString.isEmpty()) {
                throw new InvalidStringException("Target string is EMPTY");
            }

            Pattern regexPattern = Pattern.compile("^\\d{5}(-\\d{4})?$");
            Matcher regexMatcher = regexPattern.matcher(targetString);

            if (regexMatcher.matches()) {
                System.out.println("Matching Text: " + regexMatcher.group());
                System.out.println("    Starting Idx: " + regexMatcher.start());
                System.out.println("    Ending Idx: " + regexMatcher.end());
                return true;
            } else {
                System.out.println("!!!Zip code is invalid!!!");
                return false;
            }
        } catch (NullPointerException e) {
            throw new InvalidStringException("Target String is NULL" + e.getMessage());
        }
    }
    /*
     *  Phone Number has been entered and passed as an argument.
     *
     * @Return True if a Valid, accepted String.  False if invalid.
     * @Throws InvalidString Exception  Thrown if TargetString is NULL or EMPTY   
     */

    public Boolean isValidPhoneNumber(String targetString) throws InvalidStringException {
        System.out.println("===Testing isValidPhoneNumber===");
        try {
            if (targetString.isEmpty()) {
                throw new InvalidStringException("Target string is EMPTY");
            }
            Pattern regexPattern = Pattern.compile("\\D*([2-9]\\d{2})(\\D*)([2-9]\\d{2})(\\D*)(\\d{4})\\D*");
            Matcher regexMatcher = regexPattern.matcher(targetString);

            if (regexMatcher.matches()) {
                System.out.println("Matching Text: " + regexMatcher.group());
                System.out.println("    Starting Idx: " + regexMatcher.start());
                System.out.println("    Ending Idx: " + regexMatcher.end());
                return true;
            } else {
                System.out.println("!!!Phone Number is INVALID!!!");
                return false;
            }
        } catch (NullPointerException e) {
            throw new InvalidStringException("Target String is NULL" + e.getMessage());
        }
    }
    /*
     *  Tests if User Agent is valid.  
     *
     * @Return True if a Valid, accepted String.  False if invalid.
     * @Throws InvalidString Exception  Thrown if TargetString is NULL or EMPTY   
     */

    public Boolean isValidUserAgent(String targetString) throws InvalidStringException {
        System.out.println("===Testing isValidUserAgent===");
        try {
            if (targetString.isEmpty()) {
                throw new InvalidStringException("Target string is EMPTY.");
            }
            Pattern regexPattern = Pattern.compile("^PHP v\\d+.\\d+.\\d+ - URS-SSO v\\d+.\\d+$");
            Matcher regexMatcher = regexPattern.matcher(targetString);

            if (regexMatcher.matches()) {
                System.out.println("Matching Text: " + regexMatcher.group());
                System.out.println("    Starting Idx: " + regexMatcher.start());
                System.out.println("    Ending Idx: " + regexMatcher.end());
                return true;
            } else {
                System.out.println("!!!User Agent is INVALID!!!");
                return false;
            }
        } catch (NullPointerException e) {
            throw new InvalidStringException("Target String is NULL" + e.getMessage());
        }
    }
    /*
     * Method to test if credit card number is valid 
     *
     * @Return True if a Valid, accepted String.  False if invalid.
     * @Throws InvalidString Exception  Thrown if TargetString is NULL or EMPTY   
     */

    public Boolean isValidCreditCardNumber(String targetString) throws InvalidStringException {
        System.out.println("===Testing isValidCreditCardNumber===");
        try {
            if (targetString.isEmpty()) {
                throw new InvalidStringException("Target string is EMPTY.");
            }

            String cardNumber = targetString.replaceAll("\\D", "");
            if (cardNumber.length() > 16 || cardNumber.length() < 16) {
                throw new InvalidStringException("Card number is too long");
            }

            Pattern regexPattern = Pattern.compile("^(4\\d{15})|(5[1-5]\\d{14})|((37|34|65)\\d{14})|(\\d{16})|(60111\\d{11})$");
            Matcher regexMatcher = regexPattern.matcher(cardNumber);

            if (regexMatcher.matches()) {
                System.out.println("Matching Text: " + regexMatcher.group());
                System.out.println("Credit Card Type: " + getCardType(cardNumber));
                System.out.println("    Starting Idx: " + regexMatcher.start());
                System.out.println("    Ending Idx: " + regexMatcher.end());
                return true;
            } else {
                System.out.println("!!!Credit Card Number is INVALID!!!");
                return false;
            }
        } catch (NullPointerException e) {
            throw new InvalidStringException("Target String is NULL" + e.getMessage());
        }
    }
    /*
     *  Helper Method to obtain card type of credit card.
     *
     * @Return Returns the Card Type of the Credit Card Entered
     */

    public String getCardType(String cardNumber) {
        String cardType = null;
        if (cardNumber.startsWith("4")) {
            cardType = "Visa";
        } else if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            cardType = "American Express";
        } else if (cardNumber.startsWith("51") || cardNumber.startsWith("52") || cardNumber.startsWith("53") || cardNumber.startsWith("54") || cardNumber.startsWith("55")) {
            cardType = "Mastercard";
        } else if (cardNumber.startsWith("6")) {
            if (cardNumber.startsWith("65")) {
                cardType = "Discover";
            } else if (cardNumber.startsWith("60111")) {
                cardType = "Discover";
            }
        } else {
            cardType = "Card Type Not Recognized";
        }
        return cardType;
    }
}
