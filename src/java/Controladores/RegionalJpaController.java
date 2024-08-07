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
import Entidades.Regional;
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
public class RegionalJpaController implements Serializable {

    public RegionalJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Dotacion_SenaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Regional regional) throws PreexistingEntityException, Exception {
        if (regional.getCentroCollection() == null) {
            regional.setCentroCollection(new ArrayList<Centro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Centro> attachedCentroCollection = new ArrayList<Centro>();
            for (Centro centroCollectionCentroToAttach : regional.getCentroCollection()) {
                centroCollectionCentroToAttach = em.getReference(centroCollectionCentroToAttach.getClass(), centroCollectionCentroToAttach.getIdcentro());
                attachedCentroCollection.add(centroCollectionCentroToAttach);
            }
            regional.setCentroCollection(attachedCentroCollection);
            em.persist(regional);
            for (Centro centroCollectionCentro : regional.getCentroCollection()) {
                Regional oldRegionalIdregionalOfCentroCollectionCentro = centroCollectionCentro.getRegionalIdregional();
                centroCollectionCentro.setRegionalIdregional(regional);
                centroCollectionCentro = em.merge(centroCollectionCentro);
                if (oldRegionalIdregionalOfCentroCollectionCentro != null) {
                    oldRegionalIdregionalOfCentroCollectionCentro.getCentroCollection().remove(centroCollectionCentro);
                    oldRegionalIdregionalOfCentroCollectionCentro = em.merge(oldRegionalIdregionalOfCentroCollectionCentro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegional(regional.getIdregional()) != null) {
                throw new PreexistingEntityException("Regional " + regional + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Regional regional) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Regional persistentRegional = em.find(Regional.class, regional.getIdregional());
            Collection<Centro> centroCollectionOld = persistentRegional.getCentroCollection();
            Collection<Centro> centroCollectionNew = regional.getCentroCollection();
            List<String> illegalOrphanMessages = null;
            for (Centro centroCollectionOldCentro : centroCollectionOld) {
                if (!centroCollectionNew.contains(centroCollectionOldCentro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Centro " + centroCollectionOldCentro + " since its regionalIdregional field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Centro> attachedCentroCollectionNew = new ArrayList<Centro>();
            for (Centro centroCollectionNewCentroToAttach : centroCollectionNew) {
                centroCollectionNewCentroToAttach = em.getReference(centroCollectionNewCentroToAttach.getClass(), centroCollectionNewCentroToAttach.getIdcentro());
                attachedCentroCollectionNew.add(centroCollectionNewCentroToAttach);
            }
            centroCollectionNew = attachedCentroCollectionNew;
            regional.setCentroCollection(centroCollectionNew);
            regional = em.merge(regional);
            for (Centro centroCollectionNewCentro : centroCollectionNew) {
                if (!centroCollectionOld.contains(centroCollectionNewCentro)) {
                    Regional oldRegionalIdregionalOfCentroCollectionNewCentro = centroCollectionNewCentro.getRegionalIdregional();
                    centroCollectionNewCentro.setRegionalIdregional(regional);
                    centroCollectionNewCentro = em.merge(centroCollectionNewCentro);
                    if (oldRegionalIdregionalOfCentroCollectionNewCentro != null && !oldRegionalIdregionalOfCentroCollectionNewCentro.equals(regional)) {
                        oldRegionalIdregionalOfCentroCollectionNewCentro.getCentroCollection().remove(centroCollectionNewCentro);
                        oldRegionalIdregionalOfCentroCollectionNewCentro = em.merge(oldRegionalIdregionalOfCentroCollectionNewCentro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = regional.getIdregional();
                if (findRegional(id) == null) {
                    throw new NonexistentEntityException("The regional with id " + id + " no longer exists.");
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
            Regional regional;
            try {
                regional = em.getReference(Regional.class, id);
                regional.getIdregional();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regional with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Centro> centroCollectionOrphanCheck = regional.getCentroCollection();
            for (Centro centroCollectionOrphanCheckCentro : centroCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Regional (" + regional + ") cannot be destroyed since the Centro " + centroCollectionOrphanCheckCentro + " in its centroCollection field has a non-nullable regionalIdregional field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(regional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Regional> findRegionalEntities() {
        return findRegionalEntities(true, -1, -1);
    }

    public List<Regional> findRegionalEntities(int maxResults, int firstResult) {
        return findRegionalEntities(false, maxResults, firstResult);
    }

    private List<Regional> findRegionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Regional.class));
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

    public Regional findRegional(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Regional.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Regional> rt = cq.from(Regional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
