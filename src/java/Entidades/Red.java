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
@Table(name = "red")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Red.findAll", query = "SELECT r FROM Red r"),
    @NamedQuery(name = "Red.findByIdred", query = "SELECT r FROM Red r WHERE r.idred = :idred"),
    @NamedQuery(name = "Red.findByNombre", query = "SELECT r FROM Red r WHERE r.nombre = :nombre")})
public class Red implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Idred")
    private Integer idred;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "redIdred")
    private Collection<Area> areaCollection;

    public Red() {
    }

    public Red(Integer idred) {
        this.idred = idred;
    }

    public Red(Integer idred, String nombre) {
        this.idred = idred;
        this.nombre = nombre;
    }

    public Integer getIdred() {
        return idred;
    }

    public void setIdred(Integer idred) {
        this.idred = idred;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Area> getAreaCollection() {
        return areaCollection;
    }

    public void setAreaCollection(Collection<Area> areaCollection) {
        this.areaCollection = areaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idred != null ? idred.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Red)) {
            return false;
        }
        Red other = (Red) object;
        if ((this.idred == null && other.idred != null) || (this.idred != null && !this.idred.equals(other.idred))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Red[ idred=" + idred + " ]";
    }
    
}
