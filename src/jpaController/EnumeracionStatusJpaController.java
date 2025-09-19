/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaController;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Entrada;
import Entity.EnumeracionStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaController.exceptions.IllegalOrphanException;
import jpaController.exceptions.NonexistentEntityException;
import jpaController.exceptions.PreexistingEntityException;

/**
 *
 * @author juanm
 */
public class EnumeracionStatusJpaController implements Serializable {

    public EnumeracionStatusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EnumeracionStatus enumeracionStatus) throws PreexistingEntityException, Exception {
        if (enumeracionStatus.getEntradaCollection() == null) {
            enumeracionStatus.setEntradaCollection(new ArrayList<Entrada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Entrada> attachedEntradaCollection = new ArrayList<Entrada>();
            for (Entrada entradaCollectionEntradaToAttach : enumeracionStatus.getEntradaCollection()) {
                entradaCollectionEntradaToAttach = em.getReference(entradaCollectionEntradaToAttach.getClass(), entradaCollectionEntradaToAttach.getIdEntrada());
                attachedEntradaCollection.add(entradaCollectionEntradaToAttach);
            }
            enumeracionStatus.setEntradaCollection(attachedEntradaCollection);
            em.persist(enumeracionStatus);
            for (Entrada entradaCollectionEntrada : enumeracionStatus.getEntradaCollection()) {
                EnumeracionStatus oldIdStatusOfEntradaCollectionEntrada = entradaCollectionEntrada.getIdStatus();
                entradaCollectionEntrada.setIdStatus(enumeracionStatus);
                entradaCollectionEntrada = em.merge(entradaCollectionEntrada);
                if (oldIdStatusOfEntradaCollectionEntrada != null) {
                    oldIdStatusOfEntradaCollectionEntrada.getEntradaCollection().remove(entradaCollectionEntrada);
                    oldIdStatusOfEntradaCollectionEntrada = em.merge(oldIdStatusOfEntradaCollectionEntrada);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEnumeracionStatus(enumeracionStatus.getIdStatus()) != null) {
                throw new PreexistingEntityException("EnumeracionStatus " + enumeracionStatus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EnumeracionStatus enumeracionStatus) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EnumeracionStatus persistentEnumeracionStatus = em.find(EnumeracionStatus.class, enumeracionStatus.getIdStatus());
            Collection<Entrada> entradaCollectionOld = persistentEnumeracionStatus.getEntradaCollection();
            Collection<Entrada> entradaCollectionNew = enumeracionStatus.getEntradaCollection();
            List<String> illegalOrphanMessages = null;
            for (Entrada entradaCollectionOldEntrada : entradaCollectionOld) {
                if (!entradaCollectionNew.contains(entradaCollectionOldEntrada)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Entrada " + entradaCollectionOldEntrada + " since its idStatus field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Entrada> attachedEntradaCollectionNew = new ArrayList<Entrada>();
            for (Entrada entradaCollectionNewEntradaToAttach : entradaCollectionNew) {
                entradaCollectionNewEntradaToAttach = em.getReference(entradaCollectionNewEntradaToAttach.getClass(), entradaCollectionNewEntradaToAttach.getIdEntrada());
                attachedEntradaCollectionNew.add(entradaCollectionNewEntradaToAttach);
            }
            entradaCollectionNew = attachedEntradaCollectionNew;
            enumeracionStatus.setEntradaCollection(entradaCollectionNew);
            enumeracionStatus = em.merge(enumeracionStatus);
            for (Entrada entradaCollectionNewEntrada : entradaCollectionNew) {
                if (!entradaCollectionOld.contains(entradaCollectionNewEntrada)) {
                    EnumeracionStatus oldIdStatusOfEntradaCollectionNewEntrada = entradaCollectionNewEntrada.getIdStatus();
                    entradaCollectionNewEntrada.setIdStatus(enumeracionStatus);
                    entradaCollectionNewEntrada = em.merge(entradaCollectionNewEntrada);
                    if (oldIdStatusOfEntradaCollectionNewEntrada != null && !oldIdStatusOfEntradaCollectionNewEntrada.equals(enumeracionStatus)) {
                        oldIdStatusOfEntradaCollectionNewEntrada.getEntradaCollection().remove(entradaCollectionNewEntrada);
                        oldIdStatusOfEntradaCollectionNewEntrada = em.merge(oldIdStatusOfEntradaCollectionNewEntrada);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = enumeracionStatus.getIdStatus();
                if (findEnumeracionStatus(id) == null) {
                    throw new NonexistentEntityException("The enumeracionStatus with id " + id + " no longer exists.");
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
            EnumeracionStatus enumeracionStatus;
            try {
                enumeracionStatus = em.getReference(EnumeracionStatus.class, id);
                enumeracionStatus.getIdStatus();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enumeracionStatus with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Entrada> entradaCollectionOrphanCheck = enumeracionStatus.getEntradaCollection();
            for (Entrada entradaCollectionOrphanCheckEntrada : entradaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EnumeracionStatus (" + enumeracionStatus + ") cannot be destroyed since the Entrada " + entradaCollectionOrphanCheckEntrada + " in its entradaCollection field has a non-nullable idStatus field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(enumeracionStatus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EnumeracionStatus> findEnumeracionStatusEntities() {
        return findEnumeracionStatusEntities(true, -1, -1);
    }

    public List<EnumeracionStatus> findEnumeracionStatusEntities(int maxResults, int firstResult) {
        return findEnumeracionStatusEntities(false, maxResults, firstResult);
    }

    private List<EnumeracionStatus> findEnumeracionStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EnumeracionStatus.class));
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

    public EnumeracionStatus findEnumeracionStatus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EnumeracionStatus.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnumeracionStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EnumeracionStatus> rt = cq.from(EnumeracionStatus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
