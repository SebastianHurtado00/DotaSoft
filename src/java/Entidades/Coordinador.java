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
@Table(name = "coordinador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordinador.findAll", query = "SELECT c FROM Coordinador c"),
    @NamedQuery(name = "Coordinador.findByIdcoordinador", query = "SELECT c FROM Coordinador c WHERE c.idcoordinador = :idcoordinador"),
    @NamedQuery(name = "Coordinador.findByNombres", query = "SELECT c FROM Coordinador c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Coordinador.findByApellidos", query = "SELECT c FROM Coordinador c WHERE c.apellidos = :apellidos"),
    @NamedQuery(name = "Coordinador.findByCorreo", query = "SELECT c FROM Coordinador c WHERE c.correo = :correo")})
public class Coordinador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Idcoordinador")
    private Integer idcoordinador;
    @Basic(optional = false)
    @Column(name = "Nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "Apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "Correo")
    private String correo;
    @JoinColumn(name = "Centro_Idcentro", referencedColumnName = "Idcentro")
    @ManyToOne(optional = false)
    private Centro centroIdcentro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coordinadorIdcoordinador")
    private Collection<Instructor> instructorCollection;

    public Coordinador() {
    }

    public Coordinador(Integer idcoordinador) {
        this.idcoordinador = idcoordinador;
    }

    public Coordinador(Integer idcoordinador, String nombres, String apellidos, String correo) {
        this.idcoordinador = idcoordinador;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
    }

    public Integer getIdcoordinador() {
        return idcoordinador;
    }

    public void setIdcoordinador(Integer idcoordinador) {
        this.idcoordinador = idcoordinador;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Centro getCentroIdcentro() {
        return centroIdcentro;
    }

    public void setCentroIdcentro(Centro centroIdcentro) {
        this.centroIdcentro = centroIdcentro;
    }

    @XmlTransient
    public Collection<Instructor> getInstructorCollection() {
        return instructorCollection;
    }

    public void setInstructorCollection(Collection<Instructor> instructorCollection) {
        this.instructorCollection = instructorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcoordinador != null ? idcoordinador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coordinador)) {
            return false;
        }
        Coordinador other = (Coordinador) object;
        if ((this.idcoordinador == null && other.idcoordinador != null) || (this.idcoordinador != null && !this.idcoordinador.equals(other.idcoordinador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Coordinador[ idcoordinador=" + idcoordinador + " ]";
    }
    
}
