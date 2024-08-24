/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

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

    @JoinColumn(name = "Centro_Idcentro", referencedColumnName = "Idcentro")
    @ManyToOne(optional = false)
    private Centro centroIdcentro;
    @JoinColumn(name = "Coordinador_Idcoordinador", referencedColumnName = "Idcoordinador")
    @ManyToOne(optional = false)
    private Coordinador coordinadorIdcoordinador;
    @JoinColumn(name = "Sexo", referencedColumnName = "Idsexo")
    @ManyToOne
    private Sexo sexo;

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
        return "Entidades.Instructor[ idinstructor=" + idinstructor + " ]";
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    
}
