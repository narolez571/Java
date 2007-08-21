/*
 * ConnectionController.java created on 12 Jul 2007 16:30:12 by suggitpe for project GUI - JmsHelper
 * 
 */
package org.suggs.apps.mercury.controller.impl;

import org.suggs.apps.mercury.MercuryException;
import org.suggs.apps.mercury.controller.IConnectionController;
import org.suggs.apps.mercury.model.connection.IConnectionDetails;
import org.suggs.apps.mercury.model.connection.IConnectionManager;
import org.suggs.apps.mercury.model.connection.IConnectionStore;
import org.suggs.apps.mercury.model.connection.MercuryConnectionStoreException;
import org.suggs.apps.mercury.view.connection.ConnectionButtons;
import org.suggs.apps.mercury.view.connection.ConnectionManagerPanel;
import org.suggs.apps.mercury.view.connection.ConnectionStorePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * This is the main controller for theconnection part of the GUI.
 * 
 * @author suggitpe
 * @version 1.0 12 Jul 2007
 */
public class ConnectionController implements InitializingBean, IConnectionController
{

    private static final Log LOG = LogFactory.getLog( ConnectionController.class );
    private static final ImageIcon IMG_ = new ImageIcon( "jms.gif" );

    // models
    private IConnectionStore mConnStoreModel_;
    private IConnectionManager mConnManagerModel_;

    // views
    private ConnectionButtons mButtonsView_;
    private ConnectionManagerPanel mConnManagerView_;
    private ConnectionStorePanel mConnStoreView_;

    /**
     * Constructs a new instance.
     */
    @SuppressWarnings("unused")
    private ConnectionController()
    {
        throw new IllegalStateException();
    }

    /**
     * Constructs a new instance.
     * 
     * @param aStr
     *            the connection store
     * @param aMgr
     *            the connection manager
     * @param aButtons
     *            the buttons panel
     * @param aStrPanel
     *            the connection store panel
     * @param aMgrPanel
     *            the onnection manager panel
     */
    public ConnectionController( IConnectionStore aStr, IConnectionManager aMgr, ConnectionButtons aButtons,
                                 ConnectionStorePanel aStrPanel, ConnectionManagerPanel aMgrPanel )
    {
        super();
        mConnStoreModel_ = aStr;
        mConnManagerModel_ = aMgr;
        mButtonsView_ = aButtons;
        mConnStoreView_ = aStrPanel;
        mConnManagerView_ = aMgrPanel;

        init();
    }

