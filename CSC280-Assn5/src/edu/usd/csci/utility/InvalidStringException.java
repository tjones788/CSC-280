/*
 * $Id: InvalidStringException.java Version 1.0 2/10/2015 11:30pm 
 * $Name: Tyler Jones
 */
package edu.usd.csci.utility;

/**
 *  Custom exception to be thrown when a target string to be tested with regular ex is empty or null
 * 
 * @author Tyler
 */
public class InvalidStringException extends Exception {

    public InvalidStringException (String message) {
        super(message);
    }
}
