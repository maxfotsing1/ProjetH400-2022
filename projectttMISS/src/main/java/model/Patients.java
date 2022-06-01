/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "patients")
@NamedQueries({
    @NamedQuery(name = "Patients.findAll", query = "SELECT p FROM Patients p"),
    @NamedQuery(name = "Patients.findByIdpatients", query = "SELECT p FROM Patients p WHERE p.idpatients = :idpatients"),
    @NamedQuery(name = "Patients.findByPseudo", query = "SELECT p FROM Patients p WHERE p.pseudo = :pseudo"),
    @NamedQuery(name = "Patients.findByMdp", query = "SELECT p FROM Patients p WHERE p.mdp = :mdp")})
public class Patients implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpatients")
    private Integer idpatients;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pseudo")
    private String pseudo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mdp")
    private String mdp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patients")
    private Collection<Rdv> rdvCollection;
    @JoinColumn(name = "idpersonnes", referencedColumnName = "idpersonnes")
    @ManyToOne(optional = false)
    private Personnes idpersonnes;

    public Patients() {
    }

    public Patients(Integer idpatients) {
        this.idpatients = idpatients;
    }

    public Patients(Integer idpatients, String pseudo, String mdp) {
        this.idpatients = idpatients;
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    public Integer getIdpatients() {
        return idpatients;
    }

    public void setIdpatients(Integer idpatients) {
        this.idpatients = idpatients;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Collection<Rdv> getRdvCollection() {
        return rdvCollection;
    }

    public void setRdvCollection(Collection<Rdv> rdvCollection) {
        this.rdvCollection = rdvCollection;
    }

    public Personnes getIdpersonnes() {
        return idpersonnes;
    }

    public void setIdpersonnes(Personnes idpersonnes) {
        this.idpersonnes = idpersonnes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpatients != null ? idpatients.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patients)) {
            return false;
        }
        Patients other = (Patients) object;
        if ((this.idpatients == null && other.idpatients != null) || (this.idpatients != null && !this.idpatients.equals(other.idpatients))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Patients[ idpatients=" + idpatients + " ]";
    }
    
}
