package com.lntqatar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model class for users.
 *
 * @author Harikrishnan S
 */
@Entity
@Table(name = "user_tbl")
public class User implements Serializable {

    @Id
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
    @Column(name = "user_pwd")
    private String userPwd;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "contact")
    private String contact;
    @Column(name = "question")
    private String sQuestion;
    @Column(name = "answer")
    private String sAnswer;
    @Column(name = "roleid")
    private int roleid;

    /**
     * Constructor method
     */
    public User() {
    }

    /**
     * Constructor method which accepts user data
     * 
     */
    public User(String userName, String pwd, String firstName, String lastName,
            String email, String contact, String encryptKey, int roleid) {
        this.userName = userName;
        this.userPwd = pwd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.roleid = roleid;
    }

    /**
     * Retrieves first name of user
     *
     * @return first name of user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name of user
     *
     * @param first name of user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves last name of user
     *
     * @return last name of user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set last name of user
     *
     * @param last name of user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves username of user
     *
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set username of user
     *
     * @param username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves user password
     *
     * @return user password
     */
    public String getUserPwd() {
        return userPwd;
    }

    /**
     * Set user password
     *
     * @param user password
     */
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    /**
     * Retrieves user email
     *
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user email
     *
     * @param user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves user contact
     *
     * @return user contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Set user contact
     *
     * @param user contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Retrieves user Security Question
     *
     * @return user security answer
     */
    public String getsQuestion() {
        return sQuestion;
    }

    /**
     * Set user Security Question
     *
     * @param user security Question
     */
    public void setsQuestion(String sQuestion) {
        this.sQuestion = sQuestion;
    }

    /**
     * Retrieves user Security Answer
     *
     * @return user Security Answer
     */
    public String getsAnswer() {
        return sAnswer;
    }

    /**
     * Set user Security Answer
     *
     * @param security Answer
     */
    public void setsAnswer(String sAnswer) {
        this.sAnswer = sAnswer;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }
}
