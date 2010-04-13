/*
 * AbstractHibernateSpringTest.java created on 19 Apr 2007 18:27:36 by suggitpe for project SandBox - Hibernate
 * 
 */
package org.suggs.sandbox.hibernate.support;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Test object
 * 
 * @author suggitpe
 * @version 1.0 2 Jul 2007
 */
public abstract class AbstractHibernateSpringTest extends AbstractDependencyInjectionSpringContextTests {

    private static final Log LOG = LogFactory.getLog( AbstractHibernateSpringTest.class );
    private SessionFactory sessionFactory_;

    /**
     * @see org.springframework.test.AbstractDependencyInjectionSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {}

    /**
     * Constructs a new instance.
     */
    public AbstractHibernateSpringTest() {
        super();
    }

    /**
     * This will clean up all of the tables that
     * 
     * @param aClassList
     */
    @SuppressWarnings("unchecked")
    private void cleanTestTables( Class[] aClassList ) {
        Session s = getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        for ( Class c : aClassList ) {
            LOG.debug( "Cleaning table [" + c.getSimpleName() + "]" );
            Criteria cr = s.createCriteria( c );
            List l = cr.list();
            for ( Iterator iter = l.iterator(); iter.hasNext(); ) {
                s.delete( iter.next() );
            }
        }

        t.commit();
        s.close();
    }

    /**
     * This is the core test impl that will allow us to wrap up the session and transaction to a single impl
     * 
     * @param testMetaData
     *            the test metadata impl including the name and core test impl that will be called by this
     *            method.
     */
    protected void runTest( TestCallback testMetaData ) {

        String testName = testMetaData.getTestName();
        LOG.debug( "---------- " + testName + " cleaning class tables for tests" );
        cleanTestTables( testMetaData.getClassesForCleaning() );

        LOG.debug( "---------- " + testName + " preTest" );
        Session sess = sessionFactory_.openSession();
        Transaction tran = sess.beginTransaction();

        testMetaData.preTestSetup( sess );

        sess.flush();

        LOG.info( "---------- " + testName + " start" );
        testMetaData.runTest( sess );

        tran.commit();
        sess.close();
        LOG.info( "---------- " + testName + " end" );
        LOG.info( "=============================================================" );
    }

    /**
     * This is a simple interface to allow you to drop in a test to a defined hibernate transaction.
     * 
     * @author suggitpe
     * @version 1.0 19 Apr 2007
     */
    protected interface TestCallback {

        /**
         * This method is used to get a collection of classes so that we can clean down any tables used in the
         * test.
         * 
         * @return an array of classes
         */
        @SuppressWarnings("unchecked")
        Class[] getClassesForCleaning();

        /**
         * This is called by a test execution so that they can set up any pre test activities such as
         * persisting an object to the database
         * 
         * @param aSession
         *            a session to the database
         */
        void preTestSetup( Session aSession );

        /**
         * This is used by the defining impl to allow the test name to be shown
         * 
         * @return the test name
         */
        String getTestName();

        /**
         * This is the core test interface. Here we pass in a session object so that we can get access to the
         * persistent layer.
         * 
         * @param aSession
         */
        void runTest( Session aSession );

    }

    /**
     * getter for the session factory
     * 
     * @return the session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory_;
    }

    /**
     * Setter for the session factory
     * 
     * @param aFactory
     *            the factor to set
     */
    public void setSessionFactory( SessionFactory aFactory ) {
        sessionFactory_ = aFactory;
    }

}
