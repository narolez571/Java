/*
 * BundleReleaseToolGuiTest.java created on 9 Jul 2009 20:14:50 by suggitpe for project OSGi Bundle Release Tool
 * 
 */
package org.suggs.osgitools.bundlereleasetool.gui;

import org.suggs.osgitools.bundlereleasetool.BundleData;
import org.suggs.osgitools.bundlereleasetool.IBundleReleaseToolContextCallback;
import org.suggs.osgitools.bundlereleasetool.GUI.BundleReleaseToolGui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Test class so that we can open and test the GUI operations
 * 
 * @author suggitpe
 * @version 1.0 9 Jul 2009
 */
public class BundleReleaseToolGuiTest
{

    // static logger
    private static final Log LOG = LogFactory.getLog( BundleReleaseToolGuiTest.class );

    /**
     * This is just so we can easily test it (not worth building a
     * JUnit for a Swing layer)
     * 
     * @param args
     */
    public static void main( String[] args )
    {

        javax.swing.SwingUtilities.invokeLater( new Runnable()
        {

            public void run()
            {
                LOG.debug( "Executing Run to build GUI" );

                new BundleReleaseToolGui( buildBundleCallback(), true );

                LOG.debug( "GUI Run execution complete" );
            }

        } );
    }

    /**
     * This will be used solely to create the bundle callback for the
     * GUI
     * 
     * @return GUI callback to handle the bundle context
     */
    private static final IBundleReleaseToolContextCallback buildBundleCallback()
    {
        return new IBundleReleaseToolContextCallback()
        {

            /**
             * stubs out the installing of the bundle
             * 
             * @see org.suggs.osgitools.bundlereleasetool.IBundleReleaseToolContextCallback#buildWindowListener()
             */
            @Override
            public WindowListener buildWindowListener()
            {
                return new WindowAdapter()
                {

                    /**
                     * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
                     */
                    @Override
                    public void windowClosing( WindowEvent we )
                    {
                        LOG.debug( "Doing window closey things" );
                    }
                };
            }

            /**
             * @see org.suggs.osgitools.bundlereleasetool.IBundleReleaseToolContextCallback#installBundle(java.lang.String)
             */
            @Override
            public boolean installBundle( String uri )
            {
                LOG.debug( "Pretending to install bundle" );
                return true;
            }

            /**
             * @see org.suggs.osgitools.bundlereleasetool.IBundleReleaseToolContextCallback#getBundleData()
             */
            @Override
            public Vector<BundleData> getBundleData()
            {
                Vector<BundleData> ret = new Vector<BundleData>();
                LOG.debug( "Adding in some fake bundle data" );
                ret.add( new BundleData( 7, "Active", "location of bundle", "System Bundle (1.4.1)" ) );

                return ret;
            }

        };
    }
}
