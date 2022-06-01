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
@Table(name = "docteur")
@NamedQueries({
    @NamedQuery(name = "Docteur.findAll", query = "SELECT d FROM Docteur d"),
    @NamedQuery(name = "Docteur.findByIddocteur", query = "SELECT d FROM Docteur d WHERE d.iddocteur = :iddocteur"),
    @NamedQuery(name = "Docteur.findByPseudodocteur", query = "SELECT d FROM Docteur d WHERE d.pseudodocteur = :pseudodocteur"),
    @NamedQuery(name = "Docteur.findByMdpdocteur", query = "SELECT d FROM Docteur d WHERE d.mdpdocteur = :mdpdocteur")})
public class Docteur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddocteur")
    private Integer iddocteur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pseudodocteur")
    private String pseudodocteur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mdpdocteur")
    private String mdpdocteur;
    @JoinColumn(name = "idpersonnes", referencedColumnName = "idpersonnes")
    @ManyToOne(optional = false)
    private Personnes idpersonnes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docteur")
    private Collection<Rdv> rdvCollection;

    public Docteur() {
    }

    public Docteur(Integer iddocteur) {
        this.iddocteur = iddocteur;
    }

    public Docteur(Integer iddocteur, String pseudodocteur, String mdpdocteur) {
        this.iddocteur = iddocteur;
        this.pseudodocteur = pseudodocteur;
        this.mdpdocteur = mdpdocteur;
    }

    public Integer getIddocteur() {
        return iddocteur;
    }

    public void setIddocteur(Integer iddocteur) {
        this.iddocteur = iddocteur;
    }

    public String getPseudodocteur() {
        return pseudodocteur;
    }

    public void setPseudodocteur(String pseudodocteur) {
        this.pseudodocteur = pseudodocteur;
    }

    public String getMdpdocteur() {
        return mdpdocteur;
    }

    public void setMdpdocteur(String mdpdocteur) {
        this.mdpdocteur = mdpdocteur;
    }

    public Personnes getIdpersonnes() {
        return idpersonnes;
    }

    public void setIdpersonnes(Personnes idpersonnes) {
        this.idpersonnes = idpersonnes;
    }

    public Collection<Rdv> getRdvCollection() {
        return rdvCollection;
    }

    public void setRdvCollection(Collection<Rdv> rdvCollection) {
        this.rdvCollection = rdvCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddocteur != null ? iddocteur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Docteur)) {
            return false;
        }
        Docteur other = (Docteur) object;
        if ((this.iddocteur == null && other.iddocteur != null) || (this.iddocteur != null && !this.iddocteur.equals(other.iddocteur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Docteur[ iddocteur=" + iddocteur + " ]";
    }
    
}
