package com.lntqatar.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;

import com.lntqatar.errorhandler.LNTQatarException;
import com.lntqatar.model.Role;
import com.lntqatar.util.Constants;

public class RolesDAO implements Constants {
	SessionFactory sessionFactoryObj = null;
	private static Logger logger = Logger.getLogger(RolesDAO.class);

	/**
	 * Constructor method. This initializes session factory.
	 */
	public RolesDAO() {
		sessionFactoryObj = com.lntqatar.util.Session.getInstance()
				.getSessionFactory();
	}

	/**
	 * method checks whether Role is already added
	 * 
	 * @param roles
	 * @return True or False
	 * @throws LNTQatarException
	 */
	public boolean isRoleAdded(String roles) throws LNTQatarException {
		logger.info("Checking if the role is added...");
		Session session = null;
		try {
			session = sessionFactoryObj.openSession();
			Query query = session
					.createQuery("from Role where userRoleName = :roleToAdd");
			query.setParameter("roleToAdd", roles);
			List<Role> roleList = query.list();
			boolean isRolePresent = false;
			if (roleList != null) {
				isRolePresent = (query.list().size() > 0 ? true : false);
			}
			return isRolePresent;
		} catch (HibernateException he) {
			throw new LNTQatarException(ROLE_ALREADY_ADDED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			throw new LNTQatarException(ROLE_ALREADY_ADDED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/**
	 * Method add roles entry in database.
	 * 
	 * @param roles
	 * @return
	 * @throws LNTQatarException
	 */
	public boolean addRole(Role roles) throws LNTQatarException {
		logger.info("Entering Privilege table...");
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			session.save(roles);
			txn.commit();
		} catch (HibernateException he) {
			throw new LNTQatarException(ROLE_NOT_ADDED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(ROLE_NOT_ADDED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return true;

	}

	/**
	 * Method returns Role object
	 * 
	 * @param roleId
	 * @return roleObj
	 * @throws LNTQatarException
	 */
	public Role getRole(int roleId) throws LNTQatarException {
		logger.debug("Retrieving roles...");
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			Role roleObj = (Role) session.get(Role.class, roleId);
			txn.commit();
			return roleObj;
		} catch (HibernateException he) {
			throw new LNTQatarException(ROLE_NOT_ADDED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(ROLE_NOT_ADDED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

}
