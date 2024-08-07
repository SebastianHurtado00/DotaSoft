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
@Table(name = "area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Area.findAll", query = "SELECT a FROM Area a"),
    @NamedQuery(name = "Area.findByIdarea", query = "SELECT a FROM Area a WHERE a.idarea = :idarea"),
    @NamedQuery(name = "Area.findByNombre", query = "SELECT a FROM Area a WHERE a.nombre = :nombre")})
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Idarea")
    private Integer idarea;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @JoinColumn(name = "Red_Idred", referencedColumnName = "Idred")
    @ManyToOne(optional = false)
    private Red redIdred;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "areaIdarea")
    private Collection<CaracterizarInstructor> caracterizarInstructorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "areaIdarea")
    private Collection<Dotacion> dotacionCollection;

    public Area() {
    }

    public Area(Integer idarea) {
        this.idarea = idarea;
    }

    public Area(Integer idarea, String nombre) {
        this.idarea = idarea;
        this.nombre = nombre;
    }

    public Integer getIdarea() {
        return idarea;
    }

    public void setIdarea(Integer idarea) {
        this.idarea = idarea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Red getRedIdred() {
        return redIdred;
    }

    public void setRedIdred(Red redIdred) {
        this.redIdred = redIdred;
    }

    @XmlTransient
    public Collection<CaracterizarInstructor> getCaracterizarInstructorCollection() {
        return caracterizarInstructorCollection;
    }

    public void setCaracterizarInstructorCollection(Collection<CaracterizarInstructor> caracterizarInstructorCollection) {
        this.caracterizarInstructorCollection = caracterizarInstructorCollection;
    }

    @XmlTransient
    public Collection<Dotacion> getDotacionCollection() {
        return dotacionCollection;
    }

    public void setDotacionCollection(Collection<Dotacion> dotacionCollection) {
        this.dotacionCollection = dotacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idarea != null ? idarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.idarea == null && other.idarea != null) || (this.idarea != null && !this.idarea.equals(other.idarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Area[ idarea=" + idarea + " ]";
    }
    
}
