/*
 * SingletonImpl.java created on 24 Aug 2007 05:54:33 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.creational.singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is an implementation of the Singleton pattern for
 * demonstration only. Internally I have fleshed out some of the
 * issues to emphasise and also to discuss other approaches to the
 * features of the pattern.
 * 
 * @author suggitpe
 * @version 1.0 24 Aug 2007
 */
public class SingletonImpl
{

    private static final Log LOG = LogFactory.getLog( SingletonImpl.class );

    /**
     * The single instance of the class.This is a static instance so
     * that it can be instantiated by a static method. The use of
     * volatile here denotes that the instance should not be
     * thread-local and thus this should only be used in a 1.5 JVM or
     * higher.
     */
    private volatile static SingletonImpl mInstance_;

    private long mConstructionDateTime_;

    /**
     * Constructs a new instance. The key thing to note here is that
     * this constructor is private and therefore only this class can
     * instantiate an instance of itself.
     */
    private SingletonImpl()
    {
        mConstructionDateTime_ = System.currentTimeMillis();
    }

    /**
     * This is the key method in the pattern. Here we have the double
     * locked approach to avoiding the inefficient synchronised calls.
     * Theoretically this impl of the pattern will only fall into
     * synchronised issues when we first call the instance method.
     * 
     * @return the singleton instance of the class
     */
    public static final SingletonImpl instance()
    {
        if ( mInstance_ == null )
        {
            synchronized ( SingletonImpl.class )
            {
                if ( mInstance_ == null )
                {
                    LOG.info( "Creating a new instance of the singleton object" );
                    mInstance_ = new SingletonImpl();
                }
            }
        }
        LOG.debug( "Returning a copy of the singleton instance" );
        return mInstance_;
    }

    /**
     * Getter for the construction time
     * 
     * @return the construction time in milles
     */
    public long getConstructionTime()
    {
        return mConstructionDateTime_;
    }

}
