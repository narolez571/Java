/*
 * Quackologist.java created on 19 Sep 2007 17:56:21 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.compound.quackfest.observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Observer of quackObservables
 * 
 * @author suggitpe
 * @version 1.0 19 Sep 2007
 */
public class Quackologist implements IObserver {

    private static final Log LOG = LogFactory.getLog( Quackologist.class );

    /**
     * @see org.suggs.sandbox.patterns.compound.quackfest.observer.IObserver#update(org.suggs.sandbox.patterns.compound.quackfest.observer.IQuackObservable)
     */
    public void update( IQuackObservable quacker ) {
        LOG.debug( "Quackologist: [" + quacker.getClass().getSimpleName() + "] just quacked" );
    }

}
