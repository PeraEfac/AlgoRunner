/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorunner.controller;

import algorunner.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import algorunner.entity.SkipPatterns;
import java.util.ArrayList;
import java.util.List;
import algorunner.entity.DataSet;
import algorunner.entity.UserQuery;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sandaruwan
 */
public class UserQueryJpaController implements Serializable {

    public UserQueryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserQuery userQuery) {
        if (userQuery.getSkipPatternss() == null) {
            userQuery.setSkipPatternss(new ArrayList<SkipPatterns>());
        }
        if (userQuery.getDataSets() == null) {
            userQuery.setDataSets(new ArrayList<DataSet>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SkipPatterns> attachedSkipPatternss = new ArrayList<SkipPatterns>();
            for (SkipPatterns skipPatternssSkipPatternsToAttach : userQuery.getSkipPatternss()) {
                skipPatternssSkipPatternsToAttach = em.getReference(skipPatternssSkipPatternsToAttach.getClass(), skipPatternssSkipPatternsToAttach.getId());
                attachedSkipPatternss.add(skipPatternssSkipPatternsToAttach);
            }
            userQuery.setSkipPatternss(attachedSkipPatternss);
            List<DataSet> attachedDataSets = new ArrayList<DataSet>();
            for (DataSet dataSetsDataSetToAttach : userQuery.getDataSets()) {
                dataSetsDataSetToAttach = em.getReference(dataSetsDataSetToAttach.getClass(), dataSetsDataSetToAttach.getId());
                attachedDataSets.add(dataSetsDataSetToAttach);
            }
            userQuery.setDataSets(attachedDataSets);
            em.persist(userQuery);
            for (SkipPatterns skipPatternssSkipPatterns : userQuery.getSkipPatternss()) {
                UserQuery oldUserQueryOfSkipPatternssSkipPatterns = skipPatternssSkipPatterns.getUserQuery();
                skipPatternssSkipPatterns.setUserQuery(userQuery);
                skipPatternssSkipPatterns = em.merge(skipPatternssSkipPatterns);
                if (oldUserQueryOfSkipPatternssSkipPatterns != null) {
                    oldUserQueryOfSkipPatternssSkipPatterns.getSkipPatternss().remove(skipPatternssSkipPatterns);
                    oldUserQueryOfSkipPatternssSkipPatterns = em.merge(oldUserQueryOfSkipPatternssSkipPatterns);
                }
            }
            for (DataSet dataSetsDataSet : userQuery.getDataSets()) {
                dataSetsDataSet.getUserQuerys().add(userQuery);
                dataSetsDataSet = em.merge(dataSetsDataSet);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserQuery userQuery) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserQuery persistentUserQuery = em.find(UserQuery.class, userQuery.getId());
            List<SkipPatterns> skipPatternssOld = persistentUserQuery.getSkipPatternss();
            List<SkipPatterns> skipPatternssNew = userQuery.getSkipPatternss();
            List<DataSet> dataSetsOld = persistentUserQuery.getDataSets();
            List<DataSet> dataSetsNew = userQuery.getDataSets();
            List<SkipPatterns> attachedSkipPatternssNew = new ArrayList<SkipPatterns>();
            for (SkipPatterns skipPatternssNewSkipPatternsToAttach : skipPatternssNew) {
                skipPatternssNewSkipPatternsToAttach = em.getReference(skipPatternssNewSkipPatternsToAttach.getClass(), skipPatternssNewSkipPatternsToAttach.getId());
                attachedSkipPatternssNew.add(skipPatternssNewSkipPatternsToAttach);
            }
            skipPatternssNew = attachedSkipPatternssNew;
            userQuery.setSkipPatternss(skipPatternssNew);
            List<DataSet> attachedDataSetsNew = new ArrayList<DataSet>();
            for (DataSet dataSetsNewDataSetToAttach : dataSetsNew) {
                dataSetsNewDataSetToAttach = em.getReference(dataSetsNewDataSetToAttach.getClass(), dataSetsNewDataSetToAttach.getId());
                attachedDataSetsNew.add(dataSetsNewDataSetToAttach);
            }
            dataSetsNew = attachedDataSetsNew;
            userQuery.setDataSets(dataSetsNew);
            userQuery = em.merge(userQuery);
            for (SkipPatterns skipPatternssOldSkipPatterns : skipPatternssOld) {
                if (!skipPatternssNew.contains(skipPatternssOldSkipPatterns)) {
                    skipPatternssOldSkipPatterns.setUserQuery(null);
                    skipPatternssOldSkipPatterns = em.merge(skipPatternssOldSkipPatterns);
                }
            }
            for (SkipPatterns skipPatternssNewSkipPatterns : skipPatternssNew) {
                if (!skipPatternssOld.contains(skipPatternssNewSkipPatterns)) {
                    UserQuery oldUserQueryOfSkipPatternssNewSkipPatterns = skipPatternssNewSkipPatterns.getUserQuery();
                    skipPatternssNewSkipPatterns.setUserQuery(userQuery);
                    skipPatternssNewSkipPatterns = em.merge(skipPatternssNewSkipPatterns);
                    if (oldUserQueryOfSkipPatternssNewSkipPatterns != null && !oldUserQueryOfSkipPatternssNewSkipPatterns.equals(userQuery)) {
                        oldUserQueryOfSkipPatternssNewSkipPatterns.getSkipPatternss().remove(skipPatternssNewSkipPatterns);
                        oldUserQueryOfSkipPatternssNewSkipPatterns = em.merge(oldUserQueryOfSkipPatternssNewSkipPatterns);
                    }
                }
            }
            for (DataSet dataSetsOldDataSet : dataSetsOld) {
                if (!dataSetsNew.contains(dataSetsOldDataSet)) {
                    dataSetsOldDataSet.getUserQuerys().remove(userQuery);
                    dataSetsOldDataSet = em.merge(dataSetsOldDataSet);
                }
            }
            for (DataSet dataSetsNewDataSet : dataSetsNew) {
                if (!dataSetsOld.contains(dataSetsNewDataSet)) {
                    dataSetsNewDataSet.getUserQuerys().add(userQuery);
                    dataSetsNewDataSet = em.merge(dataSetsNewDataSet);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = userQuery.getId();
                if (findUserQuery(id) == null) {
                    throw new NonexistentEntityException("The userQuery with id " + id + " no longer exists.");
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
            UserQuery userQuery;
            try {
                userQuery = em.getReference(UserQuery.class, id);
                userQuery.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userQuery with id " + id + " no longer exists.", enfe);
            }
            List<SkipPatterns> skipPatternss = userQuery.getSkipPatternss();
            for (SkipPatterns skipPatternssSkipPatterns : skipPatternss) {
                skipPatternssSkipPatterns.setUserQuery(null);
                skipPatternssSkipPatterns = em.merge(skipPatternssSkipPatterns);
            }
            List<DataSet> dataSets = userQuery.getDataSets();
            for (DataSet dataSetsDataSet : dataSets) {
                dataSetsDataSet.getUserQuerys().remove(userQuery);
                dataSetsDataSet = em.merge(dataSetsDataSet);
            }
            em.remove(userQuery);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserQuery> findUserQueryEntities() {
        return findUserQueryEntities(true, -1, -1);
    }

    public List<UserQuery> findUserQueryEntities(int maxResults, int firstResult) {
        return findUserQueryEntities(false, maxResults, firstResult);
    }

    private List<UserQuery> findUserQueryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserQuery.class));
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

    public UserQuery findUserQuery(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserQuery.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserQueryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserQuery> rt = cq.from(UserQuery.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
