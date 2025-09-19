/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author juanm
 */
@Entity
@Table(name = "exposicionarte")
@NamedQueries({
    @NamedQuery(name = "Exposicionarte.findAll", query = "SELECT e FROM Exposicionarte e"),
    @NamedQuery(name = "Exposicionarte.findByIdExpo", query = "SELECT e FROM Exposicionarte e WHERE e.idExpo = :idExpo"),
    @NamedQuery(name = "Exposicionarte.findByDiasExposicion", query = "SELECT e FROM Exposicionarte e WHERE e.diasExposicion = :diasExposicion")})
public class Exposicionarte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idExpo")
    private Integer idExpo;
    @Column(name = "diasExposicion")
    private Integer diasExposicion;
    @JoinColumn(name = "idExpo", referencedColumnName = "idPresentacion", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Presentacion presentacion;

    public Exposicionarte() {
    }

    public Exposicionarte(Integer idExpo) {
        this.idExpo = idExpo;
    }

    public Integer getIdExpo() {
        return idExpo;
    }

    public void setIdExpo(Integer idExpo) {
        this.idExpo = idExpo;
    }

    public Integer getDiasExposicion() {
        return diasExposicion;
    }

    public void setDiasExposicion(Integer diasExposicion) {
        this.diasExposicion = diasExposicion;
    }

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExpo != null ? idExpo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exposicionarte)) {
            return false;
        }
        Exposicionarte other = (Exposicionarte) object;
        if ((this.idExpo == null && other.idExpo != null) || (this.idExpo != null && !this.idExpo.equals(other.idExpo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Exposicionarte[ idExpo=" + idExpo + " ]";
    }
    
}
