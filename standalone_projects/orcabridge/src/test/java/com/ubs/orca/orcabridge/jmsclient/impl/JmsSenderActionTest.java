/*
 * JmsSenderActionTest.java created on 14 Oct 2009 07:05:15 by suggitpe for project Orca Bridge
 * 
 */
package com.ubs.orca.orcabridge.jmsclient.impl;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ubs.orca.orcabridge.IMessageFacade;
import com.ubs.orca.orcabridge.OrcaBridgeMessageConversionException;
import com.ubs.orca.orcabridge.jmsclient.IJmsAction;
import com.ubs.orca.orcabridge.jmsclient.JmsClientException;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

/**
 * Test suite for the JMS sender client.
 * 
 * @author suggitpe
 * @version 1.0 14 Oct 2009
 */
public class JmsSenderActionTest
{

    private static final Log LOG = LogFactory.getLog( JmsSenderActionTest.class );

    private IMocksControl ctrl_;
    private IJmsAction jmsSenderAction_;
    private IMessageFacade mockMessageFacade_;
    private Destination mockDestination_;
    private Session mockSession_;
    private MessageProducer mockMessageProducer_;
    private Message mockJmsMessage_;

    /** */
    @BeforeClass
    public static void doBeforeClass()
    {
        LOG.debug( "=================== " + JmsSenderActionTest.class.getSimpleName() );
    }

    /** */
    @Before
    public void doBefore()
    {
        LOG.debug( "-------------" );
        ctrl_ = EasyMock.createControl();
        mockMessageFacade_ = ctrl_.createMock( IMessageFacade.class );
        mockDestination_ = ctrl_.createMock( Destination.class );
        mockSession_ = ctrl_.createMock( Session.class );
        mockJmsMessage_ = ctrl_.createMock( Message.class );
        mockMessageProducer_ = ctrl_.createMock( MessageProducer.class );

        jmsSenderAction_ = new JmsSenderAction( mockMessageFacade_ );
    }

    /**
     * Test that for the normal case, a message is sent correctly and
     * no exceptions are raised
     * 
     * @throws JmsClientException
     * @throws JMSException
     * @throws OrcaBridgeMessageConversionException
     */
    @Test
    public void testNormalSend() throws JmsClientException, JMSException,
                    OrcaBridgeMessageConversionException
    {
        expect( mockSession_.createProducer( mockDestination_ ) ).andReturn( mockMessageProducer_ )
            .once();
        expect( mockMessageFacade_.buildJmsMessage( mockSession_ ) ).andReturn( mockJmsMessage_ )
            .once();

        mockMessageProducer_.send( mockJmsMessage_ );
        expectLastCall().once();

        mockMessageProducer_.close();
        expectLastCall().once();

        mockSession_.commit();
        expectLastCall().once();

        ctrl_.replay();
        jmsSenderAction_.action( mockSession_, mockDestination_ );
        ctrl_.verify();
    }

    /**
     * Tests that when we fail to create a message producer we get an
     * exception from the top of the stack
     * 
     * @throws JmsClientException
     * @throws JMSException
     */
    @Test(expected = JmsClientException.class)
    public void testSendWithFailInProducerCreate() throws JmsClientException, JMSException
    {
        expect( mockSession_.createProducer( mockDestination_ ) ).andThrow( new JMSException( "Producer create fail: this is all part of the test" ) );

        ctrl_.replay();
        jmsSenderAction_.action( mockSession_, mockDestination_ );
        ctrl_.verify();
    }

    /**
     * Tests that when we have a message conversion issue that we
     * correctly propogate the right type of exception up the stack
     * 
     * @throws JmsClientException
     * @throws JMSException
     * @throws OrcaBridgeMessageConversionException
     */
    @Test(expected = JmsClientException.class)
    public void testSendWithFailInJmsMessageBuild() throws JmsClientException, JMSException,
                    OrcaBridgeMessageConversionException
    {
        expect( mockSession_.createProducer( mockDestination_ ) ).andReturn( mockMessageProducer_ )
            .once();
        expect( mockMessageFacade_.buildJmsMessage( mockSession_ ) ).andThrow( new OrcaBridgeMessageConversionException( "JMS Message build fail: this is all part of the test" ) );

        mockMessageProducer_.close();
        expectLastCall().once();

        ctrl_.replay();
        jmsSenderAction_.action( mockSession_, mockDestination_ );
        ctrl_.verify();

    }

    /**
     * Tests that when there is a failure in the send process, that
     * the correct exception type is passed up the stack.
     * 
     * @throws JmsClientException
     * @throws JMSException
     * @throws OrcaBridgeMessageConversionException
     */
    @Test(expected = JmsClientException.class)
    public void testSendWithFailInSend() throws JmsClientException, JMSException,
                    OrcaBridgeMessageConversionException
    {
        expect( mockSession_.createProducer( mockDestination_ ) ).andReturn( mockMessageProducer_ )
            .once();
        expect( mockMessageFacade_.buildJmsMessage( mockSession_ ) ).andReturn( mockJmsMessage_ )
            .once();

        mockMessageProducer_.send( mockJmsMessage_ );
        expectLastCall().andThrow( new JMSException( "Fail in JMS send: this is all part of the test" ) );

        mockMessageProducer_.close();
        expectLastCall().once();

        ctrl_.replay();
        jmsSenderAction_.action( mockSession_, mockDestination_ );
        ctrl_.verify();
    }

    /**
     * Tests that if there is a failure in the closing of the producer
     * then no exceptions are thrown.
     * 
     * @throws JmsClientException
     * @throws JMSException
     * @throws OrcaBridgeMessageConversionException
     */
    @Test
    public void testSendWithFailInProducerClose() throws JmsClientException, JMSException,
                    OrcaBridgeMessageConversionException
    {

        expect( mockSession_.createProducer( mockDestination_ ) ).andReturn( mockMessageProducer_ )
            .once();
        expect( mockMessageFacade_.buildJmsMessage( mockSession_ ) ).andReturn( mockJmsMessage_ )
            .once();

        mockMessageProducer_.send( mockJmsMessage_ );
        expectLastCall();

        mockMessageProducer_.close();
        expectLastCall().andThrow( new JMSException( "Producer Close fail: this is all part of the:w"
                                                     + " test" ) );

        mockSession_.commit();
        expectLastCall().once();

        ctrl_.replay();
        jmsSenderAction_.action( mockSession_, mockDestination_ );
        ctrl_.verify();
    }

    /**
     * Tests that if we get an exception during the commit phase, that
     * the correct exception is raise up the stack.
     * 
     * @throws JmsClientException
     * @throws JMSException
     * @throws OrcaBridgeMessageConversionException
     */
    @Test(expected = JmsClientException.class)
    public void testSendWithFailInCommit() throws JmsClientException, JMSException,
                    OrcaBridgeMessageConversionException
    {

        expect( mockSession_.createProducer( mockDestination_ ) ).andReturn( mockMessageProducer_ )
            .once();
        expect( mockMessageFacade_.buildJmsMessage( mockSession_ ) ).andReturn( mockJmsMessage_ )
            .once();

        mockMessageProducer_.send( mockJmsMessage_ );
        expectLastCall();

        mockMessageProducer_.close();
        expectLastCall().once();

        mockSession_.commit();
        expectLastCall().andThrow( new JMSException( "" ) );

        ctrl_.replay();
        jmsSenderAction_.action( mockSession_, mockDestination_ );
        ctrl_.verify();
    }

}
