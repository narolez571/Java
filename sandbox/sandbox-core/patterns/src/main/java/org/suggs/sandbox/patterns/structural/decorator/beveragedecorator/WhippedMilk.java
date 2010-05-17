/*
 * WhippedMilk.java created on 29 Aug 2007 06:01:48 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.structural.decorator.beveragedecorator;

import org.suggs.sandbox.patterns.structural.decorator.IBeverage;

/**
 * Whipped milk beverage
 * 
 * @author suggitpe
 * @version 1.0 29 Aug 2007
 */
public class WhippedMilk extends AbstractCondimentDecorator {

    /**
     * Constructs a new instance.
     * 
     * @param aBeverage
     *            the beverage to decorate
     */
    public WhippedMilk( IBeverage aBeverage ) {
        super( aBeverage );
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.decorator.beveragedecorator.AbstractCondimentDecorator#getDescription()
     */
    @Override
    public String getDescription() {
        return getBeverage().getDescription() + ", Whipped";
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.decorator.IBeverage#cost()
     */
    public double cost() {
        return 0.1d + getBeverage().cost();
    }

}
