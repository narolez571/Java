/*
 * MessageHibernateAnnotationDaoTest.java created on 22 May 2007 06:04:41 by suggitpe for project SandBox - Hibernate
 * 
 */
package com.suggs.sandbox.hibernate.chapter2;

/**
 * TODO Write javadoc for MessageHibernateAnnotationDaoTest
 * 
 * @author suggitpe
 * @version 1.0 2 Jul 2007
 */
public class MessageHibernateAnnotationDaoTest extends AbstractMessageHibernateDaoTest
{

    /**
     * Constructs a new instance.
     */
    public MessageHibernateAnnotationDaoTest()
    {
        super();
    }

    /**
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations()
    {
        return new String[] { "xml/ut-annotation-messagetest.xml" };
    }
}
