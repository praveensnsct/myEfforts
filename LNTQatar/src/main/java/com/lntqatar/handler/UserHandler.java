package com.lntqatar.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.lntqatar.dao.UserDAO;
import com.lntqatar.errorhandler.LNTQatarException;
import com.lntqatar.manager.UserManager;
import com.lntqatar.model.User;
import com.lntqatar.model.UserSession;
import com.lntqatar.pojo.UserDetailsWrapper;
import com.lntqatar.pojo.UserLoginWrapper;
import com.lntqatar.util.Constants;

public class UserHandler implements Constants {

	private static Logger logger = Logger.getLogger(UserHandler.class);
	UserManager userManagerObj = new UserManager();
	UserDAO userDaoObj = new UserDAO();

	/**
	 * This method retrieves user name and password from request and validates
	 * against the details saved in Database.
	 * 
	 * @param loginDetails
	 *            - login details of a user
	 * @return
	 * @throws LNTQatarException
	 */
	public Map<String, String> loginUser(UserLoginWrapper loginDetails)
			throws LNTQatarException {
		return userManagerObj.login(loginDetails);
	}

	/**
	 * Create User from the user details provided
	 * 
	 * @param userCreationDetails
	 *            - User details to be added
	 * @param sessionId
	 * @return User creation message
	 * @throws LNTQatarException
	 */
	public Map<String, String> createUser(
			UserDetailsWrapper userCreationDetails, String sessionId)
			throws LNTQatarException {
		boolean isSessionValidated = userDaoObj.validateSession(sessionId);
		if (isSessionValidated) {
			logger.info("Valid Session ...");
			UserSession userSessionDetails = userDaoObj.getUserName(sessionId);
			return userManagerObj.createUser(userSessionDetails,
					userCreationDetails);
		} else {
			throw new LNTQatarException(USER_SESSION_EXPIRED,
					HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(),
					USER_SESSION_EXPIRED);
		}
	}

	/**
	 * This method logs out user from session table Takes userName as parameter
	 * 
	 * @param userName
	 * @return String specifying whether logout is success or failure
	 * @throws LNTQatarException
	 */
	public String logoutUser(String userName, String sessionId)
			throws LNTQatarException {
		return userManagerObj.logout(userName, sessionId);
	}

	/**
	 * Get the user name and retrieves security question for the user name
	 * provided.
	 * 
	 * @param userName
	 * @return security question of the particular user
	 * @throws LNTQatarException
	 */
	public String getsQues(String userName) throws LNTQatarException {
		String question = userManagerObj.getSecurityQues(userName);
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
		String result = userManagerObj.setSecurityDetails(userName, userQues,
				userAns, sessionId);
		return result;
	}

	/**
	 * Get user name and security answer and validates answer with the password
	 * stored in database.
	 * 
	 * @param userName
	 * @param userAns
	 * @param sessionId
	 * @return String specifying whether user security answer is validated or
	 *         not
	 * @throws LNTQatarException
	 */
	public String validateSecurityAnswer(String userName, String userAns)
			throws LNTQatarException {
		String status = userManagerObj
				.validateSecurityAnswer(userName, userAns);
		return status;
	}
}
