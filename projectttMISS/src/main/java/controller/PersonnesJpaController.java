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
import model.Docteur;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Patients;
import model.Personnes;

/**
 *
 * @author pc
 */
public class PersonnesJpaController implements Serializable {

    public PersonnesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personnes personnes) {
        if (personnes.getDocteurCollection() == null) {
            personnes.setDocteurCollection(new ArrayList<Docteur>());
        }
        if (personnes.getPatientsCollection() == null) {
            personnes.setPatientsCollection(new ArrayList<Patients>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Docteur> attachedDocteurCollection = new ArrayList<Docteur>();
            for (Docteur docteurCollectionDocteurToAttach : personnes.getDocteurCollection()) {
                docteurCollectionDocteurToAttach = em.getReference(docteurCollectionDocteurToAttach.getClass(), docteurCollectionDocteurToAttach.getIddocteur());
                attachedDocteurCollection.add(docteurCollectionDocteurToAttach);
            }
            personnes.setDocteurCollection(attachedDocteurCollection);
            Collection<Patients> attachedPatientsCollection = new ArrayList<Patients>();
            for (Patients patientsCollectionPatientsToAttach : personnes.getPatientsCollection()) {
                patientsCollectionPatientsToAttach = em.getReference(patientsCollectionPatientsToAttach.getClass(), patientsCollectionPatientsToAttach.getIdpatients());
                attachedPatientsCollection.add(patientsCollectionPatientsToAttach);
            }
            personnes.setPatientsCollection(attachedPatientsCollection);
            em.persist(personnes);
            for (Docteur docteurCollectionDocteur : personnes.getDocteurCollection()) {
                Personnes oldIdpersonnesOfDocteurCollectionDocteur = docteurCollectionDocteur.getIdpersonnes();
                docteurCollectionDocteur.setIdpersonnes(personnes);
                docteurCollectionDocteur = em.merge(docteurCollectionDocteur);
                if (oldIdpersonnesOfDocteurCollectionDocteur != null) {
                    oldIdpersonnesOfDocteurCollectionDocteur.getDocteurCollection().remove(docteurCollectionDocteur);
                    oldIdpersonnesOfDocteurCollectionDocteur = em.merge(oldIdpersonnesOfDocteurCollectionDocteur);
                }
            }
            for (Patients patientsCollectionPatients : personnes.getPatientsCollection()) {
                Personnes oldIdpersonnesOfPatientsCollectionPatients = patientsCollectionPatients.getIdpersonnes();
                patientsCollectionPatients.setIdpersonnes(personnes);
                patientsCollectionPatients = em.merge(patientsCollectionPatients);
                if (oldIdpersonnesOfPatientsCollectionPatients != null) {
                    oldIdpersonnesOfPatientsCollectionPatients.getPatientsCollection().remove(patientsCollectionPatients);
                    oldIdpersonnesOfPatientsCollectionPatients = em.merge(oldIdpersonnesOfPatientsCollectionPatients);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personnes personnes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personnes persistentPersonnes = em.find(Personnes.class, personnes.getIdpersonnes());
            Collection<Docteur> docteurCollectionOld = persistentPersonnes.getDocteurCollection();
            Collection<Docteur> docteurCollectionNew = personnes.getDocteurCollection();
            Collection<Patients> patientsCollectionOld = persistentPersonnes.getPatientsCollection();
            Collection<Patients> patientsCollectionNew = personnes.getPatientsCollection();
            List<String> illegalOrphanMessages = null;
            for (Docteur docteurCollectionOldDocteur : docteurCollectionOld) {
                if (!docteurCollectionNew.contains(docteurCollectionOldDocteur)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Docteur " + docteurCollectionOldDocteur + " since its idpersonnes field is not nullable.");
                }
            }
            for (Patients patientsCollectionOldPatients : patientsCollectionOld) {
                if (!patientsCollectionNew.contains(patientsCollectionOldPatients)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patients " + patientsCollectionOldPatients + " since its idpersonnes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Docteur> attachedDocteurCollectionNew = new ArrayList<Docteur>();
            for (Docteur docteurCollectionNewDocteurToAttach : docteurCollectionNew) {
                docteurCollectionNewDocteurToAttach = em.getReference(docteurCollectionNewDocteurToAttach.getClass(), docteurCollectionNewDocteurToAttach.getIddocteur());
                attachedDocteurCollectionNew.add(docteurCollectionNewDocteurToAttach);
            }
            docteurCollectionNew = attachedDocteurCollectionNew;
            personnes.setDocteurCollection(docteurCollectionNew);
            Collection<Patients> attachedPatientsCollectionNew = new ArrayList<Patients>();
            for (Patients patientsCollectionNewPatientsToAttach : patientsCollectionNew) {
                patientsCollectionNewPatientsToAttach = em.getReference(patientsCollectionNewPatientsToAttach.getClass(), patientsCollectionNewPatientsToAttach.getIdpatients());
                attachedPatientsCollectionNew.add(patientsCollectionNewPatientsToAttach);
            }
            patientsCollectionNew = attachedPatientsCollectionNew;
            personnes.setPatientsCollection(patientsCollectionNew);
            personnes = em.merge(personnes);
            for (Docteur docteurCollectionNewDocteur : docteurCollectionNew) {
                if (!docteurCollectionOld.contains(docteurCollectionNewDocteur)) {
                    Personnes oldIdpersonnesOfDocteurCollectionNewDocteur = docteurCollectionNewDocteur.getIdpersonnes();
                    docteurCollectionNewDocteur.setIdpersonnes(personnes);
                    docteurCollectionNewDocteur = em.merge(docteurCollectionNewDocteur);
                    if (oldIdpersonnesOfDocteurCollectionNewDocteur != null && !oldIdpersonnesOfDocteurCollectionNewDocteur.equals(personnes)) {
                        oldIdpersonnesOfDocteurCollectionNewDocteur.getDocteurCollection().remove(docteurCollectionNewDocteur);
                        oldIdpersonnesOfDocteurCollectionNewDocteur = em.merge(oldIdpersonnesOfDocteurCollectionNewDocteur);
                    }
                }
            }
            for (Patients patientsCollectionNewPatients : patientsCollectionNew) {
                if (!patientsCollectionOld.contains(patientsCollectionNewPatients)) {
                    Personnes oldIdpersonnesOfPatientsCollectionNewPatients = patientsCollectionNewPatients.getIdpersonnes();
                    patientsCollectionNewPatients.setIdpersonnes(personnes);
                    patientsCollectionNewPatients = em.merge(patientsCollectionNewPatients);
                    if (oldIdpersonnesOfPatientsCollectionNewPatients != null && !oldIdpersonnesOfPatientsCollectionNewPatients.equals(personnes)) {
                        oldIdpersonnesOfPatientsCollectionNewPatients.getPatientsCollection().remove(patientsCollectionNewPatients);
                        oldIdpersonnesOfPatientsCollectionNewPatients = em.merge(oldIdpersonnesOfPatientsCollectionNewPatients);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personnes.getIdpersonnes();
                if (findPersonnes(id) == null) {
                    throw new NonexistentEntityException("The personnes with id " + id + " no longer exists.");
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
            Personnes personnes;
            try {
                personnes = em.getReference(Personnes.class, id);
                personnes.getIdpersonnes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personnes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Docteur> docteurCollectionOrphanCheck = personnes.getDocteurCollection();
            for (Docteur docteurCollectionOrphanCheckDocteur : docteurCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personnes (" + personnes + ") cannot be destroyed since the Docteur " + docteurCollectionOrphanCheckDocteur + " in its docteurCollection field has a non-nullable idpersonnes field.");
            }
            Collection<Patients> patientsCollectionOrphanCheck = personnes.getPatientsCollection();
            for (Patients patientsCollectionOrphanCheckPatients : patientsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personnes (" + personnes + ") cannot be destroyed since the Patients " + patientsCollectionOrphanCheckPatients + " in its patientsCollection field has a non-nullable idpersonnes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(personnes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personnes> findPersonnesEntities() {
        return findPersonnesEntities(true, -1, -1);
    }

    public List<Personnes> findPersonnesEntities(int maxResults, int firstResult) {
        return findPersonnesEntities(false, maxResults, firstResult);
    }

    private List<Personnes> findPersonnesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Personnes as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Personnes findPersonnes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personnes.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonnesCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Personnes as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
