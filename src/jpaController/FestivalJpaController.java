/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaController;

import Entity.Festival;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Presentacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaController.exceptions.IllegalOrphanException;
import jpaController.exceptions.NonexistentEntityException;

/**
 *
 * @author juanm
 */
public class FestivalJpaController implements Serializable {

    public FestivalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Festival festival) {
        if (festival.getPresentacionCollection() == null) {
            festival.setPresentacionCollection(new ArrayList<Presentacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Presentacion> attachedPresentacionCollection = new ArrayList<Presentacion>();
            for (Presentacion presentacionCollectionPresentacionToAttach : festival.getPresentacionCollection()) {
                presentacionCollectionPresentacionToAttach = em.getReference(presentacionCollectionPresentacionToAttach.getClass(), presentacionCollectionPresentacionToAttach.getIdPresentacion());
                attachedPresentacionCollection.add(presentacionCollectionPresentacionToAttach);
            }
            festival.setPresentacionCollection(attachedPresentacionCollection);
            em.persist(festival);
            for (Presentacion presentacionCollectionPresentacion : festival.getPresentacionCollection()) {
                Festival oldIdFestivalOfPresentacionCollectionPresentacion = presentacionCollectionPresentacion.getIdFestival();
                presentacionCollectionPresentacion.setIdFestival(festival);
                presentacionCollectionPresentacion = em.merge(presentacionCollectionPresentacion);
                if (oldIdFestivalOfPresentacionCollectionPresentacion != null) {
                    oldIdFestivalOfPresentacionCollectionPresentacion.getPresentacionCollection().remove(presentacionCollectionPresentacion);
                    oldIdFestivalOfPresentacionCollectionPresentacion = em.merge(oldIdFestivalOfPresentacionCollectionPresentacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Festival festival) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Festival persistentFestival = em.find(Festival.class, festival.getIdFestival());
            Collection<Presentacion> presentacionCollectionOld = persistentFestival.getPresentacionCollection();
            Collection<Presentacion> presentacionCollectionNew = festival.getPresentacionCollection();
            List<String> illegalOrphanMessages = null;
            for (Presentacion presentacionCollectionOldPresentacion : presentacionCollectionOld) {
                if (!presentacionCollectionNew.contains(presentacionCollectionOldPresentacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Presentacion " + presentacionCollectionOldPresentacion + " since its idFestival field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Presentacion> attachedPresentacionCollectionNew = new ArrayList<Presentacion>();
            for (Presentacion presentacionCollectionNewPresentacionToAttach : presentacionCollectionNew) {
                presentacionCollectionNewPresentacionToAttach = em.getReference(presentacionCollectionNewPresentacionToAttach.getClass(), presentacionCollectionNewPresentacionToAttach.getIdPresentacion());
                attachedPresentacionCollectionNew.add(presentacionCollectionNewPresentacionToAttach);
            }
            presentacionCollectionNew = attachedPresentacionCollectionNew;
            festival.setPresentacionCollection(presentacionCollectionNew);
            festival = em.merge(festival);
            for (Presentacion presentacionCollectionNewPresentacion : presentacionCollectionNew) {
                if (!presentacionCollectionOld.contains(presentacionCollectionNewPresentacion)) {
                    Festival oldIdFestivalOfPresentacionCollectionNewPresentacion = presentacionCollectionNewPresentacion.getIdFestival();
                    presentacionCollectionNewPresentacion.setIdFestival(festival);
                    presentacionCollectionNewPresentacion = em.merge(presentacionCollectionNewPresentacion);
                    if (oldIdFestivalOfPresentacionCollectionNewPresentacion != null && !oldIdFestivalOfPresentacionCollectionNewPresentacion.equals(festival)) {
                        oldIdFestivalOfPresentacionCollectionNewPresentacion.getPresentacionCollection().remove(presentacionCollectionNewPresentacion);
                        oldIdFestivalOfPresentacionCollectionNewPresentacion = em.merge(oldIdFestivalOfPresentacionCollectionNewPresentacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = festival.getIdFestival();
                if (findFestival(id) == null) {
                    throw new NonexistentEntityException("The festival with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Festival festival;
            try {
                festival = em.getReference(Festival.class, id);
                festival.getIdFestival();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The festival with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Presentacion> presentacionCollectionOrphanCheck = festival.getPresentacionCollection();
            for (Presentacion presentacionCollectionOrphanCheckPresentacion : presentacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Festival (" + festival + ") cannot be destroyed since the Presentacion " + presentacionCollectionOrphanCheckPresentacion + " in its presentacionCollection field has a non-nullable idFestival field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(festival);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Festival> findFestivalEntities() {
        return findFestivalEntities(true, -1, -1);
    }

    public List<Festival> findFestivalEntities(int maxResults, int firstResult) {
        return findFestivalEntities(false, maxResults, firstResult);
    }

    private List<Festival> findFestivalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Festival.class));
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

    public Festival findFestival(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Festival.class, id);
        } finally {
            em.close();
        }
    }

    public int getFestivalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Festival> rt = cq.from(Festival.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
