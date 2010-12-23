/*
 * MessageFactory.java created on 7 Oct 2009 18:05:30 by suggitpe for project Orca Bridge
 * 
 */
package com.ubs.orca.orcabridge.message;

import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubs.orca.common.bus.IOrcaMessage;
import com.ubs.orca.orcabridge.IMessageFacade;

/**
 * Factory from which to build a IMessageFacade
 * 
 * @author suggitpe
 * @version 1.0 7 Oct 2009
 */
public final class MessageFacadeFactory {

    private static final Logger LOG = LoggerFactory.getLogger( MessageFacadeFactory.class );

    /*
     * We have hideen this as we do not want people to create any instances of this class
     */
    private MessageFacadeFactory() {}

    /**
     * Builds a message facade from a JMS message
     * 
     * @param aJmsMessage
     *            the JMS message to build the facade
     * @return message facade that supports JMS messages
     */
    public static IMessageFacade createMessageAdapter( Message aJmsMessage ) {
        LOG.debug( "Building a JMS message facade" );
        return new JmsMessageFacade( aJmsMessage );
    }

    /**
     * Builds a message facade that supports Orca messages
     * 
     * @param aOrcaMessage
     *            the Orca message to build the facade
     * @return message facade that supports Orca messages
     */
    public static IMessageFacade createMessageAdapter( IOrcaMessage aOrcaMessage ) {
        LOG.debug( "Building an Orca message facade" );
        return new OrcaMessageFacade( aOrcaMessage );
    }
}
