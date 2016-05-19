package com.lntqatar.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Session {

    Logger logger = Logger.getLogger(Session.class);
    private static Session session;
    private SessionFactory sessionFactory;

    /**
     * constructor method
     */
    private Session() {
        createSessionFactory();
    }

    /**
     * Get session instance
     *
     * @return Session object
     */
    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    /**
     * Creates sessionFactory
     */
    private void createSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = HibernateUtil.getConfiguration();
            sessionFactory = HibernateUtil.getSessionFactory(configuration);
        }
    }

    /**
     * Returns Hibernate SessionFactory
     *
     * @return session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
