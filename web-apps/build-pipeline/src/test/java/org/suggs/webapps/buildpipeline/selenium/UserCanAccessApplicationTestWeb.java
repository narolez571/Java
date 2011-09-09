package org.suggs.webapps.buildpipeline.selenium;

import org.suggs.webapps.buildpipeline.dsl.DSL;
import org.suggs.webapps.buildpipeline.pages.SeleniumPages;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Basic Test to prove that the application is up and running and can be accessed.  Treat this as a litmus test for the
 * application being deployed and accessible.
 * <p/>
 * User: suggitpe
 * Date: 11/08/11
 * Time: 18:35
 */

public final class UserCanAccessApplicationTestWeb extends DSL {

    public UserCanAccessApplicationTestWeb() {
        super( new SeleniumPages() );
    }

    @Test
    public void shouldBeAbleToAccessApplication() {
        openApplication();
        assertThat( applicationIsOpen(), is( true ) );
    }

}
