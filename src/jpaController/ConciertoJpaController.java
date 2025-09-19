/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaController;

import Entity.Concierto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Presentacion;
import java.util.ArrayList;
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
public class ConciertoJpaController implements Serializable {

    public ConciertoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Concierto concierto) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Presentacion presentacionOrphanCheck = concierto.getPresentacion();
        if (presentacionOrphanCheck != null) {
            Concierto oldConciertoOfPresentacion = presentacionOrphanCheck.getConcierto();
            if (oldConciertoOfPresentacion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Presentacion " + presentacionOrphanCheck + " already has an item of type Concierto whose presentacion column cannot be null. Please make another selection for the presentacion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presentacion presentacion = concierto.getPresentacion();
            if (presentacion != null) {
                presentacion = em.getReference(presentacion.getClass(), presentacion.getIdPresentacion());
                concierto.setPresentacion(presentacion);
            }
            em.persist(concierto);
            if (presentacion != null) {
                presentacion.setConcierto(concierto);
                presentacion = em.merge(presentacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConcierto(concierto.getIdConcierto()) != null) {
                throw new PreexistingEntityException("Concierto " + concierto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Concierto concierto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Concierto persistentConcierto = em.find(Concierto.class, concierto.getIdConcierto());
            Presentacion presentacionOld = persistentConcierto.getPresentacion();
            Presentacion presentacionNew = concierto.getPresentacion();
            List<String> illegalOrphanMessages = null;
            if (presentacionNew != null && !presentacionNew.equals(presentacionOld)) {
                Concierto oldConciertoOfPresentacion = presentacionNew.getConcierto();
                if (oldConciertoOfPresentacion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Presentacion " + presentacionNew + " already has an item of type Concierto whose presentacion column cannot be null. Please make another selection for the presentacion field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (presentacionNew != null) {
                presentacionNew = em.getReference(presentacionNew.getClass(), presentacionNew.getIdPresentacion());
                concierto.setPresentacion(presentacionNew);
            }
            concierto = em.merge(concierto);
            if (presentacionOld != null && !presentacionOld.equals(presentacionNew)) {
                presentacionOld.setConcierto(null);
                presentacionOld = em.merge(presentacionOld);
            }
            if (presentacionNew != null && !presentacionNew.equals(presentacionOld)) {
                presentacionNew.setConcierto(concierto);
                presentacionNew = em.merge(presentacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = concierto.getIdConcierto();
                if (findConcierto(id) == null) {
                    throw new NonexistentEntityException("The concierto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Concierto concierto;
            try {
                concierto = em.getReference(Concierto.class, id);
                concierto.getIdConcierto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The concierto with id " + id + " no longer exists.", enfe);
            }
            Presentacion presentacion = concierto.getPresentacion();
            if (presentacion != null) {
                presentacion.setConcierto(null);
                presentacion = em.merge(presentacion);
            }
            em.remove(concierto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Concierto> findConciertoEntities() {
        return findConciertoEntities(true, -1, -1);
    }

    public List<Concierto> findConciertoEntities(int maxResults, int firstResult) {
        return findConciertoEntities(false, maxResults, firstResult);
    }

    private List<Concierto> findConciertoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Concierto.class));
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

    public Concierto findConcierto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Concierto.class, id);
        } finally {
            em.close();
        }
    }

    public int getConciertoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Concierto> rt = cq.from(Concierto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
