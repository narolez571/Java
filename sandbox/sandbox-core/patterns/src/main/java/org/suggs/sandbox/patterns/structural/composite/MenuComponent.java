/*
 * MenuComponent.java created on 7 Sep 2007 16:58:03 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.structural.composite;

import org.suggs.sandbox.patterns.structural.composite.iterators.CompositeIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The menu composite class.
 * 
 * @author suggitpe
 * @version 1.0 7 Sep 2007
 */
public class MenuComponent extends AbstractMenuComponent {

    List<IMenuComponent> menuComps = new ArrayList<IMenuComponent>();
    String name;
    String description;

    /**
     * Constructs a new instance.
     * 
     * @param aName
     * @param aDescripton
     */
    public MenuComponent( String aName, String aDescripton ) {
        name = aName;
        description = aDescripton;
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.composite.IMenuComponent#add(org.suggs.sandbox.patterns.structural.composite.IMenuComponent)
     */
    @Override
    public void add( IMenuComponent component ) {
        menuComps.add( component );
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.composite.IMenuComponent#remove(org.suggs.sandbox.patterns.structural.composite.IMenuComponent)
     */
    @Override
    public void remove( IMenuComponent component ) {
        menuComps.remove( component );
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.composite.IMenuComponent#getChild(int)
     */
    @Override
    public IMenuComponent getChild( int childIndex ) {
        return menuComps.get( childIndex );
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.composite.IMenuComponent#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.composite.IMenuComponent#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.composite.IMenuComponent#print()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void print() {
        System.out.println( "\n" + getName() + ", " + getDescription() );
        System.out.println( "-----------------------" );

        // This is a small bit of recursion (if the object in the iter
        // is a MenuComponent rather that a MenuItem).
        Iterator iter = menuComps.iterator();
        while ( iter.hasNext() ) {
            ( (IMenuComponent) iter.next() ).print();
        }
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.composite.IMenuComponent#createIterator()
     */
    @SuppressWarnings("unchecked")
    public Iterator createIterator() {
        return new CompositeIterator( menuComps.iterator() );
    }

}
