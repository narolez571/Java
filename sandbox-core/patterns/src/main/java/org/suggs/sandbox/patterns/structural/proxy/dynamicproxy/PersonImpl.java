/*
 * PersonImpl.java created on 14 Sep 2007 06:09:27 by suggitpe for project SandBox - Patterns
 * 
 */
package org.suggs.sandbox.patterns.structural.proxy.dynamicproxy;

/**
 * Concrete implementation of the person bean
 * 
 * @author suggitpe
 * @version 1.0 14 Sep 2007
 */
public class PersonImpl implements IPerson {

    private String name;
    private String gender;
    private String interests;
    private int rating;
    private int ratingCount = 0;

    /**
     * @see org.suggs.sandbox.patterns.structural.proxy.dynamicproxy.IPerson#getGender()
     */
    @Override
    public String getGender() {
        return gender;
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.proxy.dynamicproxy.IPerson#getHotOrNotRating()
     */
    @Override
    public int getHotOrNotRating() {
        if ( ratingCount == 0 ) {
            return 0;
        }
        return rating / ratingCount;
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.proxy.dynamicproxy.IPerson#getInterests()
     */
    @Override
    public String getInterests() {
        return interests;
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.proxy.dynamicproxy.IPerson#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.proxy.dynamicproxy.IPerson#setGender(java.lang.String)
     */
    @Override
    public void setGender( String aGender ) {
        gender = aGender;
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.proxy.dynamicproxy.IPerson#setHotOrNotRating(int)
     */
    @Override
    public void setHotOrNotRating( int hotOrNot ) {
        rating += hotOrNot;
        ++ratingCount;
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.proxy.dynamicproxy.IPerson#setInterests(java.lang.String)
     */
    @Override
    public void setInterests( String aInterests ) {
        interests = aInterests;
    }

    /**
     * @see org.suggs.sandbox.patterns.structural.proxy.dynamicproxy.IPerson#setName(java.lang.String)
     */
    @Override
    public void setName( String aName ) {
        name = aName;
    }
}
