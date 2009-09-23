/*
 * AllIntegrationTests.java created on 24 Aug 2009 06:56:35 by suggitpe for project Libraries - State Machine
 * 
 */
package org.suggs.libs.statemachine;

import org.suggs.libs.statemachine.integration.StateMachineConnectionIntegrationTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * All Integration Tests suite for the state machine library
 * 
 * @author suggitpe
 * @version 1.0 24 Aug 2009
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { StateMachineConnectionIntegrationTest.class })
public class AllIntegrationTests
{

    private static final Log LOG = LogFactory.getLog( AllIntegrationTests.class );

    @BeforeClass
    public static void doBefore()
    {
        LOG.debug( "================== STATE MACHINE LIBRARY INTEGRATION TESTS" );
    }

}