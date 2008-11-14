/*
 * TibcoEmsAdapter.java created on 10 Nov 2008 07:54:43 by suggitpe for project GUI - Mercury
 * 
 */
package org.suggs.apps.mercury.model.adapters.ibmmq;

import org.suggs.apps.mercury.model.adapters.support.AbstractMercuryAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Main adapter class for the IBM MQ middleware impl
 * 
 * @author suggitpe
 * @version 1.0 10 Nov 2008
 */
public class IbmMqAdapter extends AbstractMercuryAdapter
{

    public static final String TYPE = "IBM_MQ";

    private static final Log LOG = LogFactory.getLog( IbmMqAdapter.class );
    private static final String FRIENDLY_NAME = "IBM MQ Adapter";

    /**
     * @see org.suggs.apps.mercury.model.adapters.IMercuryConnectionAdapter#getFriendlyName()
     */
    public String getFriendlyName()
    {
        return FRIENDLY_NAME;
    }

    /**
     * @see org.suggs.apps.mercury.model.adapters.IMercuryConnectionAdapter#getType()
     */
    public String getType()
    {
        return TYPE;
    }

}
