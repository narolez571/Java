/*
 * SteamedMilk.java created on 29 Aug 2007 06:01:05 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.structural.decorator.beveragedecorator;

import org.suggs.sandbox.patterns.structural.decorator.IBeverage;

/**
 * Steamed Milk decorator
 * 
 * @author suggitpe
 * @version 1.0 29 Aug 2007
 */
public class SteamedMilk extends AbstractCondimentDecorator {

    /**
     * Constructs a new instance.
     * 
     * @param aBeverage
     *            the beverage to decorate
     */
    public SteamedMilk( IBeverage aBeverage ) {
        super( aBeverage );
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.decorator.beveragedecorator.AbstractCondimentDecorator#getDescription()
     */
    @Override
    public String getDescription() {
        return getBeverage().getDescription() + ", Steamed";
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.decorator.IBeverage#cost()
     */
    @Override
    public double cost() {
        return 0.1d + getBeverage().cost();
    }

}
