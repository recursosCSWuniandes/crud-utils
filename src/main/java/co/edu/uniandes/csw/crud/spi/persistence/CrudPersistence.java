package co.edu.uniandes.csw.crud.spi.persistence;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class CrudPersistence<T> {

    protected abstract EntityManager getEntityManager();

    protected abstract Class<T> getEntityClass();

    public int count() {
        Query count = getEntityManager().createQuery("select count(u) from " + getEntityClass().getSimpleName() + " u");
        return Integer.parseInt(count.getSingleResult().toString());
    }

    public T create(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    public void delete(Long id) {
        T entity = getEntityManager().find(getEntityClass(), id);
        getEntityManager().remove(entity);
    }

    public T find(Long id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    public List<T> findAll() {
        Query q = getEntityManager().createQuery("select u from " + getEntityClass().getSimpleName() + " u");
        return q.getResultList();
    }

    public List<T> findAll(Integer page, Integer maxRecords) {
        Query q = getEntityManager().createQuery("select u from " + getEntityClass().getSimpleName() + " u");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }

    public <V> List<V> executeListNamedQuery(String name) {
        return getEntityManager().createNamedQuery(name).getResultList();
    }

    public <V> List<V> executeListNamedQuery(String name, Map<String, Object> params) {
        Query q = getEntityManager().createNamedQuery(name);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return q.getResultList();
    }

    public <V> V executeSingleNamedQuery(String name) {
        return (V) getEntityManager().createNamedQuery(name).getSingleResult();
    }

    public <V> V executeSingleNamedQuery(String name, Map<String, Object> params) {
        Query q = getEntityManager().createNamedQuery(name);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return (V) q.getSingleResult();
    }

    public List<T> findByName(String name) {
        Query q = getEntityManager().createQuery("select u from " + getEntityClass().getSimpleName() + " u where u.name like :name");
        q.setParameter("name", "%" + name + "%");
        return q.getResultList();
    }

    public List<T> findByName(String name, Integer page, Integer maxRecords) {
        Query q = getEntityManager().createQuery("select u from " + getEntityClass().getSimpleName() + " u where u.name like :name");
        q.setParameter("name", "%" + name + "%");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }
}
