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
import Entidades.Descuento;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.CaracterizarInstructor;
import Entidades.Instructor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class InstructorJpaController implements Serializable {

    public InstructorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Dotacion_SenaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Instructor instructor) throws PreexistingEntityException, Exception {
        if (instructor.getDescuentoCollection() == null) {
            instructor.setDescuentoCollection(new ArrayList<Descuento>());
        }
        if (instructor.getCaracterizarInstructorCollection() == null) {
            instructor.setCaracterizarInstructorCollection(new ArrayList<CaracterizarInstructor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Centro centroIdcentro = instructor.getCentroIdcentro();
            if (centroIdcentro != null) {
                centroIdcentro = em.getReference(centroIdcentro.getClass(), centroIdcentro.getIdcentro());
                instructor.setCentroIdcentro(centroIdcentro);
            }
            Coordinador coordinadorIdcoordinador = instructor.getCoordinadorIdcoordinador();
            if (coordinadorIdcoordinador != null) {
                coordinadorIdcoordinador = em.getReference(coordinadorIdcoordinador.getClass(), coordinadorIdcoordinador.getIdcoordinador());
                instructor.setCoordinadorIdcoordinador(coordinadorIdcoordinador);
            }
            Collection<Descuento> attachedDescuentoCollection = new ArrayList<Descuento>();
            for (Descuento descuentoCollectionDescuentoToAttach : instructor.getDescuentoCollection()) {
                descuentoCollectionDescuentoToAttach = em.getReference(descuentoCollectionDescuentoToAttach.getClass(), descuentoCollectionDescuentoToAttach.getIddescuento());
                attachedDescuentoCollection.add(descuentoCollectionDescuentoToAttach);
            }
            instructor.setDescuentoCollection(attachedDescuentoCollection);
            Collection<CaracterizarInstructor> attachedCaracterizarInstructorCollection = new ArrayList<CaracterizarInstructor>();
            for (CaracterizarInstructor caracterizarInstructorCollectionCaracterizarInstructorToAttach : instructor.getCaracterizarInstructorCollection()) {
                caracterizarInstructorCollectionCaracterizarInstructorToAttach = em.getReference(caracterizarInstructorCollectionCaracterizarInstructorToAttach.getClass(), caracterizarInstructorCollectionCaracterizarInstructorToAttach.getIdCaracterizarInstructor());
                attachedCaracterizarInstructorCollection.add(caracterizarInstructorCollectionCaracterizarInstructorToAttach);
            }
            instructor.setCaracterizarInstructorCollection(attachedCaracterizarInstructorCollection);
            em.persist(instructor);
            if (centroIdcentro != null) {
                centroIdcentro.getInstructorCollection().add(instructor);
                centroIdcentro = em.merge(centroIdcentro);
            }
            if (coordinadorIdcoordinador != null) {
                coordinadorIdcoordinador.getInstructorCollection().add(instructor);
                coordinadorIdcoordinador = em.merge(coordinadorIdcoordinador);
            }
            for (Descuento descuentoCollectionDescuento : instructor.getDescuentoCollection()) {
                Instructor oldInstructoridInstructorOfDescuentoCollectionDescuento = descuentoCollectionDescuento.getInstructoridInstructor();
                descuentoCollectionDescuento.setInstructoridInstructor(instructor);
                descuentoCollectionDescuento = em.merge(descuentoCollectionDescuento);
                if (oldInstructoridInstructorOfDescuentoCollectionDescuento != null) {
                    oldInstructoridInstructorOfDescuentoCollectionDescuento.getDescuentoCollection().remove(descuentoCollectionDescuento);
                    oldInstructoridInstructorOfDescuentoCollectionDescuento = em.merge(oldInstructoridInstructorOfDescuentoCollectionDescuento);
                }
            }
            for (CaracterizarInstructor caracterizarInstructorCollectionCaracterizarInstructor : instructor.getCaracterizarInstructorCollection()) {
                Instructor oldInstructorIdinstructorOfCaracterizarInstructorCollectionCaracterizarInstructor = caracterizarInstructorCollectionCaracterizarInstructor.getInstructorIdinstructor();
                caracterizarInstructorCollectionCaracterizarInstructor.setInstructorIdinstructor(instructor);
                caracterizarInstructorCollectionCaracterizarInstructor = em.merge(caracterizarInstructorCollectionCaracterizarInstructor);
                if (oldInstructorIdinstructorOfCaracterizarInstructorCollectionCaracterizarInstructor != null) {
                    oldInstructorIdinstructorOfCaracterizarInstructorCollectionCaracterizarInstructor.getCaracterizarInstructorCollection().remove(caracterizarInstructorCollectionCaracterizarInstructor);
                    oldInstructorIdinstructorOfCaracterizarInstructorCollectionCaracterizarInstructor = em.merge(oldInstructorIdinstructorOfCaracterizarInstructorCollectionCaracterizarInstructor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInstructor(instructor.getIdinstructor()) != null) {
                throw new PreexistingEntityException("Instructor " + instructor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Instructor instructor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Instructor persistentInstructor = em.find(Instructor.class, instructor.getIdinstructor());
            Centro centroIdcentroOld = persistentInstructor.getCentroIdcentro();
            Centro centroIdcentroNew = instructor.getCentroIdcentro();
            Coordinador coordinadorIdcoordinadorOld = persistentInstructor.getCoordinadorIdcoordinador();
            Coordinador coordinadorIdcoordinadorNew = instructor.getCoordinadorIdcoordinador();
            Collection<Descuento> descuentoCollectionOld = persistentInstructor.getDescuentoCollection();
            Collection<Descuento> descuentoCollectionNew = instructor.getDescuentoCollection();
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionOld = persistentInstructor.getCaracterizarInstructorCollection();
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionNew = instructor.getCaracterizarInstructorCollection();
            List<String> illegalOrphanMessages = null;
            for (Descuento descuentoCollectionOldDescuento : descuentoCollectionOld) {
                if (!descuentoCollectionNew.contains(descuentoCollectionOldDescuento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Descuento " + descuentoCollectionOldDescuento + " since its instructoridInstructor field is not nullable.");
                }
            }
            for (CaracterizarInstructor caracterizarInstructorCollectionOldCaracterizarInstructor : caracterizarInstructorCollectionOld) {
                if (!caracterizarInstructorCollectionNew.contains(caracterizarInstructorCollectionOldCaracterizarInstructor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CaracterizarInstructor " + caracterizarInstructorCollectionOldCaracterizarInstructor + " since its instructorIdinstructor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (centroIdcentroNew != null) {
                centroIdcentroNew = em.getReference(centroIdcentroNew.getClass(), centroIdcentroNew.getIdcentro());
                instructor.setCentroIdcentro(centroIdcentroNew);
            }
            if (coordinadorIdcoordinadorNew != null) {
                coordinadorIdcoordinadorNew = em.getReference(coordinadorIdcoordinadorNew.getClass(), coordinadorIdcoordinadorNew.getIdcoordinador());
                instructor.setCoordinadorIdcoordinador(coordinadorIdcoordinadorNew);
            }
            Collection<Descuento> attachedDescuentoCollectionNew = new ArrayList<Descuento>();
            for (Descuento descuentoCollectionNewDescuentoToAttach : descuentoCollectionNew) {
                descuentoCollectionNewDescuentoToAttach = em.getReference(descuentoCollectionNewDescuentoToAttach.getClass(), descuentoCollectionNewDescuentoToAttach.getIddescuento());
                attachedDescuentoCollectionNew.add(descuentoCollectionNewDescuentoToAttach);
            }
            descuentoCollectionNew = attachedDescuentoCollectionNew;
            instructor.setDescuentoCollection(descuentoCollectionNew);
            Collection<CaracterizarInstructor> attachedCaracterizarInstructorCollectionNew = new ArrayList<CaracterizarInstructor>();
            for (CaracterizarInstructor caracterizarInstructorCollectionNewCaracterizarInstructorToAttach : caracterizarInstructorCollectionNew) {
                caracterizarInstructorCollectionNewCaracterizarInstructorToAttach = em.getReference(caracterizarInstructorCollectionNewCaracterizarInstructorToAttach.getClass(), caracterizarInstructorCollectionNewCaracterizarInstructorToAttach.getIdCaracterizarInstructor());
                attachedCaracterizarInstructorCollectionNew.add(caracterizarInstructorCollectionNewCaracterizarInstructorToAttach);
            }
            caracterizarInstructorCollectionNew = attachedCaracterizarInstructorCollectionNew;
            instructor.setCaracterizarInstructorCollection(caracterizarInstructorCollectionNew);
            instructor = em.merge(instructor);
            if (centroIdcentroOld != null && !centroIdcentroOld.equals(centroIdcentroNew)) {
                centroIdcentroOld.getInstructorCollection().remove(instructor);
                centroIdcentroOld = em.merge(centroIdcentroOld);
            }
            if (centroIdcentroNew != null && !centroIdcentroNew.equals(centroIdcentroOld)) {
                centroIdcentroNew.getInstructorCollection().add(instructor);
                centroIdcentroNew = em.merge(centroIdcentroNew);
            }
            if (coordinadorIdcoordinadorOld != null && !coordinadorIdcoordinadorOld.equals(coordinadorIdcoordinadorNew)) {
                coordinadorIdcoordinadorOld.getInstructorCollection().remove(instructor);
                coordinadorIdcoordinadorOld = em.merge(coordinadorIdcoordinadorOld);
            }
            if (coordinadorIdcoordinadorNew != null && !coordinadorIdcoordinadorNew.equals(coordinadorIdcoordinadorOld)) {
                coordinadorIdcoordinadorNew.getInstructorCollection().add(instructor);
                coordinadorIdcoordinadorNew = em.merge(coordinadorIdcoordinadorNew);
            }
            for (Descuento descuentoCollectionNewDescuento : descuentoCollectionNew) {
                if (!descuentoCollectionOld.contains(descuentoCollectionNewDescuento)) {
                    Instructor oldInstructoridInstructorOfDescuentoCollectionNewDescuento = descuentoCollectionNewDescuento.getInstructoridInstructor();
                    descuentoCollectionNewDescuento.setInstructoridInstructor(instructor);
                    descuentoCollectionNewDescuento = em.merge(descuentoCollectionNewDescuento);
                    if (oldInstructoridInstructorOfDescuentoCollectionNewDescuento != null && !oldInstructoridInstructorOfDescuentoCollectionNewDescuento.equals(instructor)) {
                        oldInstructoridInstructorOfDescuentoCollectionNewDescuento.getDescuentoCollection().remove(descuentoCollectionNewDescuento);
                        oldInstructoridInstructorOfDescuentoCollectionNewDescuento = em.merge(oldInstructoridInstructorOfDescuentoCollectionNewDescuento);
                    }
                }
            }
            for (CaracterizarInstructor caracterizarInstructorCollectionNewCaracterizarInstructor : caracterizarInstructorCollectionNew) {
                if (!caracterizarInstructorCollectionOld.contains(caracterizarInstructorCollectionNewCaracterizarInstructor)) {
                    Instructor oldInstructorIdinstructorOfCaracterizarInstructorCollectionNewCaracterizarInstructor = caracterizarInstructorCollectionNewCaracterizarInstructor.getInstructorIdinstructor();
                    caracterizarInstructorCollectionNewCaracterizarInstructor.setInstructorIdinstructor(instructor);
                    caracterizarInstructorCollectionNewCaracterizarInstructor = em.merge(caracterizarInstructorCollectionNewCaracterizarInstructor);
                    if (oldInstructorIdinstructorOfCaracterizarInstructorCollectionNewCaracterizarInstructor != null && !oldInstructorIdinstructorOfCaracterizarInstructorCollectionNewCaracterizarInstructor.equals(instructor)) {
                        oldInstructorIdinstructorOfCaracterizarInstructorCollectionNewCaracterizarInstructor.getCaracterizarInstructorCollection().remove(caracterizarInstructorCollectionNewCaracterizarInstructor);
                        oldInstructorIdinstructorOfCaracterizarInstructorCollectionNewCaracterizarInstructor = em.merge(oldInstructorIdinstructorOfCaracterizarInstructorCollectionNewCaracterizarInstructor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = instructor.getIdinstructor();
                if (findInstructor(id) == null) {
                    throw new NonexistentEntityException("The instructor with id " + id + " no longer exists.");
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
            Instructor instructor;
            try {
                instructor = em.getReference(Instructor.class, id);
                instructor.getIdinstructor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The instructor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Descuento> descuentoCollectionOrphanCheck = instructor.getDescuentoCollection();
            for (Descuento descuentoCollectionOrphanCheckDescuento : descuentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Instructor (" + instructor + ") cannot be destroyed since the Descuento " + descuentoCollectionOrphanCheckDescuento + " in its descuentoCollection field has a non-nullable instructoridInstructor field.");
            }
            Collection<CaracterizarInstructor> caracterizarInstructorCollectionOrphanCheck = instructor.getCaracterizarInstructorCollection();
            for (CaracterizarInstructor caracterizarInstructorCollectionOrphanCheckCaracterizarInstructor : caracterizarInstructorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Instructor (" + instructor + ") cannot be destroyed since the CaracterizarInstructor " + caracterizarInstructorCollectionOrphanCheckCaracterizarInstructor + " in its caracterizarInstructorCollection field has a non-nullable instructorIdinstructor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Centro centroIdcentro = instructor.getCentroIdcentro();
            if (centroIdcentro != null) {
                centroIdcentro.getInstructorCollection().remove(instructor);
                centroIdcentro = em.merge(centroIdcentro);
            }
            Coordinador coordinadorIdcoordinador = instructor.getCoordinadorIdcoordinador();
            if (coordinadorIdcoordinador != null) {
                coordinadorIdcoordinador.getInstructorCollection().remove(instructor);
                coordinadorIdcoordinador = em.merge(coordinadorIdcoordinador);
            }
            em.remove(instructor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Instructor> findInstructorEntities() {
        return findInstructorEntities(true, -1, -1);
    }

    public List<Instructor> findInstructorEntities(int maxResults, int firstResult) {
        return findInstructorEntities(false, maxResults, firstResult);
    }

    private List<Instructor> findInstructorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Instructor.class));
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

    public Instructor findInstructor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Instructor.class, id);
        } finally {
            em.close();
        }
    }

    public int getInstructorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Instructor> rt = cq.from(Instructor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
