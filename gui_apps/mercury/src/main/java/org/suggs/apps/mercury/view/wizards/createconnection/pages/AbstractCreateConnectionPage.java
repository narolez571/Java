/*
 * AbstractCreateConnectionPage.java created on 30 Oct 2008 07:54:24 by suggitpe for project GUI - Mercury
 * 
 */
package org.suggs.apps.mercury.view.wizards.createconnection.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * This is the abstract class for all of the Create Connection wizard
 * pages and manages the majority of the boiler plate code that is
 * required.
 * 
 * @author suggitpe
 * @version 1.0 30 Oct 2008
 */
public abstract class AbstractCreateConnectionPage extends WizardPage
{

    /**
     * Constructs a new instance.
     */
    public AbstractCreateConnectionPage( String pageName, String pageTitle )
    {
        super( pageName, pageTitle, null );
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl( Composite parent )
    {

        Composite topLevel = new Composite( parent, SWT.NONE );
        topLevel.setLayout( new FillLayout( SWT.VERTICAL ) );

        doBuildControls( topLevel );

        setControl( topLevel );
    }

    /**
     * This method will manage the creation of the page controls
     */
    protected abstract void doBuildControls( Composite controlComposite );

}
