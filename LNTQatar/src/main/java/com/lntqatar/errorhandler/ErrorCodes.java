package com.lntqatar.errorhandler;

/**
 * Error codes
 *
 * @author Harikrishnan.S
 */
public interface ErrorCodes {

    /**
     * Error code for
     * <code>IOException </code>
     */
    public static final int IOException = 1;
    /**
     * Error code for
     * <code>IOException </code>
     */
    public static final int NullPointerException = 2;
    /**
     * Error code for
     * <code>NumberFormatException </code>
     */
    public static final int NumberFormatException = 3;
    /**
     * Error code for
     * <code>HibernateException </code>
     */
    public static final int HibernateException = 4;
    /**
     * Error code for
     * <code>Unknown Error </code>
     */
    public static final int UnknownError = 5;
    /**
     * Error code for
     * <code>Password Validation - Password is of Incorrect length </code>
     */
    public static final int Invalid_Password_length = 6;
    /**
     * Error code for
     * <code>Password Validation - Password is not Alphanumeric </code>
     */
    public static final int Password_Not_Alphanumeric = 7;
    /**
     * Error code for
     * <code>Password Validation - Password donot contain Special Character
     * </code>
     */
    public static final int Password_Special_Character = 8;
    /**
     * Error code for
     * <code>Password Validation - Password matches with Username </code>
     */
    public static final int Username_match = 9;
    /**
     * Error code for
     * <code>Password Validation - Space is not allowed in Password </code>
     */
    public static final int Space_not_accepted = 10;
}
