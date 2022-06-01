/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import model.Patients;
import model.Docteur;
import model.Rdv;

/**
 *
 * @author pc
 */
public class RdvJpaController implements Serializable {

    public RdvJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rdv rdv) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Patients patients = rdv.getPatients();
            if (patients != null) {
                patients = em.getReference(patients.getClass(), patients.getIdpatients());
                rdv.setPatients(patients);
            }
            Docteur docteur = rdv.getDocteur();
            if (docteur != null) {
                docteur = em.getReference(docteur.getClass(), docteur.getIddocteur());
                rdv.setDocteur(docteur);
            }
            em.persist(rdv);
            if (patients != null) {
                patients.getRdvCollection().add(rdv);
                patients = em.merge(patients);
            }
            if (docteur != null) {
                docteur.getRdvCollection().add(rdv);
                docteur = em.merge(docteur);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rdv rdv) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rdv persistentRdv = em.find(Rdv.class, rdv.getIdrdv());
            Patients patientsOld = persistentRdv.getPatients();
            Patients patientsNew = rdv.getPatients();
            Docteur docteurOld = persistentRdv.getDocteur();
            Docteur docteurNew = rdv.getDocteur();
            if (patientsNew != null) {
                patientsNew = em.getReference(patientsNew.getClass(), patientsNew.getIdpatients());
                rdv.setPatients(patientsNew);
            }
            if (docteurNew != null) {
                docteurNew = em.getReference(docteurNew.getClass(), docteurNew.getIddocteur());
                rdv.setDocteur(docteurNew);
            }
            rdv = em.merge(rdv);
            if (patientsOld != null && !patientsOld.equals(patientsNew)) {
                patientsOld.getRdvCollection().remove(rdv);
                patientsOld = em.merge(patientsOld);
            }
            if (patientsNew != null && !patientsNew.equals(patientsOld)) {
                patientsNew.getRdvCollection().add(rdv);
                patientsNew = em.merge(patientsNew);
            }
            if (docteurOld != null && !docteurOld.equals(docteurNew)) {
                docteurOld.getRdvCollection().remove(rdv);
                docteurOld = em.merge(docteurOld);
            }
            if (docteurNew != null && !docteurNew.equals(docteurOld)) {
                docteurNew.getRdvCollection().add(rdv);
                docteurNew = em.merge(docteurNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rdv.getIdrdv();
                if (findRdv(id) == null) {
                    throw new NonexistentEntityException("The rdv with id " + id + " no longer exists.");
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
            Rdv rdv;
            try {
                rdv = em.getReference(Rdv.class, id);
                rdv.getIdrdv();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rdv with id " + id + " no longer exists.", enfe);
            }
            Patients patients = rdv.getPatients();
            if (patients != null) {
                patients.getRdvCollection().remove(rdv);
                patients = em.merge(patients);
            }
            Docteur docteur = rdv.getDocteur();
            if (docteur != null) {
                docteur.getRdvCollection().remove(rdv);
                docteur = em.merge(docteur);
            }
            em.remove(rdv);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rdv> findRdvEntities() {
        return findRdvEntities(true, -1, -1);
    }

    public List<Rdv> findRdvEntities(int maxResults, int firstResult) {
        return findRdvEntities(false, maxResults, firstResult);
    }

    private List<Rdv> findRdvEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Rdv as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rdv findRdv(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rdv.class, id);
        } finally {
            em.close();
        }
    }

    public int getRdvCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Rdv as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
