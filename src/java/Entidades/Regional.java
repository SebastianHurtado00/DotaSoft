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
@Table(name = "regional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Regional.findAll", query = "SELECT r FROM Regional r"),
    @NamedQuery(name = "Regional.findByIdregional", query = "SELECT r FROM Regional r WHERE r.idregional = :idregional"),
    @NamedQuery(name = "Regional.findByNombre", query = "SELECT r FROM Regional r WHERE r.nombre = :nombre")})
public class Regional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Idregional")
    private Integer idregional;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regionalIdregional")
    private Collection<Centro> centroCollection;

    public Regional() {
    }

    public Regional(Integer idregional) {
        this.idregional = idregional;
    }

    public Regional(Integer idregional, String nombre) {
        this.idregional = idregional;
        this.nombre = nombre;
    }

    public Integer getIdregional() {
        return idregional;
    }

    public void setIdregional(Integer idregional) {
        this.idregional = idregional;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Centro> getCentroCollection() {
        return centroCollection;
    }

    public void setCentroCollection(Collection<Centro> centroCollection) {
        this.centroCollection = centroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idregional != null ? idregional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regional)) {
            return false;
        }
        Regional other = (Regional) object;
        if ((this.idregional == null && other.idregional != null) || (this.idregional != null && !this.idregional.equals(other.idregional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Regional[ idregional=" + idregional + " ]";
    }
    
}
