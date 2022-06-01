/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import model.Personnes;
import model.Rdv;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Patients;

/**
 *
 * @author pc
 */
public class PatientsJpaController implements Serializable {

    public PatientsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Patients patients) {
        if (patients.getRdvCollection() == null) {
            patients.setRdvCollection(new ArrayList<Rdv>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personnes idpersonnes = patients.getIdpersonnes();
            if (idpersonnes != null) {
                idpersonnes = em.getReference(idpersonnes.getClass(), idpersonnes.getIdpersonnes());
                patients.setIdpersonnes(idpersonnes);
            }
            Collection<Rdv> attachedRdvCollection = new ArrayList<Rdv>();
            for (Rdv rdvCollectionRdvToAttach : patients.getRdvCollection()) {
                rdvCollectionRdvToAttach = em.getReference(rdvCollectionRdvToAttach.getClass(), rdvCollectionRdvToAttach.getIdrdv());
                attachedRdvCollection.add(rdvCollectionRdvToAttach);
            }
            patients.setRdvCollection(attachedRdvCollection);
            em.persist(patients);
            if (idpersonnes != null) {
                idpersonnes.getPatientsCollection().add(patients);
                idpersonnes = em.merge(idpersonnes);
            }
            for (Rdv rdvCollectionRdv : patients.getRdvCollection()) {
                Patients oldPatientsOfRdvCollectionRdv = rdvCollectionRdv.getPatients();
                rdvCollectionRdv.setPatients(patients);
                rdvCollectionRdv = em.merge(rdvCollectionRdv);
                if (oldPatientsOfRdvCollectionRdv != null) {
                    oldPatientsOfRdvCollectionRdv.getRdvCollection().remove(rdvCollectionRdv);
                    oldPatientsOfRdvCollectionRdv = em.merge(oldPatientsOfRdvCollectionRdv);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Patients patients) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Patients persistentPatients = em.find(Patients.class, patients.getIdpatients());
            Personnes idpersonnesOld = persistentPatients.getIdpersonnes();
            Personnes idpersonnesNew = patients.getIdpersonnes();
            Collection<Rdv> rdvCollectionOld = persistentPatients.getRdvCollection();
            Collection<Rdv> rdvCollectionNew = patients.getRdvCollection();
            List<String> illegalOrphanMessages = null;
            for (Rdv rdvCollectionOldRdv : rdvCollectionOld) {
                if (!rdvCollectionNew.contains(rdvCollectionOldRdv)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rdv " + rdvCollectionOldRdv + " since its patients field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idpersonnesNew != null) {
                idpersonnesNew = em.getReference(idpersonnesNew.getClass(), idpersonnesNew.getIdpersonnes());
                patients.setIdpersonnes(idpersonnesNew);
            }
            Collection<Rdv> attachedRdvCollectionNew = new ArrayList<Rdv>();
            for (Rdv rdvCollectionNewRdvToAttach : rdvCollectionNew) {
                rdvCollectionNewRdvToAttach = em.getReference(rdvCollectionNewRdvToAttach.getClass(), rdvCollectionNewRdvToAttach.getIdrdv());
                attachedRdvCollectionNew.add(rdvCollectionNewRdvToAttach);
            }
            rdvCollectionNew = attachedRdvCollectionNew;
            patients.setRdvCollection(rdvCollectionNew);
            patients = em.merge(patients);
            if (idpersonnesOld != null && !idpersonnesOld.equals(idpersonnesNew)) {
                idpersonnesOld.getPatientsCollection().remove(patients);
                idpersonnesOld = em.merge(idpersonnesOld);
            }
            if (idpersonnesNew != null && !idpersonnesNew.equals(idpersonnesOld)) {
                idpersonnesNew.getPatientsCollection().add(patients);
                idpersonnesNew = em.merge(idpersonnesNew);
            }
            for (Rdv rdvCollectionNewRdv : rdvCollectionNew) {
                if (!rdvCollectionOld.contains(rdvCollectionNewRdv)) {
                    Patients oldPatientsOfRdvCollectionNewRdv = rdvCollectionNewRdv.getPatients();
                    rdvCollectionNewRdv.setPatients(patients);
                    rdvCollectionNewRdv = em.merge(rdvCollectionNewRdv);
                    if (oldPatientsOfRdvCollectionNewRdv != null && !oldPatientsOfRdvCollectionNewRdv.equals(patients)) {
                        oldPatientsOfRdvCollectionNewRdv.getRdvCollection().remove(rdvCollectionNewRdv);
                        oldPatientsOfRdvCollectionNewRdv = em.merge(oldPatientsOfRdvCollectionNewRdv);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = patients.getIdpatients();
                if (findPatients(id) == null) {
                    throw new NonexistentEntityException("The patients with id " + id + " no longer exists.");
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
            Patients patients;
            try {
                patients = em.getReference(Patients.class, id);
                patients.getIdpatients();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The patients with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Rdv> rdvCollectionOrphanCheck = patients.getRdvCollection();
            for (Rdv rdvCollectionOrphanCheckRdv : rdvCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Patients (" + patients + ") cannot be destroyed since the Rdv " + rdvCollectionOrphanCheckRdv + " in its rdvCollection field has a non-nullable patients field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Personnes idpersonnes = patients.getIdpersonnes();
            if (idpersonnes != null) {
                idpersonnes.getPatientsCollection().remove(patients);
                idpersonnes = em.merge(idpersonnes);
            }
            em.remove(patients);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Patients> findPatientsEntities() {
        return findPatientsEntities(true, -1, -1);
    }

    public List<Patients> findPatientsEntities(int maxResults, int firstResult) {
        return findPatientsEntities(false, maxResults, firstResult);
    }

    private List<Patients> findPatientsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Patients as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Patients findPatients(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Patients.class, id);
        } finally {
            em.close();
        }
    }

    public int getPatientsCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Patients as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
