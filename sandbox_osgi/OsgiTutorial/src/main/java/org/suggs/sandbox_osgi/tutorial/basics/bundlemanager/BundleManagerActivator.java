/*
 * BundleManagerActivator.java created on 10 Mar 2009 08:02:53 by suggitpe for project SandBox_OSGI - OsgiTutorial
 * 
 */
package org.suggs.sandbox_osgi.tutorial.basics.bundlemanager;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 * A class that will actively monitor an external jar file and will
 * update the OSGI framework with that bundle when it is updated.
 * 
 * @author suggitpe
 * @version 1.0 10 Mar 2009
 */
public class BundleManagerActivator implements BundleActivator
{

    private static final long INTERVAL = 5000;
    private static final String BUND_LOC = "src/main/resources/";
    private static final String BUNDLE = BUND_LOC + "helloworld.jar";

    private final Thread mThread_ = new Thread( new BundleUpdator() );
    private volatile BundleContext mContext_;

    /**
     * Start the updator thread
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start( BundleContext ctx ) throws Exception
    {
        mContext_ = ctx;
        mThread_.start();
    }

    /**
     * Stop the updator thread
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop( BundleContext ctx ) throws Exception
    {
        mThread_.interrupt();
        mContext_ = null;
    }

    /**
     * Finds a bundle by its location
     * 
     * @param aLocation
     *            the location from where to find the bundle
     * @return the bundle from that location
     */
    protected Bundle findBundleByLocation( String aLocation )
    {
        Bundle[] bnds = mContext_.getBundles();
        for ( Bundle b : bnds )
        {
            if ( b.getLocation().equals( aLocation ) )
            {
                return b;
            }
        }
        return null;
    }

    /**
     * Finds a bundle by its name
     * 
     * @param aBundleName
     *            the name of the bundle
     * @return the bundle with the given name
     */
    protected Bundle findBundleByName( String aBundleName )
    {
        Bundle[] bnds = mContext_.getBundles();
        for ( Bundle b : bnds )
        {
            if ( b.getSymbolicName().equals( aBundleName ) )
            {
                return b;
            }
        }
        return null;
    }

    /**
     * Method to get all of the bundle files from a known directory
     * 
     * @param aDir
     *            the directory from whcih to search for the bundle
     *            files
     * @return the array of files
     */
    private File[] getBundleFiles( File aDir )
    {

        return aDir.listFiles( new FileFilter()
        {

            public boolean accept( File aPathname )
            {
                if ( aPathname.getName().endsWith( ".jar" )
                     && !aPathname.getName().endsWith( "bundlemanager.jar" ) )
                {
                    return true;
                }
                return false;
            }
        } );

    }

    /**
     * Simple method that will go through a directory and pull out the
     * name of the jar files that can be installed as a bundle.
     * 
     * @param aDir
     *            the directory from which to look for the bundles
     * @return a List of bundle names
     */
    private List<String> getAvailableBundles( final File aDir )
    {
        if ( aDir == null || !aDir.isDirectory() )
        {
            System.err.println( "Cannot find directory [" + BUND_LOC
                                + "] from which to read bundles" );
            return null;
        }

        File[] bundleFiles = getBundleFiles( aDir );
        List<String> ret = new ArrayList<String>( bundleFiles.length );
        for ( File f : bundleFiles )
        {
            ret.add( f.getName().substring( 0, f.getName().length() - 4 ) );
        }

        return ret;
    }

    /**
     * Thread class to monitor a given bundle and its file, updating
     * the bundle when a new file version is available.
     * 
     * @author suggitpe
     * @version 1.0 10 Mar 2009
     */
    private class BundleUpdator implements Runnable
    {

        /**
         * @see java.lang.Runnable#run()
         */
        public void run()
        {
            try
            {
                File dir = new File( BUND_LOC );

                // build a list of the existing bundles (that we care
                // about)
                List<String> files = getAvailableBundles( dir );
                List<String> installedBundles = new Vector<String>();
                for ( Bundle b : mContext_.getBundles() )
                {
                    if ( files.contains( b.getSymbolicName() ) )
                    {
                        installedBundles.add( b.getSymbolicName() );
                    }
                }

                while ( !Thread.interrupted() )
                {
                    // look for new bundles to install
                    for ( String s : getAvailableBundles( dir ) )
                    {
                        if ( !installedBundles.contains( s ) )
                        {
                            String url = "file:" + BUND_LOC + s + ".jar";
                            System.out.println( s + " does not exist ... installing [" + url + "]" );
                            mContext_.installBundle( url );
                            installedBundles.add( s );
                        }
                    }

                    // now we look for instances of bundles that
                    // were once installed but are no longer installed
                    List<String> filesNow = getAvailableBundles( dir );
                    for ( String s : installedBundles )
                    {
                        if ( !filesNow.contains( s ) )
                        {
                            System.out.println( s + " no longer exists .. removing" );
                            Bundle b = findBundleByName( s );
                            b.stop();
                            b.uninstall();
                            installedBundles.remove( s );
                            break;
                        }
                    }

                    // now we want to look for all bundles in the
                    // directory that have been updated so we can
                    // update them in the OSGI framework
                    for ( String s : installedBundles )
                    {
                        Bundle installed = findBundleByName( s );
                        long installedAge = installed.getLastModified();
                        File f = new File( BUND_LOC + s + ".jar" );
                        if ( !f.exists() )
                        {
                            System.err.println( "Skipping updating of [" + s
                                                + "] as cannot find the jar file" );
                            continue;
                        }
                        long jarAge = f.lastModified();
                        // now check and update
                        if ( jarAge > installedAge )
                        {
                            System.out.println( "Updating bundle [" + s + "]" );
                            installed.update();
                        }
                    }

                    Thread.sleep( INTERVAL );
                }
            }
            catch ( InterruptedException ie )
            {
                System.out.println( "Thread interrupted, exiting" );
            }
            catch ( BundleException be )
            {
                System.err.println( "Error managing bundle" );
                be.printStackTrace();
            }

        }
    }

}
