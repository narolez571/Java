/*
 * LegalEntityHibernateIntegrationTest.java created on 28 Sep 2010 20:54:47 by suggitpe for project masterfiles-receiver
 * 
 */
package com.ubs.reporting.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ubs.reporting.support.AbstractSimpleHibernateIntegrationTest;
import com.ubs.reporting.support.TestUtils;

import org.springframework.test.context.ContextConfiguration;

import org.hibernate.Session;

/**
 * Hibernate integration test for a LegalEntity object.
 * 
 * @author suggitpe
 * @version 1.0 28 Sep 2010
 */
@ContextConfiguration(locations = { "classpath:spring/hibernate-masterfiles.xml" })
public class LegalEntityHibernateIntegrationTest extends AbstractSimpleHibernateIntegrationTest<LegalEntityKey, LegalEntity> {

    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog( LegalEntityHibernateIntegrationTest.class );

    /**
     * @see com.ubs.reporting.support.AbstractSimpleHibernateIntegrationTest#cleanUpData(org.hibernate.Session)
     */
    @Override
    protected void cleanUpData( Session aSession ) {
        aSession.createQuery( "delete from LegalEntity where legalEntityKey.legalEntityDomain ='TEST_DOMAIN'" )
            .executeUpdate();
    }

    /**
     * @see com.ubs.reporting.support.AbstractSimpleHibernateIntegrationTest#createKeyTemplate()
     */
    @Override
    protected LegalEntityKey createKeyTemplate() {
        return new LegalEntityKey( Integer.valueOf( 99999 ), "TEST_DOMAIN", Integer.valueOf( 1 ) );
    }

    /**
     * @see com.ubs.reporting.support.AbstractSimpleHibernateIntegrationTest#createEntityTemplate(java.io.Serializable,
     *      org.hibernate.Session)
     */
    @Override
    protected LegalEntity createEntityTemplate( LegalEntityKey aKey, Session aSession ) {
        LegalEntity entityToReturn = new LegalEntity( aKey, "Some Legal Name" );
        entityToReturn.setCountryDomicile( "A country domicile" );
        entityToReturn.setCountryIncorporation( "A country incorporation" );
        entityToReturn.setClassCode( "Class code" );
        entityToReturn.setClassType( "Class Type" );
        entityToReturn.setLegalEntityStatusUpdateDate( TestUtils.createDateFrom_ddmmyyyy( "01102010" ) );
        entityToReturn.setLegalEntityStatus( "a Legal entity Status" );
        entityToReturn.setClientClass( "A client class" );
        entityToReturn.setHasCollateral( "Y" );
        entityToReturn.setCountryOfRisk( "Some country " );
        entityToReturn.setIsConfidential( "Y" );
        entityToReturn.setInternationalRatingCode( "Some rating code" );
        entityToReturn.setInternationalRatingReviewDate( TestUtils.createDateFrom_ddmmyyyy( "01012009" ) );
        entityToReturn.setPortfolioSegment( "a portfolio segment" );
        entityToReturn.setRatingApproach( "a rating approach" );
        entityToReturn.setBoeCode( "a boe code" );
        entityToReturn.setBoeType( "a boe type" );
        entityToReturn.setCaCode( "a ca code" );
        entityToReturn.setCaType( "a ca type" );
        entityToReturn.setCtCode( "a ct code" );
        entityToReturn.setCtType( "a ct type" );
        entityToReturn.setLegacyId( "This is a legacy ID" );
        return entityToReturn;
    }

    /**
     * @see com.ubs.reporting.support.AbstractSimpleHibernateIntegrationTest#updateEntityForUpdateTest(java.lang.Object)
     */
    @Override
    protected void updateEntityForUpdateTest( LegalEntity aEntity ) {
        aEntity.setBoeCode( "This is a new BOE Code" );
    }

    /**
     * @see com.ubs.reporting.support.AbstractSimpleHibernateIntegrationTest#createEntitySearchHql()
     */
    @Override
    protected String createEntitySearchHql() {
        return "from LegalEntity where legalEntityKey.legalEntityDomain = 'TEST_DOMAIN'";
    }
}
