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
import Entidades.Centro;
import Entidades.Coordinador;
import Entidades.Instructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class CoordinadorJpaController implements Serializable {

    public CoordinadorJpaController() {
       this.emf = Persistence.createEntityManagerFactory("Dotacion_SenaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Coordinador coordinador) throws PreexistingEntityException, Exception {
        if (coordinador.getInstructorCollection() == null) {
            coordinador.setInstructorCollection(new ArrayList<Instructor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Centro centroIdcentro = coordinador.getCentroIdcentro();
            if (centroIdcentro != null) {
                centroIdcentro = em.getReference(centroIdcentro.getClass(), centroIdcentro.getIdcentro());
                coordinador.setCentroIdcentro(centroIdcentro);
            }
            Collection<Instructor> attachedInstructorCollection = new ArrayList<Instructor>();
            for (Instructor instructorCollectionInstructorToAttach : coordinador.getInstructorCollection()) {
                instructorCollectionInstructorToAttach = em.getReference(instructorCollectionInstructorToAttach.getClass(), instructorCollectionInstructorToAttach.getIdinstructor());
                attachedInstructorCollection.add(instructorCollectionInstructorToAttach);
            }
            coordinador.setInstructorCollection(attachedInstructorCollection);
            em.persist(coordinador);
            if (centroIdcentro != null) {
                centroIdcentro.getCoordinadorCollection().add(coordinador);
                centroIdcentro = em.merge(centroIdcentro);
            }
            for (Instructor instructorCollectionInstructor : coordinador.getInstructorCollection()) {
                Coordinador oldCoordinadorIdcoordinadorOfInstructorCollectionInstructor = instructorCollectionInstructor.getCoordinadorIdcoordinador();
                instructorCollectionInstructor.setCoordinadorIdcoordinador(coordinador);
                instructorCollectionInstructor = em.merge(instructorCollectionInstructor);
                if (oldCoordinadorIdcoordinadorOfInstructorCollectionInstructor != null) {
                    oldCoordinadorIdcoordinadorOfInstructorCollectionInstructor.getInstructorCollection().remove(instructorCollectionInstructor);
                    oldCoordinadorIdcoordinadorOfInstructorCollectionInstructor = em.merge(oldCoordinadorIdcoordinadorOfInstructorCollectionInstructor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCoordinador(coordinador.getIdcoordinador()) != null) {
                throw new PreexistingEntityException("Coordinador " + coordinador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Coordinador coordinador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Coordinador persistentCoordinador = em.find(Coordinador.class, coordinador.getIdcoordinador());
            Centro centroIdcentroOld = persistentCoordinador.getCentroIdcentro();
            Centro centroIdcentroNew = coordinador.getCentroIdcentro();
            Collection<Instructor> instructorCollectionOld = persistentCoordinador.getInstructorCollection();
            Collection<Instructor> instructorCollectionNew = coordinador.getInstructorCollection();
            List<String> illegalOrphanMessages = null;
            for (Instructor instructorCollectionOldInstructor : instructorCollectionOld) {
                if (!instructorCollectionNew.contains(instructorCollectionOldInstructor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Instructor " + instructorCollectionOldInstructor + " since its coordinadorIdcoordinador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (centroIdcentroNew != null) {
                centroIdcentroNew = em.getReference(centroIdcentroNew.getClass(), centroIdcentroNew.getIdcentro());
                coordinador.setCentroIdcentro(centroIdcentroNew);
            }
            Collection<Instructor> attachedInstructorCollectionNew = new ArrayList<Instructor>();
            for (Instructor instructorCollectionNewInstructorToAttach : instructorCollectionNew) {
                instructorCollectionNewInstructorToAttach = em.getReference(instructorCollectionNewInstructorToAttach.getClass(), instructorCollectionNewInstructorToAttach.getIdinstructor());
                attachedInstructorCollectionNew.add(instructorCollectionNewInstructorToAttach);
            }
            instructorCollectionNew = attachedInstructorCollectionNew;
            coordinador.setInstructorCollection(instructorCollectionNew);
            coordinador = em.merge(coordinador);
            if (centroIdcentroOld != null && !centroIdcentroOld.equals(centroIdcentroNew)) {
                centroIdcentroOld.getCoordinadorCollection().remove(coordinador);
                centroIdcentroOld = em.merge(centroIdcentroOld);
            }
            if (centroIdcentroNew != null && !centroIdcentroNew.equals(centroIdcentroOld)) {
                centroIdcentroNew.getCoordinadorCollection().add(coordinador);
                centroIdcentroNew = em.merge(centroIdcentroNew);
            }
            for (Instructor instructorCollectionNewInstructor : instructorCollectionNew) {
                if (!instructorCollectionOld.contains(instructorCollectionNewInstructor)) {
                    Coordinador oldCoordinadorIdcoordinadorOfInstructorCollectionNewInstructor = instructorCollectionNewInstructor.getCoordinadorIdcoordinador();
                    instructorCollectionNewInstructor.setCoordinadorIdcoordinador(coordinador);
                    instructorCollectionNewInstructor = em.merge(instructorCollectionNewInstructor);
                    if (oldCoordinadorIdcoordinadorOfInstructorCollectionNewInstructor != null && !oldCoordinadorIdcoordinadorOfInstructorCollectionNewInstructor.equals(coordinador)) {
                        oldCoordinadorIdcoordinadorOfInstructorCollectionNewInstructor.getInstructorCollection().remove(instructorCollectionNewInstructor);
                        oldCoordinadorIdcoordinadorOfInstructorCollectionNewInstructor = em.merge(oldCoordinadorIdcoordinadorOfInstructorCollectionNewInstructor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = coordinador.getIdcoordinador();
                if (findCoordinador(id) == null) {
                    throw new NonexistentEntityException("The coordinador with id " + id + " no longer exists.");
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
            Coordinador coordinador;
            try {
                coordinador = em.getReference(Coordinador.class, id);
                coordinador.getIdcoordinador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The coordinador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Instructor> instructorCollectionOrphanCheck = coordinador.getInstructorCollection();
            for (Instructor instructorCollectionOrphanCheckInstructor : instructorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Coordinador (" + coordinador + ") cannot be destroyed since the Instructor " + instructorCollectionOrphanCheckInstructor + " in its instructorCollection field has a non-nullable coordinadorIdcoordinador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Centro centroIdcentro = coordinador.getCentroIdcentro();
            if (centroIdcentro != null) {
                centroIdcentro.getCoordinadorCollection().remove(coordinador);
                centroIdcentro = em.merge(centroIdcentro);
            }
            em.remove(coordinador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Coordinador> findCoordinadorEntities() {
        return findCoordinadorEntities(true, -1, -1);
    }

    public List<Coordinador> findCoordinadorEntities(int maxResults, int firstResult) {
        return findCoordinadorEntities(false, maxResults, firstResult);
    }

    private List<Coordinador> findCoordinadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Coordinador.class));
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

    public Coordinador findCoordinador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Coordinador.class, id);
        } finally {
            em.close();
        }
    }

    public int getCoordinadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Coordinador> rt = cq.from(Coordinador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
