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
import Entity.Usuario;
import Entity.Entrada;
import Entity.Persona;
import java.util.ArrayList;
import java.util.Collection;
import Entity.Presentacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaController.exceptions.IllegalOrphanException;
import jpaController.exceptions.NonexistentEntityException;

/**
 *
 * @author juanm
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws IllegalOrphanException {
        if (persona.getEntradaCollection() == null) {
            persona.setEntradaCollection(new ArrayList<Entrada>());
        }
        if (persona.getPresentacionCollection() == null) {
            persona.setPresentacionCollection(new ArrayList<Presentacion>());
        }
        List<String> illegalOrphanMessages = null;
        Usuario idUsuarioOrphanCheck = persona.getIdUsuario();
        if (idUsuarioOrphanCheck != null) {
            Persona oldPersonaOfIdUsuario = idUsuarioOrphanCheck.getPersona();
            if (oldPersonaOfIdUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + idUsuarioOrphanCheck + " already has an item of type Persona whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = persona.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                persona.setIdUsuario(idUsuario);
            }
            Collection<Entrada> attachedEntradaCollection = new ArrayList<Entrada>();
            for (Entrada entradaCollectionEntradaToAttach : persona.getEntradaCollection()) {
                entradaCollectionEntradaToAttach = em.getReference(entradaCollectionEntradaToAttach.getClass(), entradaCollectionEntradaToAttach.getIdEntrada());
                attachedEntradaCollection.add(entradaCollectionEntradaToAttach);
            }
            persona.setEntradaCollection(attachedEntradaCollection);
            Collection<Presentacion> attachedPresentacionCollection = new ArrayList<Presentacion>();
            for (Presentacion presentacionCollectionPresentacionToAttach : persona.getPresentacionCollection()) {
                presentacionCollectionPresentacionToAttach = em.getReference(presentacionCollectionPresentacionToAttach.getClass(), presentacionCollectionPresentacionToAttach.getIdPresentacion());
                attachedPresentacionCollection.add(presentacionCollectionPresentacionToAttach);
            }
            persona.setPresentacionCollection(attachedPresentacionCollection);
            em.persist(persona);
            if (idUsuario != null) {
                idUsuario.setPersona(persona);
                idUsuario = em.merge(idUsuario);
            }
            for (Entrada entradaCollectionEntrada : persona.getEntradaCollection()) {
                Persona oldIdPersonaOfEntradaCollectionEntrada = entradaCollectionEntrada.getIdPersona();
                entradaCollectionEntrada.setIdPersona(persona);
                entradaCollectionEntrada = em.merge(entradaCollectionEntrada);
                if (oldIdPersonaOfEntradaCollectionEntrada != null) {
                    oldIdPersonaOfEntradaCollectionEntrada.getEntradaCollection().remove(entradaCollectionEntrada);
                    oldIdPersonaOfEntradaCollectionEntrada = em.merge(oldIdPersonaOfEntradaCollectionEntrada);
                }
            }
            for (Presentacion presentacionCollectionPresentacion : persona.getPresentacionCollection()) {
                presentacionCollectionPresentacion.getPersonaCollection().add(persona);
                presentacionCollectionPresentacion = em.merge(presentacionCollectionPresentacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            Usuario idUsuarioOld = persistentPersona.getIdUsuario();
            Usuario idUsuarioNew = persona.getIdUsuario();
            Collection<Entrada> entradaCollectionOld = persistentPersona.getEntradaCollection();
            Collection<Entrada> entradaCollectionNew = persona.getEntradaCollection();
            Collection<Presentacion> presentacionCollectionOld = persistentPersona.getPresentacionCollection();
            Collection<Presentacion> presentacionCollectionNew = persona.getPresentacionCollection();
            List<String> illegalOrphanMessages = null;
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                Persona oldPersonaOfIdUsuario = idUsuarioNew.getPersona();
                if (oldPersonaOfIdUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + idUsuarioNew + " already has an item of type Persona whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
                }
            }
            for (Entrada entradaCollectionOldEntrada : entradaCollectionOld) {
                if (!entradaCollectionNew.contains(entradaCollectionOldEntrada)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Entrada " + entradaCollectionOldEntrada + " since its idPersona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                persona.setIdUsuario(idUsuarioNew);
            }
            Collection<Entrada> attachedEntradaCollectionNew = new ArrayList<Entrada>();
            for (Entrada entradaCollectionNewEntradaToAttach : entradaCollectionNew) {
                entradaCollectionNewEntradaToAttach = em.getReference(entradaCollectionNewEntradaToAttach.getClass(), entradaCollectionNewEntradaToAttach.getIdEntrada());
                attachedEntradaCollectionNew.add(entradaCollectionNewEntradaToAttach);
            }
            entradaCollectionNew = attachedEntradaCollectionNew;
            persona.setEntradaCollection(entradaCollectionNew);
            Collection<Presentacion> attachedPresentacionCollectionNew = new ArrayList<Presentacion>();
            for (Presentacion presentacionCollectionNewPresentacionToAttach : presentacionCollectionNew) {
                presentacionCollectionNewPresentacionToAttach = em.getReference(presentacionCollectionNewPresentacionToAttach.getClass(), presentacionCollectionNewPresentacionToAttach.getIdPresentacion());
                attachedPresentacionCollectionNew.add(presentacionCollectionNewPresentacionToAttach);
            }
            presentacionCollectionNew = attachedPresentacionCollectionNew;
            persona.setPresentacionCollection(presentacionCollectionNew);
            persona = em.merge(persona);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.setPersona(null);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.setPersona(persona);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Entrada entradaCollectionNewEntrada : entradaCollectionNew) {
                if (!entradaCollectionOld.contains(entradaCollectionNewEntrada)) {
                    Persona oldIdPersonaOfEntradaCollectionNewEntrada = entradaCollectionNewEntrada.getIdPersona();
                    entradaCollectionNewEntrada.setIdPersona(persona);
                    entradaCollectionNewEntrada = em.merge(entradaCollectionNewEntrada);
                    if (oldIdPersonaOfEntradaCollectionNewEntrada != null && !oldIdPersonaOfEntradaCollectionNewEntrada.equals(persona)) {
                        oldIdPersonaOfEntradaCollectionNewEntrada.getEntradaCollection().remove(entradaCollectionNewEntrada);
                        oldIdPersonaOfEntradaCollectionNewEntrada = em.merge(oldIdPersonaOfEntradaCollectionNewEntrada);
                    }
                }
            }
            for (Presentacion presentacionCollectionOldPresentacion : presentacionCollectionOld) {
                if (!presentacionCollectionNew.contains(presentacionCollectionOldPresentacion)) {
                    presentacionCollectionOldPresentacion.getPersonaCollection().remove(persona);
                    presentacionCollectionOldPresentacion = em.merge(presentacionCollectionOldPresentacion);
                }
            }
            for (Presentacion presentacionCollectionNewPresentacion : presentacionCollectionNew) {
                if (!presentacionCollectionOld.contains(presentacionCollectionNewPresentacion)) {
                    presentacionCollectionNewPresentacion.getPersonaCollection().add(persona);
                    presentacionCollectionNewPresentacion = em.merge(presentacionCollectionNewPresentacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Entrada> entradaCollectionOrphanCheck = persona.getEntradaCollection();
            for (Entrada entradaCollectionOrphanCheckEntrada : entradaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Entrada " + entradaCollectionOrphanCheckEntrada + " in its entradaCollection field has a non-nullable idPersona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario idUsuario = persona.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.setPersona(null);
                idUsuario = em.merge(idUsuario);
            }
            Collection<Presentacion> presentacionCollection = persona.getPresentacionCollection();
            for (Presentacion presentacionCollectionPresentacion : presentacionCollection) {
                presentacionCollectionPresentacion.getPersonaCollection().remove(persona);
                presentacionCollectionPresentacion = em.merge(presentacionCollectionPresentacion);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
