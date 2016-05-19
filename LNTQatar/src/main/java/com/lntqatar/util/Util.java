package com.lntqatar.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.lntqatar.errorhandler.LNTQatarException;

public class Util {

    /**
     * Converts Long value to date
     *
     * @param longValue
     * @return Date
     */
    public static Date convertLongToDate(Long longValue) {
        Date dateObj = new Date(longValue);
        return dateObj;
    }

    /**
     * Remove duplicate element from list
     *
     * @param list
     * @return
     */
    public static Set<String> removeDuplicateElementsFromList(List<String> list) {
        Set<String> set = new HashSet<String>(list);
        return set;
    }

    /**
     * Checks whether there are null values for the member variables in an
     * object. Receives a set of values which are member variable name which
     * needs to be excluded while checking-means these variables can be empty.
     *
     * @param obj
     * @param fieldsToNeglect
     * @return
     * @throws LNTQatarException
     */
    public static boolean isNullFieldsPresent(Object obj, Set<String> fieldsToNeglect) throws LNTQatarException {
        Set<String> nullFields = new HashSet<String>();
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(obj) == null) {
                    nullFields.add(f.getName());

                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        if (nullFields != null) {
            if (fieldsToNeglect != null) {
                nullFields.removeAll(fieldsToNeglect);
            }
            if (nullFields.size() > 0) {
                StringBuffer strBuffer = new StringBuffer();
                for (String value : nullFields) {
                    strBuffer.append(value + "\\n");
                }
                throw new LNTQatarException(strBuffer.toString() + " fields are null", HttpStatus.BAD_REQUEST.value(), "");
            } else {
                return false;
            }
        }
        return false;

    }
}
