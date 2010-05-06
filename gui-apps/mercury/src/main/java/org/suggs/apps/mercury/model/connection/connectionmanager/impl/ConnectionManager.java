/*
 * ConnectionManager.java created on 20 Jan 2009 07:45:22 by suggitpe for project GUI - Mercury
 * 
 */
package org.suggs.apps.mercury.model.connection.connectionmanager.impl;

import org.suggs.apps.mercury.ContextProvider;
import org.suggs.apps.mercury.model.connection.connection.IConnection;
import org.suggs.apps.mercury.model.connection.connectionmanager.IConnectionManager;
import org.suggs.apps.mercury.model.connection.connectionmanager.IConnectionManagerListener;
import org.suggs.apps.mercury.model.connection.connectionstore.ConnectionStoreException;
import org.suggs.apps.mercury.model.connection.connectionstore.IConnectionStore;
import org.suggs.apps.mercury.model.connection.connectionstore.IConnectionStoreChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the main impl of the IConnectionManager code. The main aim of this class is to provide the glue in
 * teh application between the connection store and the underlying connections. Any part of the system that
 * wants to have access to a connection should come through this class.
 * 
 * @author suggitpe
 * @version 1.0 20 Jan 2009
 */
public final class ConnectionManager implements IConnectionManager, IConnectionStoreChangeListener {

    private static final Log LOG = LogFactory.getLog( ConnectionManager.class );

    private static IConnectionManager instance;
    private IConnectionStore connStore;
    private Map<String, IConnection> connectionMap = new HashMap<String, IConnection>();
    private List<IConnectionManagerListener> listsners = new ArrayList<IConnectionManagerListener>();

    static {
        instance = new ConnectionManager();
    }

    // non static initialiser
    {
        connStore = (IConnectionStore) ContextProvider.instance().getBean( "connectionStoreManager" );
        connStore.addConnectionStoreChangeListener( this );
    }

    /**
     * Constructs a new instance.
     */
    private ConnectionManager() {}

    /**
     * TODO: delete me
     * 
     * @see org.suggs.apps.mercury.model.connection.connectionmanager.IConnectionManager#getConnectionDump()
     */
    public String getConnectionDump() throws ConnectionStoreException {
        return connStore.getConnectionStoreDumpAsXml();
    }

    /**
     * This is the main singleton interface.
     * 
     * @return the singleton instance of the class
     */
    public static IConnectionManager instance() {
        return instance;
    }

    /**
     * @see org.suggs.apps.mercury.model.connection.connectionmanager.IConnectionManager#getConnection(java.lang.String)
     */
    public IConnection getConnection( String aConnectionName ) {
        return connectionMap.get( aConnectionName );
    }

    /**
     * This is the part where we have registered interest in any changes in the connection store so that we
     * can correctly reflect these changes in the manager (and those that register interest in the
     * connections).
     * 
     * @see org.suggs.apps.mercury.model.connection.connectionstore.IConnectionStoreChangeListener#handleConnectionStoreChange(java.lang.String,
     *      org.suggs.apps.mercury.model.connection.connectionstore.IConnectionStoreChangeListener.ConnectionStoreEvent)
     */
    public void handleConnectionStoreChange( String aConnectionName,
                                             IConnectionStoreChangeListener.ConnectionStoreEvent aEvent ) {
        switch ( aEvent ) {
            case CREATE:
                LOG.debug( "Adding " + aConnectionName + " to conn mgr" );
                notifyAllListeners( aConnectionName, IConnectionManagerListener.ConnectionManagerEvent.CREATE );
                break;
            case EDIT:
                notifyAllListeners( aConnectionName, IConnectionManagerListener.ConnectionManagerEvent.EDIT );
                throw new NotImplementedException( "Need to implement logic for the edit case" );
                // break;
            case REMOVE:
                LOG.debug( "Removing " + aConnectionName + " from the conn mgr" );
                if ( connectionMap.containsKey( aConnectionName ) ) {
                    connectionMap.remove( aConnectionName );
                }
                notifyAllListeners( aConnectionName, IConnectionManagerListener.ConnectionManagerEvent.REMOVE );
                break;
            default:
                throw new IllegalStateException( "Unknown ConnectionStoreEvent [" + aEvent.toString() + "]" );
        }

    }

    /**
     * @see org.suggs.apps.mercury.model.connection.connectionmanager.IConnectionManager#containsConnection(java.lang.String)
     */
    public boolean containsConnection( String aConnectionName ) {
        return connectionMap.containsKey( aConnectionName );
    }

    private void notifyAllListeners( String aConnectionName,
                                     IConnectionManagerListener.ConnectionManagerEvent aEvent ) {
        for ( IConnectionManagerListener l : listsners ) {
            l.handleConnectionManagerChange( aConnectionName, aEvent );
        }
    }

    /**
     * @see org.suggs.apps.mercury.model.connection.connectionmanager.IConnectionManager#addConnectionManagerListener(org.suggs.apps.mercury.model.connection.connectionmanager.IConnectionManagerListener)
     */
    public void addConnectionManagerListener( IConnectionManagerListener aListener ) {
        listsners.add( aListener );
    }

    /**
     * @see org.suggs.apps.mercury.model.connection.connectionmanager.IConnectionManager#removeConnectionManagerListener(org.suggs.apps.mercury.model.connection.connectionmanager.IConnectionManagerListener)
     */
    public void removeConnectionManagerListener( IConnectionManagerListener aListener ) {
        listsners.remove( aListener );
    }

}
