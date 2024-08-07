/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.CaracterizarInstructor;
import Entidades.Clima;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.Dotacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class ClimaJpaController implements Serializable {

    public ClimaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Dotacion_SenaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clima clima) throws PreexistingEntityException, Exception {
        if (clima.getCaracterizarInstructorCollection() == null) {
            clima.setCaracterizarInstructorCollection(new ArrayList<CaracterizarInstructor>());
        }
        if (clima.getDotacionCollection() == null) {
            clima.setDotacionCollection(new ArrayList<Dotacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<CaracterizarInstructor> attachedCaracterizarInstructorCollection = new ArrayList<CaracterizarInstructor>();
            for (CaracterizarInstructor caracterizarInstructorCollectionCaracterizarInstructorToAttach : clima.getCaracterizarInstructorCollection()) {
                caracterizarInstructorCollectionCaracterizarInstructorToAttach = em.getReference(caracterizarInstructorCollectionCaracterizarInstructorToAttach.getClass(), caracterizarInstructorCollectionCaracterizarInstructorToAttach.getIdCaracterizarInstructor());
                attachedCaracterizarInstructorCollection.add(caracterizarInstructorCollectionCaracterizarInstructorToAttach);
            }
            clima.setCaracterizarInstructorCollection(attachedCaracterizarInstructorCollection);
            Collection<Dotacion> attachedDotacionCollection = new ArrayList<Dotacion>();
            for (Dotacion dotacionCollectionDotacionToAttach : clima.getDotacionCollection()) {
                dotacionCollectionDotacionToAttach = em.getReference(dotacionCollectionDotacionToAttach.getClass(), dotacionCollectionDotacionToAttach.getIddotacion());
                attachedDotacionCollection.add(dotacionCollectionDotacionToAttach);
            }
            clima.setDotacionCollection(attachedDotacionCollection);
            em.persist(clima);
            for (CaracterizarInstructor caracterizarInstructorCollectionCaracterizarInstructor : clima.getCaracterizarInstructorCollection()) {
                Clima oldClimaIdclimaOfCaracterizarInstructorCollectionCaracterizarInstructor = caracterizarInstructorCollectionCaracterizarInstructor.getClimaIdclima();
                caracterizarInstructorCollectionCaracterizarInstructor.setClimaIdclima(clima);
                caracterizarInstructorCollectionCaracterizarInstructor = em.merge(caracterizarInstructorCollectionCaracterizarInstructor);
                if (oldClimaIdclimaOfCaracterizarInstructorCollectionCaracterizarInstructor != null) {
                    oldClimaIdclimaOfCaracterizarInstructorCollectionCaracterizarInstructor.getCaracterizarInstructorCollection().remove(caracterizarInstructorCollectionCaracterizarInstructor);
                    oldClimaIdclimaOfCaracterizarInstructorCollectionCaracterizarInstructor = em.merge(oldClimaIdclimaOfCaracterizarInstructorCollectionCaracterizarInstructor);
                }
            }
            for (Dotacion dotacionCollectionDotacion : clima.getDotacionCollection()) {
                Clima oldClimaIdclimaOfDotacionCollectionDotacion = dotacionCollectionDotacion.getClimaIdclima();
                dotacionCollectionDotacion.setClimaIdclima(clima);
                dotacionCollectionDotacion = em.merge(dotacionCollectionDotacion);
                if (oldClimaIdclimaOfDotacionCollectionDotacion != null) {
                    oldClimaIdclimaOfDotacionCollectionDotacion.getDotacionCollection().remove(dotacionCollectionDotacion);
                    oldClimaIdclimaOfDotacionCollectionDotacion = em.merge(oldClimaIdclimaOfDotacionCollectionDotacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClima(clima.getIdclima()) != null) {
                throw new PreexistingEntityException("Clima " + clima + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clima clima) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clima persistentClima = em.find(Clima.class, clima.getIdclima());
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionOld = persistentClima.getCaracterizarInstructorCollection();
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionNew = clima.getCaracterizarInstructorCollection();
            Collection<Dotacion> dotacionCollectionOld = persistentClima.getDotacionCollection();
            Collection<Dotacion> dotacionCollectionNew = clima.getDotacionCollection();
            List<String> illegalOrphanMessages = null;
            for (CaracterizarInstructor caracterizarInstructorCollectionOldCaracterizarInstructor : caracterizarInstructorCollectionOld) {
                if (!caracterizarInstructorCollectionNew.contains(caracterizarInstructorCollectionOldCaracterizarInstructor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CaracterizarInstructor " + caracterizarInstructorCollectionOldCaracterizarInstructor + " since its climaIdclima field is not nullable.");
                }
            }
            for (Dotacion dotacionCollectionOldDotacion : dotacionCollectionOld) {
                if (!dotacionCollectionNew.contains(dotacionCollectionOldDotacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dotacion " + dotacionCollectionOldDotacion + " since its climaIdclima field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<CaracterizarInstructor> attachedCaracterizarInstructorCollectionNew = new ArrayList<CaracterizarInstructor>();
            for (CaracterizarInstructor caracterizarInstructorCollectionNewCaracterizarInstructorToAttach : caracterizarInstructorCollectionNew) {
                caracterizarInstructorCollectionNewCaracterizarInstructorToAttach = em.getReference(caracterizarInstructorCollectionNewCaracterizarInstructorToAttach.getClass(), caracterizarInstructorCollectionNewCaracterizarInstructorToAttach.getIdCaracterizarInstructor());
                attachedCaracterizarInstructorCollectionNew.add(caracterizarInstructorCollectionNewCaracterizarInstructorToAttach);
            }
            caracterizarInstructorCollectionNew = attachedCaracterizarInstructorCollectionNew;
            clima.setCaracterizarInstructorCollection(caracterizarInstructorCollectionNew);
            Collection<Dotacion> attachedDotacionCollectionNew = new ArrayList<Dotacion>();
            for (Dotacion dotacionCollectionNewDotacionToAttach : dotacionCollectionNew) {
                dotacionCollectionNewDotacionToAttach = em.getReference(dotacionCollectionNewDotacionToAttach.getClass(), dotacionCollectionNewDotacionToAttach.getIddotacion());
                attachedDotacionCollectionNew.add(dotacionCollectionNewDotacionToAttach);
            }
            dotacionCollectionNew = attachedDotacionCollectionNew;
            clima.setDotacionCollection(dotacionCollectionNew);
            clima = em.merge(clima);
            for (CaracterizarInstructor caracterizarInstructorCollectionNewCaracterizarInstructor : caracterizarInstructorCollectionNew) {
                if (!caracterizarInstructorCollectionOld.contains(caracterizarInstructorCollectionNewCaracterizarInstructor)) {
                    Clima oldClimaIdclimaOfCaracterizarInstructorCollectionNewCaracterizarInstructor = caracterizarInstructorCollectionNewCaracterizarInstructor.getClimaIdclima();
                    caracterizarInstructorCollectionNewCaracterizarInstructor.setClimaIdclima(clima);
                    caracterizarInstructorCollectionNewCaracterizarInstructor = em.merge(caracterizarInstructorCollectionNewCaracterizarInstructor);
                    if (oldClimaIdclimaOfCaracterizarInstructorCollectionNewCaracterizarInstructor != null && !oldClimaIdclimaOfCaracterizarInstructorCollectionNewCaracterizarInstructor.equals(clima)) {
                        oldClimaIdclimaOfCaracterizarInstructorCollectionNewCaracterizarInstructor.getCaracterizarInstructorCollection().remove(caracterizarInstructorCollectionNewCaracterizarInstructor);
                        oldClimaIdclimaOfCaracterizarInstructorCollectionNewCaracterizarInstructor = em.merge(oldClimaIdclimaOfCaracterizarInstructorCollectionNewCaracterizarInstructor);
                    }
                }
            }
            for (Dotacion dotacionCollectionNewDotacion : dotacionCollectionNew) {
                if (!dotacionCollectionOld.contains(dotacionCollectionNewDotacion)) {
                    Clima oldClimaIdclimaOfDotacionCollectionNewDotacion = dotacionCollectionNewDotacion.getClimaIdclima();
                    dotacionCollectionNewDotacion.setClimaIdclima(clima);
                    dotacionCollectionNewDotacion = em.merge(dotacionCollectionNewDotacion);
                    if (oldClimaIdclimaOfDotacionCollectionNewDotacion != null && !oldClimaIdclimaOfDotacionCollectionNewDotacion.equals(clima)) {
                        oldClimaIdclimaOfDotacionCollectionNewDotacion.getDotacionCollection().remove(dotacionCollectionNewDotacion);
                        oldClimaIdclimaOfDotacionCollectionNewDotacion = em.merge(oldClimaIdclimaOfDotacionCollectionNewDotacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clima.getIdclima();
                if (findClima(id) == null) {
                    throw new NonexistentEntityException("The clima with id " + id + " no longer exists.");
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
            Clima clima;
            try {
                clima = em.getReference(Clima.class, id);
                clima.getIdclima();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clima with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionOrphanCheck = clima.getCaracterizarInstructorCollection();
            for (CaracterizarInstructor caracterizarInstructorCollectionOrphanCheckCaracterizarInstructor : caracterizarInstructorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clima (" + clima + ") cannot be destroyed since the CaracterizarInstructor " + caracterizarInstructorCollectionOrphanCheckCaracterizarInstructor + " in its caracterizarInstructorCollection field has a non-nullable climaIdclima field.");
            }
            Collection<Dotacion> dotacionCollectionOrphanCheck = clima.getDotacionCollection();
            for (Dotacion dotacionCollectionOrphanCheckDotacion : dotacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clima (" + clima + ") cannot be destroyed since the Dotacion " + dotacionCollectionOrphanCheckDotacion + " in its dotacionCollection field has a non-nullable climaIdclima field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clima);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clima> findClimaEntities() {
        return findClimaEntities(true, -1, -1);
    }

    public List<Clima> findClimaEntities(int maxResults, int firstResult) {
        return findClimaEntities(false, maxResults, firstResult);
    }

    private List<Clima> findClimaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clima.class));
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

    public Clima findClima(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clima.class, id);
        } finally {
            em.close();
        }
    }

    public int getClimaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clima> rt = cq.from(Clima.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
