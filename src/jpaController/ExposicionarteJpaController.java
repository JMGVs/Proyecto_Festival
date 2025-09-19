/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaController;

import Entity.Exposicionarte;
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
public class ExposicionarteJpaController implements Serializable {

    public ExposicionarteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Exposicionarte exposicionarte) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Presentacion presentacionOrphanCheck = exposicionarte.getPresentacion();
        if (presentacionOrphanCheck != null) {
            Exposicionarte oldExposicionarteOfPresentacion = presentacionOrphanCheck.getExposicionarte();
            if (oldExposicionarteOfPresentacion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Presentacion " + presentacionOrphanCheck + " already has an item of type Exposicionarte whose presentacion column cannot be null. Please make another selection for the presentacion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presentacion presentacion = exposicionarte.getPresentacion();
            if (presentacion != null) {
                presentacion = em.getReference(presentacion.getClass(), presentacion.getIdPresentacion());
                exposicionarte.setPresentacion(presentacion);
            }
            em.persist(exposicionarte);
            if (presentacion != null) {
                presentacion.setExposicionarte(exposicionarte);
                presentacion = em.merge(presentacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findExposicionarte(exposicionarte.getIdExpo()) != null) {
                throw new PreexistingEntityException("Exposicionarte " + exposicionarte + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Exposicionarte exposicionarte) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Exposicionarte persistentExposicionarte = em.find(Exposicionarte.class, exposicionarte.getIdExpo());
            Presentacion presentacionOld = persistentExposicionarte.getPresentacion();
            Presentacion presentacionNew = exposicionarte.getPresentacion();
            List<String> illegalOrphanMessages = null;
            if (presentacionNew != null && !presentacionNew.equals(presentacionOld)) {
                Exposicionarte oldExposicionarteOfPresentacion = presentacionNew.getExposicionarte();
                if (oldExposicionarteOfPresentacion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Presentacion " + presentacionNew + " already has an item of type Exposicionarte whose presentacion column cannot be null. Please make another selection for the presentacion field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (presentacionNew != null) {
                presentacionNew = em.getReference(presentacionNew.getClass(), presentacionNew.getIdPresentacion());
                exposicionarte.setPresentacion(presentacionNew);
            }
            exposicionarte = em.merge(exposicionarte);
            if (presentacionOld != null && !presentacionOld.equals(presentacionNew)) {
                presentacionOld.setExposicionarte(null);
                presentacionOld = em.merge(presentacionOld);
            }
            if (presentacionNew != null && !presentacionNew.equals(presentacionOld)) {
                presentacionNew.setExposicionarte(exposicionarte);
                presentacionNew = em.merge(presentacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = exposicionarte.getIdExpo();
                if (findExposicionarte(id) == null) {
                    throw new NonexistentEntityException("The exposicionarte with id " + id + " no longer exists.");
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
            Exposicionarte exposicionarte;
            try {
                exposicionarte = em.getReference(Exposicionarte.class, id);
                exposicionarte.getIdExpo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The exposicionarte with id " + id + " no longer exists.", enfe);
            }
            Presentacion presentacion = exposicionarte.getPresentacion();
            if (presentacion != null) {
                presentacion.setExposicionarte(null);
                presentacion = em.merge(presentacion);
            }
            em.remove(exposicionarte);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Exposicionarte> findExposicionarteEntities() {
        return findExposicionarteEntities(true, -1, -1);
    }

    public List<Exposicionarte> findExposicionarteEntities(int maxResults, int firstResult) {
        return findExposicionarteEntities(false, maxResults, firstResult);
    }

    private List<Exposicionarte> findExposicionarteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Exposicionarte.class));
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

    public Exposicionarte findExposicionarte(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Exposicionarte.class, id);
        } finally {
            em.close();
        }
    }

    public int getExposicionarteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Exposicionarte> rt = cq.from(Exposicionarte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
