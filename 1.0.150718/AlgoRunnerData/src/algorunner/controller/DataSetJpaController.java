/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorunner.controller;

import algorunner.controller.exceptions.NonexistentEntityException;
import algorunner.entity.DataSet;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import algorunner.entity.UserQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sandaruwan
 */
public class DataSetJpaController implements Serializable {

    public DataSetJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DataSet dataSet) {
        if (dataSet.getUserQuerys() == null) {
            dataSet.setUserQuerys(new ArrayList<UserQuery>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<UserQuery> attachedUserQuerys = new ArrayList<UserQuery>();
            for (UserQuery userQuerysUserQueryToAttach : dataSet.getUserQuerys()) {
                userQuerysUserQueryToAttach = em.getReference(userQuerysUserQueryToAttach.getClass(), userQuerysUserQueryToAttach.getId());
                attachedUserQuerys.add(userQuerysUserQueryToAttach);
            }
            dataSet.setUserQuerys(attachedUserQuerys);
            em.persist(dataSet);
            for (UserQuery userQuerysUserQuery : dataSet.getUserQuerys()) {
                userQuerysUserQuery.getDataSets().add(dataSet);
                userQuerysUserQuery = em.merge(userQuerysUserQuery);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DataSet dataSet) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DataSet persistentDataSet = em.find(DataSet.class, dataSet.getId());
            List<UserQuery> userQuerysOld = persistentDataSet.getUserQuerys();
            List<UserQuery> userQuerysNew = dataSet.getUserQuerys();
            List<UserQuery> attachedUserQuerysNew = new ArrayList<UserQuery>();
            for (UserQuery userQuerysNewUserQueryToAttach : userQuerysNew) {
                userQuerysNewUserQueryToAttach = em.getReference(userQuerysNewUserQueryToAttach.getClass(), userQuerysNewUserQueryToAttach.getId());
                attachedUserQuerysNew.add(userQuerysNewUserQueryToAttach);
            }
            userQuerysNew = attachedUserQuerysNew;
            dataSet.setUserQuerys(userQuerysNew);
            dataSet = em.merge(dataSet);
            for (UserQuery userQuerysOldUserQuery : userQuerysOld) {
                if (!userQuerysNew.contains(userQuerysOldUserQuery)) {
                    userQuerysOldUserQuery.getDataSets().remove(dataSet);
                    userQuerysOldUserQuery = em.merge(userQuerysOldUserQuery);
                }
            }
            for (UserQuery userQuerysNewUserQuery : userQuerysNew) {
                if (!userQuerysOld.contains(userQuerysNewUserQuery)) {
                    userQuerysNewUserQuery.getDataSets().add(dataSet);
                    userQuerysNewUserQuery = em.merge(userQuerysNewUserQuery);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = dataSet.getId();
                if (findDataSet(id) == null) {
                    throw new NonexistentEntityException("The dataSet with id " + id + " no longer exists.");
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
            DataSet dataSet;
            try {
                dataSet = em.getReference(DataSet.class, id);
                dataSet.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dataSet with id " + id + " no longer exists.", enfe);
            }
            List<UserQuery> userQuerys = dataSet.getUserQuerys();
            for (UserQuery userQuerysUserQuery : userQuerys) {
                userQuerysUserQuery.getDataSets().remove(dataSet);
                userQuerysUserQuery = em.merge(userQuerysUserQuery);
            }
            em.remove(dataSet);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DataSet> findDataSetEntities() {
        return findDataSetEntities(true, -1, -1);
    }

    public List<DataSet> findDataSetEntities(int maxResults, int firstResult) {
        return findDataSetEntities(false, maxResults, firstResult);
    }

    private List<DataSet> findDataSetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DataSet.class));
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

    public DataSet findDataSet(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DataSet.class, id);
        } finally {
            em.close();
        }
    }

    public int getDataSetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DataSet> rt = cq.from(DataSet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
