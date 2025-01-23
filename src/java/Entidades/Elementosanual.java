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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yunis
 */
@Entity
@Table(name = "elementosanual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Elementosanual.findAll", query = "SELECT e FROM Elementosanual e"),
    @NamedQuery(name = "Elementosanual.findById", query = "SELECT e FROM Elementosanual e WHERE e.id = :id"),
    @NamedQuery(name = "Elementosanual.findByNombreCoordinador", query = "SELECT e FROM Elementosanual e WHERE e.nombreCoordinador = :nombreCoordinador"),
    @NamedQuery(name = "Elementosanual.findByCentro", query = "SELECT e FROM Elementosanual e WHERE e.centro = :centro"),
    @NamedQuery(name = "Elementosanual.findByAnio", query = "SELECT e FROM Elementosanual e WHERE e.anio = :anio")})
public class Elementosanual implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "elemento")
    private String elemento;
    @Basic(optional = false)
    @Column(name = "nombreCoordinador")
    private String nombreCoordinador;
    @Basic(optional = false)
    @Column(name = "centro")
    private String centro;
    @Basic(optional = false)
    @Column(name = "anio")
    private int anio;

    public Elementosanual() {
    }

    public Elementosanual(Integer id) {
        this.id = id;
    }

    public Elementosanual(Integer id, String elemento, String nombreCoordinador, String centro, int anio) {
        this.id = id;
        this.elemento = elemento;
        this.nombreCoordinador = nombreCoordinador;
        this.centro = centro;
        this.anio = anio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Elementosanual)) {
            return false;
        }
        Elementosanual other = (Elementosanual) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Elementosanual[ id=" + id + " ]";
    }
    
}
