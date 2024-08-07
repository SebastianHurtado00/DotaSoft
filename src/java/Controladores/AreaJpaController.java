/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Area;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Red;
import Entidades.CaracterizarInstructor;
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
public class AreaJpaController implements Serializable {

    public AreaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Dotacion_SenaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Area area) throws PreexistingEntityException, Exception {
        if (area.getCaracterizarInstructorCollection() == null) {
            area.setCaracterizarInstructorCollection(new ArrayList<CaracterizarInstructor>());
        }
        if (area.getDotacionCollection() == null) {
            area.setDotacionCollection(new ArrayList<Dotacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Red redIdred = area.getRedIdred();
            if (redIdred != null) {
                redIdred = em.getReference(redIdred.getClass(), redIdred.getIdred());
                area.setRedIdred(redIdred);
            }
            Collection<CaracterizarInstructor> attachedCaracterizarInstructorCollection = new ArrayList<CaracterizarInstructor>();
            for (CaracterizarInstructor caracterizarInstructorCollectionCaracterizarInstructorToAttach : area.getCaracterizarInstructorCollection()) {
                caracterizarInstructorCollectionCaracterizarInstructorToAttach = em.getReference(caracterizarInstructorCollectionCaracterizarInstructorToAttach.getClass(), caracterizarInstructorCollectionCaracterizarInstructorToAttach.getIdCaracterizarInstructor());
                attachedCaracterizarInstructorCollection.add(caracterizarInstructorCollectionCaracterizarInstructorToAttach);
            }
            area.setCaracterizarInstructorCollection(attachedCaracterizarInstructorCollection);
            Collection<Dotacion> attachedDotacionCollection = new ArrayList<Dotacion>();
            for (Dotacion dotacionCollectionDotacionToAttach : area.getDotacionCollection()) {
                dotacionCollectionDotacionToAttach = em.getReference(dotacionCollectionDotacionToAttach.getClass(), dotacionCollectionDotacionToAttach.getIddotacion());
                attachedDotacionCollection.add(dotacionCollectionDotacionToAttach);
            }
            area.setDotacionCollection(attachedDotacionCollection);
            em.persist(area);
            if (redIdred != null) {
                redIdred.getAreaCollection().add(area);
                redIdred = em.merge(redIdred);
            }
            for (CaracterizarInstructor caracterizarInstructorCollectionCaracterizarInstructor : area.getCaracterizarInstructorCollection()) {
                Area oldAreaIdareaOfCaracterizarInstructorCollectionCaracterizarInstructor = caracterizarInstructorCollectionCaracterizarInstructor.getAreaIdarea();
                caracterizarInstructorCollectionCaracterizarInstructor.setAreaIdarea(area);
                caracterizarInstructorCollectionCaracterizarInstructor = em.merge(caracterizarInstructorCollectionCaracterizarInstructor);
                if (oldAreaIdareaOfCaracterizarInstructorCollectionCaracterizarInstructor != null) {
                    oldAreaIdareaOfCaracterizarInstructorCollectionCaracterizarInstructor.getCaracterizarInstructorCollection().remove(caracterizarInstructorCollectionCaracterizarInstructor);
                    oldAreaIdareaOfCaracterizarInstructorCollectionCaracterizarInstructor = em.merge(oldAreaIdareaOfCaracterizarInstructorCollectionCaracterizarInstructor);
                }
            }
            for (Dotacion dotacionCollectionDotacion : area.getDotacionCollection()) {
                Area oldAreaIdareaOfDotacionCollectionDotacion = dotacionCollectionDotacion.getAreaIdarea();
                dotacionCollectionDotacion.setAreaIdarea(area);
                dotacionCollectionDotacion = em.merge(dotacionCollectionDotacion);
                if (oldAreaIdareaOfDotacionCollectionDotacion != null) {
                    oldAreaIdareaOfDotacionCollectionDotacion.getDotacionCollection().remove(dotacionCollectionDotacion);
                    oldAreaIdareaOfDotacionCollectionDotacion = em.merge(oldAreaIdareaOfDotacionCollectionDotacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArea(area.getIdarea()) != null) {
                throw new PreexistingEntityException("Area " + area + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Area area) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area persistentArea = em.find(Area.class, area.getIdarea());
            Red redIdredOld = persistentArea.getRedIdred();
            Red redIdredNew = area.getRedIdred();
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionOld = persistentArea.getCaracterizarInstructorCollection();
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionNew = area.getCaracterizarInstructorCollection();
            Collection<Dotacion> dotacionCollectionOld = persistentArea.getDotacionCollection();
            Collection<Dotacion> dotacionCollectionNew = area.getDotacionCollection();
            List<String> illegalOrphanMessages = null;
            for (CaracterizarInstructor caracterizarInstructorCollectionOldCaracterizarInstructor : caracterizarInstructorCollectionOld) {
                if (!caracterizarInstructorCollectionNew.contains(caracterizarInstructorCollectionOldCaracterizarInstructor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CaracterizarInstructor " + caracterizarInstructorCollectionOldCaracterizarInstructor + " since its areaIdarea field is not nullable.");
                }
            }
            for (Dotacion dotacionCollectionOldDotacion : dotacionCollectionOld) {
                if (!dotacionCollectionNew.contains(dotacionCollectionOldDotacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dotacion " + dotacionCollectionOldDotacion + " since its areaIdarea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (redIdredNew != null) {
                redIdredNew = em.getReference(redIdredNew.getClass(), redIdredNew.getIdred());
                area.setRedIdred(redIdredNew);
            }
            Collection<CaracterizarInstructor> attachedCaracterizarInstructorCollectionNew = new ArrayList<CaracterizarInstructor>();
            for (CaracterizarInstructor caracterizarInstructorCollectionNewCaracterizarInstructorToAttach : caracterizarInstructorCollectionNew) {
                caracterizarInstructorCollectionNewCaracterizarInstructorToAttach = em.getReference(caracterizarInstructorCollectionNewCaracterizarInstructorToAttach.getClass(), caracterizarInstructorCollectionNewCaracterizarInstructorToAttach.getIdCaracterizarInstructor());
                attachedCaracterizarInstructorCollectionNew.add(caracterizarInstructorCollectionNewCaracterizarInstructorToAttach);
            }
            caracterizarInstructorCollectionNew = attachedCaracterizarInstructorCollectionNew;
            area.setCaracterizarInstructorCollection(caracterizarInstructorCollectionNew);
            Collection<Dotacion> attachedDotacionCollectionNew = new ArrayList<Dotacion>();
            for (Dotacion dotacionCollectionNewDotacionToAttach : dotacionCollectionNew) {
                dotacionCollectionNewDotacionToAttach = em.getReference(dotacionCollectionNewDotacionToAttach.getClass(), dotacionCollectionNewDotacionToAttach.getIddotacion());
                attachedDotacionCollectionNew.add(dotacionCollectionNewDotacionToAttach);
            }
            dotacionCollectionNew = attachedDotacionCollectionNew;
            area.setDotacionCollection(dotacionCollectionNew);
            area = em.merge(area);
            if (redIdredOld != null && !redIdredOld.equals(redIdredNew)) {
                redIdredOld.getAreaCollection().remove(area);
                redIdredOld = em.merge(redIdredOld);
            }
            if (redIdredNew != null && !redIdredNew.equals(redIdredOld)) {
                redIdredNew.getAreaCollection().add(area);
                redIdredNew = em.merge(redIdredNew);
            }
            for (CaracterizarInstructor caracterizarInstructorCollectionNewCaracterizarInstructor : caracterizarInstructorCollectionNew) {
                if (!caracterizarInstructorCollectionOld.contains(caracterizarInstructorCollectionNewCaracterizarInstructor)) {
                    Area oldAreaIdareaOfCaracterizarInstructorCollectionNewCaracterizarInstructor = caracterizarInstructorCollectionNewCaracterizarInstructor.getAreaIdarea();
                    caracterizarInstructorCollectionNewCaracterizarInstructor.setAreaIdarea(area);
                    caracterizarInstructorCollectionNewCaracterizarInstructor = em.merge(caracterizarInstructorCollectionNewCaracterizarInstructor);
                    if (oldAreaIdareaOfCaracterizarInstructorCollectionNewCaracterizarInstructor != null && !oldAreaIdareaOfCaracterizarInstructorCollectionNewCaracterizarInstructor.equals(area)) {
                        oldAreaIdareaOfCaracterizarInstructorCollectionNewCaracterizarInstructor.getCaracterizarInstructorCollection().remove(caracterizarInstructorCollectionNewCaracterizarInstructor);
                        oldAreaIdareaOfCaracterizarInstructorCollectionNewCaracterizarInstructor = em.merge(oldAreaIdareaOfCaracterizarInstructorCollectionNewCaracterizarInstructor);
                    }
                }
            }
            for (Dotacion dotacionCollectionNewDotacion : dotacionCollectionNew) {
                if (!dotacionCollectionOld.contains(dotacionCollectionNewDotacion)) {
                    Area oldAreaIdareaOfDotacionCollectionNewDotacion = dotacionCollectionNewDotacion.getAreaIdarea();
                    dotacionCollectionNewDotacion.setAreaIdarea(area);
                    dotacionCollectionNewDotacion = em.merge(dotacionCollectionNewDotacion);
                    if (oldAreaIdareaOfDotacionCollectionNewDotacion != null && !oldAreaIdareaOfDotacionCollectionNewDotacion.equals(area)) {
                        oldAreaIdareaOfDotacionCollectionNewDotacion.getDotacionCollection().remove(dotacionCollectionNewDotacion);
                        oldAreaIdareaOfDotacionCollectionNewDotacion = em.merge(oldAreaIdareaOfDotacionCollectionNewDotacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = area.getIdarea();
                if (findArea(id) == null) {
                    throw new NonexistentEntityException("The area with id " + id + " no longer exists.");
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
            Area area;
            try {
                area = em.getReference(Area.class, id);
                area.getIdarea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The area with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionOrphanCheck = area.getCaracterizarInstructorCollection();
            for (CaracterizarInstructor caracterizarInstructorCollectionOrphanCheckCaracterizarInstructor : caracterizarInstructorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the CaracterizarInstructor " + caracterizarInstructorCollectionOrphanCheckCaracterizarInstructor + " in its caracterizarInstructorCollection field has a non-nullable areaIdarea field.");
            }
            Collection<Dotacion> dotacionCollectionOrphanCheck = area.getDotacionCollection();
            for (Dotacion dotacionCollectionOrphanCheckDotacion : dotacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the Dotacion " + dotacionCollectionOrphanCheckDotacion + " in its dotacionCollection field has a non-nullable areaIdarea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Red redIdred = area.getRedIdred();
            if (redIdred != null) {
                redIdred.getAreaCollection().remove(area);
                redIdred = em.merge(redIdred);
            }
            em.remove(area);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Area> findAreaEntities() {
        return findAreaEntities(true, -1, -1);
    }

    public List<Area> findAreaEntities(int maxResults, int firstResult) {
        return findAreaEntities(false, maxResults, firstResult);
    }

    private List<Area> findAreaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Area.class));
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

    public Area findArea(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Area.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Area> rt = cq.from(Area.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
