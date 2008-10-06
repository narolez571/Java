/*
 * IMercuryXmlFilePersister.java created on 3 Oct 2008 18:48:46 by suggitpe for project GUI - Mercury
 * 
 */
package org.suggs.apps.mercury.model.util;

import org.suggs.apps.mercury.model.connection.connectionstore.ConnectionStoreException;

import java.io.File;
import java.io.IOException;

/**
 * This interface manages the persistence of all files to the
 * underlying operating system. The interfaces here are at the file
 * level and have no concept of the use of these files.
 * 
 * @author suggitpe
 * @version 1.0 3 Oct 2008
 */
public interface IMercuryFileManager
{

    /**
     * This method will allow any string to be persisted to a given
     * file location. It will also verify that the persistence layer
     * is correctly set up
     * 
     * @param aClob
     *            the string to create the file with
     * @param aFile
     *            the file that you want to use to persist the file
     * @throws ConnectionStoreException
     *             if there are any issues in the persistence layer
     */
    void persistClob( String aClob, File aFile ) throws IOException;

}
