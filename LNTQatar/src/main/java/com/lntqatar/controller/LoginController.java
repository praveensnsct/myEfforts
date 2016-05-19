package com.lntqatar.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lntqatar.parser.JSONParser;
import com.lntqatar.errorhandler.HandleLNTQatarException;
import com.lntqatar.errorhandler.LNTQatarException;
import com.lntqatar.handler.UserHandler;
import com.lntqatar.pojo.UserLoginWrapper;
import com.lntqatar.util.Constants;
import com.lntqatar.util.URLs;
import java.util.Map;

@Controller
public class LoginController extends HandleLNTQatarException implements
		Constants, URLs {

	private static Logger logger = Logger.getLogger(LoginController.class);
	private UserHandler userHanlderObj = new UserHandler();

	@RequestMapping(value = login, method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Response loginUser(@RequestBody String userDetails)
			throws LNTQatarException, JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("Entering login request...");
		userDetails = java.net.URLDecoder.decode(userDetails, "UTF-8");
		UserLoginWrapper loginDetails = JSONParser.fromJSON(userDetails,
				UserLoginWrapper.class);
		Map<String, String> loginResponse = userHanlderObj
				.loginUser(loginDetails);
		return Response.ok().entity(loginResponse).build();
	}

	/**
	 * logout user on request
	 * 
	 * @param request
	 *            : URL containing username and sessionID
	 * @throws LNTQatarException
	 */
	@RequestMapping(value = logout, method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public String logoutUser(HttpServletRequest request,
			HttpServletResponse response) throws LNTQatarException {
		String result;
		String userName = request.getParameter("username");
		String sessionId = request.getParameter("sessionId");
		result = userHanlderObj.logoutUser(userName, sessionId);
		return result;
	}

	/**
	 * Checks is the session is live for a given user
	 * 
	 * @param request
	 *            : URL containing username and sessionID
	 * @return : boolean response (true - exists and false - destroyed)
	 * @throws LNTQatarException
	 */
	// @RequestMapping(value = session, method = RequestMethod.POST)
	// @ResponseBody
	// @ResponseStatus(value = HttpStatus.OK)
	// public boolean isSessionExists(HttpServletRequest request,
	// HttpServletResponse response) throws LNTQatarException {
	// String userName = request.getParameter("username");
	// String sessionId = request.getParameter("sessionId");
	// return userHanlderObj.isSessionExists(userName, sessionId);
	// }
}
