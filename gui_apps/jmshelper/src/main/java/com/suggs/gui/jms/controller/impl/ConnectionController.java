/*
 * ConnectionController.java created on 12 Jul 2007 16:30:12 by suggitpe for project GUI - JmsHelper
 * 
 */
package com.suggs.gui.jms.controller.impl;

import com.suggs.gui.jms.controller.IConnectionController;
import com.suggs.gui.jms.model.connection.IJmsConnectionManager;
import com.suggs.gui.jms.model.connection.IJmsConnectionStore;
import com.suggs.gui.jms.view.connection.JmsConnectionButtons;
import com.suggs.gui.jms.view.connection.JmsConnectionManagerPanel;
import com.suggs.gui.jms.view.connection.JmsConnectionStorePanel;

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

    // models
    private IJmsConnectionStore mConnStoreModel_;
    private IJmsConnectionManager mConnManagerModel_;

    // views
    private JmsConnectionButtons mButtonsView_;
    private JmsConnectionManagerPanel mConnManagerView_;
    private JmsConnectionStorePanel mConnStoreView_;

    /**
     * Constructs a new instance.
     */
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
    public ConnectionController( IJmsConnectionStore aStr, IJmsConnectionManager aMgr, JmsConnectionButtons aButtons,
                                 JmsConnectionStorePanel aStrPanel, JmsConnectionManagerPanel aMgrPanel )
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
        mConnStoreView_.initialise( "Initial" );
        mConnStoreView_.loadDefaultValues();
        mConnManagerView_.initialise( "Initial" );
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
     * Returns the value of ConnectionManager.
     * 
     * @return Returns the ConnectionManager.
     */
    public IJmsConnectionManager getConnectionManager()
    {
        return mConnManagerModel_;
    }

    /**
     * Returns the value of ConnectionStore.
     * 
     * @return Returns the ConnectionStore.
     */
    public IJmsConnectionStore getConnectionStore()
    {
        return mConnStoreModel_;
    }

    /**
     * Returns the value of ButtonsView.
     * 
     * @return Returns the ButtonsView.
     */
    public JmsConnectionButtons getButtonsView()
    {
        return mButtonsView_;
    }

    /**
     * Returns the value of ConnManagerView.
     * 
     * @return Returns the ConnManagerView.
     */
    public JmsConnectionManagerPanel getConnManagerView()
    {
        return mConnManagerView_;
    }

    /**
     * Returns the value of ConnStoreView.
     * 
     * @return Returns the ConnStoreView.
     */
    public JmsConnectionStorePanel getConnStoreView()
    {
        return mConnStoreView_;
    }

}
