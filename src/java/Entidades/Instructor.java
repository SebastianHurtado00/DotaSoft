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
@Table(name = "instructor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instructor.findAll", query = "SELECT i FROM Instructor i"),
    @NamedQuery(name = "Instructor.findByIdinstructor", query = "SELECT i FROM Instructor i WHERE i.idinstructor = :idinstructor"),
    @NamedQuery(name = "Instructor.findByNombres", query = "SELECT i FROM Instructor i WHERE i.nombres = :nombres"),
    @NamedQuery(name = "Instructor.findByApellidos", query = "SELECT i FROM Instructor i WHERE i.apellidos = :apellidos"),
    @NamedQuery(name = "Instructor.findByTelefono", query = "SELECT i FROM Instructor i WHERE i.telefono = :telefono"),
    @NamedQuery(name = "Instructor.findByCorreo", query = "SELECT i FROM Instructor i WHERE i.correo = :correo")})
public class Instructor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Idinstructor")
    private Integer idinstructor;
    @Basic(optional = false)
    @Column(name = "Nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "Apellidos")
    private String apellidos;
    @Column(name = "Telefono")
    private String telefono;
    @Column(name = "Correo")
    private String correo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructoridInstructor")
    private Collection<Descuento> descuentoCollection;
    @JoinColumn(name = "Centro_Idcentro", referencedColumnName = "Idcentro")
    @ManyToOne(optional = false)
    private Centro centroIdcentro;
    @JoinColumn(name = "Coordinador_Idcoordinador", referencedColumnName = "Idcoordinador")
    @ManyToOne(optional = false)
    private Coordinador coordinadorIdcoordinador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructorIdinstructor")
    private Collection<CaracterizarInstructor> caracterizarInstructorCollection;

    public Instructor() {
    }

    public Instructor(Integer idinstructor) {
        this.idinstructor = idinstructor;
    }

    public Instructor(Integer idinstructor, String nombres, String apellidos) {
        this.idinstructor = idinstructor;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Integer getIdinstructor() {
        return idinstructor;
    }

    public void setIdinstructor(Integer idinstructor) {
        this.idinstructor = idinstructor;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @XmlTransient
    public Collection<Descuento> getDescuentoCollection() {
        return descuentoCollection;
    }

    public void setDescuentoCollection(Collection<Descuento> descuentoCollection) {
        this.descuentoCollection = descuentoCollection;
    }

    public Centro getCentroIdcentro() {
        return centroIdcentro;
    }

    public void setCentroIdcentro(Centro centroIdcentro) {
        this.centroIdcentro = centroIdcentro;
    }

    public Coordinador getCoordinadorIdcoordinador() {
        return coordinadorIdcoordinador;
    }

    public void setCoordinadorIdcoordinador(Coordinador coordinadorIdcoordinador) {
        this.coordinadorIdcoordinador = coordinadorIdcoordinador;
    }

    @XmlTransient
    public Collection<CaracterizarInstructor> getCaracterizarInstructorCollection() {
        return caracterizarInstructorCollection;
    }

    public void setCaracterizarInstructorCollection(Collection<CaracterizarInstructor> caracterizarInstructorCollection) {
        this.caracterizarInstructorCollection = caracterizarInstructorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinstructor != null ? idinstructor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instructor)) {
            return false;
        }
        Instructor other = (Instructor) object;
        if ((this.idinstructor == null && other.idinstructor != null) || (this.idinstructor != null && !this.idinstructor.equals(other.idinstructor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + idinstructor;
    }
    
}
