package com.lntqatar.manager;

import org.apache.log4j.Logger;

import com.lntqatar.dao.RolePrivilegeDAO;
import com.lntqatar.dao.PrivilegeDao;
import com.lntqatar.errorhandler.LNTQatarException;
import com.lntqatar.model.Privilege;
import com.lntqatar.dao.RolesDAO;
import com.lntqatar.dao.UserDAO;
import com.lntqatar.model.Role;

public class InitialSetupManager {

	private static Logger logger = Logger.getLogger(InitialSetupManager.class);

	/**
	 * Adds Admin entry into database. This method is called while deploying
	 * application in server.
	 * 
	 * @throws LNTQatarException
	 */
	public void createAdminEntry() throws LNTQatarException {
		logger.info("Initial setup started..");
		UserDAO dao = new UserDAO();
		dao.createAdminEntry();
	}

	/**
	 * Add roles into database.
	 * 
	 * @throws LNTQatarException
	 */
	public void createRoles() throws LNTQatarException {
		logger.info("Setting roles ...");
		RolesDAO roleDAOObj = new RolesDAO();
		String[] roles = { "admin", "countryAdmin", "projectManager",
				"clusterManager", "planningEngineer", "countryHead",
				"siteEngineer" };
		for (int i = 0; i < roles.length; i++) {
			boolean isRoleAdded = roleDAOObj.isRoleAdded(roles[i]);
			if (isRoleAdded)
				continue;
			else {
				Role role = new Role();
				role.setUserRoleName(roles[i]);
				roleDAOObj.addRole(role);
				logger.info("New role is added ... role name = "
						+ role.getUserRoleName());
			}
		}
	}

	/**
	 * Create prvileges to users
	 * 
	 * @throws LNTQatarException
	 */
	public void createPrivilege() throws LNTQatarException {
		PrivilegeDao privilegeDAOobj = new PrivilegeDao();
		String[] privilegeRight = { "createAdmin", "createCountryAdmin",
				"createProjectManager", "createClusterManager",
				"createPlanningEngineer", "createCountryHead",
				"createSiteEngineer" };
		for (int i = 0; i < privilegeRight.length; i++) {
			boolean isPrivilegeAdded = privilegeDAOobj
					.isPrivilegeAdded(privilegeRight[i]);
			if (isPrivilegeAdded) {
				continue;
			} else {
				Privilege privilege = new Privilege();
				privilege.setPrivilege(privilegeRight[i]);
				privilegeDAOobj.addPrivilege(privilege);
			}
		}
	}

	/**
	 * Adds Role-Privilege entry into database. This method is called while
	 * deploying application in server.
	 * 
	 * @throws LNTQatarException
	 */
	public void insertRolePrivilege() throws LNTQatarException {
		RolePrivilegeDAO rolePrivilegeObj = new RolePrivilegeDAO();
		rolePrivilegeObj.insertRolePrivilege();
		logger.info(" Privilege added to role...");
	}
}
