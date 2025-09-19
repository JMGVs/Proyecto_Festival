/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaController;

import Entity.Obrateatro;
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
public class ObrateatroJpaController implements Serializable {

    public ObrateatroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Obrateatro obrateatro) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Presentacion presentacionOrphanCheck = obrateatro.getPresentacion();
        if (presentacionOrphanCheck != null) {
            Obrateatro oldObrateatroOfPresentacion = presentacionOrphanCheck.getObrateatro();
            if (oldObrateatroOfPresentacion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Presentacion " + presentacionOrphanCheck + " already has an item of type Obrateatro whose presentacion column cannot be null. Please make another selection for the presentacion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presentacion presentacion = obrateatro.getPresentacion();
            if (presentacion != null) {
                presentacion = em.getReference(presentacion.getClass(), presentacion.getIdPresentacion());
                obrateatro.setPresentacion(presentacion);
            }
            em.persist(obrateatro);
            if (presentacion != null) {
                presentacion.setObrateatro(obrateatro);
                presentacion = em.merge(presentacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findObrateatro(obrateatro.getIdObra()) != null) {
                throw new PreexistingEntityException("Obrateatro " + obrateatro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Obrateatro obrateatro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Obrateatro persistentObrateatro = em.find(Obrateatro.class, obrateatro.getIdObra());
            Presentacion presentacionOld = persistentObrateatro.getPresentacion();
            Presentacion presentacionNew = obrateatro.getPresentacion();
            List<String> illegalOrphanMessages = null;
            if (presentacionNew != null && !presentacionNew.equals(presentacionOld)) {
                Obrateatro oldObrateatroOfPresentacion = presentacionNew.getObrateatro();
                if (oldObrateatroOfPresentacion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Presentacion " + presentacionNew + " already has an item of type Obrateatro whose presentacion column cannot be null. Please make another selection for the presentacion field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (presentacionNew != null) {
                presentacionNew = em.getReference(presentacionNew.getClass(), presentacionNew.getIdPresentacion());
                obrateatro.setPresentacion(presentacionNew);
            }
            obrateatro = em.merge(obrateatro);
            if (presentacionOld != null && !presentacionOld.equals(presentacionNew)) {
                presentacionOld.setObrateatro(null);
                presentacionOld = em.merge(presentacionOld);
            }
            if (presentacionNew != null && !presentacionNew.equals(presentacionOld)) {
                presentacionNew.setObrateatro(obrateatro);
                presentacionNew = em.merge(presentacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = obrateatro.getIdObra();
                if (findObrateatro(id) == null) {
                    throw new NonexistentEntityException("The obrateatro with id " + id + " no longer exists.");
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
            Obrateatro obrateatro;
            try {
                obrateatro = em.getReference(Obrateatro.class, id);
                obrateatro.getIdObra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The obrateatro with id " + id + " no longer exists.", enfe);
            }
            Presentacion presentacion = obrateatro.getPresentacion();
            if (presentacion != null) {
                presentacion.setObrateatro(null);
                presentacion = em.merge(presentacion);
            }
            em.remove(obrateatro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Obrateatro> findObrateatroEntities() {
        return findObrateatroEntities(true, -1, -1);
    }

    public List<Obrateatro> findObrateatroEntities(int maxResults, int firstResult) {
        return findObrateatroEntities(false, maxResults, firstResult);
    }

    private List<Obrateatro> findObrateatroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Obrateatro.class));
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

    public Obrateatro findObrateatro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Obrateatro.class, id);
        } finally {
            em.close();
        }
    }

    public int getObrateatroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Obrateatro> rt = cq.from(Obrateatro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
