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
import Entidades.Area;
import Entidades.Red;
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
public class RedJpaController implements Serializable {

    public RedJpaController() {
       this.emf = Persistence.createEntityManagerFactory("Dotacion_SenaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Red red) throws PreexistingEntityException, Exception {
        if (red.getAreaCollection() == null) {
            red.setAreaCollection(new ArrayList<Area>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Area> attachedAreaCollection = new ArrayList<Area>();
            for (Area areaCollectionAreaToAttach : red.getAreaCollection()) {
                areaCollectionAreaToAttach = em.getReference(areaCollectionAreaToAttach.getClass(), areaCollectionAreaToAttach.getIdarea());
                attachedAreaCollection.add(areaCollectionAreaToAttach);
            }
            red.setAreaCollection(attachedAreaCollection);
            em.persist(red);
            for (Area areaCollectionArea : red.getAreaCollection()) {
                Red oldRedIdredOfAreaCollectionArea = areaCollectionArea.getRedIdred();
                areaCollectionArea.setRedIdred(red);
                areaCollectionArea = em.merge(areaCollectionArea);
                if (oldRedIdredOfAreaCollectionArea != null) {
                    oldRedIdredOfAreaCollectionArea.getAreaCollection().remove(areaCollectionArea);
                    oldRedIdredOfAreaCollectionArea = em.merge(oldRedIdredOfAreaCollectionArea);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRed(red.getIdred()) != null) {
                throw new PreexistingEntityException("Red " + red + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Red red) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Red persistentRed = em.find(Red.class, red.getIdred());
            Collection<Area> areaCollectionOld = persistentRed.getAreaCollection();
            Collection<Area> areaCollectionNew = red.getAreaCollection();
            List<String> illegalOrphanMessages = null;
            for (Area areaCollectionOldArea : areaCollectionOld) {
                if (!areaCollectionNew.contains(areaCollectionOldArea)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Area " + areaCollectionOldArea + " since its redIdred field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Area> attachedAreaCollectionNew = new ArrayList<Area>();
            for (Area areaCollectionNewAreaToAttach : areaCollectionNew) {
                areaCollectionNewAreaToAttach = em.getReference(areaCollectionNewAreaToAttach.getClass(), areaCollectionNewAreaToAttach.getIdarea());
                attachedAreaCollectionNew.add(areaCollectionNewAreaToAttach);
            }
            areaCollectionNew = attachedAreaCollectionNew;
            red.setAreaCollection(areaCollectionNew);
            red = em.merge(red);
            for (Area areaCollectionNewArea : areaCollectionNew) {
                if (!areaCollectionOld.contains(areaCollectionNewArea)) {
                    Red oldRedIdredOfAreaCollectionNewArea = areaCollectionNewArea.getRedIdred();
                    areaCollectionNewArea.setRedIdred(red);
                    areaCollectionNewArea = em.merge(areaCollectionNewArea);
                    if (oldRedIdredOfAreaCollectionNewArea != null && !oldRedIdredOfAreaCollectionNewArea.equals(red)) {
                        oldRedIdredOfAreaCollectionNewArea.getAreaCollection().remove(areaCollectionNewArea);
                        oldRedIdredOfAreaCollectionNewArea = em.merge(oldRedIdredOfAreaCollectionNewArea);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = red.getIdred();
                if (findRed(id) == null) {
                    throw new NonexistentEntityException("The red with id " + id + " no longer exists.");
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
            Red red;
            try {
                red = em.getReference(Red.class, id);
                red.getIdred();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The red with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Area> areaCollectionOrphanCheck = red.getAreaCollection();
            for (Area areaCollectionOrphanCheckArea : areaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Red (" + red + ") cannot be destroyed since the Area " + areaCollectionOrphanCheckArea + " in its areaCollection field has a non-nullable redIdred field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(red);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Red> findRedEntities() {
        return findRedEntities(true, -1, -1);
    }

    public List<Red> findRedEntities(int maxResults, int firstResult) {
        return findRedEntities(false, maxResults, firstResult);
    }

    private List<Red> findRedEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Red.class));
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

    public Red findRed(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Red.class, id);
        } finally {
            em.close();
        }
    }

    public int getRedCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Red> rt = cq.from(Red.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
