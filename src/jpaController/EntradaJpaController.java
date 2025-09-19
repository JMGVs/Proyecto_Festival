/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaController;

import Entity.Entrada;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.EnumeracionStatus;
import Entity.Persona;
import Entity.Presentacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaController.exceptions.NonexistentEntityException;

/**
 *
 * @author juanm
 */
public class EntradaJpaController implements Serializable {

    public EntradaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entrada entrada) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EnumeracionStatus idStatus = entrada.getIdStatus();
            if (idStatus != null) {
                idStatus = em.getReference(idStatus.getClass(), idStatus.getIdStatus());
                entrada.setIdStatus(idStatus);
            }
            Persona idPersona = entrada.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                entrada.setIdPersona(idPersona);
            }
            Presentacion idPresentacion = entrada.getIdPresentacion();
            if (idPresentacion != null) {
                idPresentacion = em.getReference(idPresentacion.getClass(), idPresentacion.getIdPresentacion());
                entrada.setIdPresentacion(idPresentacion);
            }
            em.persist(entrada);
            if (idStatus != null) {
                idStatus.getEntradaCollection().add(entrada);
                idStatus = em.merge(idStatus);
            }
            if (idPersona != null) {
                idPersona.getEntradaCollection().add(entrada);
                idPersona = em.merge(idPersona);
            }
            if (idPresentacion != null) {
                idPresentacion.getEntradaCollection().add(entrada);
                idPresentacion = em.merge(idPresentacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entrada entrada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entrada persistentEntrada = em.find(Entrada.class, entrada.getIdEntrada());
            EnumeracionStatus idStatusOld = persistentEntrada.getIdStatus();
            EnumeracionStatus idStatusNew = entrada.getIdStatus();
            Persona idPersonaOld = persistentEntrada.getIdPersona();
            Persona idPersonaNew = entrada.getIdPersona();
            Presentacion idPresentacionOld = persistentEntrada.getIdPresentacion();
            Presentacion idPresentacionNew = entrada.getIdPresentacion();
            if (idStatusNew != null) {
                idStatusNew = em.getReference(idStatusNew.getClass(), idStatusNew.getIdStatus());
                entrada.setIdStatus(idStatusNew);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                entrada.setIdPersona(idPersonaNew);
            }
            if (idPresentacionNew != null) {
                idPresentacionNew = em.getReference(idPresentacionNew.getClass(), idPresentacionNew.getIdPresentacion());
                entrada.setIdPresentacion(idPresentacionNew);
            }
            entrada = em.merge(entrada);
            if (idStatusOld != null && !idStatusOld.equals(idStatusNew)) {
                idStatusOld.getEntradaCollection().remove(entrada);
                idStatusOld = em.merge(idStatusOld);
            }
            if (idStatusNew != null && !idStatusNew.equals(idStatusOld)) {
                idStatusNew.getEntradaCollection().add(entrada);
                idStatusNew = em.merge(idStatusNew);
            }
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getEntradaCollection().remove(entrada);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getEntradaCollection().add(entrada);
                idPersonaNew = em.merge(idPersonaNew);
            }
            if (idPresentacionOld != null && !idPresentacionOld.equals(idPresentacionNew)) {
                idPresentacionOld.getEntradaCollection().remove(entrada);
                idPresentacionOld = em.merge(idPresentacionOld);
            }
            if (idPresentacionNew != null && !idPresentacionNew.equals(idPresentacionOld)) {
                idPresentacionNew.getEntradaCollection().add(entrada);
                idPresentacionNew = em.merge(idPresentacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = entrada.getIdEntrada();
                if (findEntrada(id) == null) {
                    throw new NonexistentEntityException("The entrada with id " + id + " no longer exists.");
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
            Entrada entrada;
            try {
                entrada = em.getReference(Entrada.class, id);
                entrada.getIdEntrada();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entrada with id " + id + " no longer exists.", enfe);
            }
            EnumeracionStatus idStatus = entrada.getIdStatus();
            if (idStatus != null) {
                idStatus.getEntradaCollection().remove(entrada);
                idStatus = em.merge(idStatus);
            }
            Persona idPersona = entrada.getIdPersona();
            if (idPersona != null) {
                idPersona.getEntradaCollection().remove(entrada);
                idPersona = em.merge(idPersona);
            }
            Presentacion idPresentacion = entrada.getIdPresentacion();
            if (idPresentacion != null) {
                idPresentacion.getEntradaCollection().remove(entrada);
                idPresentacion = em.merge(idPresentacion);
            }
            em.remove(entrada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entrada> findEntradaEntities() {
        return findEntradaEntities(true, -1, -1);
    }

    public List<Entrada> findEntradaEntities(int maxResults, int firstResult) {
        return findEntradaEntities(false, maxResults, firstResult);
    }

    private List<Entrada> findEntradaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entrada.class));
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

    public Entrada findEntrada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entrada.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntradaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entrada> rt = cq.from(Entrada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
