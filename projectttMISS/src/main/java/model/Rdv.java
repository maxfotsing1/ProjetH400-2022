/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "rdv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rdv.findAll", query = "SELECT r FROM Rdv r"),
    @NamedQuery(name = "Rdv.findByIdrdv", query = "SELECT r FROM Rdv r WHERE r.idrdv = :idrdv"),
    @NamedQuery(name = "Rdv.findByDaterdv", query = "SELECT r FROM Rdv r WHERE r.daterdv = :daterdv"),
    @NamedQuery(name = "Rdv.findByHeurerdv", query = "SELECT r FROM Rdv r WHERE r.heurerdv = :heurerdv"),
    @NamedQuery(name = "Rdv.findByPresence", query = "SELECT r FROM Rdv r WHERE r.presence = :presence"),
    @NamedQuery(name = "Rdv.findByPec", query = "SELECT r FROM Rdv r WHERE r.pec = :pec")})
public class Rdv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrdv")
    private Integer idrdv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "daterdv")
    @Temporal(TemporalType.DATE)
    private Date daterdv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "heurerdv")
    @Temporal(TemporalType.TIME)
    private Date heurerdv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "presence")
    private String presence;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "pec")
    private String pec;
    @JoinColumn(name = "patients", referencedColumnName = "idpatients")
    @ManyToOne(optional = false)
    private Patients patients;
    @JoinColumn(name = "docteur", referencedColumnName = "iddocteur")
    @ManyToOne(optional = false)
    private Docteur docteur;

    public Rdv() {
    }

    public Rdv(Integer idrdv) {
        this.idrdv = idrdv;
    }

    public Rdv(Integer idrdv, Date daterdv, Date heurerdv, String presence, String pec) {
        this.idrdv = idrdv;
        this.daterdv = daterdv;
        this.heurerdv = heurerdv;
        this.presence = presence;
        this.pec = pec;
    }

    public Integer getIdrdv() {
        return idrdv;
    }

    public void setIdrdv(Integer idrdv) {
        this.idrdv = idrdv;
    }

    public Date getDaterdv() {
        return daterdv;
    }

    public void setDaterdv(Date daterdv) {
        this.daterdv = daterdv;
    }

    public Date getHeurerdv() {
        return heurerdv;
    }

    public void setHeurerdv(Date heurerdv) {
        this.heurerdv = heurerdv;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public Patients getPatients() {
        return patients;
    }

    public void setPatients(Patients patients) {
        this.patients = patients;
    }

    public Docteur getDocteur() {
        return docteur;
    }

    public void setDocteur(Docteur docteur) {
        this.docteur = docteur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrdv != null ? idrdv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rdv)) {
            return false;
        }
        Rdv other = (Rdv) object;
        if ((this.idrdv == null && other.idrdv != null) || (this.idrdv != null && !this.idrdv.equals(other.idrdv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Rdv[ idrdv=" + idrdv + " ]";
    }
    
}
