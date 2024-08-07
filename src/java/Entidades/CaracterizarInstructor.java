/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "caracterizar_instructor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaracterizarInstructor.findAll", query = "SELECT c FROM CaracterizarInstructor c"),
    @NamedQuery(name = "CaracterizarInstructor.findByIdCaracterizarInstructor", query = "SELECT c FROM CaracterizarInstructor c WHERE c.idCaracterizarInstructor = :idCaracterizarInstructor"),
    @NamedQuery(name = "CaracterizarInstructor.findByAno", query = "SELECT c FROM CaracterizarInstructor c WHERE c.ano = :ano"),
    @NamedQuery(name = "CaracterizarInstructor.findByDescripcion", query = "SELECT c FROM CaracterizarInstructor c WHERE c.descripcion = :descripcion")})
public class CaracterizarInstructor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCaracterizar_Instructor")
    private Integer idCaracterizarInstructor;
    @Basic(optional = false)
    @Column(name = "ano")
    private int ano;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @JoinColumn(name = "Area_Idarea", referencedColumnName = "Idarea")
    @ManyToOne(optional = false)
    private Area areaIdarea;
    @JoinColumn(name = "Clima_Idclima", referencedColumnName = "Idclima")
    @ManyToOne(optional = false)
    private Clima climaIdclima;
    @JoinColumn(name = "Instructor_Idinstructor", referencedColumnName = "Idinstructor")
    @ManyToOne(optional = false)
    private Instructor instructorIdinstructor;
    @JoinColumn(name = "Sexo_Idsexo", referencedColumnName = "Idsexo")
    @ManyToOne(optional = false)
    private Sexo sexoIdsexo;

    public CaracterizarInstructor() {
    }

    public CaracterizarInstructor(Integer idCaracterizarInstructor) {
        this.idCaracterizarInstructor = idCaracterizarInstructor;
    }

    public CaracterizarInstructor(Integer idCaracterizarInstructor, int ano, String descripcion) {
        this.idCaracterizarInstructor = idCaracterizarInstructor;
        this.ano = ano;
        this.descripcion = descripcion;
    }

    public Integer getIdCaracterizarInstructor() {
        return idCaracterizarInstructor;
    }

    public void setIdCaracterizarInstructor(Integer idCaracterizarInstructor) {
        this.idCaracterizarInstructor = idCaracterizarInstructor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Area getAreaIdarea() {
        return areaIdarea;
    }

    public void setAreaIdarea(Area areaIdarea) {
        this.areaIdarea = areaIdarea;
    }

    public Clima getClimaIdclima() {
        return climaIdclima;
    }

    public void setClimaIdclima(Clima climaIdclima) {
        this.climaIdclima = climaIdclima;
    }

    public Instructor getInstructorIdinstructor() {
        return instructorIdinstructor;
    }

    public void setInstructorIdinstructor(Instructor instructorIdinstructor) {
        this.instructorIdinstructor = instructorIdinstructor;
    }

    public Sexo getSexoIdsexo() {
        return sexoIdsexo;
    }

    public void setSexoIdsexo(Sexo sexoIdsexo) {
        this.sexoIdsexo = sexoIdsexo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCaracterizarInstructor != null ? idCaracterizarInstructor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaracterizarInstructor)) {
            return false;
        }
        CaracterizarInstructor other = (CaracterizarInstructor) object;
        if ((this.idCaracterizarInstructor == null && other.idCaracterizarInstructor != null) || (this.idCaracterizarInstructor != null && !this.idCaracterizarInstructor.equals(other.idCaracterizarInstructor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.CaracterizarInstructor[ idCaracterizarInstructor=" + idCaracterizarInstructor + " ]";
    }
    
}
