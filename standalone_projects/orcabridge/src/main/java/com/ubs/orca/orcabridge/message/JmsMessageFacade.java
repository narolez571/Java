/*
 * JmsMessageFacade.java created on 7 Oct 2009 19:11:18 by suggitpe for project Orca Bridge
 * 
 */
package com.ubs.orca.orcabridge.message;

import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ubs.orca.client.api.IOrcaClient;
import com.ubs.orca.common.bus.IOrcaMessage;
import com.ubs.orca.orcabridge.IMessageFacade;

/**
 * Message facade to encapsulate a JMS message
 * 
 * @author suggitpe
 * @version 1.0 7 Oct 2009
 */
class JmsMessageFacade implements IMessageFacade
{

    private static final Log LOG = LogFactory.getLog( JmsMessageFacade.class );
    private final Message mJmsMessage_;

    JmsMessageFacade( Message aJmsMessage )
    {
        super();
        mJmsMessage_ = aJmsMessage;
    }

    /**
     * @see com.ubs.orca.orcabridge.IMessageFacade#buildJmsMessage(javax.jms.Session)
     */
    @Override
    public Message buildJmsMessage( Session aSession )
    {
        LOG.debug( "Building JMS message" );
        return null;
    }

    /**
     * @see com.ubs.orca.orcabridge.IMessageFacade#buildOrcaMesage(com.ubs.orca.client.api.IOrcaClient)
     */
    @Override
    public IOrcaMessage buildOrcaMesage( IOrcaClient aClient )
    {
        LOG.debug( "Building Orca message" );
        return null;
    }
}
