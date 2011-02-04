package org.suggs.sandbox_webapps.springmvcpersistenttest.dao.jpa;

import org.suggs.sandbox_webapps.springmvcpersistenttest.dao.GenericDao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

/**
 * This class serves as the Base class for all other DAOs - namely to hold common CRUD methods that they might
 * all use. You should only need to extend this class when your require custom CRUD logic.
 */
@Transactional
public abstract class AbstractJpaDao<PK extends Serializable, T> implements GenericDao<PK, T> {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger( AbstractJpaDao.class );

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> persistentClass;

    /**
     * Constructs a new instance.
     * 
     * @param persistentClass
     */
    public AbstractJpaDao( final Class<T> persistentClass ) {
        this.persistentClass = persistentClass;
    }

    @Transactional(readOnly = true)
    @Override
    public T get( PK id ) {
        return entityManager.find( persistentClass, id );
    }

    @Override
    public boolean exists( PK id ) {
        return get( id ) != null;
    }

    @Override
    public void save( T object ) {
        entityManager.persist( object );
    }

    @Override
    public void remove( T object ) {
        entityManager.remove( object );
    }

    public void setEntityManager( EntityManager entityManager ) {
        this.entityManager = entityManager;
    }
}
