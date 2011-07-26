package com.ubs.gfit.buildpipeline.domain.component;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to encapsulate the available component versions.
 * <p/>
 * User: suggitpe
 * Date: 12/07/11
 * Time: 08:17
 */

public class ComponentVersionsBean {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger( ComponentVersionsBean.class );

    private Map<ComponentBean, List<String>> componentVersions = new HashMap<ComponentBean, List<String>>();

    public List<String> getVersionsForComponent( String aComponent ) {
        return componentVersions.get( new ComponentBean( aComponent ) );
    }

    public Map<ComponentBean, List<String>> getComponentVersions() {
        return componentVersions;
    }

    public List<ComponentBean> getComponents() {
        LOG.debug("Getting components" );
        return getComponentsWithTestSuiteFlag( false );
    }

    public List<ComponentBean> getTestSuites() {
        LOG.debug("Getting test suites" );
        return getComponentsWithTestSuiteFlag( true );
    }

    private List<ComponentBean> getComponentsWithTestSuiteFlag( final Boolean aTestSuiteFlagToMatch ) {
        List<ComponentBean> keys = new ArrayList<ComponentBean>( componentVersions.keySet() );
        CollectionUtils.filter( keys, new org.apache.commons.collections.Predicate() {
            @Override
            public boolean evaluate( Object object ) {
                return ( ( ComponentBean ) object ).isTestSuite() == aTestSuiteFlagToMatch;
            }
        } );
        Collections.sort( keys );
        return keys;
    }

    public void setVersionsForComponent( ComponentBean aComponent, List<String> aListOfVersions ) {
        if ( componentVersions.containsKey( aComponent ) ) {
            componentVersions.remove( aComponent );
        }
        componentVersions.put( aComponent, aListOfVersions );
    }

    public void addVersion( ComponentBean aComponent, String aVersion ) {
        if ( !componentVersions.containsKey( aComponent ) ) {
            componentVersions.put( aComponent, new ArrayList<String>() );
        }
        componentVersions.get( aComponent ).add( aVersion );
    }
}
