/*
 * Observable.java created on 19 Sep 2007 06:56:45 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.compound.quackfest.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to encapsulate the observable nature of the quackable objects. This manages the logic for all of the
 * behaviour that is required for being observabnle.
 * 
 * @author suggitpe
 * @version 1.0 19 Sep 2007
 */
public class Observable implements IQuackObservable {

    List<IObserver> observers = new ArrayList<IObserver>();
    IQuackObservable duck;

    /**
     * Constructs a new instance.
     * 
     * @param aDuck
     *            the observable duck
     */
    public Observable( IQuackObservable aDuck ) {
        duck = aDuck;
    }

    /**
     * @see org.suggs.sandbox.patterns.compound.quackfest.observer.IQuackObservable#notifyObservers()
     */
    public void notifyObservers() {
        for ( IObserver o : observers ) {
            o.update( duck );
        }
    }

    /**
     * @see org.suggs.sandbox.patterns.compound.quackfest.observer.IQuackObservable#registerObserver(org.suggs.sandbox.patterns.compound.quackfest.observer.IObserver)
     */
    public void registerObserver( IObserver observer ) {
        observers.add( observer );
    }
}
