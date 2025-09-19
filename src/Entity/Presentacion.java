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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author juanm
 */
@Entity
@Table(name = "presentacion")
@NamedQueries({
    @NamedQuery(name = "Presentacion.findAll", query = "SELECT p FROM Presentacion p"),
    @NamedQuery(name = "Presentacion.findByIdPresentacion", query = "SELECT p FROM Presentacion p WHERE p.idPresentacion = :idPresentacion"),
    @NamedQuery(name = "Presentacion.findByTitulo", query = "SELECT p FROM Presentacion p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Presentacion.findByDuracion", query = "SELECT p FROM Presentacion p WHERE p.duracion = :duracion"),
    @NamedQuery(name = "Presentacion.findByCostoBase", query = "SELECT p FROM Presentacion p WHERE p.costoBase = :costoBase")})
public class Presentacion implements Serializable {

    @ManyToMany(mappedBy = "presentacionCollection")
    private Collection<Persona> personaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPresentacion")
    private Integer idPresentacion;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "duracion")
    private Integer duracion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costoBase")
    private Double costoBase;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPresentacion")
    private Collection<Entrada> entradaCollection;
    @JoinColumn(name = "idEscenario", referencedColumnName = "idEscenario")
    @ManyToOne(optional = false)
    private Escenario idEscenario;
    @JoinColumn(name = "idFestival", referencedColumnName = "idFestival")
    @ManyToOne(optional = false)
    private Festival idFestival;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "presentacion")
    private Exposicionarte exposicionarte;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "presentacion")
    private Concierto concierto;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "presentacion")
    private Obrateatro obrateatro;

    public Presentacion() {
    }

    public Presentacion(Integer idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public Presentacion(Integer idPresentacion, String titulo) {
        this.idPresentacion = idPresentacion;
        this.titulo = titulo;
    }

    public Integer getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Integer idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Double getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(Double costoBase) {
        this.costoBase = costoBase;
    }

    public Collection<Entrada> getEntradaCollection() {
        return entradaCollection;
    }

    public void setEntradaCollection(Collection<Entrada> entradaCollection) {
        this.entradaCollection = entradaCollection;
    }

    public Escenario getIdEscenario() {
        return idEscenario;
    }

    public void setIdEscenario(Escenario idEscenario) {
        this.idEscenario = idEscenario;
    }

    public Festival getIdFestival() {
        return idFestival;
    }

    public void setIdFestival(Festival idFestival) {
        this.idFestival = idFestival;
    }

    public Exposicionarte getExposicionarte() {
        return exposicionarte;
    }

    public void setExposicionarte(Exposicionarte exposicionarte) {
        this.exposicionarte = exposicionarte;
    }

    public Concierto getConcierto() {
        return concierto;
    }

    public void setConcierto(Concierto concierto) {
        this.concierto = concierto;
    }

    public Obrateatro getObrateatro() {
        return obrateatro;
    }

    public void setObrateatro(Obrateatro obrateatro) {
        this.obrateatro = obrateatro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPresentacion != null ? idPresentacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Presentacion)) {
            return false;
        }
        Presentacion other = (Presentacion) object;
        if ((this.idPresentacion == null && other.idPresentacion != null) || (this.idPresentacion != null && !this.idPresentacion.equals(other.idPresentacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Presentacion[ idPresentacion=" + idPresentacion + " ]";
    }

    public Collection<Persona> getPersonaCollection() {
        return personaCollection;
    }

    public void setPersonaCollection(Collection<Persona> personaCollection) {
        this.personaCollection = personaCollection;
    }
    
}
