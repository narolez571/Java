/*
 * JmsClientCoreTestProcess.java created on 27 Oct 2009 12:26:41 by suggitpe for project Orca Bridge
 * 
 */
package com.ubs.orca.orcabridge.jmsclient.impl;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ubs.orca.orcabridge.jmsclient.IJmsAction;
import com.ubs.orca.orcabridge.jmsclient.JmsClientException;

import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

/**
 * Test class to test the processInTransaction functionality in the JmsClientCore
 * 
 * @author suggitpe
 * @version 1.0 27 Oct 2009
 */
public class JmsClientCoreTestProcess {

    private static final Logger LOG = LoggerFactory.getLogger( JmsClientCoreTestProcess.class );

    private IMocksControl ctrl;
    private JmsClientCore jmsClientCore;
    private Context mockInitialContext;
    private Connection mockConnection;
    private Session mockSession;
    private IJmsAction mockAction;
    private Destination mockDestination;

    private static final String DEST_NAME = "TestDestination";
    private static final String CONNFACT_NAME = "TestConnectionFactory";

    /** */
    @BeforeClass
    public static void doBeforeClass() {
        LOG.debug( "=================== " + JmsClientCoreTestProcess.class.getSimpleName() );
    }

    /**
     * @throws Exception
     */
    @Before
    public void doBefore() throws Exception {
        LOG.debug( "-------------" );
        ctrl = createControl();
        mockInitialContext = ctrl.createMock( Context.class );
        mockConnection = ctrl.createMock( Connection.class );
        mockSession = ctrl.createMock( Session.class );
        mockAction = ctrl.createMock( IJmsAction.class );
        mockDestination = ctrl.createMock( Destination.class );

        jmsClientCore = new JmsClientCore( mockInitialContext, CONNFACT_NAME, DEST_NAME );
    }

    /**
     * Test that for the normal flow we can perform the process in transaction actions correctly.
     * 
     * @throws JmsClientException
     * @throws JMSException
     */
    @Test
    public void testProcessInTransaction() throws JmsClientException, JMSException {
        jmsClientCore.setConnection( mockConnection );
        jmsClientCore.setDestination( mockDestination );

        expect( mockConnection.createSession( true, Session.CLIENT_ACKNOWLEDGE ) ).andReturn( mockSession )
            .once();
        mockConnection.start();
        expectLastCall().once();

        mockAction.actionInTransaction( mockSession, mockDestination );
        expectLastCall().once();

        mockSession.close();
        expectLastCall().once();

        ctrl.replay();
        jmsClientCore.processAction( mockAction );
        ctrl.verify();
    }

    /**
     * Tests that if we get an exception raised from the execution of the action, that we propogate the
     * correct type of exception up the stack.
     * 
     * @throws JmsClientException
     * @throws JMSException
     */
    @Test(expected = JmsClientException.class)
    public void testProcessInTransactionWithFailInAction() throws JmsClientException, JMSException {
        jmsClientCore.setConnection( mockConnection );
        jmsClientCore.setDestination( mockDestination );

        expect( mockConnection.createSession( true, Session.CLIENT_ACKNOWLEDGE ) ).andReturn( mockSession )
            .once();
        mockConnection.start();
        expectLastCall().once();

        mockAction.actionInTransaction( mockSession, mockDestination );
        expectLastCall().andThrow( new JmsClientException( "Action fail: this is all part of the test" ) );

        mockSession.close();
        expectLastCall().once();

        ctrl.replay();
        jmsClientCore.processAction( mockAction );
        ctrl.verify();
    }

    /**
     * Tests that if you try and process anything outside of an active connection, that an adequate exception
     * is thrown.
     * 
     * @throws JmsClientException
     */
    @Test(expected = JmsClientException.class)
    public void testProcessInTransactionWithNoActiveConnection() throws JmsClientException {

        ctrl.replay();
        jmsClientCore.processAction( mockAction );
        ctrl.verify();
    }

    /**
     * Tests that if an exception is thrown from the session create, that we will ensure that the correct
     * exception type is propagated up the stack.
     * 
     * @throws JmsClientException
     * @throws JMSException
     */
    @Test(expected = JmsClientException.class)
    public void testProcessInTransactionWithFailedSessionCreate() throws JmsClientException, JMSException {
        jmsClientCore.setConnection( mockConnection );
        jmsClientCore.setDestination( mockDestination );

        expect( mockConnection.createSession( true, Session.CLIENT_ACKNOWLEDGE ) ).andThrow( new JMSException( "Session create fail: this is all part of the test" ) );

        ctrl.replay();
        jmsClientCore.processAction( mockAction );
        ctrl.verify();
    }

    /**
     * Tests that when we are processing in transaction and an error occurs in the session close, that we do
     * not throw an exception but we do log it.
     * 
     * @throws JmsClientException
     * @throws JMSException
     */
    @Test
    public void testProcessInTransactionWithFailedSessionClose() throws JmsClientException, JMSException {
        expect( mockConnection.createSession( true, Session.CLIENT_ACKNOWLEDGE ) ).andReturn( mockSession )
            .once();
        mockConnection.start();
        expectLastCall().once();

        mockAction.actionInTransaction( mockSession, mockDestination );
        expectLastCall().once();

        mockSession.close();
        expectLastCall().andThrow( new JMSException( "Session close fail: this is all part of the test" ) );

        jmsClientCore.setConnection( mockConnection );
        jmsClientCore.setDestination( mockDestination );

        ctrl.replay();
        jmsClientCore.processAction( mockAction );
        ctrl.verify();
    }

}
