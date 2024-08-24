/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Centro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Regional;
import Entidades.Coordinador;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.Instructor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ASUS
 */
public class CentroJpaController1 implements Serializable {

    public CentroJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Centro centro) throws PreexistingEntityException, Exception {
        if (centro.getCoordinadorCollection() == null) {
            centro.setCoordinadorCollection(new ArrayList<Coordinador>());
        }
        if (centro.getInstructorCollection() == null) {
            centro.setInstructorCollection(new ArrayList<Instructor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regional regionalIdregional = centro.getRegionalIdregional();
            if (regionalIdregional != null) {
                regionalIdregional = em.getReference(regionalIdregional.getClass(), regionalIdregional.getIdregional());
                centro.setRegionalIdregional(regionalIdregional);
            }
            Collection<Coordinador> attachedCoordinadorCollection = new ArrayList<Coordinador>();
            for (Coordinador coordinadorCollectionCoordinadorToAttach : centro.getCoordinadorCollection()) {
                coordinadorCollectionCoordinadorToAttach = em.getReference(coordinadorCollectionCoordinadorToAttach.getClass(), coordinadorCollectionCoordinadorToAttach.getIdcoordinador());
                attachedCoordinadorCollection.add(coordinadorCollectionCoordinadorToAttach);
            }
            centro.setCoordinadorCollection(attachedCoordinadorCollection);
            Collection<Instructor> attachedInstructorCollection = new ArrayList<Instructor>();
            for (Instructor instructorCollectionInstructorToAttach : centro.getInstructorCollection()) {
                instructorCollectionInstructorToAttach = em.getReference(instructorCollectionInstructorToAttach.getClass(), instructorCollectionInstructorToAttach.getIdinstructor());
                attachedInstructorCollection.add(instructorCollectionInstructorToAttach);
            }
            centro.setInstructorCollection(attachedInstructorCollection);
            em.persist(centro);
            if (regionalIdregional != null) {
                regionalIdregional.getCentroCollection().add(centro);
                regionalIdregional = em.merge(regionalIdregional);
            }
            for (Coordinador coordinadorCollectionCoordinador : centro.getCoordinadorCollection()) {
                Centro oldCentroIdcentroOfCoordinadorCollectionCoordinador = coordinadorCollectionCoordinador.getCentroIdcentro();
                coordinadorCollectionCoordinador.setCentroIdcentro(centro);
                coordinadorCollectionCoordinador = em.merge(coordinadorCollectionCoordinador);
                if (oldCentroIdcentroOfCoordinadorCollectionCoordinador != null) {
                    oldCentroIdcentroOfCoordinadorCollectionCoordinador.getCoordinadorCollection().remove(coordinadorCollectionCoordinador);
                    oldCentroIdcentroOfCoordinadorCollectionCoordinador = em.merge(oldCentroIdcentroOfCoordinadorCollectionCoordinador);
                }
            }
            for (Instructor instructorCollectionInstructor : centro.getInstructorCollection()) {
                Centro oldCentroIdcentroOfInstructorCollectionInstructor = instructorCollectionInstructor.getCentroIdcentro();
                instructorCollectionInstructor.setCentroIdcentro(centro);
                instructorCollectionInstructor = em.merge(instructorCollectionInstructor);
                if (oldCentroIdcentroOfInstructorCollectionInstructor != null) {
                    oldCentroIdcentroOfInstructorCollectionInstructor.getInstructorCollection().remove(instructorCollectionInstructor);
                    oldCentroIdcentroOfInstructorCollectionInstructor = em.merge(oldCentroIdcentroOfInstructorCollectionInstructor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCentro(centro.getIdcentro()) != null) {
                throw new PreexistingEntityException("Centro " + centro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Centro centro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Centro persistentCentro = em.find(Centro.class, centro.getIdcentro());
            Regional regionalIdregionalOld = persistentCentro.getRegionalIdregional();
            Regional regionalIdregionalNew = centro.getRegionalIdregional();
            Collection<Coordinador> coordinadorCollectionOld = persistentCentro.getCoordinadorCollection();
            Collection<Coordinador> coordinadorCollectionNew = centro.getCoordinadorCollection();
            Collection<Instructor> instructorCollectionOld = persistentCentro.getInstructorCollection();
            Collection<Instructor> instructorCollectionNew = centro.getInstructorCollection();
            List<String> illegalOrphanMessages = null;
            for (Coordinador coordinadorCollectionOldCoordinador : coordinadorCollectionOld) {
                if (!coordinadorCollectionNew.contains(coordinadorCollectionOldCoordinador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Coordinador " + coordinadorCollectionOldCoordinador + " since its centroIdcentro field is not nullable.");
                }
            }
            for (Instructor instructorCollectionOldInstructor : instructorCollectionOld) {
                if (!instructorCollectionNew.contains(instructorCollectionOldInstructor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Instructor " + instructorCollectionOldInstructor + " since its centroIdcentro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (regionalIdregionalNew != null) {
                regionalIdregionalNew = em.getReference(regionalIdregionalNew.getClass(), regionalIdregionalNew.getIdregional());
                centro.setRegionalIdregional(regionalIdregionalNew);
            }
            Collection<Coordinador> attachedCoordinadorCollectionNew = new ArrayList<Coordinador>();
            for (Coordinador coordinadorCollectionNewCoordinadorToAttach : coordinadorCollectionNew) {
                coordinadorCollectionNewCoordinadorToAttach = em.getReference(coordinadorCollectionNewCoordinadorToAttach.getClass(), coordinadorCollectionNewCoordinadorToAttach.getIdcoordinador());
                attachedCoordinadorCollectionNew.add(coordinadorCollectionNewCoordinadorToAttach);
            }
            coordinadorCollectionNew = attachedCoordinadorCollectionNew;
            centro.setCoordinadorCollection(coordinadorCollectionNew);
            Collection<Instructor> attachedInstructorCollectionNew = new ArrayList<Instructor>();
            for (Instructor instructorCollectionNewInstructorToAttach : instructorCollectionNew) {
                instructorCollectionNewInstructorToAttach = em.getReference(instructorCollectionNewInstructorToAttach.getClass(), instructorCollectionNewInstructorToAttach.getIdinstructor());
                attachedInstructorCollectionNew.add(instructorCollectionNewInstructorToAttach);
            }
            instructorCollectionNew = attachedInstructorCollectionNew;
            centro.setInstructorCollection(instructorCollectionNew);
            centro = em.merge(centro);
            if (regionalIdregionalOld != null && !regionalIdregionalOld.equals(regionalIdregionalNew)) {
                regionalIdregionalOld.getCentroCollection().remove(centro);
                regionalIdregionalOld = em.merge(regionalIdregionalOld);
            }
            if (regionalIdregionalNew != null && !regionalIdregionalNew.equals(regionalIdregionalOld)) {
                regionalIdregionalNew.getCentroCollection().add(centro);
                regionalIdregionalNew = em.merge(regionalIdregionalNew);
            }
            for (Coordinador coordinadorCollectionNewCoordinador : coordinadorCollectionNew) {
                if (!coordinadorCollectionOld.contains(coordinadorCollectionNewCoordinador)) {
                    Centro oldCentroIdcentroOfCoordinadorCollectionNewCoordinador = coordinadorCollectionNewCoordinador.getCentroIdcentro();
                    coordinadorCollectionNewCoordinador.setCentroIdcentro(centro);
                    coordinadorCollectionNewCoordinador = em.merge(coordinadorCollectionNewCoordinador);
                    if (oldCentroIdcentroOfCoordinadorCollectionNewCoordinador != null && !oldCentroIdcentroOfCoordinadorCollectionNewCoordinador.equals(centro)) {
                        oldCentroIdcentroOfCoordinadorCollectionNewCoordinador.getCoordinadorCollection().remove(coordinadorCollectionNewCoordinador);
                        oldCentroIdcentroOfCoordinadorCollectionNewCoordinador = em.merge(oldCentroIdcentroOfCoordinadorCollectionNewCoordinador);
                    }
                }
            }
            for (Instructor instructorCollectionNewInstructor : instructorCollectionNew) {
                if (!instructorCollectionOld.contains(instructorCollectionNewInstructor)) {
                    Centro oldCentroIdcentroOfInstructorCollectionNewInstructor = instructorCollectionNewInstructor.getCentroIdcentro();
                    instructorCollectionNewInstructor.setCentroIdcentro(centro);
                    instructorCollectionNewInstructor = em.merge(instructorCollectionNewInstructor);
                    if (oldCentroIdcentroOfInstructorCollectionNewInstructor != null && !oldCentroIdcentroOfInstructorCollectionNewInstructor.equals(centro)) {
                        oldCentroIdcentroOfInstructorCollectionNewInstructor.getInstructorCollection().remove(instructorCollectionNewInstructor);
                        oldCentroIdcentroOfInstructorCollectionNewInstructor = em.merge(oldCentroIdcentroOfInstructorCollectionNewInstructor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = centro.getIdcentro();
                if (findCentro(id) == null) {
                    throw new NonexistentEntityException("The centro with id " + id + " no longer exists.");
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
            Centro centro;
            try {
                centro = em.getReference(Centro.class, id);
                centro.getIdcentro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The centro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Coordinador> coordinadorCollectionOrphanCheck = centro.getCoordinadorCollection();
            for (Coordinador coordinadorCollectionOrphanCheckCoordinador : coordinadorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Centro (" + centro + ") cannot be destroyed since the Coordinador " + coordinadorCollectionOrphanCheckCoordinador + " in its coordinadorCollection field has a non-nullable centroIdcentro field.");
            }
            Collection<Instructor> instructorCollectionOrphanCheck = centro.getInstructorCollection();
            for (Instructor instructorCollectionOrphanCheckInstructor : instructorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Centro (" + centro + ") cannot be destroyed since the Instructor " + instructorCollectionOrphanCheckInstructor + " in its instructorCollection field has a non-nullable centroIdcentro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Regional regionalIdregional = centro.getRegionalIdregional();
            if (regionalIdregional != null) {
                regionalIdregional.getCentroCollection().remove(centro);
                regionalIdregional = em.merge(regionalIdregional);
            }
            em.remove(centro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Centro> findCentroEntities() {
        return findCentroEntities(true, -1, -1);
    }

    public List<Centro> findCentroEntities(int maxResults, int firstResult) {
        return findCentroEntities(false, maxResults, firstResult);
    }

    private List<Centro> findCentroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Centro.class));
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

    public Centro findCentro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Centro.class, id);
        } finally {
            em.close();
        }
    }

    public int getCentroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Centro> rt = cq.from(Centro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
