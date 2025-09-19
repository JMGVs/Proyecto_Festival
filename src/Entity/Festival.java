/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author juanm
 */
@Entity
@Table(name = "festival")
@NamedQueries({
    @NamedQuery(name = "Festival.findAll", query = "SELECT f FROM Festival f"),
    @NamedQuery(name = "Festival.findByIdFestival", query = "SELECT f FROM Festival f WHERE f.idFestival = :idFestival"),
    @NamedQuery(name = "Festival.findByNombre", query = "SELECT f FROM Festival f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "Festival.findByFechaInicio", query = "SELECT f FROM Festival f WHERE f.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Festival.findByFechaFinal", query = "SELECT f FROM Festival f WHERE f.fechaFinal = :fechaFinal")})
public class Festival implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFestival")
    private Integer idFestival;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fechaFinal")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFestival")
    private Collection<Presentacion> presentacionCollection;

    public Festival() {
    }

    public Festival(Integer idFestival) {
        this.idFestival = idFestival;
    }

    public Festival(Integer idFestival, String nombre) {
        this.idFestival = idFestival;
        this.nombre = nombre;
    }

    public Integer getIdFestival() {
        return idFestival;
    }

    public void setIdFestival(Integer idFestival) {
        this.idFestival = idFestival;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
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
        hash += (idFestival != null ? idFestival.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Festival)) {
            return false;
        }
        Festival other = (Festival) object;
        if ((this.idFestival == null && other.idFestival != null) || (this.idFestival != null && !this.idFestival.equals(other.idFestival))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }
    
}
