/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "centro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Centro.findAll", query = "SELECT c FROM Centro c"),
    @NamedQuery(name = "Centro.findByIdcentro", query = "SELECT c FROM Centro c WHERE c.idcentro = :idcentro"),
    @NamedQuery(name = "Centro.findByNombre", query = "SELECT c FROM Centro c WHERE c.nombre = :nombre")})
public class Centro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Idcentro")
    private Integer idcentro;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centroIdcentro")
    private Collection<Coordinador> coordinadorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centroIdcentro")
    private Collection<Instructor> instructorCollection;
    @JoinColumn(name = "Regional_Idregional", referencedColumnName = "Idregional")
    @ManyToOne(optional = false)
    private Regional regionalIdregional;

    public Centro() {
    }

    public Centro(Integer idcentro) {
        this.idcentro = idcentro;
    }

    public Centro(Integer idcentro, String nombre) {
        this.idcentro = idcentro;
        this.nombre = nombre;
    }

    public Integer getIdcentro() {
        return idcentro;
    }

    public void setIdcentro(Integer idcentro) {
        this.idcentro = idcentro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Coordinador> getCoordinadorCollection() {
        return coordinadorCollection;
    }

    public void setCoordinadorCollection(Collection<Coordinador> coordinadorCollection) {
        this.coordinadorCollection = coordinadorCollection;
    }

    @XmlTransient
    public Collection<Instructor> getInstructorCollection() {
        return instructorCollection;
    }

    public void setInstructorCollection(Collection<Instructor> instructorCollection) {
        this.instructorCollection = instructorCollection;
    }

    public Regional getRegionalIdregional() {
        return regionalIdregional;
    }

    public void setRegionalIdregional(Regional regionalIdregional) {
        this.regionalIdregional = regionalIdregional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcentro != null ? idcentro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centro)) {
            return false;
        }
        Centro other = (Centro) object;
        if ((this.idcentro == null && other.idcentro != null) || (this.idcentro != null && !this.idcentro.equals(other.idcentro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Centro[ idcentro=" + idcentro + " ]";
    }
    
}
