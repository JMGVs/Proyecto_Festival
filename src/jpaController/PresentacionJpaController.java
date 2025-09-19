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
import Entity.Escenario;
import Entity.Festival;
import Entity.Exposicionarte;
import Entity.Concierto;
import Entity.Obrateatro;
import Entity.Entrada;
import java.util.ArrayList;
import java.util.Collection;
import Entity.Persona;
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
public class PresentacionJpaController implements Serializable {

    public PresentacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Presentacion presentacion) {
        if (presentacion.getEntradaCollection() == null) {
            presentacion.setEntradaCollection(new ArrayList<Entrada>());
        }
        if (presentacion.getPersonaCollection() == null) {
            presentacion.setPersonaCollection(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Escenario idEscenario = presentacion.getIdEscenario();
            if (idEscenario != null) {
                idEscenario = em.getReference(idEscenario.getClass(), idEscenario.getIdEscenario());
                presentacion.setIdEscenario(idEscenario);
            }
            Festival idFestival = presentacion.getIdFestival();
            if (idFestival != null) {
                idFestival = em.getReference(idFestival.getClass(), idFestival.getIdFestival());
                presentacion.setIdFestival(idFestival);
            }
            Exposicionarte exposicionarte = presentacion.getExposicionarte();
            if (exposicionarte != null) {
                exposicionarte = em.getReference(exposicionarte.getClass(), exposicionarte.getIdExpo());
                presentacion.setExposicionarte(exposicionarte);
            }
            Concierto concierto = presentacion.getConcierto();
            if (concierto != null) {
                concierto = em.getReference(concierto.getClass(), concierto.getIdConcierto());
                presentacion.setConcierto(concierto);
            }
            Obrateatro obrateatro = presentacion.getObrateatro();
            if (obrateatro != null) {
                obrateatro = em.getReference(obrateatro.getClass(), obrateatro.getIdObra());
                presentacion.setObrateatro(obrateatro);
            }
            Collection<Entrada> attachedEntradaCollection = new ArrayList<Entrada>();
            for (Entrada entradaCollectionEntradaToAttach : presentacion.getEntradaCollection()) {
                entradaCollectionEntradaToAttach = em.getReference(entradaCollectionEntradaToAttach.getClass(), entradaCollectionEntradaToAttach.getIdEntrada());
                attachedEntradaCollection.add(entradaCollectionEntradaToAttach);
            }
            presentacion.setEntradaCollection(attachedEntradaCollection);
            Collection<Persona> attachedPersonaCollection = new ArrayList<Persona>();
            for (Persona personaCollectionPersonaToAttach : presentacion.getPersonaCollection()) {
                personaCollectionPersonaToAttach = em.getReference(personaCollectionPersonaToAttach.getClass(), personaCollectionPersonaToAttach.getIdPersona());
                attachedPersonaCollection.add(personaCollectionPersonaToAttach);
            }
            presentacion.setPersonaCollection(attachedPersonaCollection);
            em.persist(presentacion);
            if (idEscenario != null) {
                idEscenario.getPresentacionCollection().add(presentacion);
                idEscenario = em.merge(idEscenario);
            }
            if (idFestival != null) {
                idFestival.getPresentacionCollection().add(presentacion);
                idFestival = em.merge(idFestival);
            }
            if (exposicionarte != null) {
                Presentacion oldPresentacionOfExposicionarte = exposicionarte.getPresentacion();
                if (oldPresentacionOfExposicionarte != null) {
                    oldPresentacionOfExposicionarte.setExposicionarte(null);
                    oldPresentacionOfExposicionarte = em.merge(oldPresentacionOfExposicionarte);
                }
                exposicionarte.setPresentacion(presentacion);
                exposicionarte = em.merge(exposicionarte);
            }
            if (concierto != null) {
                Presentacion oldPresentacionOfConcierto = concierto.getPresentacion();
                if (oldPresentacionOfConcierto != null) {
                    oldPresentacionOfConcierto.setConcierto(null);
                    oldPresentacionOfConcierto = em.merge(oldPresentacionOfConcierto);
                }
                concierto.setPresentacion(presentacion);
                concierto = em.merge(concierto);
            }
            if (obrateatro != null) {
                Presentacion oldPresentacionOfObrateatro = obrateatro.getPresentacion();
                if (oldPresentacionOfObrateatro != null) {
                    oldPresentacionOfObrateatro.setObrateatro(null);
                    oldPresentacionOfObrateatro = em.merge(oldPresentacionOfObrateatro);
                }
                obrateatro.setPresentacion(presentacion);
                obrateatro = em.merge(obrateatro);
            }
            for (Entrada entradaCollectionEntrada : presentacion.getEntradaCollection()) {
                Presentacion oldIdPresentacionOfEntradaCollectionEntrada = entradaCollectionEntrada.getIdPresentacion();
                entradaCollectionEntrada.setIdPresentacion(presentacion);
                entradaCollectionEntrada = em.merge(entradaCollectionEntrada);
                if (oldIdPresentacionOfEntradaCollectionEntrada != null) {
                    oldIdPresentacionOfEntradaCollectionEntrada.getEntradaCollection().remove(entradaCollectionEntrada);
                    oldIdPresentacionOfEntradaCollectionEntrada = em.merge(oldIdPresentacionOfEntradaCollectionEntrada);
                }
            }
            for (Persona personaCollectionPersona : presentacion.getPersonaCollection()) {
                personaCollectionPersona.getPresentacionCollection().add(presentacion);
                personaCollectionPersona = em.merge(personaCollectionPersona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Presentacion presentacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presentacion persistentPresentacion = em.find(Presentacion.class, presentacion.getIdPresentacion());
            Escenario idEscenarioOld = persistentPresentacion.getIdEscenario();
            Escenario idEscenarioNew = presentacion.getIdEscenario();
            Festival idFestivalOld = persistentPresentacion.getIdFestival();
            Festival idFestivalNew = presentacion.getIdFestival();
            Exposicionarte exposicionarteOld = persistentPresentacion.getExposicionarte();
            Exposicionarte exposicionarteNew = presentacion.getExposicionarte();
            Concierto conciertoOld = persistentPresentacion.getConcierto();
            Concierto conciertoNew = presentacion.getConcierto();
            Obrateatro obrateatroOld = persistentPresentacion.getObrateatro();
            Obrateatro obrateatroNew = presentacion.getObrateatro();
            Collection<Entrada> entradaCollectionOld = persistentPresentacion.getEntradaCollection();
            Collection<Entrada> entradaCollectionNew = presentacion.getEntradaCollection();
            Collection<Persona> personaCollectionOld = persistentPresentacion.getPersonaCollection();
            Collection<Persona> personaCollectionNew = presentacion.getPersonaCollection();
            List<String> illegalOrphanMessages = null;
            if (exposicionarteOld != null && !exposicionarteOld.equals(exposicionarteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Exposicionarte " + exposicionarteOld + " since its presentacion field is not nullable.");
            }
            if (conciertoOld != null && !conciertoOld.equals(conciertoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Concierto " + conciertoOld + " since its presentacion field is not nullable.");
            }
            if (obrateatroOld != null && !obrateatroOld.equals(obrateatroNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Obrateatro " + obrateatroOld + " since its presentacion field is not nullable.");
            }
            for (Entrada entradaCollectionOldEntrada : entradaCollectionOld) {
                if (!entradaCollectionNew.contains(entradaCollectionOldEntrada)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Entrada " + entradaCollectionOldEntrada + " since its idPresentacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEscenarioNew != null) {
                idEscenarioNew = em.getReference(idEscenarioNew.getClass(), idEscenarioNew.getIdEscenario());
                presentacion.setIdEscenario(idEscenarioNew);
            }
            if (idFestivalNew != null) {
                idFestivalNew = em.getReference(idFestivalNew.getClass(), idFestivalNew.getIdFestival());
                presentacion.setIdFestival(idFestivalNew);
            }
            if (exposicionarteNew != null) {
                exposicionarteNew = em.getReference(exposicionarteNew.getClass(), exposicionarteNew.getIdExpo());
                presentacion.setExposicionarte(exposicionarteNew);
            }
            if (conciertoNew != null) {
                conciertoNew = em.getReference(conciertoNew.getClass(), conciertoNew.getIdConcierto());
                presentacion.setConcierto(conciertoNew);
            }
            if (obrateatroNew != null) {
                obrateatroNew = em.getReference(obrateatroNew.getClass(), obrateatroNew.getIdObra());
                presentacion.setObrateatro(obrateatroNew);
            }
            Collection<Entrada> attachedEntradaCollectionNew = new ArrayList<Entrada>();
            for (Entrada entradaCollectionNewEntradaToAttach : entradaCollectionNew) {
                entradaCollectionNewEntradaToAttach = em.getReference(entradaCollectionNewEntradaToAttach.getClass(), entradaCollectionNewEntradaToAttach.getIdEntrada());
                attachedEntradaCollectionNew.add(entradaCollectionNewEntradaToAttach);
            }
            entradaCollectionNew = attachedEntradaCollectionNew;
            presentacion.setEntradaCollection(entradaCollectionNew);
            Collection<Persona> attachedPersonaCollectionNew = new ArrayList<Persona>();
            for (Persona personaCollectionNewPersonaToAttach : personaCollectionNew) {
                personaCollectionNewPersonaToAttach = em.getReference(personaCollectionNewPersonaToAttach.getClass(), personaCollectionNewPersonaToAttach.getIdPersona());
                attachedPersonaCollectionNew.add(personaCollectionNewPersonaToAttach);
            }
            personaCollectionNew = attachedPersonaCollectionNew;
            presentacion.setPersonaCollection(personaCollectionNew);
            presentacion = em.merge(presentacion);
            if (idEscenarioOld != null && !idEscenarioOld.equals(idEscenarioNew)) {
                idEscenarioOld.getPresentacionCollection().remove(presentacion);
                idEscenarioOld = em.merge(idEscenarioOld);
            }
            if (idEscenarioNew != null && !idEscenarioNew.equals(idEscenarioOld)) {
                idEscenarioNew.getPresentacionCollection().add(presentacion);
                idEscenarioNew = em.merge(idEscenarioNew);
            }
            if (idFestivalOld != null && !idFestivalOld.equals(idFestivalNew)) {
                idFestivalOld.getPresentacionCollection().remove(presentacion);
                idFestivalOld = em.merge(idFestivalOld);
            }
            if (idFestivalNew != null && !idFestivalNew.equals(idFestivalOld)) {
                idFestivalNew.getPresentacionCollection().add(presentacion);
                idFestivalNew = em.merge(idFestivalNew);
            }
            if (exposicionarteNew != null && !exposicionarteNew.equals(exposicionarteOld)) {
                Presentacion oldPresentacionOfExposicionarte = exposicionarteNew.getPresentacion();
                if (oldPresentacionOfExposicionarte != null) {
                    oldPresentacionOfExposicionarte.setExposicionarte(null);
                    oldPresentacionOfExposicionarte = em.merge(oldPresentacionOfExposicionarte);
                }
                exposicionarteNew.setPresentacion(presentacion);
                exposicionarteNew = em.merge(exposicionarteNew);
            }
            if (conciertoNew != null && !conciertoNew.equals(conciertoOld)) {
                Presentacion oldPresentacionOfConcierto = conciertoNew.getPresentacion();
                if (oldPresentacionOfConcierto != null) {
                    oldPresentacionOfConcierto.setConcierto(null);
                    oldPresentacionOfConcierto = em.merge(oldPresentacionOfConcierto);
                }
                conciertoNew.setPresentacion(presentacion);
                conciertoNew = em.merge(conciertoNew);
            }
            if (obrateatroNew != null && !obrateatroNew.equals(obrateatroOld)) {
                Presentacion oldPresentacionOfObrateatro = obrateatroNew.getPresentacion();
                if (oldPresentacionOfObrateatro != null) {
                    oldPresentacionOfObrateatro.setObrateatro(null);
                    oldPresentacionOfObrateatro = em.merge(oldPresentacionOfObrateatro);
                }
                obrateatroNew.setPresentacion(presentacion);
                obrateatroNew = em.merge(obrateatroNew);
            }
            for (Entrada entradaCollectionNewEntrada : entradaCollectionNew) {
                if (!entradaCollectionOld.contains(entradaCollectionNewEntrada)) {
                    Presentacion oldIdPresentacionOfEntradaCollectionNewEntrada = entradaCollectionNewEntrada.getIdPresentacion();
                    entradaCollectionNewEntrada.setIdPresentacion(presentacion);
                    entradaCollectionNewEntrada = em.merge(entradaCollectionNewEntrada);
                    if (oldIdPresentacionOfEntradaCollectionNewEntrada != null && !oldIdPresentacionOfEntradaCollectionNewEntrada.equals(presentacion)) {
                        oldIdPresentacionOfEntradaCollectionNewEntrada.getEntradaCollection().remove(entradaCollectionNewEntrada);
                        oldIdPresentacionOfEntradaCollectionNewEntrada = em.merge(oldIdPresentacionOfEntradaCollectionNewEntrada);
                    }
                }
            }
            for (Persona personaCollectionOldPersona : personaCollectionOld) {
                if (!personaCollectionNew.contains(personaCollectionOldPersona)) {
                    personaCollectionOldPersona.getPresentacionCollection().remove(presentacion);
                    personaCollectionOldPersona = em.merge(personaCollectionOldPersona);
                }
            }
            for (Persona personaCollectionNewPersona : personaCollectionNew) {
                if (!personaCollectionOld.contains(personaCollectionNewPersona)) {
                    personaCollectionNewPersona.getPresentacionCollection().add(presentacion);
                    personaCollectionNewPersona = em.merge(personaCollectionNewPersona);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = presentacion.getIdPresentacion();
                if (findPresentacion(id) == null) {
                    throw new NonexistentEntityException("The presentacion with id " + id + " no longer exists.");
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
            Presentacion presentacion;
            try {
                presentacion = em.getReference(Presentacion.class, id);
                presentacion.getIdPresentacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presentacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Exposicionarte exposicionarteOrphanCheck = presentacion.getExposicionarte();
            if (exposicionarteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Presentacion (" + presentacion + ") cannot be destroyed since the Exposicionarte " + exposicionarteOrphanCheck + " in its exposicionarte field has a non-nullable presentacion field.");
            }
            Concierto conciertoOrphanCheck = presentacion.getConcierto();
            if (conciertoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Presentacion (" + presentacion + ") cannot be destroyed since the Concierto " + conciertoOrphanCheck + " in its concierto field has a non-nullable presentacion field.");
            }
            Obrateatro obrateatroOrphanCheck = presentacion.getObrateatro();
            if (obrateatroOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Presentacion (" + presentacion + ") cannot be destroyed since the Obrateatro " + obrateatroOrphanCheck + " in its obrateatro field has a non-nullable presentacion field.");
            }
            Collection<Entrada> entradaCollectionOrphanCheck = presentacion.getEntradaCollection();
            for (Entrada entradaCollectionOrphanCheckEntrada : entradaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Presentacion (" + presentacion + ") cannot be destroyed since the Entrada " + entradaCollectionOrphanCheckEntrada + " in its entradaCollection field has a non-nullable idPresentacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Escenario idEscenario = presentacion.getIdEscenario();
            if (idEscenario != null) {
                idEscenario.getPresentacionCollection().remove(presentacion);
                idEscenario = em.merge(idEscenario);
            }
            Festival idFestival = presentacion.getIdFestival();
            if (idFestival != null) {
                idFestival.getPresentacionCollection().remove(presentacion);
                idFestival = em.merge(idFestival);
            }
            Collection<Persona> personaCollection = presentacion.getPersonaCollection();
            for (Persona personaCollectionPersona : personaCollection) {
                personaCollectionPersona.getPresentacionCollection().remove(presentacion);
                personaCollectionPersona = em.merge(personaCollectionPersona);
            }
            em.remove(presentacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Presentacion> findPresentacionEntities() {
        return findPresentacionEntities(true, -1, -1);
    }

    public List<Presentacion> findPresentacionEntities(int maxResults, int firstResult) {
        return findPresentacionEntities(false, maxResults, firstResult);
    }

    private List<Presentacion> findPresentacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Presentacion.class));
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

    public Presentacion findPresentacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Presentacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresentacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Presentacion> rt = cq.from(Presentacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
