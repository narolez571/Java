/*
 * MacroCommand.java created on 31 Aug 2007 06:28:23 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.behavioural.command.commands;

import org.suggs.sandbox.patterns.behavioural.command.ICommand;

/**
 * A command that will execute a colection of commands
 * 
 * @author suggitpe
 * @version 1.0 31 Aug 2007
 */
public class MacroCommand implements ICommand
{

    ICommand[] mCommands_;

    /**
     * Constructs a new instance.
     * 
     * @param aCmds
     *            an array of commands to execute
     */
    public MacroCommand( ICommand[] aCmds )
    {
        mCommands_ = aCmds;
    }

    /**
     * @see org.suggs.sandbox.patterns.behavioural.command.ICommand#execute()
     */
    public void execute()
    {
        for ( ICommand cmd : mCommands_ )
        {
            cmd.execute();
        }
    }

    /**
     * @see org.suggs.sandbox.patterns.behavioural.command.ICommand#undo()
     */
    public void undo()
    {
        for ( ICommand cmd : mCommands_ )
        {
            cmd.undo();
        }
    }

}