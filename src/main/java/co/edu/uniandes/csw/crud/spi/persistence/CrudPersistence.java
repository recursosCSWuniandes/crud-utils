package co.edu.uniandes.csw.crud.spi.persistence;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class CrudPersistence<T> {

    protected EntityManager em;

    protected Class<T> entityClass;

    public int count() {
        Query count = em.createQuery("select count(u) from " + entityClass.getSimpleName() + " u");
        return Integer.parseInt(count.getSingleResult().toString());
    }

    public T create(T entity) {
        em.persist(entity);
        return entity;
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public void delete(Long id) {
        T entity = em.find(entityClass, id);
        em.remove(entity);
    }

    public T find(Long id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll(Integer page, Integer maxRecords) {
        Query q = em.createQuery("select u from " + entityClass.getSimpleName() + " u");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }

    public <V> List<V> executeListNamedQuery(String name) {
        return em.createNamedQuery(name).getResultList();
    }

    public <V> List<V> executeListNamedQuery(String name, Map<String, Object> params) {
        Query q = em.createNamedQuery(name);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return q.getResultList();
    }

    public <V> V executeSingleNamedQuery(String name) {
        return (V) em.createNamedQuery(name).getSingleResult();
    }

    public <V> V executeSingleNamedQuery(String name, Map<String, Object> params) {
        Query q = em.createNamedQuery(name);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return (V) q.getSingleResult();
    }

    public List<T> findByName(String name) {
        Query q = em.createQuery("select u from " + entityClass.getSimpleName() + " u where u.name like :name");
        q.setParameter("name", "%" + name + "%");
        return q.getResultList();
    }
}
