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
@Table(name = "dotacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dotacion.findAll", query = "SELECT d FROM Dotacion d"),
    @NamedQuery(name = "Dotacion.findByIddotacion", query = "SELECT d FROM Dotacion d WHERE d.iddotacion = :iddotacion"),
    @NamedQuery(name = "Dotacion.findByCantidad", query = "SELECT d FROM Dotacion d WHERE d.cantidad = :cantidad")})
public class Dotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Iddotacion")
    private Integer iddotacion;
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private int cantidad;
    @JoinColumn(name = "Area_Idarea", referencedColumnName = "Idarea")
    @ManyToOne(optional = false)
    private Area areaIdarea;
    @JoinColumn(name = "Clima_Idclima", referencedColumnName = "Idclima")
    @ManyToOne(optional = false)
    private Clima climaIdclima;
    @JoinColumn(name = "Elementos_Idelemento", referencedColumnName = "Idelemento")
    @ManyToOne(optional = false)
    private Elementos elementosIdelemento;
    @JoinColumn(name = "Sexo_Idsexo", referencedColumnName = "Idsexo")
    @ManyToOne(optional = false)
    private Sexo sexoIdsexo;

    public Dotacion() {
    }

    public Dotacion(Integer iddotacion) {
        this.iddotacion = iddotacion;
    }

    public Dotacion(Integer iddotacion, int cantidad) {
        this.iddotacion = iddotacion;
        this.cantidad = cantidad;
    }

    public Integer getIddotacion() {
        return iddotacion;
    }

    public void setIddotacion(Integer iddotacion) {
        this.iddotacion = iddotacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public Elementos getElementosIdelemento() {
        return elementosIdelemento;
    }

    public void setElementosIdelemento(Elementos elementosIdelemento) {
        this.elementosIdelemento = elementosIdelemento;
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
        hash += (iddotacion != null ? iddotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dotacion)) {
            return false;
        }
        Dotacion other = (Dotacion) object;
        if ((this.iddotacion == null && other.iddotacion != null) || (this.iddotacion != null && !this.iddotacion.equals(other.iddotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Dotacion[ iddotacion=" + iddotacion + " ]";
    }
    
}
