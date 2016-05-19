package com.lntqatar.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;

import com.lntqatar.model.UserSession;
import com.lntqatar.model.User;
import com.lntqatar.crypto.EncryptionEngine;
import com.lntqatar.errorhandler.LNTQatarException;
import com.lntqatar.util.Constants;

public class UserDAO implements Constants {

	SessionFactory sessionFactoryObj = null;
	EncryptionEngine cryptoObj = new EncryptionEngine();
	String encryptKey = null;
	private static Logger logger = Logger.getLogger(UserDAO.class);

	/**
	 * Constructor method. This initializes session factory.
	 */
	public UserDAO() {
		sessionFactoryObj = com.lntqatar.util.Session.getInstance()
				.getSessionFactory();
	}

	/**
	 * Adds Admin entry into database. This method is called while deploying
	 * application in server.
	 * 
	 * @throws LNTQatarException
	 */
	public void createAdminEntry() throws LNTQatarException {
		logger.debug("Going to add admin entry");
		UserDAO dao = new UserDAO();
		User user = dao.getUser("admin");
		if (user == null) {
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setUserPwd(EncryptionEngine.toHex("admin"));
			adminUser.setFirstName("admin");
			adminUser.setLastName("admin");
			adminUser.setEmail("admin@lntqatar.com");
			adminUser.setContact("00000000");
			adminUser.setRoleid(1);
			dao.createUser(adminUser);
		}
	}

