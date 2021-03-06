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
import com.ubs.orca.orcabridge.support.OrcaClientTestStub;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Test suite to test the Orca Reader Spring Injection.
 * 
 * @author suggitpe
 * @version 1.0 25 Sep 2009
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:xml/ut-orca-reader-spring-injection-test.xml" })
public class OrcaReaderSpringInjectionTest {

    private static final Logger LOG = LoggerFactory.getLogger( OrcaReaderSpringInjectionTest.class );

    @Resource(name = "orcaReader")
    private IMessageReader orcaReader;

    /** */
    @BeforeClass
    public static void doBeforeClass() {
        LOG.debug( "=================== " + OrcaReaderSpringInjectionTest.class.getSimpleName() );
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
    public void testSpringInjectedOrcaReader() {
        LOG.debug( "Testing that the Orca Reader has been injected correctly" );
        assertThat( orcaReader, notNullValue() );
    }

    /**
     * Tests that the
     * 
     * @throws OrcaBridgeException
     */
    @Test
    public void testStartAndStopOrcaReader() throws OrcaBridgeException {
        OrcaSingleMessageReader reader = (OrcaSingleMessageReader) orcaReader;
        reader.setOrcaClient( new OrcaClientTestStub() );
        orcaReader.startReader();
        orcaReader.stopReader();
    }
}
