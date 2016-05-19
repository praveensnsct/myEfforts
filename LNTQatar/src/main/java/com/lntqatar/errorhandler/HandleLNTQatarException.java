package com.lntqatar.errorhandler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class HandleLNTQatarException {

    @ExceptionHandler(LNTQatarException.class)
    public void handleLNTQatarException(LNTQatarException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {        
        response.setHeader("ErrorMessage", ex.getMessage());
        response.sendError(ex.getErrorCode(), ex.getMessage());
    }
}
