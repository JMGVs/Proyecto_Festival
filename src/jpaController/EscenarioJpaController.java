/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaController;

import Entity.Escenario;
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
public class EscenarioJpaController implements Serializable {

    public EscenarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Escenario escenario) {
        if (escenario.getPresentacionCollection() == null) {
            escenario.setPresentacionCollection(new ArrayList<Presentacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Presentacion> attachedPresentacionCollection = new ArrayList<Presentacion>();
            for (Presentacion presentacionCollectionPresentacionToAttach : escenario.getPresentacionCollection()) {
                presentacionCollectionPresentacionToAttach = em.getReference(presentacionCollectionPresentacionToAttach.getClass(), presentacionCollectionPresentacionToAttach.getIdPresentacion());
                attachedPresentacionCollection.add(presentacionCollectionPresentacionToAttach);
            }
            escenario.setPresentacionCollection(attachedPresentacionCollection);
            em.persist(escenario);
            for (Presentacion presentacionCollectionPresentacion : escenario.getPresentacionCollection()) {
                Escenario oldIdEscenarioOfPresentacionCollectionPresentacion = presentacionCollectionPresentacion.getIdEscenario();
                presentacionCollectionPresentacion.setIdEscenario(escenario);
                presentacionCollectionPresentacion = em.merge(presentacionCollectionPresentacion);
                if (oldIdEscenarioOfPresentacionCollectionPresentacion != null) {
                    oldIdEscenarioOfPresentacionCollectionPresentacion.getPresentacionCollection().remove(presentacionCollectionPresentacion);
                    oldIdEscenarioOfPresentacionCollectionPresentacion = em.merge(oldIdEscenarioOfPresentacionCollectionPresentacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Escenario escenario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Escenario persistentEscenario = em.find(Escenario.class, escenario.getIdEscenario());
            Collection<Presentacion> presentacionCollectionOld = persistentEscenario.getPresentacionCollection();
            Collection<Presentacion> presentacionCollectionNew = escenario.getPresentacionCollection();
            List<String> illegalOrphanMessages = null;
            for (Presentacion presentacionCollectionOldPresentacion : presentacionCollectionOld) {
                if (!presentacionCollectionNew.contains(presentacionCollectionOldPresentacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Presentacion " + presentacionCollectionOldPresentacion + " since its idEscenario field is not nullable.");
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
            escenario.setPresentacionCollection(presentacionCollectionNew);
            escenario = em.merge(escenario);
            for (Presentacion presentacionCollectionNewPresentacion : presentacionCollectionNew) {
                if (!presentacionCollectionOld.contains(presentacionCollectionNewPresentacion)) {
                    Escenario oldIdEscenarioOfPresentacionCollectionNewPresentacion = presentacionCollectionNewPresentacion.getIdEscenario();
                    presentacionCollectionNewPresentacion.setIdEscenario(escenario);
                    presentacionCollectionNewPresentacion = em.merge(presentacionCollectionNewPresentacion);
                    if (oldIdEscenarioOfPresentacionCollectionNewPresentacion != null && !oldIdEscenarioOfPresentacionCollectionNewPresentacion.equals(escenario)) {
                        oldIdEscenarioOfPresentacionCollectionNewPresentacion.getPresentacionCollection().remove(presentacionCollectionNewPresentacion);
                        oldIdEscenarioOfPresentacionCollectionNewPresentacion = em.merge(oldIdEscenarioOfPresentacionCollectionNewPresentacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = escenario.getIdEscenario();
                if (findEscenario(id) == null) {
                    throw new NonexistentEntityException("The escenario with id " + id + " no longer exists.");
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
            Escenario escenario;
            try {
                escenario = em.getReference(Escenario.class, id);
                escenario.getIdEscenario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The escenario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Presentacion> presentacionCollectionOrphanCheck = escenario.getPresentacionCollection();
            for (Presentacion presentacionCollectionOrphanCheckPresentacion : presentacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Escenario (" + escenario + ") cannot be destroyed since the Presentacion " + presentacionCollectionOrphanCheckPresentacion + " in its presentacionCollection field has a non-nullable idEscenario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(escenario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Escenario> findEscenarioEntities() {
        return findEscenarioEntities(true, -1, -1);
    }

    public List<Escenario> findEscenarioEntities(int maxResults, int firstResult) {
        return findEscenarioEntities(false, maxResults, firstResult);
    }

    private List<Escenario> findEscenarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Escenario.class));
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

    public Escenario findEscenario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Escenario.class, id);
        } finally {
            em.close();
        }
    }

    public int getEscenarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Escenario> rt = cq.from(Escenario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
