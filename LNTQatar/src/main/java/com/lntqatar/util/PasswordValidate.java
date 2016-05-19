package com.lntqatar.util;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.lntqatar.crypto.EncryptionEngine;
import com.lntqatar.errorhandler.LNTQatarException;
import com.lntqatar.errorhandler.ErrorCodes;

public class PasswordValidate implements Constants, ErrorCodes {

	private static Logger logger = Logger.getLogger(PasswordValidate.class);

	public boolean checkpassword(String userPwd, String userName)
			throws LNTQatarException {
		userPwd = EncryptionEngine.fromHex(userPwd);
		logger.info("Validating password...");
		if (userPwd.length() > 20 || userPwd.length() < 6) {
			logger.info("Password should be less than 20 and more than 6 characters in length");
			System.out.println("Exception caught");
			throw new LNTQatarException(PASSWORD_LENGTH_INVALID,
					HttpStatus.BAD_REQUEST.value(),
					PASSWORD_LENGTH_INVALID_MESSAGE);
		}
		if (!userPwd.matches(".*(?=.*[a-zA-Z])(?=.*[0-9]).*")) {
			logger.info("Password should be alphanumeric");
			throw new LNTQatarException(PASSWORD_ALPHANUMERIC_REQUIRED,
					HttpStatus.BAD_REQUEST.value(),
					PASSWORD_ALPHANUMERIC_REQUIRED_MESSAGE);
		}
		if (userPwd.matches(".*\\s+.*")) {
			logger.info("Password should not contain space");
			throw new LNTQatarException(SPACE_INVALID,
					HttpStatus.BAD_REQUEST.value(), SPACE_INVALID_MESSAGE);
		}
		String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
		if (!userPwd.matches(specialChars)) {
			logger.info("Password should contain atleast one special character");
			throw new LNTQatarException(PASSWORD_SPECIAL_CHARACTER,
					HttpStatus.BAD_REQUEST.value(),
					PASSWORD_SPECIAL_CHARACTER_MESSAGE);
		}
		if (userPwd.substring(0, 2).equalsIgnoreCase(userName.substring(0, 2))) {
			logger.info("Password should not contain Username characters");
			throw new LNTQatarException(USERNAME_MATCH,
					HttpStatus.BAD_REQUEST.value(), USERNAME_MATCH_MESSAGE);
		}
		return true;

	}
}
