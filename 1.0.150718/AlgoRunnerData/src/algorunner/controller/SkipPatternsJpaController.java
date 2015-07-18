/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorunner.controller;

import algorunner.controller.exceptions.NonexistentEntityException;
import algorunner.entity.SkipPatterns;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import algorunner.entity.UserQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sandaruwan
 */
public class SkipPatternsJpaController implements Serializable {

    public SkipPatternsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SkipPatterns skipPatterns) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserQuery userQuery = skipPatterns.getUserQuery();
            if (userQuery != null) {
                userQuery = em.getReference(userQuery.getClass(), userQuery.getId());
                skipPatterns.setUserQuery(userQuery);
            }
            em.persist(skipPatterns);
            if (userQuery != null) {
                userQuery.getSkipPatternss().add(skipPatterns);
                userQuery = em.merge(userQuery);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SkipPatterns skipPatterns) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SkipPatterns persistentSkipPatterns = em.find(SkipPatterns.class, skipPatterns.getId());
            UserQuery userQueryOld = persistentSkipPatterns.getUserQuery();
            UserQuery userQueryNew = skipPatterns.getUserQuery();
            if (userQueryNew != null) {
                userQueryNew = em.getReference(userQueryNew.getClass(), userQueryNew.getId());
                skipPatterns.setUserQuery(userQueryNew);
            }
            skipPatterns = em.merge(skipPatterns);
            if (userQueryOld != null && !userQueryOld.equals(userQueryNew)) {
                userQueryOld.getSkipPatternss().remove(skipPatterns);
                userQueryOld = em.merge(userQueryOld);
            }
            if (userQueryNew != null && !userQueryNew.equals(userQueryOld)) {
                userQueryNew.getSkipPatternss().add(skipPatterns);
                userQueryNew = em.merge(userQueryNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = skipPatterns.getId();
                if (findSkipPatterns(id) == null) {
                    throw new NonexistentEntityException("The skipPatterns with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SkipPatterns skipPatterns;
            try {
                skipPatterns = em.getReference(SkipPatterns.class, id);
                skipPatterns.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The skipPatterns with id " + id + " no longer exists.", enfe);
            }
            UserQuery userQuery = skipPatterns.getUserQuery();
            if (userQuery != null) {
                userQuery.getSkipPatternss().remove(skipPatterns);
                userQuery = em.merge(userQuery);
            }
            em.remove(skipPatterns);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SkipPatterns> findSkipPatternsEntities() {
        return findSkipPatternsEntities(true, -1, -1);
    }

    public List<SkipPatterns> findSkipPatternsEntities(int maxResults, int firstResult) {
        return findSkipPatternsEntities(false, maxResults, firstResult);
    }

    private List<SkipPatterns> findSkipPatternsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SkipPatterns.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SkipPatterns findSkipPatterns(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SkipPatterns.class, id);
        } finally {
            em.close();
        }
    }

    public int getSkipPatternsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SkipPatterns> rt = cq.from(SkipPatterns.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
