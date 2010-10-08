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

/**
 * This class implements the IMenuFactory and will manage the construction of menus. This implementation is
 * really simple so as to provide the high level interface for refactoring later on.
 * 
 * @author suggitpe
 * @version 1.0 16 Sep 2008
 */
public class MenuFactory implements IMenuFactory {

    private static final Log LOG = LogFactory.getLog( MenuFactory.class );
    private IActionManager actionManager;

    /**
     * Constructs a new instance.
     */
    public MenuFactory( IActionManager aActionManager ) {
        actionManager = aActionManager;
    }

    /**
     * @see org.suggs.apps.mercury.view.IMenuFactory#createMenuManager(java.lang.String)
     */
    @Override
    public MenuManager createMenuManager( String menuType ) {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug( "Constructing menu manager for type [" + menuType + "]" );
        }

        if ( menuType.equals( "MAIN" ) ) {
            return buildMainMenu();
        }
        throw new NotImplementedException( "No menu manager exists for type [" + menuType + "]" );

    }

    /**
     * This method will create the menu for the main screen
     * 
     * @return the menu manager for the main screen
     */
    private MenuManager buildMainMenu() {
        // get action manager factory
        ActionManager mgr = (ActionManager) ContextProvider.instance().getBean( ActionManager.BEAN_NAME );

        MenuManager main = new MenuManager( null );

        // create the top level menus
        MenuManager file = new MenuManager( "File" );
        main.add( file );
        MenuManager conn = new MenuManager( "ConnectionContext" );
        main.add( conn );
        MenuManager help = new MenuManager( "Help" );
        main.add( help );

        // build file menu
        file.add( mgr.getAction( "FILE_EXIT" ) );

        // build connection menu
        conn.add( mgr.getAction( "CREATE_CONNECTION" ) );
        conn.add( mgr.getAction( "EDIT_CONNECTION" ) );
        conn.add( mgr.getAction( "REMOVE_CONNECTION" ) );

        // build help menu
        help.add( mgr.getAction( "HELP_ABOUT" ) );
        help.add( mgr.getAction( "CONNECTION_DEBUG" ) );

        return main;
    }

    /**
     * Setter for the action manager
     * 
     * @param aActionManager
     *            the action manager to set
     */
    public final void setActionManager( IActionManager aActionManager ) {
        actionManager = aActionManager;
    }

}
