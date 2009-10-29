/*
 * JmsMessageSender.java created on 29 Oct 2009 07:09:34 by suggitpe for project Orca Bridge
 * 
 */
package com.ubs.orca.orcabridge.processors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ubs.orca.orcabridge.IMessageFacade;
import com.ubs.orca.orcabridge.IMessageProcessor;
import com.ubs.orca.orcabridge.OrcaBridgeException;
import com.ubs.orca.orcabridge.jmsclient.IJmsClient;
import com.ubs.orca.orcabridge.jmsclient.JmsClientException;
import com.ubs.orca.orcabridge.jmsclient.impl.JmsSenderAction;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * TODO Write javadoc for JmsMessageSender
 * 
 * @author suggitpe
 * @version 1.0 29 Oct 2009
 */
public class JmsMessageSender implements IMessageProcessor, InitializingBean
{

    private static final Log LOG = LogFactory.getLog( JmsMessageSender.class );

    private IJmsClient jmsClientCore_;

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception
    {
        Assert.notNull( jmsClientCore_, "Must set the client core on the JMS message sender" );
    }

    /**
     * Initialisation method
     * 
     * @throws JmsClientException
     */
    public void init() throws JmsClientException
    {
        LOG.debug( "Initialising the sender connection" );
        jmsClientCore_.connect();
    }

    /**
     * @see com.ubs.orca.orcabridge.IMessageProcessor#processMessage(com.ubs.orca.orcabridge.IMessageFacade)
     */
    @Override
    public void processMessage( IMessageFacade aMessageFacade ) throws OrcaBridgeException
    {
        JmsSenderAction action = new JmsSenderAction( aMessageFacade );
        try
        {
            jmsClientCore_.processInTransaction( action );
        }
        catch ( JmsClientException jce )
        {
            throw new OrcaBridgeException( "Failed to send message to JMS destination", jce );
        }
    }

    /**
     * Sets the jmsclientCore field to the specified value.
     * 
     * @param jmsclientCore
     *            The jmsclientCore to set.
     */
    public void setJmsClientCore( IJmsClient jmsclientCore )
    {
        jmsClientCore_ = jmsclientCore;
    }

}
