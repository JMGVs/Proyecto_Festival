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
@Table(name = "enumeracion_status")
@NamedQueries({
    @NamedQuery(name = "EnumeracionStatus.findAll", query = "SELECT e FROM EnumeracionStatus e"),
    @NamedQuery(name = "EnumeracionStatus.findByIdStatus", query = "SELECT e FROM EnumeracionStatus e WHERE e.idStatus = :idStatus"),
    @NamedQuery(name = "EnumeracionStatus.findByDescripcion", query = "SELECT e FROM EnumeracionStatus e WHERE e.descripcion = :descripcion")})
public class EnumeracionStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idStatus")
    private Integer idStatus;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idStatus")
    private Collection<Entrada> entradaCollection;

    public EnumeracionStatus() {
    }

    public EnumeracionStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public EnumeracionStatus(Integer idStatus, String descripcion) {
        this.idStatus = idStatus;
        this.descripcion = descripcion;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<Entrada> getEntradaCollection() {
        return entradaCollection;
    }

    public void setEntradaCollection(Collection<Entrada> entradaCollection) {
        this.entradaCollection = entradaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStatus != null ? idStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EnumeracionStatus)) {
            return false;
        }
        EnumeracionStatus other = (EnumeracionStatus) object;
        if ((this.idStatus == null && other.idStatus != null) || (this.idStatus != null && !this.idStatus.equals(other.idStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.EnumeracionStatus[ idStatus=" + idStatus + " ]";
    }
    
}
