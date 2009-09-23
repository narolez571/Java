/*
 * AllTests.java created on 24 Aug 2009 06:54:16 by suggitpe for project Libraries - State Machine
 * 
 */
package org.suggs.libs.statemachine;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * All Tests suite for the state machine library.
 * 
 * @author suggitpe
 * @version 1.0 24 Aug 2009
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { AllUnitTests.class, AllIntegrationTests.class })
public class AllTests
{}