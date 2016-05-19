package com.lntqatar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model class for user sessions.
 *
 * @author Harikrishnan S
 */
@Entity
@Table(name = "session_tbl")
public class UserSession {

    @Id
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
    @Column(name = "user_session")
    private String userSession;
    @Column(name = "last_access_time")
    private Long lastAccessTime;

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    /**
     * Returns user name
     *
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * set username
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns user session stored in database
     *
     * @return user session
     */
    public String getUserSession() {
        return userSession;
    }

    /**
     * set user Session
     *
     * @param userSession
     */
    public void setUserSession(String userSession) {
        this.userSession = userSession;
    }
}
