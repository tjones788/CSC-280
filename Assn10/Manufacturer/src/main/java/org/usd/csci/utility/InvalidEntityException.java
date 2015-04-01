/*
 * $ID: InvalidEntityException.java Version 1.0 - 3/3/2015 11:30pm
 * $Name: $Tyler Jones
 */
package org.usd.csci.utility;

/**
 * This is a custom exception, thrown when an Invalid Entity is used within a session bean.  Thrown when required fields are not found and if the entity is not unique.
 *
 * @author Tyler
 */
public class InvalidEntityException extends Exception {
    
    /*
    * InvalidEntityException Default Constructor
    */
    public InvalidEntityException(){
    }

    /*
    * InvalidEntity Exception message
    *
    * @param msg
    */
    public InvalidEntityException(String msg){
        super(msg);
    }
}
