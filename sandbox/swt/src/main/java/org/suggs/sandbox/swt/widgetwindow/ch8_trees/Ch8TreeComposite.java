/*
 * Ch8TreeComposite.java created on 8 Sep 2008 06:58:03 by suggitpe for project SandBox - SWT
 * 
 */
package org.suggs.sandbox.swt.widgetwindow.ch8_trees;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * simple class to show how a tree is composed with a content handler
 * and a tree viewer. This is all about showing how the separation of
 * control really works.
 * 
 * @author suggitpe
 * @version 1.0 8 Sep 2008
 */
public class Ch8TreeComposite extends Composite
{

    private static final int[] SELECTION_STYLE = { SWT.SINGLE, SWT.MULTI };
    private static final int[] CHECK_STYLE = { SWT.NONE, SWT.CHECK };

    /**
     * Constructs a new instance.
     */
    public Ch8TreeComposite( Composite parent )
    {
        super( parent, SWT.NULL );
        populateControl();
    }

    /**
     * Builds the control. This is way over developed!
     */
    protected void populateControl()
    {
        setLayout( new FillLayout() );

        // this bit simple sets up the various styles of tree to use
        // so we can show them all
        for ( int sel = 0; sel < SELECTION_STYLE.length; ++sel )
        {
            for ( int check = 0; check < CHECK_STYLE.length; ++check )
            {
                System.out.println( "creating a tree" );
                createTreeViewer( SELECTION_STYLE[sel] | CHECK_STYLE[check] );
            }
        }
    }

    /**
     * Creates a tree viewer based on the passed in style. This sets
     * the content provider piece.
     * 
     * @param style
     *            the syle to use in constructing the tree viewer
     */
    private void createTreeViewer( int style )
    {
        TreeViewer viewer = new TreeViewer( this, style );
        viewer.setContentProvider( new ITreeContentProvider()
        {

            public Object[] getChildren( Object parentElem )
            {
                return ( (TreeNode) parentElem ).getChildren().toArray();
            }

            public Object getParent( Object elem )
            {
                return ( (TreeNode) elem ).getParent();
            }

            public boolean hasChildren( Object elem )
            {
                return ( (TreeNode) elem ).getChildren().size() > 0;
            }

            public Object[] getElements( Object elem )
            {
                return ( (TreeNode) elem ).getChildren().toArray();
            }

            public void dispose()
            {
                // nadda
            }

            public void inputChanged( Viewer arg0, Object arg1, Object arg2 )
            {
                // nadda
            }
        } );

        viewer.setInput( getRootNode() );
    }

    /**
     * Creates the data tree
     * 
     * @return the new data tree
     */
    private TreeNode getRootNode()
    {
        TreeNode root = new TreeNode( "root" );
        root.addChild( new TreeNode( "child 1" ).addChild( new TreeNode( "subchild 1" ) ) );
        root.addChild( new TreeNode( "child 2" ).addChild( new TreeNode( "subchild 2" ).addChild( new TreeNode( "grandchild 2" ) ) ) );

        return root;
    }

    /**
     * Class to encapsulate the tree data for tree
     * 
     * @author suggitpe
     * @version 1.0 8 Sep 2008
     */
    class TreeNode
    {

        private String mName_;
        private List<TreeNode> mChildren_ = new ArrayList<TreeNode>();
        private TreeNode mParent_;

        public TreeNode( String name )
        {
            mName_ = name;
        }

        protected Object getParent()
        {
            return mParent_;
        }

        public TreeNode addChild( TreeNode child )
        {
            mChildren_.add( child );
            child.mParent_ = this;
            return this;
        }

        public List getChildren()
        {
            return mChildren_;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            return mName_;
        }
    }
}