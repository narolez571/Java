/*
 * OrcaReaderSpringInjectionTest.java created on 25 Sep 2009 18:14:12 by suggitpe for project Orca Bridge
 * 
 */
package com.ubs.orca.orcabridge.readers;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ubs.orca.orcabridge.IMessageReader;
import com.ubs.orca.orcabridge.OrcaBridgeException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Test suite to test the JMS Reader Spring Injection.
 * 
 * @author suggitpe
 * @version 1.0 25 Sep 2009
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:xml/ut-jms-reader-spring-injection-test.xml" })
public class JmsReaderSpringInjectionTest {

    private static final Logger LOG = LoggerFactory.getLogger( JmsReaderSpringInjectionTest.class );

    @Resource(name = "jmsReader")
    private IMessageReader jmsReader;

    /** */
    @BeforeClass
    public static void doBeforeClass() {
        LOG.debug( "=================== " + JmsReaderSpringInjectionTest.class.getSimpleName() );
    }

    /** */
    @Before
    public void doBefore() {
        LOG.debug( "------------------- " );
    }

    /** */
    @After
    public void doAfter() {
        LOG.debug( "------------------- " );
    }

    /**
     * Tests that the Orca Reader will work correctly with Spring injection.
     */
    @Test
    public void testSpringInjectedJmsReader() {
        LOG.debug( "Testing that the JMS Reader has been injected correctly" );
        assertThat( jmsReader, notNullValue() );
    }

    /**
     * Tests that the
     * 
     * @throws OrcaBridgeException
     */
    @Test
    public void testStartAndStopJmsReader() throws OrcaBridgeException {
        jmsReader.startReader();
        jmsReader.stopReader();
    }
}
