/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.Descuento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Elementos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ASUS
 */
public class DescuentoJpaController implements Serializable {

    public DescuentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Descuento descuento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Elementos elementosIdelemento = descuento.getElementosIdelemento();
            if (elementosIdelemento != null) {
                elementosIdelemento = em.getReference(elementosIdelemento.getClass(), elementosIdelemento.getIdelemento());
                descuento.setElementosIdelemento(elementosIdelemento);
            }
            em.persist(descuento);
            if (elementosIdelemento != null) {
                elementosIdelemento.getDescuentoCollection().add(descuento);
                elementosIdelemento = em.merge(elementosIdelemento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Descuento descuento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Descuento persistentDescuento = em.find(Descuento.class, descuento.getIddescuento());
            Elementos elementosIdelementoOld = persistentDescuento.getElementosIdelemento();
            Elementos elementosIdelementoNew = descuento.getElementosIdelemento();
            if (elementosIdelementoNew != null) {
                elementosIdelementoNew = em.getReference(elementosIdelementoNew.getClass(), elementosIdelementoNew.getIdelemento());
                descuento.setElementosIdelemento(elementosIdelementoNew);
            }
            descuento = em.merge(descuento);
            if (elementosIdelementoOld != null && !elementosIdelementoOld.equals(elementosIdelementoNew)) {
                elementosIdelementoOld.getDescuentoCollection().remove(descuento);
                elementosIdelementoOld = em.merge(elementosIdelementoOld);
            }
            if (elementosIdelementoNew != null && !elementosIdelementoNew.equals(elementosIdelementoOld)) {
                elementosIdelementoNew.getDescuentoCollection().add(descuento);
                elementosIdelementoNew = em.merge(elementosIdelementoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = descuento.getIddescuento();
                if (findDescuento(id) == null) {
                    throw new NonexistentEntityException("The descuento with id " + id + " no longer exists.");
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
            Descuento descuento;
            try {
                descuento = em.getReference(Descuento.class, id);
                descuento.getIddescuento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The descuento with id " + id + " no longer exists.", enfe);
            }
            Elementos elementosIdelemento = descuento.getElementosIdelemento();
            if (elementosIdelemento != null) {
                elementosIdelemento.getDescuentoCollection().remove(descuento);
                elementosIdelemento = em.merge(elementosIdelemento);
            }
            em.remove(descuento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Descuento> findDescuentoEntities() {
        return findDescuentoEntities(true, -1, -1);
    }

    public List<Descuento> findDescuentoEntities(int maxResults, int firstResult) {
        return findDescuentoEntities(false, maxResults, firstResult);
    }

    private List<Descuento> findDescuentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Descuento.class));
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

    public Descuento findDescuento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Descuento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDescuentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Descuento> rt = cq.from(Descuento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
