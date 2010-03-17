/*
 * BundleGuiException.java created on 16 Jul 2009 06:54:28 by suggitpe for project OSGi Bundle Release Tool
 * 
 */
package org.suggs.osgitools.bundlereleasetool;


/**
 * TODO Write javadoc for BundleGuiException
 * 
 * @author suggitpe
 * @version 1.0 16 Jul 2009
 */
public class BundleGuiException extends Exception
{

    /**
     * Constructs a new instance.
     */
    public BundleGuiException()
    {
        super();
    }

    /**
     * Constructs a new instance.
     * 
     * @param aMessage
     */
    public BundleGuiException( String aMessage )
    {
        super( aMessage );
    }

    /**
     * Constructs a new instance.
     * 
     * @param aMessage
     * @param aError
     */
    public BundleGuiException( String aMessage, Throwable aError )
    {
        super( aMessage, aError );
    }

    /**
     * Constructs a new instance.
     * 
     * @param aError
     */
    public BundleGuiException( Throwable aError )
    {
        super( aError );
    }
}