	/**
	 * Creates user session in database.
	 * 
	 * @param UserSession
	 * @return boolean - userSession is created or not
	 * @throws LNTQatarException
	 */
	public boolean createUserSession(UserSession userSession)
			throws LNTQatarException {
		Session session = null;
		Transaction txn = null;
		try {
			logger.info("Going to start transaction...");
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(userSession);
			txn.commit();
		} catch (HibernateException he) {
			logger.info("Hibernate exception while login transaction is being done...");
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(CREATE_SESSION_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			logger.info("Exception while login transaction is being done...");
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(CREATE_SESSION_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return true;
	}

	/**
	 * Accepts user name as input and returns User object with that user name
	 * 
	 * @param userName
	 * @return User object
	 * @throws LNTQatarException
	 */
	public User getUser(String userName) throws LNTQatarException {
		Session session = null;
		User userObj = null;
		try {
			session = sessionFactoryObj.openSession();
			userObj = (User) session.get(User.class, userName);
			if (userObj == null && userName != "admin") {
				throw new LNTQatarException(RETRIEVE_USER_ERROR,
						HttpStatus.NO_CONTENT.value(), "");
			}
		} catch (LNTQatarException lnte) {
			throw lnte;
		} catch (HibernateException he) {
			throw new LNTQatarException(CREATE_SESSION_UNKNOWN_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			throw new LNTQatarException(CREATE_SESSION_UNKNOWN_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return userObj;

	}

	/**
	 * Accepting the user to be created and persists the value in database
	 * 
	 * @param user
	 * @return boolean whether user is created or not
	 * @throws LNTQatarException
	 */
	public boolean createUser(User user) throws LNTQatarException {
		Session session = null;
		Transaction txn = null;
		String userName = user.getUserName();
		// Since there is no session id associated with user admin ,exempting
		// admin user from session Id check
		if (userName == "admin") {
			try {
				session = sessionFactoryObj.openSession();
				txn = session.beginTransaction();
				session.save(user);
				txn.commit();
			} catch (HibernateException he) {
				User userRetrievedObj = getUser(user.getUserName());
				if (userRetrievedObj != null) {
					if (txn != null) {
						txn.rollback();
					}
					throw new LNTQatarException(USER_NAME_ALREADY_EXISTS,
							HttpStatus.CONFLICT.value(), he.getMessage());
				} else {
					if (txn != null) {
						txn.rollback();
					}
					throw new LNTQatarException(USER_CREATE_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							he.getMessage());
				}

			} catch (Exception e) {
				if (txn != null) {
					txn.rollback();
				}
				throw new LNTQatarException(CREATE_USER_UNKNOWN_ERROR,
						HttpStatus.INTERNAL_SERVER_ERROR.value(),
						e.getMessage());
			} finally {
				session.clear();
				session.close();
			}

		} else {
			try {
				session = sessionFactoryObj.openSession();
				txn = session.beginTransaction();
				session.save(user);
				txn.commit();
			} catch (HibernateException he) {
				User userRetrievedObj = getUser(user.getUserName());
				if (userRetrievedObj != null) {
					if (txn != null) {
						txn.rollback();
					}
					throw new LNTQatarException(USER_NAME_ALREADY_EXISTS,
							HttpStatus.CONFLICT.value(), he.getMessage());
				} else {
					if (txn != null) {
						txn.rollback();
					}
					throw new LNTQatarException(USER_CREATE_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							he.getMessage());
				}

			} catch (Exception e) {
				if (txn != null) {
					txn.rollback();
				}
				throw new LNTQatarException(CREATE_USER_UNKNOWN_ERROR,
						HttpStatus.INTERNAL_SERVER_ERROR.value(),
						e.getMessage());
			} finally {
				session.clear();
				session.close();
			}
		}
		return true;
	}

	/**
	 * Validates user's session Id and see whether the user session is valid or
	 * not.
	 * 
	 * @param sessionId
	 * @return boolean whether user session is validated or not
	 * @throws LNTQatarException
	 */
	public boolean validateSession(String sessionId) throws LNTQatarException {
		logger.info("User session is going to be validated..." + sessionId);
		if (sessionId == null) {
			return false;
		}
		Session session = sessionFactoryObj.openSession();
		try {
			Query queryObj = session
					.createQuery("from UserSession where userSession = :sessionId");
			queryObj.setParameter("sessionId", sessionId);
			List<UserSession> userSessionList = queryObj.list();
			if (userSessionList.size() > 0) {
				logger.info("User session is validated...");
				SimpleDateFormat dt = new SimpleDateFormat(
						"yyyyy-mm-dd hh:mm:ss");
				UserSession user = userSessionList.get(0);
				Date currentTime = new Date();
				Date lastAccessTime = new Date(user.getLastAccessTime());
				logger.info(" Last access Time :" + dt.format(lastAccessTime));
				logger.info(" current access Time :" + dt.format(currentTime));
				long diff = currentTime.getTime() - lastAccessTime.getTime();
				long diffMinutes = diff / (60 * 1000) % 60;
				if (diffMinutes >= 30) {
					logger.info(" Session is expired ");
					logoutUser(user.getUserName(), sessionId);
					return false;
				} else {
					user.setLastAccessTime(currentTime.getTime());
					createUserSession(user);
					logger.info(" Session time is updated ");
					return true;
				}
			} else {
				logger.info("User session expired...");
				return false;
			}
		} catch (HibernateException he) {
			logger.info("Couldn't validate session");
			throw new LNTQatarException(SESSION_RETRIEVE_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), "");
		}
	}

	/**
	 * Deletes user with the userName provided
	 * 
	 * @param userName
	 * @return boolean - user is deleted or not
	 * @throws LNTQatarException
	 */
	public String logoutUser(String userName, String sessionId)
			throws LNTQatarException {
		Session session = null;
		Transaction txn = null;
		UserSession userSessionObj = null;
		boolean result;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			// user = (UserSession) session.get(UserSession.class, userName);
			userSessionObj = getUserSession(userName, sessionId);
			// result = validateSession(sessionId);
			if (userSessionObj != null) {
				session.delete(userSessionObj);
				txn.commit();
				return Constants.USER_LOGOUT_SUCCESS;
			} else {
				throw new LNTQatarException(USER_SESSION_EXPIRED,
						HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), "");
			}
		} catch (LNTQatarException ce) {
			throw ce;
		} catch (HibernateException he) {
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(DELETE_SESSION_FAILED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			logger.info("Couldn't delete user - " + e.getMessage());
			// logger.debug("Leave");
			throw new LNTQatarException(DELETE_SESSION_FAILED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	private UserSession getUserSession(String userName, String sessionId)
			throws LNTQatarException {
		// logger.debug("Enter");
		Session session = null;
		UserSession user = null;
		try {
			session = sessionFactoryObj.openSession();
			Query queryObj = session
					.createQuery("from UserSession where userSession = :userSession and userName=:userName");
			queryObj.setParameter("userSession", sessionId);
			queryObj.setParameter("userName", userName);
			List<UserSession> userSession = queryObj.list();
			if (userSession.size() > 0) {
				return userSession.get(0);
			} else {
				throw new LNTQatarException(USER_SESSION_EXPIRED,
						HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), "");
			}

		} catch (LNTQatarException ce) {
			throw ce;
		} catch (Exception e) {
			throw new LNTQatarException(DELETE_SESSION_FAILED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/**
	 * Accepts username as input and fetches Security Question
	 * 
	 * @param username
	 *            : username of user
	 * @param sessionId
	 *            :sessionId stored in session table
	 * @return Security question
	 */
	public String getSecurityQues(String userName) throws LNTQatarException {
		Session session = null;
		try {
			session = sessionFactoryObj.openSession();
			User user = (User) session.get(User.class, userName);
			if (user == null) {
				throw new LNTQatarException(RETRIEVE_USER_ERROR,
						HttpStatus.NOT_FOUND.value(), "");
			} else {
				String question = user.getsQuestion();
				return question;
			}
		} catch (LNTQatarException lnte) {
			throw lnte;
		} catch (HibernateException he) {
			throw new LNTQatarException(RETRIEVE_QUESTION_UNKNOWN_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			logger.info("Couldn't get Security Question - " + e.getMessage());
			throw new LNTQatarException(RETRIEVE_QUESTION_UNKNOWN_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/**
	 * Sets Security details in database
	 * 
	 * @param userName
	 *            : username
	 * @param userQues
	 *            : Question
	 * @param userAns
	 *            : Answer
	 * @param sessionId
	 *            : sessionId stored in Session table
	 * @return response whether Security Question and Answer got updated
	 * @throws LNTQatarException
	 */
	public String setSecurityDetails(String userName, String userQues,
			String userAns, String sessionId) throws LNTQatarException {
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			User user = (User) session.get(User.class, userName);
			boolean result = validateSession(sessionId);
			if (result) {
				user.setsQuestion(userQues);
				user.setsAnswer(userAns);
				session.update(user);
				txn.commit();
				return Constants.SECURITY_DETAILS_SAVED;
			} else {
				throw new LNTQatarException(USER_SESSION_EXPIRED,
						HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), "");
			}
		} catch (LNTQatarException lnte) {
			throw lnte;
		} catch (HibernateException he) {
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(SAVE_DETAILS_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			logger.info("Couldn't save Security Details - " + e.getMessage());
			throw new LNTQatarException(SAVE_DETAILS_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/**
	 * Validates Security answer with database
	 * 
	 * @param userName
	 *            : userName
	 * @param userAns
	 *            : Security answer
	 * @return boolean telling whether Security Answer matches with database or
	 *         not
	 * @throws LNTQatarException
	 */
	public String validateSecurityAnswer(String userName, String userAns)
			throws LNTQatarException {
		Session session = null;
		try {
			session = sessionFactoryObj.openSession();
			User user = (User) session.get(User.class, userName);
			if (user == null) {
				throw new LNTQatarException(RETRIEVE_USER_ERROR,
						HttpStatus.NOT_FOUND.value(), "");
			} else {
				if (userAns.equals(user.getsAnswer())) {
					return Constants.SECURITY_ANSWER_VALIDATED_SUCCESS;
				} else {
					throw new LNTQatarException(VALIDATE_ANSWER_FAILED,
							HttpStatus.EXPECTATION_FAILED.value(), "");
				}
			}
		} catch (LNTQatarException lnte) {
			throw lnte;
		} catch (HibernateException he) {
			throw new LNTQatarException(VALIDATE_ANSWER_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			throw new LNTQatarException(VALIDATE_ANSWER_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/**
	 * Accepts username as input and returns session Id from Session table
	 * 
	 * @param userName
	 * @return sessionId
	 * @throws LNTQatarException
	 */
	public String getSessionId(String userName) throws LNTQatarException {
		logger.debug("Enter");
		Session session = null;
		String sessionid = null;
		try {
			session = sessionFactoryObj.openSession();
			UserSession userSession = (UserSession) session.get(
					UserSession.class, userName);
			sessionid = userSession.getUserSession();
		} catch (Exception e) {
		} finally {
			session.clear();
			session.close();
		}
		return sessionid;
	}

	public List<User> getUserDetails(String userName) throws LNTQatarException {
		Session session = sessionFactoryObj.openSession();
		Transaction txn = null;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			Query queryObj = session
					.createQuery("from User where user_name= :username");
			queryObj.setParameter("username", userName);
			List<User> userList = queryObj.list();
			txn.commit();
			return userList;
		} catch (HibernateException he) {
			throw new LNTQatarException("Error while Retrieving User details ",
					HttpStatus.INTERNAL_SERVER_ERROR.value(), "");
		} finally {
			session.clear();
			session.close();
		}
	}

	public UserSession getUserName(String sessionId) throws LNTQatarException {
		Session session = sessionFactoryObj.openSession();
		Transaction txn = null;
		try {
			logger.info("Retrieving user name...");
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			Query queryObj = session
					.createQuery("from UserSession where userSession = :sessionId");
			queryObj.setParameter("sessionId", sessionId);
			List<UserSession> userSessionList = queryObj.list();
			txn.commit();
			return userSessionList.get(0);
		} catch (HibernateException he) {
			throw new LNTQatarException(SESSION_RETRIEVE_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());

		} finally {
			session.clear();
			session.close();
		}
	}
}
