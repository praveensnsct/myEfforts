package com.lntqatar.manager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.lntqatar.crypto.EncryptionEngine;
import com.lntqatar.dao.UserDAO;
import com.lntqatar.errorhandler.ErrorCodes;
import com.lntqatar.errorhandler.LNTQatarException;
import com.lntqatar.model.User;
import com.lntqatar.model.UserSession;
import com.lntqatar.pojo.UserDetailsWrapper;
import com.lntqatar.pojo.UserLoginWrapper;
import com.lntqatar.util.Constants;
import com.lntqatar.util.PasswordValidate;

public class UserManager implements Constants, ErrorCodes {

	private static Logger logger = Logger.getLogger(UserManager.class);
	UserDAO userDaoObj = null;

	/**
	 * constructor method
	 */
	public UserManager() {
		userDaoObj = new UserDAO();
	}

	/**
	 * This method retrieves user name and hex value of password from request
	 * and validates against the details saved in Database.
	 * 
	 * @param userName
	 * @param toHexpwd
	 * @param req
	 *            : {@link HttpServletRequest}
	 * @return String - user is validated or not
	 * @throws LNTQatarException
	 */
	public Map<String, String> login(UserLoginWrapper loginDetails)
			throws LNTQatarException {
		logger.debug("Entering Login in usermanager...");
		String userName = loginDetails.getUsername();
		String toHexpwd = loginDetails.getPassword();
		User user = userDaoObj.getUser(userName);
		UserSession userSession = new UserSession();
		String password = EncryptionEngine.fromHex(toHexpwd);
		String userPwd = EncryptionEngine.fromHex(user.getUserPwd());
		Map<String, String> userDetails = new HashMap<String, String>();
		if (password.equals(userPwd)) {
			logger.info("Password matches...");
			String sessionString = createSession(user.getUserName());
			userSession.setUserName(user.getUserName());
			userSession.setUserSession(EncryptionEngine.toHex(sessionString));
			Date date = new Date();
			userSession.setLastAccessTime(date.getTime());
			boolean sessionCreated = userDaoObj.createUserSession(userSession);
			if (sessionCreated) {
				logger.info("Session created...");
				List<User> userList = userDaoObj.getUserDetails(userName);
				userDetails.put("firstName", userList.get(0).getUserName());
				userDetails.put("lastName", userList.get(0).getLastName());
				userDetails.put("emailId", userList.get(0).getEmail());
				userDetails.put("contact", userList.get(0).getContact());
				userDetails.put("roleId",
						String.valueOf(userList.get(0).getRoleid()));
				UserDAO userDAOobj = new UserDAO();
				String sessionId = userDAOobj.getSessionId(user.getUserName());
				userDetails.put("sessionId", sessionId);
				return userDetails;
			} else {
				logger.info("Session not created...");
				throw new LNTQatarException(INVALID_LOGIN,
						HttpStatus.UNAUTHORIZED.value(), "");
			}
		} else {
			logger.info("Invalid login - Password doesn't match...");
			throw new LNTQatarException(INVALID_LOGIN,
					HttpStatus.UNAUTHORIZED.value(), "");
		}
	}

	/**
	 * This method retrieves user name from request and logs out user from
	 * Session Table.
	 * 
	 * @param userName
	 * @return String - Success or failure message or not
	 * @throws LNTQatarException
	 */
	public String logout(String userName, String sessionId)
			throws LNTQatarException {
		return userDaoObj.logoutUser(userName, sessionId);
	}

	/**
	 * Create session for the user with the username provided and the current
	 * timestamp.
	 * 
	 * @param userName
	 * @return user's session which is a combination of username and timestamp
	 */
	private String createSession(String userName) {
		String userSession = null;
		Timestamp timeStampObj = new Timestamp(System.currentTimeMillis());
		userSession = userName + timeStampObj;
		return userSession;
	}

	/**
	 * Create User from the user details provided
	 * 
	 * @param userName
	 * @param userPwd
	 * @param firstName
	 * @param lastName
	 * @param roleId
	 * @param email
	 * @param contact
	 * @param loginUserName
	 * @param sessionId
	 * @return boolean : whether user is created or not
	 * @throws LNTQatarException
	 */
	public Map<String, String> createUser(UserSession userSessionDetails,
			UserDetailsWrapper userCreationDetails) throws LNTQatarException {
		boolean isUserAuthorized = isUserAuthorized(userSessionDetails,
				userCreationDetails);
		Map<String, String> response = new HashMap<String, String>();
		if (isUserAuthorized) {
			User userObj = new User();
			String userName = userCreationDetails.getUsername();
			String userPwd = userCreationDetails.getPassword();
			userObj.setUserName(userName);
			PasswordValidate passwordValidateObj = new PasswordValidate();
			boolean result = passwordValidateObj.checkpassword(userPwd,
					userName);
			if (result) {
				String firstName = userCreationDetails.getFirstName();
				String lastName = userCreationDetails.getLastName();
				String email = userCreationDetails.getEmail();
				String contact = userCreationDetails.getContactNo();
				int roleId = userCreationDetails.getRoleId();
				userObj.setUserPwd(userPwd);
				userObj.setFirstName(firstName);
				userObj.setLastName(lastName);
				userObj.setEmail(email);
				userObj.setContact(contact);
				userObj.setRoleid(roleId);
				result = userDaoObj.createUser(userObj);
				response.put("message", USER_CREATE_SUCCESS);
				response.put("username", userName);
			}
		}
		return response;
	}

	/**
	 * Get the user name and retrieves security question for the user name
	 * provided.
	 * 
	 * @param userName
	 * @return security question of the particular user
	 * @throws LNTQatarException
	 */
	public String getSecurityQues(String userName) throws LNTQatarException {
		String question = userDaoObj.getSecurityQues(userName);
		return question;
	}

	/**
	 * Set security question and answer for the user with the user name
	 * provided.
	 * 
	 * @param userName
	 * @param userQues
	 * @param userAns
	 * @param sessionId
	 * @return String specifying whether the security details are updated or
	 *         not.
	 * @throws LNTQatarException
	 */
	public String setSecurityDetails(String userName, String userQues,
			String userAns, String sessionId) throws LNTQatarException {
		String result = userDaoObj.setSecurityDetails(userName, userQues,
				userAns, sessionId);
		return result;
	}

	/**
	 * Get user name and security answer and validates answer with the password
	 * stored in database.
	 * 
	 * @param userName
	 * @param userAns
	 * @return String specifying whether user security answer is validated or
	 *         not
	 * @throws LNTQatarException
	 */
	public String validateSecurityAnswer(String userName, String userAns)
			throws LNTQatarException {
		String status = userDaoObj.validateSecurityAnswer(userName, userAns);
		return status;
	}

	public boolean isUserAuthorized(UserSession userSessionDetails,
			UserDetailsWrapper userCreationDetails) throws LNTQatarException {
		List<User> loginUserDetails = userDaoObj
				.getUserDetails(userSessionDetails.getUserName());
		if (loginUserDetails != null) {
			int loginUserRoleId = loginUserDetails.get(0).getRoleid();
			int newUserRoleId = userCreationDetails.getRoleId();
			if (loginUserRoleId > newUserRoleId)
				throw new LNTQatarException(USER_NOT_AUTHORIZED,
						HttpStatus.NO_CONTENT.value(), "");
			else
				return true;
		} else
			throw new LNTQatarException(SESSION_RETRIEVE_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), "");

	}
}
