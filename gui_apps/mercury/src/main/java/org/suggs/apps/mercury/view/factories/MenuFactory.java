/*
 * ToolbarBuilder.java created on 16 Sep 2008 07:15:27 by suggitpe for project GUI - Mercury
 * 
 */
package org.suggs.apps.mercury.view.factories;

import org.suggs.apps.mercury.ContextProvider;
import org.suggs.apps.mercury.view.IActionManager;
import org.suggs.apps.mercury.view.IMenuFactory;
import org.suggs.apps.mercury.view.actions.ActionManager;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.MenuManager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * This class implements the IMenuFactory and will manage the
 * construction of menus. This implementation is really simple so as
 * to provide the high level interface for refactoring later on.
 * 
 * @author suggitpe
 * @version 1.0 16 Sep 2008
 */
public class MenuFactory implements IMenuFactory, InitializingBean
{

    private static final Log LOG = LogFactory.getLog( MenuFactory.class );
    private IActionManager mActionManager_;

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception
    {
        Assert.notNull( mActionManager_,
                        "An action manager object must be injected into the MenuFactory object.  Please revise Spring XML" );
    }

    /**
     * @see org.suggs.apps.mercury.view.IMenuFactory#createMenuManager(java.lang.String)
     */
    public MenuManager createMenuManager( String menuType )
    {
        if ( LOG.isDebugEnabled() )
        {
            LOG.debug( "Constructing menu manager for type [" + menuType + "]" );
        }

        if ( menuType.equals( "MAIN" ) )
        {
            return buildMainMenu();
        }
        throw new NotImplementedException( "No menu manager exists for type [" + menuType + "]" );

    }

    /**
     * This method will create the menu for the main screen
     * 
     * @return the menu manager for the main screen
     */
    private final MenuManager buildMainMenu()
    {

        MenuManager main = new MenuManager( null );

        // create the top level menus
        MenuManager conn = new MenuManager( "Connection" );
        main.add( conn );
        MenuManager help = new MenuManager( "Help" );
        main.add( help );

        // get action manager factory
        ActionManager mgr = (ActionManager) ContextProvider.instance()
            .getBean( ActionManager.BEAN_NAME );

        // build connection menu
        conn.add( mgr.getAction( "CONNECTION_WIZARD" ) );
        conn.add( mgr.getAction( "CONNECTION_DEBUG" ) );

        // build help menu
        help.add( mgr.getAction( "HELP_ABOUT" ) );

        return main;
    }

    /**
     * Setter for the action manager
     * 
     * @param actionManager
     *            the action manager to set
     */
    public void setActionManager( IActionManager actionManager )
    {
        mActionManager_ = actionManager;
    }

}