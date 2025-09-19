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
@Table(name = "concierto")
@NamedQueries({
    @NamedQuery(name = "Concierto.findAll", query = "SELECT c FROM Concierto c"),
    @NamedQuery(name = "Concierto.findByIdConcierto", query = "SELECT c FROM Concierto c WHERE c.idConcierto = :idConcierto"),
    @NamedQuery(name = "Concierto.findByNumArtistas", query = "SELECT c FROM Concierto c WHERE c.numArtistas = :numArtistas"),
    @NamedQuery(name = "Concierto.findByGeneroMusical", query = "SELECT c FROM Concierto c WHERE c.generoMusical = :generoMusical")})
public class Concierto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idConcierto")
    private Integer idConcierto;
    @Column(name = "numArtistas")
    private Integer numArtistas;
    @Column(name = "generoMusical")
    private String generoMusical;
    @JoinColumn(name = "idConcierto", referencedColumnName = "idPresentacion", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Presentacion presentacion;

    public Concierto() {
    }

    public Concierto(Integer idConcierto) {
        this.idConcierto = idConcierto;
    }

    public Integer getIdConcierto() {
        return idConcierto;
    }

    public void setIdConcierto(Integer idConcierto) {
        this.idConcierto = idConcierto;
    }

    public Integer getNumArtistas() {
        return numArtistas;
    }

    public void setNumArtistas(Integer numArtistas) {
        this.numArtistas = numArtistas;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
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
        hash += (idConcierto != null ? idConcierto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Concierto)) {
            return false;
        }
        Concierto other = (Concierto) object;
        if ((this.idConcierto == null && other.idConcierto != null) || (this.idConcierto != null && !this.idConcierto.equals(other.idConcierto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Concierto[ idConcierto=" + idConcierto + " ]";
    }
    
}