    /**
     * Local method to initialise itself and all of the inter related
     * objects
     */
    private void init()
    {
        LOG.debug( "Initialising the Connection Controller" );
        mConnStoreView_.initialise( mConnStoreModel_.getState() );
        mConnManagerView_.initialise( mConnManagerModel_.getConnectionState().name() );

        mButtonsView_.addLoadActionListener( createLoadActionListener() );
        mButtonsView_.addSaveActionListener( createSaveActionListener() );
        mButtonsView_.addDeleteActionListener( createDeleteActionListener() );
        mButtonsView_.addTestActionListener( createTestConnActionListener() );
        mButtonsView_.addConnectActionListener( createConnectActionListener() );
        mButtonsView_.addDisconnectActionListener( createDisconnectActionListener() );
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception
    {
        Assert.notNull( mConnStoreModel_, "Must set the connection store in the connection controller" );
        Assert.notNull( mConnManagerModel_, "Must set the connection manager in the connection controller" );
        Assert.notNull( mButtonsView_, "Must set the buttons view in the connection controller" );
        Assert.notNull( mConnStoreView_, "Must set the connection store view in the connection controller" );
        Assert.notNull( mConnManagerView_, "Must set the connection manager view in the connection controller" );
    }

    /**
     * Creates a new Test connection action listener
     * 
     * @return a new test connection action listener
     */
    private ActionListener createTestConnActionListener()
    {
        return new ActionListener()
        {

            public void actionPerformed( ActionEvent arg0 )
            {
                if ( !( mConnManagerModel_.testConnection( null ) ) )
                {
                    String err = "Test connection failed";
                    LOG.warn( err );
                    JOptionPane.showMessageDialog( mConnManagerView_, err, "Connect failure", JOptionPane.ERROR_MESSAGE );
                }
            }
        };
    }

    /**
     * Creates a new connect action listsner
     * 
     * @return a new connect action listener
     */
    private ActionListener createConnectActionListener()
    {
        return new ActionListener()
        {

            public void actionPerformed( ActionEvent arg0 )
            {
                try
                {
                    mConnManagerModel_.connect( null );
                }
                catch ( MercuryException jhe )
                {
                    String err = "Exception caught when trying to connect to connection:\n" + jhe.getMessage();
                    LOG.warn( err );
                    JOptionPane.showMessageDialog( mConnManagerView_, err, "Connect failure", JOptionPane.ERROR_MESSAGE );
                }
            }
        };
    }

    /**
     * Creates a new disconnect action listsner
     * 
     * @return a new disconnect action listener
     */
    private ActionListener createDisconnectActionListener()
    {
        return new ActionListener()
        {

            public void actionPerformed( ActionEvent arg0 )
            {
                try
                {
                    mConnManagerModel_.disconnect();
                }
                catch ( MercuryException jhe )
                {
                    String err = "Exception caught when trying to disconnect from connection:\n" + jhe.getMessage();
                    LOG.warn( err );
                    JOptionPane.showMessageDialog( mConnManagerView_, err, "Disconnect failure", JOptionPane.ERROR_MESSAGE );
                }
            }
        };
    }

    /**
     * Creates a new delete action listener
     * 
     * @return a new delete action listsner
     */
    private ActionListener createDeleteActionListener()
    {
        return new ActionListener()
        {

            public void actionPerformed( ActionEvent arg0 )
            {
                String[] conns = mConnStoreModel_.getListOfKnownConnectionNames();
                String input = (String) JOptionPane.showInputDialog( mConnStoreView_,
                                                                     "Please select the connection to delete:",
                                                                     "Select connection for delete",
                                                                     JOptionPane.INFORMATION_MESSAGE,
                                                                     IMG_,
                                                                     conns,
                                                                     "..." );

                if ( input != null )
                {
                    int choice = JOptionPane.showConfirmDialog( mConnStoreView_,
                                                                "Are you sure that you want to delete the named connection[" + input + "]",
                                                                "Delete Confirmation",
                                                                JOptionPane.YES_NO_CANCEL_OPTION );
                    if ( choice == JOptionPane.OK_OPTION )
                    {
                        int sure = JOptionPane.showConfirmDialog( mConnStoreView_,
                                                                  "Are you sure?\n[" + input + "] could be useful",
                                                                  "Delete Verify",
                                                                  JOptionPane.YES_NO_CANCEL_OPTION );
                        if ( sure == JOptionPane.OK_OPTION )
                        {
                            try
                            {
                                LOG.info( "Deleting named connection [" + input + "]" );
                                mConnStoreModel_.deleteNamedConnection( input );
                            }
                            catch ( MercuryConnectionStoreException mcse )
                            {
                                JOptionPane.showMessageDialog( mConnStoreView_, "Delete failed", "Failed to delete connection [" + input
                                                                                                 + "]", JOptionPane.ERROR_MESSAGE );
                            }
                        }
                        else
                        {
                            LOG.debug( "Delete cancelled at the last minute for [" + input + "]" );
                        }
                    }
                    else
                    {
                        LOG.debug( "Delete action for [" + input + "] canceled" );
                    }
                }
                else
                {
                    LOG.debug( "Delete ction cancelled" );
                }
            }
        };
    }

    /**
     * Creates an action listener for the loading of connection
     * pramters from a source
     * 
     * @returN
     */
    private ActionListener createLoadActionListener()
    {
        return new ActionListener()
        {

            public void actionPerformed( ActionEvent arg0 )
            {
                String[] conns = mConnStoreModel_.getListOfKnownConnectionNames();
                String input = (String) JOptionPane.showInputDialog( mConnStoreView_,
                                                                     "Please select the connection to load:",
                                                                     "Select connection",
                                                                     JOptionPane.INFORMATION_MESSAGE,
                                                                     IMG_,
                                                                     conns,
                                                                     "..." );
                if ( input != null )
                {
                    LOG.debug( "Loading connection [" + input + "]" );
                    try
                    {
                        IConnectionDetails dtls = mConnStoreModel_.loadConnectionParameters( input );
                        mConnStoreView_.loadValues( dtls );
                        mConnManagerView_.loadTypeValues( dtls );
                    }
                    catch ( MercuryConnectionStoreException mce )
                    {
                        JOptionPane.showMessageDialog( mConnStoreView_, "Failed to load named configuration [" + input + "]\n"
                                                                        + mce.getMessage(), "Load failure", JOptionPane.ERROR_MESSAGE );
                    }
                }
                else
                {
                    LOG.debug( "Load action canceled" );
                }
            }
        };
    }

    /**
     * Creates a new action listener for the saving of a set of
     * connection parameters
     * 
     * @return the action listener
     */
    private ActionListener createSaveActionListener()
    {
        return new ActionListener()
        {

            public void actionPerformed( ActionEvent arg0 )
            {
                // first we should make sure that the conn dedtails
                // have been saved correctly
                String input = (String) JOptionPane.showInputDialog( mConnStoreView_,
                                                                     "Please enter a name for this conection",
                                                                     "Save connection",
                                                                     JOptionPane.INFORMATION_MESSAGE,
                                                                     IMG_,
                                                                     null,
                                                                     "..." );

                if ( input != null )
                {
                    LOG.debug( "Saving connection as [" + input + "]" );

                    if ( mConnStoreModel_.doesConnectionExist( input ) )
                    {
                        int sure = JOptionPane.showConfirmDialog( mConnStoreView_,
                                                                  "This will overwrite the exitsing connection\ncontinue?",
                                                                  "Overwrite existing",
                                                                  JOptionPane.YES_NO_CANCEL_OPTION );
                        if ( sure != JOptionPane.OK_OPTION )
                        {
                            return;
                        }
                    }

                    try
                    {
                        IConnectionDetails dtls = mConnStoreView_.getConnectionDetails();
                        mConnManagerView_.populateConnectionDetails( dtls );
                        mConnStoreModel_.saveConnectionParameters( input, dtls );
                    }
                    catch ( MercuryConnectionStoreException mce )
                    {
                        JOptionPane.showMessageDialog( mConnStoreView_,
                                                       "Failed to save named configuration [" + input + "]\n" + mce.getMessage(),
                                                       "Internal save failure",
                                                       JOptionPane.ERROR_MESSAGE );
                    }
                    catch ( Exception e )
                    {
                        JOptionPane.showMessageDialog( mConnStoreView_, "Failed to save named configuration [" + input + "]\n"
                                                                        + e.getMessage(), "Save failure", JOptionPane.ERROR_MESSAGE );
                    }
                }
                else
                {
                    LOG.debug( "Save action cancelled" );
                }
            }
        };
    }

    /**
     * Returns the value of ConnectionManager.
     * 
     * @return Returns the ConnectionManager.
     */
    public IConnectionManager getConnectionManager()
    {
        return mConnManagerModel_;
    }

    /**
     * Returns the value of ConnectionStore.
     * 
     * @return Returns the ConnectionStore.
     */
    public IConnectionStore getConnectionStore()
    {
        return mConnStoreModel_;
    }

    /**
     * Returns the value of ButtonsView.
     * 
     * @return Returns the ButtonsView.
     */
    public ConnectionButtons getButtonsView()
    {
        return mButtonsView_;
    }

    /**
     * Returns the value of ConnManagerView.
     * 
     * @return Returns the ConnManagerView.
     */
    public ConnectionManagerPanel getConnManagerView()
    {
        return mConnManagerView_;
    }

    /**
     * Returns the value of ConnStoreView.
     * 
     * @return Returns the ConnStoreView.
     */
    public ConnectionStorePanel getConnStoreView()
    {
        return mConnStoreView_;
    }

}
