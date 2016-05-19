package com.lntqatar.sessionhandler;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

@WebListener
public class LNTQatarHttpSessionListener implements HttpSessionListener {

    private static Logger logger = Logger
            .getLogger(LNTQatarHttpSessionListener.class);

    /**
     * Gives Notification that a session was created.
     *
     * @param sessionEvent - HttpSessionEvent Object
     */
    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        // logger.info("Session Created");
    }

    /**
     * Gives Notification that a session is about to be invalidated.
     *
     * @param sessionEvent - HttpSessionEvent Object
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        // // logger.info("Session Destroyed");
        // String username = (String)
        // sessionEvent.getSession().getAttribute("username");
        // String
        // sessionId=(String)sessionEvent.getSession().getAttribute("sessionID");
        // UserDAO userDaoObj = new UserDAO();
        // boolean result = false;
        // try {
        // result = userDaoObj.checkUser(username);
        // } catch (LNTQatarException e1) {
        // e1.printStackTrace();
        // }
        // if (result) {
        // try {
        // userDaoObj.logoutUser(username,sessionId);
        // } catch (LNTQatarException e) {
        // e.printStackTrace();
        // }
        // } else {
        // // logger.debug("no need to delete user already deleted");
        // }
    }
}
