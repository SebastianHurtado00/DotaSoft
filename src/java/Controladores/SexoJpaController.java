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
import java.util.ArrayList;
import java.util.Collection;
import Entidades.Dotacion;
import Entidades.Sexo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class SexoJpaController implements Serializable {

    public SexoJpaController() {
       this.emf = Persistence.createEntityManagerFactory("Dotacion_SenaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sexo sexo) throws PreexistingEntityException, Exception {
        if (sexo.getCaracterizarInstructorCollection() == null) {
            sexo.setCaracterizarInstructorCollection(new ArrayList<CaracterizarInstructor>());
        }
        if (sexo.getDotacionCollection() == null) {
            sexo.setDotacionCollection(new ArrayList<Dotacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<CaracterizarInstructor> attachedCaracterizarInstructorCollection = new ArrayList<CaracterizarInstructor>();
            for (CaracterizarInstructor caracterizarInstructorCollectionCaracterizarInstructorToAttach : sexo.getCaracterizarInstructorCollection()) {
                caracterizarInstructorCollectionCaracterizarInstructorToAttach = em.getReference(caracterizarInstructorCollectionCaracterizarInstructorToAttach.getClass(), caracterizarInstructorCollectionCaracterizarInstructorToAttach.getIdCaracterizarInstructor());
                attachedCaracterizarInstructorCollection.add(caracterizarInstructorCollectionCaracterizarInstructorToAttach);
            }
            sexo.setCaracterizarInstructorCollection(attachedCaracterizarInstructorCollection);
            Collection<Dotacion> attachedDotacionCollection = new ArrayList<Dotacion>();
            for (Dotacion dotacionCollectionDotacionToAttach : sexo.getDotacionCollection()) {
                dotacionCollectionDotacionToAttach = em.getReference(dotacionCollectionDotacionToAttach.getClass(), dotacionCollectionDotacionToAttach.getIddotacion());
                attachedDotacionCollection.add(dotacionCollectionDotacionToAttach);
            }
            sexo.setDotacionCollection(attachedDotacionCollection);
            em.persist(sexo);
            for (CaracterizarInstructor caracterizarInstructorCollectionCaracterizarInstructor : sexo.getCaracterizarInstructorCollection()) {
                Sexo oldSexoIdsexoOfCaracterizarInstructorCollectionCaracterizarInstructor = caracterizarInstructorCollectionCaracterizarInstructor.getSexoIdsexo();
                caracterizarInstructorCollectionCaracterizarInstructor.setSexoIdsexo(sexo);
                caracterizarInstructorCollectionCaracterizarInstructor = em.merge(caracterizarInstructorCollectionCaracterizarInstructor);
                if (oldSexoIdsexoOfCaracterizarInstructorCollectionCaracterizarInstructor != null) {
                    oldSexoIdsexoOfCaracterizarInstructorCollectionCaracterizarInstructor.getCaracterizarInstructorCollection().remove(caracterizarInstructorCollectionCaracterizarInstructor);
                    oldSexoIdsexoOfCaracterizarInstructorCollectionCaracterizarInstructor = em.merge(oldSexoIdsexoOfCaracterizarInstructorCollectionCaracterizarInstructor);
                }
            }
            for (Dotacion dotacionCollectionDotacion : sexo.getDotacionCollection()) {
                Sexo oldSexoIdsexoOfDotacionCollectionDotacion = dotacionCollectionDotacion.getSexoIdsexo();
                dotacionCollectionDotacion.setSexoIdsexo(sexo);
                dotacionCollectionDotacion = em.merge(dotacionCollectionDotacion);
                if (oldSexoIdsexoOfDotacionCollectionDotacion != null) {
                    oldSexoIdsexoOfDotacionCollectionDotacion.getDotacionCollection().remove(dotacionCollectionDotacion);
                    oldSexoIdsexoOfDotacionCollectionDotacion = em.merge(oldSexoIdsexoOfDotacionCollectionDotacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSexo(sexo.getIdsexo()) != null) {
                throw new PreexistingEntityException("Sexo " + sexo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sexo sexo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sexo persistentSexo = em.find(Sexo.class, sexo.getIdsexo());
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionOld = persistentSexo.getCaracterizarInstructorCollection();
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionNew = sexo.getCaracterizarInstructorCollection();
            Collection<Dotacion> dotacionCollectionOld = persistentSexo.getDotacionCollection();
            Collection<Dotacion> dotacionCollectionNew = sexo.getDotacionCollection();
            List<String> illegalOrphanMessages = null;
            for (CaracterizarInstructor caracterizarInstructorCollectionOldCaracterizarInstructor : caracterizarInstructorCollectionOld) {
                if (!caracterizarInstructorCollectionNew.contains(caracterizarInstructorCollectionOldCaracterizarInstructor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CaracterizarInstructor " + caracterizarInstructorCollectionOldCaracterizarInstructor + " since its sexoIdsexo field is not nullable.");
                }
            }
            for (Dotacion dotacionCollectionOldDotacion : dotacionCollectionOld) {
                if (!dotacionCollectionNew.contains(dotacionCollectionOldDotacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dotacion " + dotacionCollectionOldDotacion + " since its sexoIdsexo field is not nullable.");
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
            sexo.setCaracterizarInstructorCollection(caracterizarInstructorCollectionNew);
            Collection<Dotacion> attachedDotacionCollectionNew = new ArrayList<Dotacion>();
            for (Dotacion dotacionCollectionNewDotacionToAttach : dotacionCollectionNew) {
                dotacionCollectionNewDotacionToAttach = em.getReference(dotacionCollectionNewDotacionToAttach.getClass(), dotacionCollectionNewDotacionToAttach.getIddotacion());
                attachedDotacionCollectionNew.add(dotacionCollectionNewDotacionToAttach);
            }
            dotacionCollectionNew = attachedDotacionCollectionNew;
            sexo.setDotacionCollection(dotacionCollectionNew);
            sexo = em.merge(sexo);
            for (CaracterizarInstructor caracterizarInstructorCollectionNewCaracterizarInstructor : caracterizarInstructorCollectionNew) {
                if (!caracterizarInstructorCollectionOld.contains(caracterizarInstructorCollectionNewCaracterizarInstructor)) {
                    Sexo oldSexoIdsexoOfCaracterizarInstructorCollectionNewCaracterizarInstructor = caracterizarInstructorCollectionNewCaracterizarInstructor.getSexoIdsexo();
                    caracterizarInstructorCollectionNewCaracterizarInstructor.setSexoIdsexo(sexo);
                    caracterizarInstructorCollectionNewCaracterizarInstructor = em.merge(caracterizarInstructorCollectionNewCaracterizarInstructor);
                    if (oldSexoIdsexoOfCaracterizarInstructorCollectionNewCaracterizarInstructor != null && !oldSexoIdsexoOfCaracterizarInstructorCollectionNewCaracterizarInstructor.equals(sexo)) {
                        oldSexoIdsexoOfCaracterizarInstructorCollectionNewCaracterizarInstructor.getCaracterizarInstructorCollection().remove(caracterizarInstructorCollectionNewCaracterizarInstructor);
                        oldSexoIdsexoOfCaracterizarInstructorCollectionNewCaracterizarInstructor = em.merge(oldSexoIdsexoOfCaracterizarInstructorCollectionNewCaracterizarInstructor);
                    }
                }
            }
            for (Dotacion dotacionCollectionNewDotacion : dotacionCollectionNew) {
                if (!dotacionCollectionOld.contains(dotacionCollectionNewDotacion)) {
                    Sexo oldSexoIdsexoOfDotacionCollectionNewDotacion = dotacionCollectionNewDotacion.getSexoIdsexo();
                    dotacionCollectionNewDotacion.setSexoIdsexo(sexo);
                    dotacionCollectionNewDotacion = em.merge(dotacionCollectionNewDotacion);
                    if (oldSexoIdsexoOfDotacionCollectionNewDotacion != null && !oldSexoIdsexoOfDotacionCollectionNewDotacion.equals(sexo)) {
                        oldSexoIdsexoOfDotacionCollectionNewDotacion.getDotacionCollection().remove(dotacionCollectionNewDotacion);
                        oldSexoIdsexoOfDotacionCollectionNewDotacion = em.merge(oldSexoIdsexoOfDotacionCollectionNewDotacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sexo.getIdsexo();
                if (findSexo(id) == null) {
                    throw new NonexistentEntityException("The sexo with id " + id + " no longer exists.");
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
            Sexo sexo;
            try {
                sexo = em.getReference(Sexo.class, id);
                sexo.getIdsexo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sexo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionOrphanCheck = sexo.getCaracterizarInstructorCollection();
            for (CaracterizarInstructor caracterizarInstructorCollectionOrphanCheckCaracterizarInstructor : caracterizarInstructorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sexo (" + sexo + ") cannot be destroyed since the CaracterizarInstructor " + caracterizarInstructorCollectionOrphanCheckCaracterizarInstructor + " in its caracterizarInstructorCollection field has a non-nullable sexoIdsexo field.");
            }
            Collection<Dotacion> dotacionCollectionOrphanCheck = sexo.getDotacionCollection();
            for (Dotacion dotacionCollectionOrphanCheckDotacion : dotacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sexo (" + sexo + ") cannot be destroyed since the Dotacion " + dotacionCollectionOrphanCheckDotacion + " in its dotacionCollection field has a non-nullable sexoIdsexo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sexo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sexo> findSexoEntities() {
        return findSexoEntities(true, -1, -1);
    }

    public List<Sexo> findSexoEntities(int maxResults, int firstResult) {
        return findSexoEntities(false, maxResults, firstResult);
    }

    private List<Sexo> findSexoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sexo.class));
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

    public Sexo findSexo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sexo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSexoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sexo> rt = cq.from(Sexo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
