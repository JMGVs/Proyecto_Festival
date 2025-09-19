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
@Table(name = "obrateatro")
@NamedQueries({
    @NamedQuery(name = "Obrateatro.findAll", query = "SELECT o FROM Obrateatro o"),
    @NamedQuery(name = "Obrateatro.findByIdObra", query = "SELECT o FROM Obrateatro o WHERE o.idObra = :idObra"),
    @NamedQuery(name = "Obrateatro.findByNumActos", query = "SELECT o FROM Obrateatro o WHERE o.numActos = :numActos")})
public class Obrateatro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idObra")
    private Integer idObra;
    @Column(name = "numActos")
    private Integer numActos;
    @JoinColumn(name = "idObra", referencedColumnName = "idPresentacion", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Presentacion presentacion;

    public Obrateatro() {
    }

    public Obrateatro(Integer idObra) {
        this.idObra = idObra;
    }

    public Integer getIdObra() {
        return idObra;
    }

    public void setIdObra(Integer idObra) {
        this.idObra = idObra;
    }

    public Integer getNumActos() {
        return numActos;
    }

    public void setNumActos(Integer numActos) {
        this.numActos = numActos;
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
        hash += (idObra != null ? idObra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obrateatro)) {
            return false;
        }
        Obrateatro other = (Obrateatro) object;
        if ((this.idObra == null && other.idObra != null) || (this.idObra != null && !this.idObra.equals(other.idObra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Obrateatro[ idObra=" + idObra + " ]";
    }
    
}
