/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.Elementosanual;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author yunis
 */
public class ElementosanualJpaController implements Serializable {

    public ElementosanualJpaController( ) {
        this.emf = Persistence.createEntityManagerFactory("Dotacion_SenaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Elementosanual elementosanual) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(elementosanual);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Elementosanual elementosanual) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            elementosanual = em.merge(elementosanual);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = elementosanual.getId();
                if (findElementosanual(id) == null) {
                    throw new NonexistentEntityException("The elementosanual with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Elementosanual elementosanual;
            try {
                elementosanual = em.getReference(Elementosanual.class, id);
                elementosanual.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The elementosanual with id " + id + " no longer exists.", enfe);
            }
            em.remove(elementosanual);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Elementosanual> findElementosanualEntities() {
        return findElementosanualEntities(true, -1, -1);
    }

    public List<Elementosanual> findElementosanualEntities(int maxResults, int firstResult) {
        return findElementosanualEntities(false, maxResults, firstResult);
    }

    private List<Elementosanual> findElementosanualEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Elementosanual.class));
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

    public Elementosanual findElementosanual(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Elementosanual.class, id);
        } finally {
            em.close();
        }
    }

    public int getElementosanualCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Elementosanual> rt = cq.from(Elementosanual.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
