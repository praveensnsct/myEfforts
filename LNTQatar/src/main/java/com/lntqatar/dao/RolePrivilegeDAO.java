package com.lntqatar.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.HttpStatus;

import com.lntqatar.errorhandler.LNTQatarException;
import com.lntqatar.model.Privilege;
import com.lntqatar.model.Role;
import com.lntqatar.model.User;
import com.lntqatar.util.Constants;

public class RolePrivilegeDAO implements Constants {

	SessionFactory sessionFactoryObj = null;
	private static Logger logger = Logger.getLogger(RolePrivilegeDAO.class);

	/**
	 * Constructor method. This initializes session factory.
	 */
	public RolePrivilegeDAO() {
		sessionFactoryObj = com.lntqatar.util.Session.getInstance()
				.getSessionFactory();
	}

	/**
	 * Method maps roles and privileges and add entry in database.
	 * 
	 * @return
	 * @throws CRCException
	 */
	public void insertRolePrivilege() throws LNTQatarException {
		logger.debug("Enter inside insert Role Privilege table");
		Session session = null;
		Transaction txn = null;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			Privilege privilege1 = (Privilege) session.get(Privilege.class, 1);
			Privilege privilege2 = (Privilege) session.get(Privilege.class, 2);
			Privilege privilege3 = (Privilege) session.get(Privilege.class, 3);
			Privilege privilege4 = (Privilege) session.get(Privilege.class, 4);
			Privilege privilege5 = (Privilege) session.get(Privilege.class, 5);
			Privilege privilege6 = (Privilege) session.get(Privilege.class, 6);
			Privilege privilege7 = (Privilege) session.get(Privilege.class, 7);

			Role role1 = (Role) session.get(Role.class, 1); // admin
			Role role2 = (Role) session.get(Role.class, 2); // country admin
			Role role3 = (Role) session.get(Role.class, 3); // project manager
			Role role4 = (Role) session.get(Role.class, 4); // cluster manager
			Role role5 = (Role) session.get(Role.class, 5); // planning engineer
			Role role6 = (Role) session.get(Role.class, 6); // country head
			Role role7 = (Role) session.get(Role.class, 7); // site engineer

			role1.getPrivileges().add(privilege1);
			role1.getPrivileges().add(privilege2);
			role1.getPrivileges().add(privilege3);
			role1.getPrivileges().add(privilege4);
			role1.getPrivileges().add(privilege5);
			role1.getPrivileges().add(privilege6);
			role1.getPrivileges().add(privilege7);

			role2.getPrivileges().add(privilege2);
			role2.getPrivileges().add(privilege3);
			role2.getPrivileges().add(privilege4);
			role2.getPrivileges().add(privilege5);
			role2.getPrivileges().add(privilege6);
			role2.getPrivileges().add(privilege7);

			role3.getPrivileges().add(privilege3);
			role3.getPrivileges().add(privilege4);
			role3.getPrivileges().add(privilege5);
			role3.getPrivileges().add(privilege6);
			role3.getPrivileges().add(privilege7);

			role4.getPrivileges().add(privilege4);
			role4.getPrivileges().add(privilege5);
			role4.getPrivileges().add(privilege6);
			role4.getPrivileges().add(privilege7);

			role5.getPrivileges().add(privilege5);
			role5.getPrivileges().add(privilege6);
			role5.getPrivileges().add(privilege7);

			role6.getPrivileges().add(privilege6);
			role6.getPrivileges().add(privilege7);

			role7.getPrivileges().add(privilege7);

			session.save(role1);
			session.save(role2);
			session.save(role3);
			session.save(role4);
			session.save(role5);
			session.save(role6);
			session.save(role7);
			session.getTransaction().commit();
			session.clear();
		} catch (HibernateException he) {
			throw new LNTQatarException(USER_NOT_ASSIGNED_TO_PRIVILEGE,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(USER_NOT_ASSIGNED_TO_PRIVILEGE,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/**
	 * Validates privilege and see whether the privilege is valid or not.
	 * 
	 * @param privilege
	 * @return boolean whether privilege is validated or not
	 * @throws CRCException
	 */
	public boolean validatePrivilege(String privilege) throws LNTQatarException {
		logger.info("Privilege is going to be validated...");
		Session session = sessionFactoryObj.openSession();
		try {
			Query queryObj = session
					.createQuery("from Privilege where Privilege_rights = :privilege");
			queryObj.setParameter("privilege", privilege);
			List<Privilege> privilegeList = queryObj.list();
			if (privilegeList.size() > 0) {
				logger.info("Privilege is validated...");
				return true;
			} else {
				logger.info("No privilege exist...");
				return false;
			}
		} catch (HibernateException he) {
			logger.info("Couldn't validate privilege");
			throw new LNTQatarException(
					"Couldn't validate privilege - some Internal erorr",
					HttpStatus.INTERNAL_SERVER_ERROR.value(), "");
		}
	}

	/**
	 * Method fetches list of roles assigned to privilege
	 * 
	 * @param privilege
	 * @return
	 * @throws CRCException
	 */
	public String fetchUserToPrivilege(String privilege, String sessionId,
			String clientId) throws LNTQatarException {
		logger.debug("Enter inside insert Role Privilege table");
		Session session = null;
		Transaction txn = null;
		String userList = null;
		UserDAO userDAOObj = new UserDAO();
		boolean result;
		boolean result1;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			result1 = validatePrivilege(privilege);
			if (result1) {
				result = userDAOObj.validateSession(sessionId);
				if (result) {
					Query query = session
							.createQuery("from Privilege where Privilege_rights= :privilge");
					query.setParameter("privilge", privilege);
					List<Privilege> privilegeList = query.list();
					Privilege privilege2 = privilegeList.get(0);
					List<Role> roleNamesList = new ArrayList<Role>();
					for (Role role : privilege2.getRole()) {
						roleNamesList.add(role);
					}
					userList = getUserWithPrivilege(roleNamesList);
					session.getTransaction().commit();
					session.clear();
					return userList;
				} else {
					throw new LNTQatarException(USER_SESSION_EXPIRED,
							HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(),
							"");
				}
			} else {
				logger.info("privilege not valid");
				throw new LNTQatarException(PRIVILEGE_NOT_VALID,
						HttpStatus.BAD_REQUEST.value(), "");
			}
		} catch (HibernateException he) {
			throw new LNTQatarException(ROLES_NOT_FETCHED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} finally {
			session.clear();
			session.close();
		}

	}

	/**
	 * Sets privilege to role
	 * 
	 * @param privilege
	 * @param role
	 * @param sessionId
	 * @param flag
	 * @return status
	 * @throws CRCException
	 */
	public Boolean setPrivilege(String privilege, String role,
			String sessionId, Boolean flag) throws LNTQatarException {
		logger.debug("Enter inside set Privilege");
		Session session = null;
		Transaction txn = null;
		UserDAO userDAOObj = new UserDAO();
		boolean result;
		boolean result1;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			result1 = validatePrivilege(privilege);
			if (result1) {
				result = userDAOObj.validateSession(sessionId);
				if (result) {
					Query query = session
							.createQuery("from Role where user_roleName= :role");
					query.setParameter("role", role);
					List<Role> roleList = query.list();
					int roleId = roleList.get(0).getUserRoleId();
					Role role1 = (Role) session.get(Role.class, roleId);
					Query query1 = session
							.createQuery("from Privilege where Privilege_rights= :privilge");
					query1.setParameter("privilge", privilege);
					List<Privilege> privilegeList = query1.list();
					int privilegeId = privilegeList.get(0).getId();
					Privilege privilege1 = (Privilege) session.get(
							Privilege.class, privilegeId);
					if (flag) {
						role1.getPrivileges().add(privilege1);
						session.save(role1);
					} else {
						role1.getPrivileges().remove(privilege1);
						session.save(role1);
					}
					session.getTransaction().commit();
					session.clear();
					return true;
				} else {
					throw new LNTQatarException(USER_SESSION_EXPIRED,
							HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(),
							"");
				}
			} else {
				logger.info("privilege not valid");
				throw new LNTQatarException(PRIVILEGE_NOT_VALID,
						HttpStatus.BAD_REQUEST.value(), "");
			}
		} catch (HibernateException he) {
			throw new LNTQatarException(SETPRIVILEGE_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(SETPRIVILEGE_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}

	}

	/**
	 * Method fetches list of roles assigned to privilege
	 * 
	 * @param privilege
	 * @return
	 * @throws CRCException
	 */
	public String getPrivilege(int id) throws LNTQatarException {
		logger.debug("Enter inside insert Role Privilege table");
		Session session = null;
		Transaction txn = null;
		String privileges = null;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			Query query = session
					.createQuery("from Role where user_roleId= :roleId");
			query.setParameter("roleId", id);
			List<Role> roleList = query.list();
			Role role = roleList.get(0);
			List<String> privilegeList = new ArrayList<String>();
			for (Privilege privilege : role.getPrivileges()) {
				privilegeList.add(privilege.getPrivilege());
				privileges = privilegeList.toString();
			}
			session.getTransaction().commit();
			session.clear();
			return privileges;
		} catch (HibernateException he) {
			throw new LNTQatarException(PRIVILEGE_NOT_FETCHED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(PRIVILEGE_NOT_FETCHED,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/**
	 * fetches list of user assigned to privilege
	 * 
	 * @param roleNamesList
	 * @return
	 * @throws CRCException
	 */
	private String getUserWithPrivilege(List<Role> roleNamesList)
			throws LNTQatarException {
		logger.debug("Enter inside insert getUser with Privilege table");
		Session session = null;
		Transaction txn = null;
		List<String> userList = new ArrayList<String>();
		String usersList = null;
		List<User> users = null;
		try {
			session = sessionFactoryObj.openSession();
			txn = session.beginTransaction();
			for (int i = 0; i < roleNamesList.size(); i++) {

				Criteria criteria = session.createCriteria(User.class);
				criteria.add(Restrictions.eq("userRoleId", roleNamesList.get(i)));
				users = criteria.list();
				if (users.size() <= 0)
					continue;
				for (User user : users) {
					userList.add(user.getUserName());
				}
			}
			usersList = userList.toString();
			session.getTransaction().commit();
			session.clear();
			return usersList;
		} catch (HibernateException he) {
			throw new LNTQatarException(USER_NOT_ASSIGNED_TO_PRIVILEGE,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), he.getMessage());
		} catch (Exception e) {
			if (txn != null) {
				txn.rollback();
			}
			throw new LNTQatarException(USER_NOT_ASSIGNED_TO_PRIVILEGE,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		} finally {
			session.clear();
			session.close();
		}

	}

}