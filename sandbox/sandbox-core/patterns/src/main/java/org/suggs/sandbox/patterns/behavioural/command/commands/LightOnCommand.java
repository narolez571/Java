/*
 * LightOnCommand.java created on 29 Aug 2007 17:02:27 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.behavioural.command.commands;

import org.suggs.sandbox.patterns.behavioural.command.ICommand;
import org.suggs.sandbox.patterns.behavioural.command.receivers.Light;

/**
 * Command for turning on a light.
 * 
 * @author suggitpe
 * @version 1.0 29 Aug 2007
 */
public class LightOnCommand implements ICommand
{

    private Light mLight_;

    /**
     * Constructs a new instance.
     * 
     * @param aLight
     *            the command receiver
     */
    public LightOnCommand( Light aLight )
    {
        mLight_ = aLight;
    }

    /**
     * @see org.suggs.sandbox.patterns.behavioural.command.ICommand#execute()
     */
    public void execute()
    {
        mLight_.on();
    }

    /**
     * @see org.suggs.sandbox.patterns.behavioural.command.ICommand#undo()
     */
    public void undo()
    {
        mLight_.off();
    }

}
