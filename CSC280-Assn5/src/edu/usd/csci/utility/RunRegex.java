///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package edu.usd.csci.utility;
//
//import static edu.usd.csci.utility.RegularExpressionUtility.isValidCreditCardNumber;
//import static edu.usd.csci.utility.RegularExpressionUtility.isValidPhoneNumber;
//import static edu.usd.csci.utility.RegularExpressionUtility.isValidUserAgent;
//import static edu.usd.csci.utility.RegularExpressionUtility.isValidZipCode;
//
///**
// *
// * @author Tyler_000
// */
//public class RunRegex {
//     public static void main(String[] args) {
//        try {
//            isValidZipCode("12345");
//            isValidZipCode("12345-9876");
//            isValidPhoneNumber("2225551212");
//            isValidPhoneNumber("222 555 1212");
//            isValidPhoneNumber("222-555-1212");
//            isValidPhoneNumber("(222) 555 1212");
//            isValidPhoneNumber("(222) 555-1212");
//            isValidUserAgent("PHP v5.5.18 - URS-SSO v1.0");
//            isValidUserAgent("PHP v6.1.0 - URS-SSO v3.2");
//            isValidUserAgent("PHP v6.1.02 - URS-SSO v3.22");
//            isValidCreditCardNumber("4111-1111-1111-1111");
//            isValidCreditCardNumber("3411111111111111");
//            isValidCreditCardNumber("5111 1222 2222 2222");
//            isValidCreditCardNumber("6011 1222 2222 2222");
//            isValidCreditCardNumber("9876 1222 2222 2222");
//            //isValidZipCode("");
//            //isValidCreditCardNumber("9876 1222 2222 2222 2222");
//        } catch (InvalidStringException e) {
//            e.printStackTrace();
//        }
//     }
//}
