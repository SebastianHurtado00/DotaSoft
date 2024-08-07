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
@Table(name = "descuento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Descuento.findAll", query = "SELECT d FROM Descuento d"),
    @NamedQuery(name = "Descuento.findByIddescuento", query = "SELECT d FROM Descuento d WHERE d.iddescuento = :iddescuento"),
    @NamedQuery(name = "Descuento.findByCantidad", query = "SELECT d FROM Descuento d WHERE d.cantidad = :cantidad")})
public class Descuento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddescuento")
    private Integer iddescuento;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "elementos_idelemento", referencedColumnName = "Idelemento")
    @ManyToOne(optional = false)
    private Elementos elementosIdelemento;
    @JoinColumn(name = "instructor_idInstructor", referencedColumnName = "Idinstructor")
    @ManyToOne(optional = false)
    private Instructor instructoridInstructor;

    public Descuento() {
    }

    public Descuento(Integer iddescuento) {
        this.iddescuento = iddescuento;
    }

    public Integer getIddescuento() {
        return iddescuento;
    }

    public void setIddescuento(Integer iddescuento) {
        this.iddescuento = iddescuento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Elementos getElementosIdelemento() {
        return elementosIdelemento;
    }

    public void setElementosIdelemento(Elementos elementosIdelemento) {
        this.elementosIdelemento = elementosIdelemento;
    }

    public Instructor getInstructoridInstructor() {
        return instructoridInstructor;
    }

    public void setInstructoridInstructor(Instructor instructoridInstructor) {
        this.instructoridInstructor = instructoridInstructor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddescuento != null ? iddescuento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Descuento)) {
            return false;
        }
        Descuento other = (Descuento) object;
        if ((this.iddescuento == null && other.iddescuento != null) || (this.iddescuento != null && !this.iddescuento.equals(other.iddescuento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Descuento[ iddescuento=" + iddescuento + " ]";
    }
    
}
