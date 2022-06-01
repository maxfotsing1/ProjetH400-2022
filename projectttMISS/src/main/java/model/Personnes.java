/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "personnes")
@NamedQueries({
    @NamedQuery(name = "Personnes.findAll", query = "SELECT p FROM Personnes p"),
    @NamedQuery(name = "Personnes.findByIdpersonnes", query = "SELECT p FROM Personnes p WHERE p.idpersonnes = :idpersonnes"),
    @NamedQuery(name = "Personnes.findByFirstName", query = "SELECT p FROM Personnes p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "Personnes.findByLastName", query = "SELECT p FROM Personnes p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "Personnes.findByDateofbirth", query = "SELECT p FROM Personnes p WHERE p.dateofbirth = :dateofbirth"),
    @NamedQuery(name = "Personnes.findByAdresse", query = "SELECT p FROM Personnes p WHERE p.adresse = :adresse")})
public class Personnes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpersonnes")
    private Integer idpersonnes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "firstName")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "lastName")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateofbirth")
    @Temporal(TemporalType.DATE)
    private Date dateofbirth;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "numeroRN")
    private String numeroRN;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "adresse")
    private String adresse;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "tel")
    private String tel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpersonnes")
    private Collection<Docteur> docteurCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpersonnes")
    private Collection<Patients> patientsCollection;

    public Personnes() {
    }

    public Personnes(Integer idpersonnes) {
        this.idpersonnes = idpersonnes;
    }

    public Personnes(Integer idpersonnes, String firstName, String lastName, Date dateofbirth, String numeroRN, String adresse, String tel) {
        this.idpersonnes = idpersonnes;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateofbirth = dateofbirth;
        this.numeroRN = numeroRN;
        this.adresse = adresse;
        this.tel = tel;
    }

    public Integer getIdpersonnes() {
        return idpersonnes;
    }

    public void setIdpersonnes(Integer idpersonnes) {
        this.idpersonnes = idpersonnes;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getNumeroRN() {
        return numeroRN;
    }

    public void setNumeroRN(String numeroRN) {
        this.numeroRN = numeroRN;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Collection<Docteur> getDocteurCollection() {
        return docteurCollection;
    }

    public void setDocteurCollection(Collection<Docteur> docteurCollection) {
        this.docteurCollection = docteurCollection;
    }

    public Collection<Patients> getPatientsCollection() {
        return patientsCollection;
    }

    public void setPatientsCollection(Collection<Patients> patientsCollection) {
        this.patientsCollection = patientsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpersonnes != null ? idpersonnes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personnes)) {
            return false;
        }
        Personnes other = (Personnes) object;
        if ((this.idpersonnes == null && other.idpersonnes != null) || (this.idpersonnes != null && !this.idpersonnes.equals(other.idpersonnes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Personnes[ idpersonnes=" + idpersonnes + " ]";
    }
    
}
