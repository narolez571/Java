/*
 * OrcaClientIntegrationTest.java created on 4 Nov 2009 18:48:31 by suggitpe for project Orca Bridge
 * 
 */
package com.ubs.orca.orcabridge;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ubs.orca.client.api.IAttributesConversationMessage;
import com.ubs.orca.client.api.IOrcaClient;
import com.ubs.orca.client.api.IOrcaSinkSingleMsgCallback;
import com.ubs.orca.client.api.ITextConversationMessage;
import com.ubs.orca.client.api.OrcaException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Integration test that will instantiate an Orca Client through
 * spring injection.
 * 
 * @author suggitpe
 * @version 1.0 4 Nov 2009
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:xml/it-orca-client-test.xml" })
public class OrcaClientIntegrationTest
{

    private static final Log LOG = LogFactory.getLog( OrcaClientIntegrationTest.class );

    @Resource(name = "orcaClient")
    private IOrcaClient orcaClient_;

    /** */
    @BeforeClass
    public static void doBeforeClass()
    {
        LOG.debug( "=================== " + OrcaClientIntegrationTest.class.getSimpleName() );
    }

    /** */
    @Before
    public void doBefore()
    {
        LOG.debug( "------------------- " );
    }

    /** */
    @After
    public void doAfter()
    {
        LOG.debug( "------------------- " );
    }

    /**
     * Test that we can inject an instance of the Orca client
     * 
     * @throws OrcaException
     */
    @Test
    public void testSprintInjection() throws OrcaException
    {
        assertThat( orcaClient_, notNullValue() );
        // orcaClient_.connect();
    }

    /**
     * Inner callback class for processing received orca messages
     * 
     * @author suggitpe
     * @version 1.0 5 Nov 2009
     */
    public class OrcaSingleMsgCallback implements IOrcaSinkSingleMsgCallback
    {

        /**
         * Constructs a new instance.
         */
        public OrcaSingleMsgCallback()
        {
            super();
        }

        /**
         * @see com.ubs.orca.client.api.IOrcaSinkSingleMsgCallback#onReceived(com.ubs.orca.client.api.IAttributesConversationMessage)
         */
        @Override
        public void onReceived( IAttributesConversationMessage aArg0 ) throws Throwable
        {
            LOG.debug( "onReceived for IAttributesConversationMessage" );
        }

        /**
         * @see com.ubs.orca.client.api.IOrcaSinkSingleMsgCallback#onReceived(com.ubs.orca.client.api.ITextConversationMessage)
         */
        @Override
        public void onReceived( ITextConversationMessage aArg0 ) throws Throwable
        {
            LOG.debug( "onReceived for ITextConversationMessage" );
        }

    }

}
