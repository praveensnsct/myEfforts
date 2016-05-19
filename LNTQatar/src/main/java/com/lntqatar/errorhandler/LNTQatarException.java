package com.lntqatar.errorhandler;

public class LNTQatarException extends Exception {

    private final String message;
    private final int errorCode;
    private final String reason;

    /**
     * Constructor method with parameters message, error code and reason.
     *
     * @param message
     * @param errorCode
     * @param reason
     */
    public LNTQatarException(String message, int errorCode, String reason) {
        this.message = message;
        this.errorCode = errorCode;
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getReason() {
        return reason;
    }
}