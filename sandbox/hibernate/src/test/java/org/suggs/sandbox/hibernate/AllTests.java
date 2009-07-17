/*
 * AllTetds.java created on 13 Apr 2007 06:21:23 by suggitpe for project SandBox - Hibernate
 * 
 */
package org.suggs.sandbox.hibernate;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test suite for Hibernate sandbox
 * 
 * @author suggitpe
 * @version 1.0 13 Apr 2007
 */
public class AllTests
{

    /**
     * @return test suite
     */
    public static Test suite()
    {
        TestSuite s = new TestSuite();
        s.addTestSuite( org.suggs.sandbox.hibernate.chapter2.MessageHibernateHbmDaoTest.class );
        s.addTestSuite( org.suggs.sandbox.hibernate.chapter2.MessageHibernateAnnotationDaoTest.class );
        s.addTestSuite( org.suggs.sandbox.hibernate.caveatEmptor.CaveatEmptorHbmObjectTest.class );
        s.addTestSuite( org.suggs.sandbox.hibernate.caveatEmptor.CaveatEmptorHbmRelationshipTest.class );
        s.addTestSuite( org.suggs.sandbox.hibernate.caveatEmptor.CaveatEmptorAnnotationObjectTest.class );
        s.addTestSuite( org.suggs.sandbox.hibernate.caveatEmptor.CaveatEmptorAnnotationRelationshipTest.class );
        return s;
    }
}
