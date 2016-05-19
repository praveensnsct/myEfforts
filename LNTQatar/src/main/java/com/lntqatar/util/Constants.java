package com.lntqatar.util;

public interface Constants {
	public String SUCCESS_STATUS = "success";
	public String INVALID_LOGIN = "Invalid login credentials";
	public String CREATE_SESSION_ERROR = "Couldn't create user session";
	public String SESSION_RETRIEVE_ERROR ="Error while Retrieving Session details";
	public String CREATE_SESSION_UNKNOWN_ERROR = "Couldn't create user session - Unknown Error";
	public String USER_SESSION_EXPIRED = "User session expired";
	public String USER_LOGOUT_SUCCESS = "User logged out successfully";
	public String DELETE_SESSION_FAILED = "Unsuccesful Logout";
	public String RETRIEVE_QUESTION_UNKNOWN_ERROR = "Couldn't retrieve secret Question";
	public String SECURITY_DETAILS_SAVED = SUCCESS_STATUS
			+ "& message=Saved data";
	public String SAVE_DETAILS_ERROR = "Couldn't save secret details";
	public String SECURITY_ANSWER_VALIDATED_SUCCESS = "Validated answer";
	public String VALIDATE_ANSWER_FAILED = "Security Answer entered mismatches";
	public String VALIDATE_ANSWER_ERROR = "Couldn't validate secret answer";
	public String ROLE_ALREADY_ADDED = "Role already added";
	public String ROLE_NOT_ADDED = "Role not added";
	public String PRIVILEGE_ALREADY_ADDED = "Privilege already added";
	public String PRIVILEGE_NOT_ADDED = "Privilege not added";
	public String USER_NOT_ASSIGNED_TO_PRIVILEGE = "Role-Privilege not mapped";
	public String PRIVILEGE_NOT_VALID = "Privilege not valid";
	public String SETPRIVILEGE_ERROR = "Unable to set privilege";
	public String PRIVILEGE_NOT_FETCHED = "Unable to fetch privilege";
	public String ROLES_NOT_FETCHED = "Roles not fetched";

	public String RETRIEVE_USER_ERROR = "User doesn't exists";
	public String USER_CREATE_SUCCESS = "User created succesfully";
	public String USER_NAME_ALREADY_EXISTS = "Couldn't create user - Username already exists";
	public String USER_CREATE_ERROR = "User Creation Failure";
	public String USER_NOT_AUTHORIZED = "User not authorized to create selected role";
	public String CREATE_USER_UNKNOWN_ERROR = "Couldn't create user-Unknown Exception";
	public String PASSWORD_LENGTH_INVALID = "Password Length Invalid";
	public String PASSWORD_ALPHANUMERIC_REQUIRED = "Password Aphanumeric Required";
	public String SPACE_INVALID = "Space not Allowed";
	public String PASSWORD_SPECIAL_CHARACTER = "Special Character Required";
	public String USERNAME_MATCH = "Username matches with Password";
	public String PASSWORD_LENGTH_INVALID_MESSAGE = "Password should be less than 12 and more than 8 characters in length";
	public String PASSWORD_ALPHANUMERIC_REQUIRED_MESSAGE = "Password should be Alphanumeric";
	public String SPACE_INVALID_MESSAGE = "Password should not contain space";
	public String PASSWORD_SPECIAL_CHARACTER_MESSAGE = "Password should contain atleast one special character";
	public String USERNAME_MATCH_MESSAGE = "Password should not contain Username characters";
}
