/*
 * JmxBookConfig.java created on 18 Feb 2008 19:31:19 by suggitpe for project SandBox - JMX
 * 
 */
package org.suggs.sandbox.jmx.jmxbook.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton class to encapsulate the properties for the JmxBook application
 * 
 * @author suggitpe
 * @version 1.0 18 Feb 2008
 */
public final class JmxBookConfig {

    private static final Logger LOG = LoggerFactory.getLogger( JmxBookConfig.class );

    private static JmxBookConfig instance;

    private Properties properties;

    /** The HTTP port config name */
    public static final String HTTP_PORT = "agent.http.port";
    /** the RMI port config name */
    public static final String RMI_PORT = "agent.rmi.port";
    /** The RMI URL prefix config name */
    public static final String RMI_URL_PREFIX = "agent.rmi.url.prefix";
    /** The RMI URL postfix name */
    public static final String RMI_URL_POSTFIX = "agent.rmi.url.postfix";
    /** the MBEAN server name config name */
    public static final String MBEAN_SERVERNAME = "server.mbean.name";

    static {
        instance = new JmxBookConfig();
        instance.loadConfig();
    }

    /**
     * Hidden. Constructs a new instance.
     */
    private JmxBookConfig() {
        LOG.debug( "Creating new instance of the JMX Book Configuration helper" );
        properties = new Properties();
    }

    /**
     * Instance accessor
     * 
     * @return the instance variable
     */
    public static JmxBookConfig getInstance() {
        return instance;
    }

    /**
     * Method used to load the properties file from the command line
     */
    private void loadConfig() {
        String name = System.getProperty( "jmxbook.config.filename" );
        if ( name == null || name.equals( "" ) ) {
            throw new IllegalArgumentException( "System requires system property [jmxbook.config.filename] set to the name of the configuration filename" );
        }

        LOG.debug( "Loading system properties from file [" + name + "]" );
        InputStream is = JmxBookConfig.class.getClassLoader().getResourceAsStream( name );
        if ( is == null ) {
            throw new IllegalStateException( "Could not load configuration file [" + name + "]" );
        }

        try {
            properties.load( is );
            is.close();
        }
        catch ( IOException ioe ) {
            throw new IllegalStateException( "Could not load configuration file [" + name + "]", ioe );
        }
    }

    /**
     * Gets a property from the loaded properties
     * 
     * @param aPropertyName
     *            the name of the property
     * @return the String value from the properties file
     */
    public String getCfgProperty( String aPropertyName ) {
        return properties.getProperty( aPropertyName );
    }

    /**
     * Abstraction for a common piece of code
     * 
     * @return the RMI url string
     */
    public String getRmiUrl() {
        return new StringBuffer( getCfgProperty( JmxBookConfig.RMI_URL_PREFIX ) ).append( getCfgProperty( JmxBookConfig.RMI_PORT ) )
            .append( getCfgProperty( JmxBookConfig.RMI_URL_POSTFIX ) )
            .toString();
    }

}
