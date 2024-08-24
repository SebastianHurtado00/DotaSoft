/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Centro;
import Entidades.Coordinador;
import Entidades.Instructor;
import Entidades.Sexo;
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
            Sexo sexo = instructor.getSexo();
            if (sexo != null) {
                sexo = em.getReference(sexo.getClass(), sexo.getIdsexo());
                instructor.setSexo(sexo);
            }
            em.persist(instructor);
            if (centroIdcentro != null) {
                centroIdcentro.getInstructorCollection().add(instructor);
                centroIdcentro = em.merge(centroIdcentro);
            }
            if (coordinadorIdcoordinador != null) {
                coordinadorIdcoordinador.getInstructorCollection().add(instructor);
                coordinadorIdcoordinador = em.merge(coordinadorIdcoordinador);
            }
            if (sexo != null) {
                sexo.getInstructorList().add(instructor);
                sexo = em.merge(sexo);
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

    public void edit(Instructor instructor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Instructor persistentInstructor = em.find(Instructor.class, instructor.getIdinstructor());
            Centro centroIdcentroOld = persistentInstructor.getCentroIdcentro();
            Centro centroIdcentroNew = instructor.getCentroIdcentro();
            Coordinador coordinadorIdcoordinadorOld = persistentInstructor.getCoordinadorIdcoordinador();
            Coordinador coordinadorIdcoordinadorNew = instructor.getCoordinadorIdcoordinador();
            Sexo sexoOld = persistentInstructor.getSexo();
            Sexo sexoNew = instructor.getSexo();
            if (centroIdcentroNew != null) {
                centroIdcentroNew = em.getReference(centroIdcentroNew.getClass(), centroIdcentroNew.getIdcentro());
                instructor.setCentroIdcentro(centroIdcentroNew);
            }
            if (coordinadorIdcoordinadorNew != null) {
                coordinadorIdcoordinadorNew = em.getReference(coordinadorIdcoordinadorNew.getClass(), coordinadorIdcoordinadorNew.getIdcoordinador());
                instructor.setCoordinadorIdcoordinador(coordinadorIdcoordinadorNew);
            }
            if (sexoNew != null) {
                sexoNew = em.getReference(sexoNew.getClass(), sexoNew.getIdsexo());
                instructor.setSexo(sexoNew);
            }
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
            if (sexoOld != null && !sexoOld.equals(sexoNew)) {
                sexoOld.getInstructorList().remove(instructor);
                sexoOld = em.merge(sexoOld);
            }
            if (sexoNew != null && !sexoNew.equals(sexoOld)) {
                sexoNew.getInstructorList().add(instructor);
                sexoNew = em.merge(sexoNew);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            Sexo sexo = instructor.getSexo();
            if (sexo != null) {
                sexo.getInstructorList().remove(instructor);
                sexo = em.merge(sexo);
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
