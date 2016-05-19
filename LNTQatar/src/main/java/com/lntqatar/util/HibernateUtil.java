package com.lntqatar.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Creates hibernate session factory
 *
 * @author Harikrishnan.S
 *
 */
public class HibernateUtil {

    static Logger logger = Logger.getLogger(HibernateUtil.class);

    /**
     * Returns Hibernate Configuration object
     *
     * @return Hibernate configuration
     */
    public static Configuration getConfiguration() {
        if (logger.isTraceEnabled()) {
            logger.trace("getConfiguration enter -leave");
        }
        return new AnnotationConfiguration();
    }

    /**
     * Returns Hibernate session factory
     *
     * @param configuration
     * @return Hibernate SessionFactory
     */
    public static SessionFactory getSessionFactory(Configuration configuration) {
        if (logger.isTraceEnabled()) {
            logger.trace("getSessionFactory(Configuration) enter -leave");
        }
        configuration.configure("hibernate.cfg.xml");
        return configuration.buildSessionFactory();
    }
}