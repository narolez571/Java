/*
 * HibernateTimestampEntityIntegrationTest.java created on 25 Mar 2010 07:03:30 by suggitpe for project sandbox-hibernate
 * 
 */
package org.suggs.sandbox.hibernate.timestamps;

import org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest;
import org.suggs.sandbox.hibernate.support.HibernateIntegrationTestCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import org.springframework.test.context.ContextConfiguration;

import org.hibernate.Session;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Integration test to demonstrate how to use timestamps in a Hibernate entity.
 * 
 * @author suggitpe
 * @version 1.0 25 Mar 2010
 */
@ContextConfiguration(locations = { "classpath:xml/ut-annotation-timestamps.xml" })
public class HibernateTimestampEntityIntegrationTest extends AbstractSimpleHibernateIntegrationTest<Long, TimestampedEntity> {

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
        entityClassses.add( TimestampedChildEntity.class );
        return entityClassses;
    }

    /**
     * @see org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest#createKeyTemplate()
     */
    @Override
    protected Long createKeyTemplate() {
        // this is actually not needed for this entity.
        return null;
    }

    /**
     * @see org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest#createEntityTemplate(java.lang.Object)
     */
    @Override
    protected TimestampedEntity createEntityTemplate( Long aKey ) {
        TimestampedEntity entity = new TimestampedEntity( "deleteMe",
                                                          Calendar.getInstance().getTime(),
                                                          Integer.valueOf( 9876 ) );
        return entity;
    }

    /**
     * @see org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest#createEntitySearchHql()
     */
    @Override
    protected String createEntitySearchHql() {
        return TEST_HQL;
    }

    /**
     * @see org.suggs.sandbox.hibernate.support.AbstractSimpleHibernateIntegrationTest#updateEntityForUpdateTest(java.lang.Object)
     */
    @Override
    protected void updateEntityForUpdateTest( TimestampedEntity aEntity ) {
        aEntity.setSomeString( "altered" );
    }

    @Test
    public void creationOfNewObjectPopulatesCreateAndUpdateDatesAndThatTheyAreSameValue() {
        runGenericTest( new HibernateIntegrationTestCallback() {

            TimestampedEntity entity = createEntityTemplate( createKeyTemplate() );

            @Override
            public void beforeTest( Session aSession ) {
                verifyEntityCount( aSession, 0L );
            }

            @Override
            public void executeTest( Session aSession ) {
                aSession.save( entity );
            }

            @Override
            public void verifyTest( Session aSession ) {
                TimestampedEntity result = (TimestampedEntity) aSession.createQuery( TEST_HQL )
                    .uniqueResult();
                assertThat( entity.getTimestampAuditInfo().getCreateDate(),
                            not( equalTo( result.getTimestampAuditInfo().getCreateDate() ) ) );
                assertThat( entity.getTimestampAuditInfo().getUpdateDate(),
                            not( equalTo( result.getTimestampAuditInfo().getUpdateDate() ) ) );
                assertThat( result.getTimestampAuditInfo().getCreateDate(),
                            equalTo( result.getTimestampAuditInfo().getUpdateDate() ) );

                verifyEntityCount( aSession, 1L );
            }
        } );
    }

    @Test
    public void updateOfExistingObjectPopulatesUpdateDateAndThatCreateDateDiffers() {
        runGenericTest( new HibernateIntegrationTestCallback() {

            TimestampedEntity entity = createEntityTemplate( createKeyTemplate() );

            @Override
            public void beforeTest( Session aSession ) {
                aSession.save( entity );
                verifyEntityCount( aSession, 1L );
            }

            @Override
            public void executeTest( Session aSession ) {
                TimestampedEntity toUpdate = (TimestampedEntity) aSession.createQuery( TEST_HQL )
                    .uniqueResult();
                toUpdate.setSomeInteger( Integer.valueOf( 23 ) );
                aSession.save( toUpdate );
            }

            @Override
            public void verifyTest( Session aSession ) {
                TimestampedEntity result = (TimestampedEntity) aSession.createQuery( TEST_HQL )
                    .uniqueResult();
                assertThat( result, not( nullValue() ) );
                assertThat( result.getTimestampAuditInfo().getCreateDate(),
                            not( equalTo( result.getTimestampAuditInfo().getUpdateDate() ) ) );
            }
        } );
    }

}
