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
import model.Docteur;

/**
 *
 * @author pc
 */
public class DocteurJpaController implements Serializable {

    public DocteurJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Docteur docteur) {
        if (docteur.getRdvCollection() == null) {
            docteur.setRdvCollection(new ArrayList<Rdv>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personnes idpersonnes = docteur.getIdpersonnes();
            if (idpersonnes != null) {
                idpersonnes = em.getReference(idpersonnes.getClass(), idpersonnes.getIdpersonnes());
                docteur.setIdpersonnes(idpersonnes);
            }
            Collection<Rdv> attachedRdvCollection = new ArrayList<Rdv>();
            for (Rdv rdvCollectionRdvToAttach : docteur.getRdvCollection()) {
                rdvCollectionRdvToAttach = em.getReference(rdvCollectionRdvToAttach.getClass(), rdvCollectionRdvToAttach.getIdrdv());
                attachedRdvCollection.add(rdvCollectionRdvToAttach);
            }
            docteur.setRdvCollection(attachedRdvCollection);
            em.persist(docteur);
            if (idpersonnes != null) {
                idpersonnes.getDocteurCollection().add(docteur);
                idpersonnes = em.merge(idpersonnes);
            }
            for (Rdv rdvCollectionRdv : docteur.getRdvCollection()) {
                Docteur oldDocteurOfRdvCollectionRdv = rdvCollectionRdv.getDocteur();
                rdvCollectionRdv.setDocteur(docteur);
                rdvCollectionRdv = em.merge(rdvCollectionRdv);
                if (oldDocteurOfRdvCollectionRdv != null) {
                    oldDocteurOfRdvCollectionRdv.getRdvCollection().remove(rdvCollectionRdv);
                    oldDocteurOfRdvCollectionRdv = em.merge(oldDocteurOfRdvCollectionRdv);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Docteur docteur) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docteur persistentDocteur = em.find(Docteur.class, docteur.getIddocteur());
            Personnes idpersonnesOld = persistentDocteur.getIdpersonnes();
            Personnes idpersonnesNew = docteur.getIdpersonnes();
            Collection<Rdv> rdvCollectionOld = persistentDocteur.getRdvCollection();
            Collection<Rdv> rdvCollectionNew = docteur.getRdvCollection();
            List<String> illegalOrphanMessages = null;
            for (Rdv rdvCollectionOldRdv : rdvCollectionOld) {
                if (!rdvCollectionNew.contains(rdvCollectionOldRdv)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rdv " + rdvCollectionOldRdv + " since its docteur field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idpersonnesNew != null) {
                idpersonnesNew = em.getReference(idpersonnesNew.getClass(), idpersonnesNew.getIdpersonnes());
                docteur.setIdpersonnes(idpersonnesNew);
            }
            Collection<Rdv> attachedRdvCollectionNew = new ArrayList<Rdv>();
            for (Rdv rdvCollectionNewRdvToAttach : rdvCollectionNew) {
                rdvCollectionNewRdvToAttach = em.getReference(rdvCollectionNewRdvToAttach.getClass(), rdvCollectionNewRdvToAttach.getIdrdv());
                attachedRdvCollectionNew.add(rdvCollectionNewRdvToAttach);
            }
            rdvCollectionNew = attachedRdvCollectionNew;
            docteur.setRdvCollection(rdvCollectionNew);
            docteur = em.merge(docteur);
            if (idpersonnesOld != null && !idpersonnesOld.equals(idpersonnesNew)) {
                idpersonnesOld.getDocteurCollection().remove(docteur);
                idpersonnesOld = em.merge(idpersonnesOld);
            }
            if (idpersonnesNew != null && !idpersonnesNew.equals(idpersonnesOld)) {
                idpersonnesNew.getDocteurCollection().add(docteur);
                idpersonnesNew = em.merge(idpersonnesNew);
            }
            for (Rdv rdvCollectionNewRdv : rdvCollectionNew) {
                if (!rdvCollectionOld.contains(rdvCollectionNewRdv)) {
                    Docteur oldDocteurOfRdvCollectionNewRdv = rdvCollectionNewRdv.getDocteur();
                    rdvCollectionNewRdv.setDocteur(docteur);
                    rdvCollectionNewRdv = em.merge(rdvCollectionNewRdv);
                    if (oldDocteurOfRdvCollectionNewRdv != null && !oldDocteurOfRdvCollectionNewRdv.equals(docteur)) {
                        oldDocteurOfRdvCollectionNewRdv.getRdvCollection().remove(rdvCollectionNewRdv);
                        oldDocteurOfRdvCollectionNewRdv = em.merge(oldDocteurOfRdvCollectionNewRdv);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = docteur.getIddocteur();
                if (findDocteur(id) == null) {
                    throw new NonexistentEntityException("The docteur with id " + id + " no longer exists.");
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
            Docteur docteur;
            try {
                docteur = em.getReference(Docteur.class, id);
                docteur.getIddocteur();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The docteur with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Rdv> rdvCollectionOrphanCheck = docteur.getRdvCollection();
            for (Rdv rdvCollectionOrphanCheckRdv : rdvCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Docteur (" + docteur + ") cannot be destroyed since the Rdv " + rdvCollectionOrphanCheckRdv + " in its rdvCollection field has a non-nullable docteur field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Personnes idpersonnes = docteur.getIdpersonnes();
            if (idpersonnes != null) {
                idpersonnes.getDocteurCollection().remove(docteur);
                idpersonnes = em.merge(idpersonnes);
            }
            em.remove(docteur);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Docteur> findDocteurEntities() {
        return findDocteurEntities(true, -1, -1);
    }

    public List<Docteur> findDocteurEntities(int maxResults, int firstResult) {
        return findDocteurEntities(false, maxResults, firstResult);
    }

    private List<Docteur> findDocteurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Docteur as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Docteur findDocteur(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Docteur.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocteurCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Docteur as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
