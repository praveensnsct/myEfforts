package com.lntqatar.parser;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;

import com.lntqatar.controller.LoginController;
import com.lntqatar.errorhandler.LNTQatarException;

/**
 * JSON Parser Utility
 */
public class JSONParser {
	private static Logger logger = Logger.getLogger(LoginController.class);

	/**
	 * To JSON
	 * 
	 * @param respObj
	 * @return JSON String
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String toJSON(Object respObj) throws JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Writer strWriter = new StringWriter();
		mapper.writeValue(strWriter, respObj);
		return strWriter.toString();
	}

	/**
	 * From JSON
	 * 
	 * @param jsonData
	 * @param clazz
	 * @return
	 * @throws LNTQatarException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T fromJSON(String jsonData, Class<T> clazz)
			throws LNTQatarException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.info("jsonData--" + jsonData);
			return mapper.readValue(jsonData, clazz);
		} catch (JsonParseException e) {
			logger.info("JSON Parse Exception");
			throw new LNTQatarException("JSON Parse Exception",
					HttpStatus.BAD_REQUEST.value(),
					"JSON Parse Exception while converting to Object "
							+ e.getMessage());
		} catch (JsonMappingException e) {
			logger.info("JsonMappingException");
			throw new LNTQatarException("JsonMappingException",
					HttpStatus.BAD_REQUEST.value(),
					"JSON Parse Exception while converting to Object "
							+ e.getMessage());
		} catch (IOException e) {
			logger.info("IOException");
			throw new LNTQatarException("IOException while JSON Parsing",
					HttpStatus.BAD_REQUEST.value(),
					"JSON Parse Exception while converting to Object "
							+ e.getMessage());
		}
	}
}
