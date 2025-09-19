/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author juanm
 */
@Entity
@Table(name = "escenario")
@NamedQueries({
    @NamedQuery(name = "Escenario.findAll", query = "SELECT e FROM Escenario e"),
    @NamedQuery(name = "Escenario.findByIdEscenario", query = "SELECT e FROM Escenario e WHERE e.idEscenario = :idEscenario"),
    @NamedQuery(name = "Escenario.findByNombre", query = "SELECT e FROM Escenario e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Escenario.findByCapacidad", query = "SELECT e FROM Escenario e WHERE e.capacidad = :capacidad"),
    @NamedQuery(name = "Escenario.findByUbicacion", query = "SELECT e FROM Escenario e WHERE e.ubicacion = :ubicacion")})
public class Escenario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEscenario")
    private Integer idEscenario;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "capacidad")
    private Integer capacidad;
    @Column(name = "ubicacion")
    private String ubicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEscenario")
    private Collection<Presentacion> presentacionCollection;

    public Escenario() {
    }

    public Escenario(Integer idEscenario) {
        this.idEscenario = idEscenario;
    }

    public Escenario(Integer idEscenario, String nombre) {
        this.idEscenario = idEscenario;
        this.nombre = nombre;
    }

    public Integer getIdEscenario() {
        return idEscenario;
    }

    public void setIdEscenario(Integer idEscenario) {
        this.idEscenario = idEscenario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Collection<Presentacion> getPresentacionCollection() {
        return presentacionCollection;
    }

    public void setPresentacionCollection(Collection<Presentacion> presentacionCollection) {
        this.presentacionCollection = presentacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEscenario != null ? idEscenario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escenario)) {
            return false;
        }
        Escenario other = (Escenario) object;
        if ((this.idEscenario == null && other.idEscenario != null) || (this.idEscenario != null && !this.idEscenario.equals(other.idEscenario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
