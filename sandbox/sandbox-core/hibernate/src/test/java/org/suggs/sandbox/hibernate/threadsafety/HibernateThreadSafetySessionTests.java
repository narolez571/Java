/*
 * HibernateThreadSafetySessionTests.java created on 29 Dec 2010 13:45:06 by suggitpe for project sandbox-hibernate
 * 
 */
package org.suggs.sandbox.hibernate.threadsafety;

import org.suggs.sandbox.hibernate.support.ReallyBasicEntity;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 * Tests that Hibernate is a thread safe implementation.
 * 
 * @author suggitpe
 * @version 1.0 29 Dec 2010
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:xml/ut-basicconnection.xml" })
public class HibernateThreadSafetySessionTests {

    private static final Logger LOG = LoggerFactory.getLogger( HibernateThreadSafetySessionTests.class );
    private static final long SLEEP_TIME = 2000;

    @Resource(name = "sessionFactory")
    protected SessionFactory sessionfactory;

    private Long objectId;
    private ReallyBasicEntity reallyBasicEntity;

    @Before
    public void onSetup() {
        LOG.debug( "-------------------------- setup start" );
        objectId = null;
        ReallyBasicEntity entity = new ReallyBasicEntity( "This is a string", 25 );
        Session session = sessionfactory.openSession();
        try {
            Transaction trans = session.beginTransaction();
            try {
                session.save( entity );
                objectId = new Long( entity.getId().longValue() );
                LOG.debug( "Set up entity [" + entity + "]" );
            }
            catch ( Exception exception ) {
                LOG.error( "Error in transaction", exception );
                if ( trans.isActive() ) {
                    trans.rollback();
                }
            }
            finally {
                if ( trans.isActive() ) {
                    trans.commit();
                }
            }
        }
        catch ( HibernateException sessionException ) {
            LOG.error( "problem trying to open Transaction", sessionException );
        }
        finally {
            session.clear();
            session.close();
        }
        LOG.debug( "-------------------------- setup end" );
    }

    @Test
    public void hibernateOnlyUpdatesChangedFields() {

        Session session = sessionfactory.openSession();
        try {
            Transaction trans = session.beginTransaction();
            try {
                if ( objectId == null ) {
                    throw new IllegalStateException();
                }
                ReallyBasicEntity entity = (ReallyBasicEntity) session.get( ReallyBasicEntity.class, objectId );
                entity.setStringField( "rah rah rah" );
                session.flush();
            }
            catch ( Exception inner ) {
                LOG.debug( "rollback !!!!" );
                if ( trans.isActive() ) {
                    trans.rollback();
                }
            }
            finally {
                LOG.debug( "commit !!!!" );
                if ( trans.isActive() ) {
                    trans.commit();
                }
            }
        }
        catch ( Exception outer ) {
            LOG.error( "Error in the transaction creation", outer );
        }
        finally {
            session.close();
        }
    }

    @Test
    public void hibernateThrowsExcpetionWhenObjectUpdatedInOtherThreadsSession() throws InterruptedException {

        DbReaderThread readerThread = new DbReaderThread();
        LOG.debug( "Starting reader thread" );
        readerThread.start();

        Thread.sleep( 500 );
        if ( reallyBasicEntity == null ) {
            throw new IllegalStateException( "Test in wrong state ... waiting for the local object to be updated by the thread" );
        }

        Session session = sessionfactory.openSession();
        try {
            Transaction trans = session.beginTransaction();
            try {
                LOG.debug( "Updating the entity stord in the local thread" );
                reallyBasicEntity.setStringField( "doo be doo" );

                while ( readerThread.isAlive() ) {
                    Thread.sleep( 500 );
                }
                session.flush();

            }
            catch ( Exception inner ) {
                LOG.error( "exception caught with the transaction", inner );
                if ( trans.isActive() ) {
                    trans.rollback();
                }
            }
            finally {
                LOG.debug( "committing" );
                if ( trans.isActive() ) {
                    trans.commit();
                }
            }
        }
        catch ( Exception excep ) {
            LOG.error( "session level exception", excep );
        }
        finally {
            session.close();
        }

        LOG.debug( "########### ALL THREADS COMPLETE" );
    }

    class DbReaderThread extends Thread {

        @Override
        public void run() {
            Session session = sessionfactory.openSession();
            try {
                Transaction trans = session.beginTransaction();
                try {
                    ReallyBasicEntity entity = (ReallyBasicEntity) session.get( ReallyBasicEntity.class,
                                                                                objectId );
                    reallyBasicEntity = entity;
                    LOG.debug( "Read back in object [" + reallyBasicEntity + "]" );
                    try {
                        Thread.sleep( SLEEP_TIME );
                    }
                    catch ( InterruptedException ie ) {
                        // nada
                    }
                    LOG.debug( "Object shoul have been updated by other thread [" + reallyBasicEntity + "]" );
                }
                catch ( Exception ex ) {
                    LOG.debug( "failed to do anything with the database", ex );
                    if ( trans.isActive() ) {
                        trans.rollback();
                    }
                }
                finally {
                    if ( trans.isActive() ) {
                        LOG.debug( "Committing the open transaction" );
                        trans.commit();
                        LOG.debug( "Post Commit version of the local object [" + reallyBasicEntity + "]" );
                    }
                }
                LOG.debug( ">>>>>>>> Thread terminating" );
            }
            catch ( HibernateException hibException ) {
                LOG.error( "Failed to open transaction", hibException );
            }
            finally {
                session.close();
            }
        }
    }

}
