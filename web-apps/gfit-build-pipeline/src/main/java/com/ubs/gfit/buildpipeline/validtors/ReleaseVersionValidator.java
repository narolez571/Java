package com.ubs.gfit.buildpipeline.validtors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ubs.gfit.buildpipeline.domain.ReleaseVersion;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates that a ReleaseVersion is true and complete.
 * <p/>
 * User: suggitpe
 * Date: 06/07/11
 * Time: 10:44
 */

public class ReleaseVersionValidator implements Validator {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger( ReleaseVersionValidator.class );

    @Override
    public boolean supports( Class<?> aClass ) {
        return ReleaseVersion.class.isAssignableFrom( aClass );
    }

    @Override
    public void validate(Object aObject, Errors aErrors){
        ReleaseVersion version = (ReleaseVersion) aObject;
    }


}
