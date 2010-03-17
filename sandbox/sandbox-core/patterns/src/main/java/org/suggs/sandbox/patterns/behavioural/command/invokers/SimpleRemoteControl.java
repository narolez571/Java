/*
 * SimpleRemoteControl.java created on 29 Aug 2007 17:10:54 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.behavioural.command.invokers;

import org.suggs.sandbox.patterns.behavioural.command.ICommand;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Simple implementation where only one command is allowed
 * 
 * @author suggitpe
 * @version 1.0 29 Aug 2007
 */
public class SimpleRemoteControl
{

    private static final Log LOG = LogFactory.getLog( SimpleRemoteControl.class );

    private ICommand mSlot_;

    /**
     * Constructs a new instance.
     */
    public SimpleRemoteControl()
    {
    }

    /**
     * Setter for the command
     * 
     * @param aCommand
     *            the command to set
     */
    public void setCommand( ICommand aCommand )
    {
        mSlot_ = aCommand;
    }

    /**
     * This method is called when the button is pressed
     */
    public void buttonWasPressed()
    {
        LOG.debug( "Button pressed" );
        mSlot_.execute();
    }

}
