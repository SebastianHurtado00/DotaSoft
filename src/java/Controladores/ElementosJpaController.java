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
import Entidades.Descuento;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.Dotacion;
import Entidades.Elementos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class ElementosJpaController implements Serializable {

    public ElementosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Dotacion_SenaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Elementos elementos) throws PreexistingEntityException, Exception {
        if (elementos.getDescuentoCollection() == null) {
            elementos.setDescuentoCollection(new ArrayList<Descuento>());
        }
        if (elementos.getDotacionCollection() == null) {
            elementos.setDotacionCollection(new ArrayList<Dotacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Descuento> attachedDescuentoCollection = new ArrayList<Descuento>();
            for (Descuento descuentoCollectionDescuentoToAttach : elementos.getDescuentoCollection()) {
                descuentoCollectionDescuentoToAttach = em.getReference(descuentoCollectionDescuentoToAttach.getClass(), descuentoCollectionDescuentoToAttach.getIddescuento());
                attachedDescuentoCollection.add(descuentoCollectionDescuentoToAttach);
            }
            elementos.setDescuentoCollection(attachedDescuentoCollection);
            Collection<Dotacion> attachedDotacionCollection = new ArrayList<Dotacion>();
            for (Dotacion dotacionCollectionDotacionToAttach : elementos.getDotacionCollection()) {
                dotacionCollectionDotacionToAttach = em.getReference(dotacionCollectionDotacionToAttach.getClass(), dotacionCollectionDotacionToAttach.getIddotacion());
                attachedDotacionCollection.add(dotacionCollectionDotacionToAttach);
            }
            elementos.setDotacionCollection(attachedDotacionCollection);
            em.persist(elementos);
            for (Descuento descuentoCollectionDescuento : elementos.getDescuentoCollection()) {
                Elementos oldElementosIdelementoOfDescuentoCollectionDescuento = descuentoCollectionDescuento.getElementosIdelemento();
                descuentoCollectionDescuento.setElementosIdelemento(elementos);
                descuentoCollectionDescuento = em.merge(descuentoCollectionDescuento);
                if (oldElementosIdelementoOfDescuentoCollectionDescuento != null) {
                    oldElementosIdelementoOfDescuentoCollectionDescuento.getDescuentoCollection().remove(descuentoCollectionDescuento);
                    oldElementosIdelementoOfDescuentoCollectionDescuento = em.merge(oldElementosIdelementoOfDescuentoCollectionDescuento);
                }
            }
            for (Dotacion dotacionCollectionDotacion : elementos.getDotacionCollection()) {
                Elementos oldElementosIdelementoOfDotacionCollectionDotacion = dotacionCollectionDotacion.getElementosIdelemento();
                dotacionCollectionDotacion.setElementosIdelemento(elementos);
                dotacionCollectionDotacion = em.merge(dotacionCollectionDotacion);
                if (oldElementosIdelementoOfDotacionCollectionDotacion != null) {
                    oldElementosIdelementoOfDotacionCollectionDotacion.getDotacionCollection().remove(dotacionCollectionDotacion);
                    oldElementosIdelementoOfDotacionCollectionDotacion = em.merge(oldElementosIdelementoOfDotacionCollectionDotacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findElementos(elementos.getIdelemento()) != null) {
                throw new PreexistingEntityException("Elementos " + elementos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Elementos elementos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Elementos persistentElementos = em.find(Elementos.class, elementos.getIdelemento());
            Collection<Descuento> descuentoCollectionOld = persistentElementos.getDescuentoCollection();
            Collection<Descuento> descuentoCollectionNew = elementos.getDescuentoCollection();
            Collection<Dotacion> dotacionCollectionOld = persistentElementos.getDotacionCollection();
            Collection<Dotacion> dotacionCollectionNew = elementos.getDotacionCollection();
            List<String> illegalOrphanMessages = null;
            for (Descuento descuentoCollectionOldDescuento : descuentoCollectionOld) {
                if (!descuentoCollectionNew.contains(descuentoCollectionOldDescuento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Descuento " + descuentoCollectionOldDescuento + " since its elementosIdelemento field is not nullable.");
                }
            }
            for (Dotacion dotacionCollectionOldDotacion : dotacionCollectionOld) {
                if (!dotacionCollectionNew.contains(dotacionCollectionOldDotacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dotacion " + dotacionCollectionOldDotacion + " since its elementosIdelemento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Descuento> attachedDescuentoCollectionNew = new ArrayList<Descuento>();
            for (Descuento descuentoCollectionNewDescuentoToAttach : descuentoCollectionNew) {
                descuentoCollectionNewDescuentoToAttach = em.getReference(descuentoCollectionNewDescuentoToAttach.getClass(), descuentoCollectionNewDescuentoToAttach.getIddescuento());
                attachedDescuentoCollectionNew.add(descuentoCollectionNewDescuentoToAttach);
            }
            descuentoCollectionNew = attachedDescuentoCollectionNew;
            elementos.setDescuentoCollection(descuentoCollectionNew);
            Collection<Dotacion> attachedDotacionCollectionNew = new ArrayList<Dotacion>();
            for (Dotacion dotacionCollectionNewDotacionToAttach : dotacionCollectionNew) {
                dotacionCollectionNewDotacionToAttach = em.getReference(dotacionCollectionNewDotacionToAttach.getClass(), dotacionCollectionNewDotacionToAttach.getIddotacion());
                attachedDotacionCollectionNew.add(dotacionCollectionNewDotacionToAttach);
            }
            dotacionCollectionNew = attachedDotacionCollectionNew;
            elementos.setDotacionCollection(dotacionCollectionNew);
            elementos = em.merge(elementos);
            for (Descuento descuentoCollectionNewDescuento : descuentoCollectionNew) {
                if (!descuentoCollectionOld.contains(descuentoCollectionNewDescuento)) {
                    Elementos oldElementosIdelementoOfDescuentoCollectionNewDescuento = descuentoCollectionNewDescuento.getElementosIdelemento();
                    descuentoCollectionNewDescuento.setElementosIdelemento(elementos);
                    descuentoCollectionNewDescuento = em.merge(descuentoCollectionNewDescuento);
                    if (oldElementosIdelementoOfDescuentoCollectionNewDescuento != null && !oldElementosIdelementoOfDescuentoCollectionNewDescuento.equals(elementos)) {
                        oldElementosIdelementoOfDescuentoCollectionNewDescuento.getDescuentoCollection().remove(descuentoCollectionNewDescuento);
                        oldElementosIdelementoOfDescuentoCollectionNewDescuento = em.merge(oldElementosIdelementoOfDescuentoCollectionNewDescuento);
                    }
                }
            }
            for (Dotacion dotacionCollectionNewDotacion : dotacionCollectionNew) {
                if (!dotacionCollectionOld.contains(dotacionCollectionNewDotacion)) {
                    Elementos oldElementosIdelementoOfDotacionCollectionNewDotacion = dotacionCollectionNewDotacion.getElementosIdelemento();
                    dotacionCollectionNewDotacion.setElementosIdelemento(elementos);
                    dotacionCollectionNewDotacion = em.merge(dotacionCollectionNewDotacion);
                    if (oldElementosIdelementoOfDotacionCollectionNewDotacion != null && !oldElementosIdelementoOfDotacionCollectionNewDotacion.equals(elementos)) {
                        oldElementosIdelementoOfDotacionCollectionNewDotacion.getDotacionCollection().remove(dotacionCollectionNewDotacion);
                        oldElementosIdelementoOfDotacionCollectionNewDotacion = em.merge(oldElementosIdelementoOfDotacionCollectionNewDotacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = elementos.getIdelemento();
                if (findElementos(id) == null) {
                    throw new NonexistentEntityException("The elementos with id " + id + " no longer exists.");
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
            Elementos elementos;
            try {
                elementos = em.getReference(Elementos.class, id);
                elementos.getIdelemento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The elementos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Descuento> descuentoCollectionOrphanCheck = elementos.getDescuentoCollection();
            for (Descuento descuentoCollectionOrphanCheckDescuento : descuentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Elementos (" + elementos + ") cannot be destroyed since the Descuento " + descuentoCollectionOrphanCheckDescuento + " in its descuentoCollection field has a non-nullable elementosIdelemento field.");
            }
            Collection<Dotacion> dotacionCollectionOrphanCheck = elementos.getDotacionCollection();
            for (Dotacion dotacionCollectionOrphanCheckDotacion : dotacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Elementos (" + elementos + ") cannot be destroyed since the Dotacion " + dotacionCollectionOrphanCheckDotacion + " in its dotacionCollection field has a non-nullable elementosIdelemento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(elementos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Elementos> findElementosEntities() {
        return findElementosEntities(true, -1, -1);
    }

    public List<Elementos> findElementosEntities(int maxResults, int firstResult) {
        return findElementosEntities(false, maxResults, firstResult);
    }

    private List<Elementos> findElementosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Elementos.class));
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

    public Elementos findElementos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Elementos.class, id);
        } finally {
            em.close();
        }
    }

    public int getElementosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Elementos> rt = cq.from(Elementos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
