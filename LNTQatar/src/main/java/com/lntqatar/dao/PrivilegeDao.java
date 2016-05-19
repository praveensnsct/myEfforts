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
import com.lntqatar.model.Privilege;
import com.lntqatar.util.Constants;

public class PrivilegeDao implements Constants {
	SessionFactory sessionFactoryObj = null;
	private static Logger logger = Logger.getLogger(PrivilegeDao.class);

	/**
	 * Constructor method. This initializes session factory.
	 */
	public PrivilegeDao() {
		sessionFactoryObj = com.lntqatar.util.Session.getInstance()
				.getSessionFactory();
	}

	/**
	 * method checks whether privilege is already added
	 * 
	 * @param privilegeRight
	 * @return True or False
	 * @throws LNTQatarException
	 */

	public boolean isPrivilegeAdded(String privilegeRight)
			throws LNTQatarException {
		logger.info("Enter inside access rights ");
		Session session = null;
		try {
			session = sessionFactoryObj.openSession();
			Query query = session
					.createQuery("from Privilege where privilege = :privilegeToAdd");
			query.setParameter("privilegeToAdd", privilegeRight);
			List<Privilege> privilegeList = query.list();
			boolean isPrivilegeAlreadyPresent = false;
			if (privilegeList != null) {
				isPrivilegeAlreadyPresent = (query.list().size() > 0 ? true
						: false);
			}
			return isPrivilegeAlreadyPresent;
		} catch (HibernateException he) {
			throw new LNTQatarException(PRIVILEGE_ALREADY_ADDED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			throw new LNTQatarException(PRIVILEGE_ALREADY_ADDED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/**
	 * Method add privilege entry in database.
	 * 
	 * @param roles
	 * @return
	 * @throws LNTQatarException
	 */
	public boolean addPrivilege(Privilege priviledgeRight)
			throws LNTQatarException {
		logger.info("Enter inside add privilege");
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			session.save(priviledgeRight);
			txn.commit();
		} catch (HibernateException he) {
			throw new LNTQatarException(PRIVILEGE_NOT_ADDED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(PRIVILEGE_NOT_ADDED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return true;

	}
}
