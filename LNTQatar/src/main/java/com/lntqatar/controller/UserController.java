package com.lntqatar.controller;

import java.io.IOException;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lntqatar.errorhandler.HandleLNTQatarException;
import com.lntqatar.errorhandler.LNTQatarException;
import com.lntqatar.handler.UserHandler;
import com.lntqatar.parser.JSONParser;
import com.lntqatar.pojo.UserDetailsWrapper;
import com.lntqatar.util.Constants;
import com.lntqatar.util.URLs;

@Controller
public class UserController extends HandleLNTQatarException implements
		Constants, URLs {
	private static Logger logger = Logger.getLogger(UserController.class);
	private UserHandler userHanlderObj = new UserHandler();

	@RequestMapping(value = userCreate, method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody
	Response createUser(@RequestBody String userDetails,
			@RequestParam("sessionId") String sessionId)
			throws LNTQatarException, JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("Entering userCreation request...");	
		userDetails = java.net.URLDecoder.decode(userDetails, "UTF-8");
		UserDetailsWrapper userCreationDetails = JSONParser.fromJSON(
				userDetails, UserDetailsWrapper.class);
		Map<String, String> userCreationResponse = userHanlderObj.createUser(userCreationDetails, sessionId);
		return Response.ok().entity(userCreationResponse).build();			
	}
}
