/*
 * HibernateTimestampEntityIntegrationTest.java created on 25 Mar 2010 07:03:30 by suggitpe for project sandbox-hibernate
 * 
 */
package org.suggs.sandbox.hibernate.timestamps;

import org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest;
import org.suggs.sandbox.hibernate.support.IHibernateIntegrationTestCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.test.context.ContextConfiguration;

import org.hibernate.Criteria;
import org.hibernate.Session;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Integration test to demonstrate how to use timestamps in a
 * Hibernate entity.
 * 
 * @author suggitpe
 * @version 1.0 25 Mar 2010
 */
@ContextConfiguration(locations = { "classpath:xml/ut-annotation-timestamps.xml" })
public class HibernateTimestampEntityIntegrationTest extends AbstractSimpleHibernateIntegrationTest {

    private static final Log LOG = LogFactory.getLog( HibernateTimestampEntityIntegrationTest.class );
    private static final String TEST_HQL = "from TimestampedEntity where someString in ('deleteMe', 'altered')";

    /**
     * @see org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest#cleanUpData(org.hibernate.Session)
     */
    @Override
    protected void cleanUpData( Session aSession ) {
        aSession.createQuery( "delete " + TEST_HQL ).executeUpdate();
    }

    /**
     * @see org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest#getEntityList()
     */
    @Override
    protected List<Class<?>> getEntityListForSchemaCreation() {
        List<Class<?>> entityClassses = new ArrayList<Class<?>>();
        entityClassses.add( TimestampedEntity.class );
        return entityClassses;
    }

    /**
     * @see org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest#createBasicCreateTest()
     */
    @Override
    protected IHibernateIntegrationTestCallback createBasicCreateTest() {
        return new IHibernateIntegrationTestCallback() {

            TimestampedEntity entity = buildTimeStampedEntityTemplate();

            @Override
            public void beforeTest( Session aSession ) {
                verifyEntityCount( aSession, 0L );
                debugTimestampedEntities( aSession );
            }

            @Override
            public void executeTest( Session aSession ) {
                aSession.save( entity );
            }

            @Override
            public void verifyTest( Session aSession ) {
                verifyEntityCount( aSession, 1L );
                debugTimestampedEntities( aSession );
            }

        };
    }

    /**
     * @see org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest#createBasicDeleteTest()
     */
    @Override
    protected IHibernateIntegrationTestCallback createBasicDeleteTest() {
        return new IHibernateIntegrationTestCallback() {

            @Override
            public void beforeTest( Session aSession ) {
                aSession.save( buildTimeStampedEntityTemplate() );
                verifyEntityCount( aSession, 1L );
                debugTimestampedEntities( aSession );
            }

            @SuppressWarnings("boxing")
            @Override
            public void executeTest( Session aSession ) {
                TimestampedEntity entity = (TimestampedEntity) aSession.createQuery( TEST_HQL )
                    .uniqueResult();
                aSession.delete( entity.getId() );
            }

            @Override
            public void verifyTest( Session aSession ) {
                verifyEntityCount( aSession, 0L );
                debugTimestampedEntities( aSession );
            }
        };
    }

    /**
     * @see org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest#createBasicReadTest()
     */
    @Override
    protected IHibernateIntegrationTestCallback createBasicReadTest() {
        return new IHibernateIntegrationTestCallback() {

            private Long theId = Long.valueOf( 0L );
            TimestampedEntity entity = buildTimeStampedEntityTemplate();
            TimestampedEntity readEntity = null;

            @SuppressWarnings("boxing")
            @Override
            public void beforeTest( Session aSession ) {
                aSession.save( entity );
                entity = (TimestampedEntity) aSession.createQuery( TEST_HQL ).uniqueResult();
                theId = entity.getId();
                verifyEntityCount( aSession, 1L );
                debugTimestampedEntities( aSession );
            }

            @Override
            public void executeTest( Session aSession ) {
                readEntity = (TimestampedEntity) aSession.get( TimestampedEntity.class, theId );
            }

            @Override
            public void verifyTest( Session aSession ) {
                assertThat( readEntity, not( nullValue() ) );
                assertThat( readEntity, not( sameInstance( entity ) ) );
                assertThat( readEntity, equalTo( entity ) );
                debugTimestampedEntities( aSession );
            }
        };
    }

    /**
     * @see org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest#createBasicUpdateTest()
     */
    @Override
    protected IHibernateIntegrationTestCallback createBasicUpdateTest() {
        return new IHibernateIntegrationTestCallback() {

            TimestampedEntity entity = buildTimeStampedEntityTemplate();
            TimestampedEntity clone = new TimestampedEntity( entity.getSomeString(),
                                                             entity.getSomeDate(),
                                                             entity.getSomeInteger() );

            @Override
            public void beforeTest( Session aSession ) {
                aSession.save( entity );
                verifyEntityCount( aSession, 1L );
                debugTimestampedEntities( aSession );
            }

            @Override
            public void executeTest( Session aSession ) {
                entity = (TimestampedEntity) aSession.createQuery( TEST_HQL ).uniqueResult();
                entity.setSomeString( "altered" );
                aSession.save( entity );
            }

            @Override
            public void verifyTest( Session aSession ) {
                // refresh entity from db
                entity = (TimestampedEntity) aSession.createQuery( TEST_HQL ).uniqueResult();
                assertThat( entity, not( nullValue() ) );
                assertThat( entity, not( sameInstance( clone ) ) );
                assertThat( entity, not( equalTo( clone ) ) );
                assertThat( entity.getSomeInteger(), equalTo( clone.getSomeInteger() ) );
                assertThat( entity.getSomeDate(), equalTo( clone.getSomeDate() ) );
                assertThat( entity.getSomeString(), not( equalTo( clone.getSomeString() ) ) );
                debugTimestampedEntities( aSession );
            }
        };
    }

    private TimestampedEntity buildTimeStampedEntityTemplate() {
        return new TimestampedEntity( "deleteMe",
                                      Calendar.getInstance().getTime(),
                                      Integer.valueOf( 9876 ) );
    }

    private void verifyEntityCount( Session aSession, long aCountOfEntities ) {
        Long count = (Long) aSession.createQuery( "select count(*) " + TEST_HQL ).uniqueResult();
        assertThat( count, equalTo( Long.valueOf( aCountOfEntities ) ) );
    }

    @SuppressWarnings("unchecked")
    private void debugTimestampedEntities( Session aSession ) {
        Criteria criteria = aSession.createCriteria( TimestampedEntity.class );
        List<TimestampedEntity> entityList = criteria.list();
        LOG.debug( "****** Entity debug" );
        for ( TimestampedEntity tse : entityList ) {
            LOG.debug( tse );
        }
        LOG.debug( "******" );

    }
}
