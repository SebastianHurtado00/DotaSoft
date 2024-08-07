/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Area;
import Entidades.Clima;
import Entidades.Dotacion;
import Entidades.Elementos;
import Entidades.Sexo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ASUS
 */
public class DotacionJpaController implements Serializable {

    public DotacionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Dotacion_SenaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dotacion dotacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area areaIdarea = dotacion.getAreaIdarea();
            if (areaIdarea != null) {
                areaIdarea = em.getReference(areaIdarea.getClass(), areaIdarea.getIdarea());
                dotacion.setAreaIdarea(areaIdarea);
            }
            Clima climaIdclima = dotacion.getClimaIdclima();
            if (climaIdclima != null) {
                climaIdclima = em.getReference(climaIdclima.getClass(), climaIdclima.getIdclima());
                dotacion.setClimaIdclima(climaIdclima);
            }
            Elementos elementosIdelemento = dotacion.getElementosIdelemento();
            if (elementosIdelemento != null) {
                elementosIdelemento = em.getReference(elementosIdelemento.getClass(), elementosIdelemento.getIdelemento());
                dotacion.setElementosIdelemento(elementosIdelemento);
            }
            Sexo sexoIdsexo = dotacion.getSexoIdsexo();
            if (sexoIdsexo != null) {
                sexoIdsexo = em.getReference(sexoIdsexo.getClass(), sexoIdsexo.getIdsexo());
                dotacion.setSexoIdsexo(sexoIdsexo);
            }
            em.persist(dotacion);
            if (areaIdarea != null) {
                areaIdarea.getDotacionCollection().add(dotacion);
                areaIdarea = em.merge(areaIdarea);
            }
            if (climaIdclima != null) {
                climaIdclima.getDotacionCollection().add(dotacion);
                climaIdclima = em.merge(climaIdclima);
            }
            if (elementosIdelemento != null) {
                elementosIdelemento.getDotacionCollection().add(dotacion);
                elementosIdelemento = em.merge(elementosIdelemento);
            }
            if (sexoIdsexo != null) {
                sexoIdsexo.getDotacionCollection().add(dotacion);
                sexoIdsexo = em.merge(sexoIdsexo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dotacion dotacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dotacion persistentDotacion = em.find(Dotacion.class, dotacion.getIddotacion());
            Area areaIdareaOld = persistentDotacion.getAreaIdarea();
            Area areaIdareaNew = dotacion.getAreaIdarea();
            Clima climaIdclimaOld = persistentDotacion.getClimaIdclima();
            Clima climaIdclimaNew = dotacion.getClimaIdclima();
            Elementos elementosIdelementoOld = persistentDotacion.getElementosIdelemento();
            Elementos elementosIdelementoNew = dotacion.getElementosIdelemento();
            Sexo sexoIdsexoOld = persistentDotacion.getSexoIdsexo();
            Sexo sexoIdsexoNew = dotacion.getSexoIdsexo();
            if (areaIdareaNew != null) {
                areaIdareaNew = em.getReference(areaIdareaNew.getClass(), areaIdareaNew.getIdarea());
                dotacion.setAreaIdarea(areaIdareaNew);
            }
            if (climaIdclimaNew != null) {
                climaIdclimaNew = em.getReference(climaIdclimaNew.getClass(), climaIdclimaNew.getIdclima());
                dotacion.setClimaIdclima(climaIdclimaNew);
            }
            if (elementosIdelementoNew != null) {
                elementosIdelementoNew = em.getReference(elementosIdelementoNew.getClass(), elementosIdelementoNew.getIdelemento());
                dotacion.setElementosIdelemento(elementosIdelementoNew);
            }
            if (sexoIdsexoNew != null) {
                sexoIdsexoNew = em.getReference(sexoIdsexoNew.getClass(), sexoIdsexoNew.getIdsexo());
                dotacion.setSexoIdsexo(sexoIdsexoNew);
            }
            dotacion = em.merge(dotacion);
            if (areaIdareaOld != null && !areaIdareaOld.equals(areaIdareaNew)) {
                areaIdareaOld.getDotacionCollection().remove(dotacion);
                areaIdareaOld = em.merge(areaIdareaOld);
            }
            if (areaIdareaNew != null && !areaIdareaNew.equals(areaIdareaOld)) {
                areaIdareaNew.getDotacionCollection().add(dotacion);
                areaIdareaNew = em.merge(areaIdareaNew);
            }
            if (climaIdclimaOld != null && !climaIdclimaOld.equals(climaIdclimaNew)) {
                climaIdclimaOld.getDotacionCollection().remove(dotacion);
                climaIdclimaOld = em.merge(climaIdclimaOld);
            }
            if (climaIdclimaNew != null && !climaIdclimaNew.equals(climaIdclimaOld)) {
                climaIdclimaNew.getDotacionCollection().add(dotacion);
                climaIdclimaNew = em.merge(climaIdclimaNew);
            }
            if (elementosIdelementoOld != null && !elementosIdelementoOld.equals(elementosIdelementoNew)) {
                elementosIdelementoOld.getDotacionCollection().remove(dotacion);
                elementosIdelementoOld = em.merge(elementosIdelementoOld);
            }
            if (elementosIdelementoNew != null && !elementosIdelementoNew.equals(elementosIdelementoOld)) {
                elementosIdelementoNew.getDotacionCollection().add(dotacion);
                elementosIdelementoNew = em.merge(elementosIdelementoNew);
            }
            if (sexoIdsexoOld != null && !sexoIdsexoOld.equals(sexoIdsexoNew)) {
                sexoIdsexoOld.getDotacionCollection().remove(dotacion);
                sexoIdsexoOld = em.merge(sexoIdsexoOld);
            }
            if (sexoIdsexoNew != null && !sexoIdsexoNew.equals(sexoIdsexoOld)) {
                sexoIdsexoNew.getDotacionCollection().add(dotacion);
                sexoIdsexoNew = em.merge(sexoIdsexoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dotacion.getIddotacion();
                if (findDotacion(id) == null) {
                    throw new NonexistentEntityException("The dotacion with id " + id + " no longer exists.");
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
            Dotacion dotacion;
            try {
                dotacion = em.getReference(Dotacion.class, id);
                dotacion.getIddotacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dotacion with id " + id + " no longer exists.", enfe);
            }
            Area areaIdarea = dotacion.getAreaIdarea();
            if (areaIdarea != null) {
                areaIdarea.getDotacionCollection().remove(dotacion);
                areaIdarea = em.merge(areaIdarea);
            }
            Clima climaIdclima = dotacion.getClimaIdclima();
            if (climaIdclima != null) {
                climaIdclima.getDotacionCollection().remove(dotacion);
                climaIdclima = em.merge(climaIdclima);
            }
            Elementos elementosIdelemento = dotacion.getElementosIdelemento();
            if (elementosIdelemento != null) {
                elementosIdelemento.getDotacionCollection().remove(dotacion);
                elementosIdelemento = em.merge(elementosIdelemento);
            }
            Sexo sexoIdsexo = dotacion.getSexoIdsexo();
            if (sexoIdsexo != null) {
                sexoIdsexo.getDotacionCollection().remove(dotacion);
                sexoIdsexo = em.merge(sexoIdsexo);
            }
            em.remove(dotacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dotacion> findDotacionEntities() {
        return findDotacionEntities(true, -1, -1);
    }

    public List<Dotacion> findDotacionEntities(int maxResults, int firstResult) {
        return findDotacionEntities(false, maxResults, firstResult);
    }

    private List<Dotacion> findDotacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dotacion.class));
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

    public Dotacion findDotacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dotacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDotacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dotacion> rt = cq.from(Dotacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
